package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.wrapped
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.shpeuf.shpe_uf_mobile_kotlin.EventsQuery
import com.shpeuf.shpe_uf_mobile_kotlin.GetUserQuery // Import the GetUserQuery
import com.shpeuf.shpe_uf_mobile_kotlin.PointsQuery // Import the PointsQuery
import com.shpeuf.shpe_uf_mobile_kotlin.apolloClient
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.points.formatDate
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