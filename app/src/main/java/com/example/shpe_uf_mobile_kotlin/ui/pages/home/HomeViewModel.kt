package com.example.shpe_uf_mobile_kotlin.ui.pages.home
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.example.shpe_uf_mobile_kotlin.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import androidx.compose.ui.graphics.Color
import com.example.shpe_uf_mobile_kotlin.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class HomeViewModel : ViewModel() {
    // API Keys
    private val calendarId = BuildConfig.CALENDAR_ID
    private val apiKey = BuildConfig.REACT_APP_API_KEY

    // HomeViewModel State
    private val _homeUIState = MutableStateFlow(HomeScreenState())
    val homeState: StateFlow<HomeScreenState> = _homeUIState.asStateFlow()

    fun selectEvent(event: Event?) {
        _homeUIState.value = _homeUIState.value.copy(selectedEvent = event)
        _homeUIState.value = _homeUIState.value.copy(isEventDetailsVisible = true)
    }

    fun hideEventDetails() {
        _homeUIState.value = _homeUIState.value.copy(isEventDetailsVisible = false)
    }

    fun openNotificationWindow() {
        _homeUIState.value = _homeUIState.value.copy(isNotificationWindowVisible = true)
    }

    fun hideNotificationWindow() {
        _homeUIState.value = _homeUIState.value.copy(isNotificationWindowVisible = false)
    }

    init {
        loadEvents()
    }

    // Event fetching
    private fun loadEvents() {
        // Fetch events for the current date when the ViewModel is create
        fetchEventsForMonth(YearMonth.now())
    }

    fun fetchEventsForMonth(yearMonth: YearMonth) {
        val zoneId = ZoneId.of("America/New_York")
        val monthStart = yearMonth.atDay(1)
        val monthEnd = yearMonth.atEndOfMonth()

        val timeMin = ZonedDateTime.of(monthStart, LocalTime.MIN, zoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val timeMax = ZonedDateTime.of(monthEnd, LocalTime.MAX, zoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

        fetchCalendarEvents(timeMin, timeMax)
    }

    private fun fetchCalendarEvents(timeMin: String, timeMax: String) {
        // Check if any of the required parameters are null or empty and log an error if they are
        if (calendarId.isEmpty() || timeMin.isEmpty() || timeMax.isEmpty() || apiKey.isEmpty()) {
            Log.d("HomeViewModel", "calendarId: $calendarId")
            Log.d("HomeViewModel", "timeMin: $timeMin")
            Log.d("HomeViewModel", "timeMax: $timeMax")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Create a Retrofit builder object
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.googleapis.com/calendar/v3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                // Create an instance of the API service
                val service = retrofit.create(GoogleCalendarService::class.java)
                // Make the network call
                val response = service.getCalendarEvents(calendarId, timeMin, timeMax, apiKey)

                // Check if the response is successful
                if (response.isSuccessful) {
                    // Parse the response body
                    val events = response.body()?.items?.map { calendarEvent ->
                        val eventTypeDetermined = if (calendarEvent.eventType == "default") determineEventType(calendarEvent.summary) else calendarEvent.eventType
                        val determinedColor = when (eventTypeDetermined) {
                            "GBM" -> GBMColor
                            "Social" -> SocialColor
                            "Workshop" -> WorkshopColor
                            "Info Session" -> InfoSessionColor
                            "Volunteering" -> VolunteeringColor
                            else -> GBMColor
                        }

                        Event(
                            id = calendarEvent.id,
                            summary = calendarEvent.summary,
                            description = calendarEvent.description,
                            location = calendarEvent.location,
                            start = calendarEvent.start,
                            end = calendarEvent.end,
                            eventType = eventTypeDetermined,
                            colorResId = determinedColor,
                       )
                    }
                    // Update the LiveData with the new list of events
                    withContext(Dispatchers.Main) {
                        _homeUIState.value = _homeUIState.value.copy(events = events ?: emptyList())
                    }
                } else {
                    // Log an error if the response is not successful
                    Log.e("HomeViewModel", "Failed to fetch calendar events: ${response.errorBody()?.string()}")
                }
            }
            catch (e: HttpException) {
                Log.e("HomeViewModel", "HttpException in fetchCalendarEvents", e)
            }
            catch (e: Throwable) {
                Log.e("HomeViewModel", "Failure in fetchCalendarEvents", e)
            }
        }
    }

    private fun determineEventType(summary: String): String {
        return when {
            summary.contains(
                "GBM",
                ignoreCase = true
            ) -> "GBM"

            summary.contains(
                "Social",
                ignoreCase = true
            ) -> "Social"

            summary.contains(
                "Bootcamp",
                ignoreCase = true
            ) -> "Workshop"

            summary.contains(
                "Work",
                ignoreCase = true
            ) -> "Workshop"

            summary.contains(
                "Study Session",
                ignoreCase = true
            ) -> "Social"

            summary.contains(
                "Info",
                ignoreCase = true
            ) -> "Info Session"

            summary.contains(
                "Volunteer",
                ignoreCase = true
            ) -> "Volunteering"

            // Add more conditions as needed
            else -> "default" // Default eventType if no keywords found
        }
    }

    fun saveEvent(event: Event) {
        // Implement the logic to save the event
        // This could involve database operations or other state changes
    }

    data class Event(
        val id: String?,
        val summary: String,
        val description: String?,
        val location: String?,
        val start: EventDateTime,
        val end: EventDateTime,
        val colorResId: Color,
        val eventType: String,
    ) {
        fun matchesDate(date: LocalDate): Boolean {
            return this.start.toLocalDate() == date
        }

        fun occursOnDate(date: LocalDate): Boolean {
            val eventStartDate = start?.dateTime?.let { LocalDate.parse(it, DateTimeFormatter.ISO_DATE_TIME) }
            val eventEndDate = end?.dateTime?.let { LocalDate.parse(it, DateTimeFormatter.ISO_DATE_TIME) }

            return eventStartDate != null && eventEndDate != null &&
                    !date.isBefore(eventStartDate) && !date.isAfter(eventEndDate)
        }
    }

    data class EventDateTime(
        val dateTime: String,
        val timeZone: String
    ) {
        fun toLocalDate(): LocalDate? {
            return dateTime?.let {
                LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME).toLocalDate()
            }
        }
    }

    // Google API Things
    interface GoogleCalendarService {
        @GET("calendars/{calendarId}/events")
        suspend fun getCalendarEvents(
            @Path("calendarId") calendarId: String,
            @Query("timeMin") timeMin: String,
            @Query("timeMax") timeMax: String,
            @Query("key") apiKey: String,
        ): Response<CalendarEventsResponse>
    }
    data class CalendarEventsResponse(
        val items: List<Event>
    )
}

