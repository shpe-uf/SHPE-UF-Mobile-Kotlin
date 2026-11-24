package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpeuf.shpe_uf_mobile_kotlin.DeleteEventMutation
import com.shpeuf.shpe_uf_mobile_kotlin.EventInfoTableQuery
import com.shpeuf.shpe_uf_mobile_kotlin.apolloClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/*
 * Data + backend calls for the Event Info page (EventInfo.kt).
 *
 * - Pulls every event with the EventInfoTable query and keeps them newest first
 * - Keeps track of which semester tab is selected
 * - Deletes events with the DeleteEvent mutation
 *
 * The screen just reads uiState and calls the functions in EventInfoViewModel.
 */

// The three semester tabs. backendValue has to match what the backend puts in Event.semester.
enum class Semester(val label: String, val backendValue: String) {
    FALL("Fall Semester", "Fall Semester"),
    SPRING("Spring Semester", "Spring Semester"),
    SUMMER("Summer Semester", "Summer Semester");

    companion object {
        // Which tab to open on: Jan-Apr is Spring, May-Jul is Summer, Aug-Dec is Fall
        fun current(): Semester = when (Calendar.getInstance().get(Calendar.MONTH)) {
            in Calendar.JANUARY..Calendar.APRIL -> SPRING
            in Calendar.MAY..Calendar.JULY -> SUMMER
            else -> FALL
        }
    }
}

// Someone who's checked in to an event
data class EventAttendee(
    val username: String,
    val firstName: String,
    val lastName: String
)

// One row in the table. It also carries the code and attendee list,
// which the Manual Input and Info popups will probably want.
data class EventInfoItem(
    val id: String,
    val name: String,
    val code: String,
    val category: String,
    val points: Int,
    val attendance: Int,
    val expiration: String,
    val request: Boolean,
    val semester: String,
    val createdAt: String,
    val users: List<EventAttendee>
) {
    // Expiration the way the website shows it, e.g. 01/21/2026 @ 04:25 PM (phone's time zone)
    val formattedExpiration: String
        get() = formatExpiration(expiration)
}

// A message the screen should show once as a toast (cleared with messageShown())
data class EventInfoMessage(val text: String, val isError: Boolean)

// Everything the Event Info screen needs to draw itself
data class EventInfoUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val events: List<EventInfoItem> = emptyList(),
    val selectedSemester: Semester = Semester.current(),
    // Id of the event that's in the middle of being deleted (null if none)
    val deletingEventId: String? = null,
    val message: EventInfoMessage? = null
) {
    // Only the events for the selected semester tab
    val visibleEvents: List<EventInfoItem>
        get() = events.filter { it.semester.equals(selectedSemester.backendValue, ignoreCase = true) }
}

class EventInfoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EventInfoUiState())
    val uiState: StateFlow<EventInfoUiState> = _uiState.asStateFlow()

    // Load the table as soon as the page opens
    init {
        loadEvents()
    }

    fun selectSemester(semester: Semester) {
        _uiState.update { it.copy(selectedSemester = semester) }
    }

    fun messageShown() {
        _uiState.update { it.copy(message = null) }
    }

    // Deletes an event, then reloads the table. Only one delete runs at a time.
    // Heads up: the backend's deleteEvent finds the event by NAME, not id,
    // so two events with the same name could be a problem.
    fun deleteEvent(event: EventInfoItem) {
        if (_uiState.value.deletingEventId != null) return
        viewModelScope.launch {
            _uiState.update { it.copy(deletingEventId = event.id) }
            try {
                val response = apolloClient.mutation(DeleteEventMutation(event.name)).execute()
                if (response.hasErrors() || response.exception != null) {
                    val message = response.errors?.firstOrNull()?.message
                        ?: response.exception?.message
                        ?: "Failed to delete event"
                    Log.e("EventInfoViewModel", "Delete error: $message")
                    _uiState.update {
                        it.copy(
                            deletingEventId = null,
                            message = EventInfoMessage("Error: $message", isError = true)
                        )
                    }
                } else {
                    Log.d("EventInfoViewModel", "Deleted event ${event.name}")
                    _uiState.update { state ->
                        state.copy(
                            deletingEventId = null,
                            events = state.events.filterNot { it.id == event.id },
                            message = EventInfoMessage("Event ${event.name} deleted", isError = false)
                        )
                    }
                    loadEvents()
                }
            } catch (e: Exception) {
                Log.e("EventInfoViewModel", "Delete exception: ${e.message}", e)
                _uiState.update {
                    it.copy(
                        deletingEventId = null,
                        message = EventInfoMessage(
                            "Error: ${e.message ?: "Failed to delete event"}",
                            isError = true
                        )
                    )
                }
            }
        }
    }

    // Fetches all events again. Also worth calling after a popup changes something
    // (like a manual input) so the attendance numbers update.
    fun loadEvents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = apolloClient.query(EventInfoTableQuery()).execute()
                val data = response.data?.getEvents
                if (data == null) {
                    val message = response.errors?.firstOrNull()?.message
                        ?: response.exception?.message
                        ?: "Failed to load events"
                    Log.e("EventInfoViewModel", "Error: $message")
                    _uiState.update { it.copy(isLoading = false, error = message) }
                    return@launch
                }

                val events = data.filterNotNull()
                    .map { event ->
                        EventInfoItem(
                            id = event.id,
                            name = event.name,
                            code = event.code,
                            category = event.category,
                            points = event.points,
                            attendance = event.attendance,
                            expiration = event.expiration,
                            request = event.request,
                            semester = event.semester,
                            createdAt = event.createdAt,
                            users = event.users.filterNotNull().map { user ->
                                EventAttendee(
                                    username = user.username,
                                    firstName = user.firstName,
                                    lastName = user.lastName
                                )
                            }
                        )
                    }
                    // Newest first like the website. createdAt is an ISO date string,
                    // so sorting it as plain text works.
                    .sortedByDescending { it.createdAt }

                _uiState.update { it.copy(isLoading = false, events = events) }
            } catch (e: Exception) {
                Log.e("EventInfoViewModel", "Exception: ${e.message}", e)
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Failed to load events")
                }
            }
        }
    }
}

// The backend sends expiration as a JavaScript date string like
// "Mon Aug 24 2026 18:09:31 GMT+0000 (Coordinated Universal Time)".
// This turns it into two lines: "08/24/2026" and "@ 02:09 PM".
// If parsing fails we just show the raw string instead of crashing.
internal fun formatExpiration(raw: String): String {
    return try {
        val trimmed = raw.substringBefore(" (").trim()
        val parser = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.US)
        val date = parser.parse(trimmed) ?: return raw
        val formatter = SimpleDateFormat("MM/dd/yyyy\n'@' hh:mm a", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
        formatter.format(date)
    } catch (e: Exception) {
        raw
    }
}
