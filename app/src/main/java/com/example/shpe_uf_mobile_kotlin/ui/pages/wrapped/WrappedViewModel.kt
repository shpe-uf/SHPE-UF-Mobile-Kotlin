package com.example.shpe_uf_mobile_kotlin.ui.pages.wrapped
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.shpe_uf_mobile_kotlin.EventsQuery
import com.example.shpe_uf_mobile_kotlin.GetUserQuery // Import the GetUserQuery
import com.example.shpe_uf_mobile_kotlin.PointsQuery // Import the PointsQuery
import com.example.shpe_uf_mobile_kotlin.apolloClient
import com.example.shpe_uf_mobile_kotlin.ui.pages.points.formatDate
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.max

class WrappedViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WrappedState())
    val uiState = _uiState.asStateFlow()

    private var ticker: Job? = null
    private var clockStartMs: Long = System.currentTimeMillis()

    /** Load the storyboard (sequence of steps) and start the ticker. */
    fun loadTimeline(steps: List<WrappedStep>, startIndex: Int = 0) {
        val safeIndex = startIndex.coerceIn(0, max(0, steps.lastIndex))
        _uiState.update {
            it.copy(
                steps = steps,
                index = safeIndex,
                progress = 0f,
                isPlaying = true,
                finished = false,
                lastAdvanceAtMs = elapsed()
            )
        }
        restartTicker()
    }

    fun play() {
        if (_uiState.value.isPlaying) return
        _uiState.update { it.copy(isPlaying = true) }
        restartTicker()
    }

    fun pause() {
        if (!_uiState.value.isPlaying) return
        _uiState.update { it.copy(isPlaying = false) }
        ticker?.cancel()
    }

    /** Jump to exact fraction within the current step (0..1). */
    fun seek(fraction: Float) {
        _uiState.update { it.copy(progress = fraction.coerceIn(0f, 1f)) }
    }

    /** Called when user starts scrubbing (pause auto). */
    fun onScrubStart() {
        pause()
        _uiState.update { it.copy(isUserScrubbing = true) }
    }

    /** Called when user ends scrubbing; will settle via the caller, then resume if desired. */
    fun onScrubEnd(resume: Boolean) {
        _uiState.update { it.copy(isUserScrubbing = false, lastAdvanceAtMs = elapsed()) }
        if (resume) play()
    }

    /** Allow pager to tell us when its page changed (e.g., a fling). Resets progress & cooldown. */
    fun setPage(index: Int) {
        val s = _uiState.value
        if (index == s.index) return
        val clamped = index.coerceIn(0, max(0, s.steps.lastIndex))
        _uiState.update { it.copy(index = clamped, progress = 0f, lastAdvanceAtMs = elapsed(), finished = false) }
    }

    /** Manual next, honoring cooldown. Returns true if the advance happened. */
    fun next(): Boolean {
        val s = _uiState.value
        if (!canManuallyAdvance()) return false
        return if (s.index < s.steps.lastIndex) {
            _uiState.update { it.copy(index = s.index + 1, progress = 0f, lastAdvanceAtMs = elapsed()) }
            true
        } else {
            // completed
            _uiState.update { it.copy(progress = 1f, isPlaying = false, finished = true) }
            ticker?.cancel()
            false
        }
    }

    /** Manual prev, honoring cooldown. Returns true if the move happened. */
    fun prev(): Boolean {
        val s = _uiState.value
        if (!canManuallyAdvance()) return false
        if (s.index > 0) {
            _uiState.update { it.copy(index = s.index - 1, progress = 0f, lastAdvanceAtMs = elapsed(), finished = false) }
            return true
        }
        // Already at first; reset progress only
        _uiState.update { it.copy(progress = 0f, lastAdvanceAtMs = elapsed()) }
        return false
    }

    /** Can the user manually change step (prev/next) right now (cooldown)? */
    fun canManuallyAdvance(): Boolean {
        val s = _uiState.value
        if (s.steps.isEmpty()) return false
        val cd = s.steps[s.index].cooldownMs
        return elapsed() - s.lastAdvanceAtMs >= cd
    }

    /** Values for a segmented progress bar. Each segment ∈ [0f,1f]. */
    fun segmentProgresses(): List<Float> {
        val s = _uiState.value
        if (s.steps.isEmpty()) return emptyList()
        return List(s.steps.size) { i ->
            when {
                i < s.index -> 1f
                i == s.index -> s.progress.coerceIn(0f, 1f)
                else -> 0f
            }
        }
    }

    private fun restartTicker() {
        ticker?.cancel()
        val frameMs = 16L
        ticker = viewModelScope.launch {
            while (_uiState.value.isPlaying) {
                val s = _uiState.value
                if (s.steps.isEmpty()) {
                    delay(frameMs); continue
                }
                val duration = s.steps[s.index].durationMs.coerceAtLeast(1L)
                val p = (s.progress + frameMs.toFloat() / duration).coerceAtMost(1f)

                if (p >= 1f) {
                    // Auto-advance
                    if (s.index < s.steps.lastIndex) {
                        _uiState.update {
                            it.copy(index = s.index + 1, progress = 0f, lastAdvanceAtMs = elapsed())
                        }
                    } else {
                        _uiState.update { it.copy(progress = 1f, isPlaying = false, finished = true) }
                        break
                    }
                } else {
                    _uiState.update { it.copy(progress = p) }
                }
                delay(frameMs)
            }
        }
    }

    private fun elapsed(): Long = System.currentTimeMillis() - clockStartMs

    /**
     * Calculates the category with the most events from a given list.
     */
    private fun calculateTopCategory(events: List<EventsQuery.Event>): String {
        return events.groupBy { it.category }
            .mapValues { it.value.size }
            .maxByOrNull { it.value }?.key ?: "N/A"
    }

    /**
     * Calculates the month with the most events from a given list.
     * Assumes `createdAt` is a String in "YYYY-MM-DD" format.
     */
    private fun calculateTopMonth(events: List<EventsQuery.Event>): String {
        return events.mapNotNull { event ->
            event.createdAt.substring(5, 7).toIntOrNull()
        }
            .groupBy { it }
            .mapValues { it.value.size }
            .maxByOrNull { it.value }
            ?.key
            ?.let { monthNumber ->
                // Convert the month number to its full name
                Month.of(monthNumber).getDisplayName(
                    TextStyle.FULL,
                    Locale.getDefault())
            } ?: "N/A"
    }

    /**
     * Determines the current semester name based on the current date.
     */
    private fun getCurrentSemesterName(): String {
        return when (LocalDate.now().month) {
            in Month.JANUARY..Month.APRIL -> "Spring"
            in Month.MAY..Month.JULY -> "Summer"
            else -> "Fall"
        }
    }

    /**
     * Processes the response from PointsQuery to extract the current semester's data.
     */
    private fun processPointsData(response: ApolloResponse<PointsQuery.Data>): Triple<String, Int, Int> {
        val userData = response.data?.getUser
        val semesterName = getCurrentSemesterName()
        val points = userData?.points ?: 0
        val percentile = when (semesterName) {
            "Fall" -> userData?.fallPercentile
            "Spring" -> userData?.springPercentile
            "Summer" -> userData?.summerPercentile
            else -> 0
        } ?: 0
        return Triple(semesterName, points, percentile)
    }

    /**
     * Processes the response from GetUserQuery to extract and format the join date.
     */
    private fun processUserJoinDate(response: ApolloResponse<GetUserQuery.Data>): String {
        val createdAt = response.data?.getUser?.createdAt
        if (createdAt == null) return "N/A"

        return try {
            val zonedDateTime = ZonedDateTime.parse(createdAt.toString())
            val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())
            zonedDateTime.format(formatter)
        } catch (e: Exception) {
            "Invalid date"
        }
    }

    /**
     * Fetches all data for the Wrapped screen (events, points, user info) concurrently
     * and updates the UI state with all calculated metrics.
     */

    fun getWrappedData(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val eventsResponseDeferred = async { apolloClient.query(EventsQuery(id)).execute() }
                val pointsResponseDeferred = async { apolloClient.query(PointsQuery(id)).execute() }
                val userResponseDeferred = async { apolloClient.query(GetUserQuery(id)).execute() }

                val eventsResponse = eventsResponseDeferred.await()
                val pointsResponse = pointsResponseDeferred.await()
                val userResponse = userResponseDeferred.await()

                val events = eventsResponse.data?.getUser?.events?.filterNotNull()?.map {
                    it.copy(createdAt = formatDate(it.createdAt))
                } ?: emptyList()
                val topCategory = if (events.isNotEmpty()) calculateTopCategory(events) else "No events attended"
                val topMonth = if (events.isNotEmpty()) calculateTopMonth(events) else "No events attended"

                val (semesterName, points, percentile) = processPointsData(pointsResponse)
                val memberSince = processUserJoinDate(userResponse)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        topCategory = topCategory,
                        topMonth = topMonth,
                        semester = semesterName,
                        points = points,
                        percentile = percentile,
                        memberSince = memberSince,
                        error = null
                    )
                }

            } catch (e: Exception) {
                // Handle any network or processing errors
                _uiState.update {
                    it.copy(isLoading = false, error = "Failed to fetch wrapped data.")
                }
            }
        }
    }
}