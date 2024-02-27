package com.example.shpe_uf_mobile_kotlin.ui.pages.home
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import com.example.shpe_uf_mobile_kotlin.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import com.example.shpe_uf_mobile_kotlin.ui.theme.*


//   I need to call the google calendar API to get events
// This class will be used to handle all the logic for the home screen
// here we should be getting the google calendar events and displaying them
// this could be in a form of a list in addition to a calendar view

// variables


class HomeViewModel : ViewModel() {
    // Property to store the current date
    private val _currentDate = MutableLiveData(LocalDate.now())
    val currentDate: LiveData<LocalDate> = _currentDate

    private val calendarId = BuildConfig.CALENDAR_ID
    private val apiKey = BuildConfig.REACT_APP_API_KEY


    // Event Window State
    private val _isEventDetailsVisible = mutableStateOf(false)
    val isEventDetailsVisible: State<Boolean> = _isEventDetailsVisible

    private val _selectedEvent = mutableStateOf<Event?>(null)
    val selectedEvent: State<Event?> = _selectedEvent

    fun selectEvent(event: Event?) {
        _selectedEvent.value = event
        _isEventDetailsVisible.value = true
    }

    fun hideEventDetails() {
        _isEventDetailsVisible.value = false
    }


    // Notification Window State
    private val _isNotificationWindowVisible = mutableStateOf(false)
    val isNotificationWindowVisible: State<Boolean> = _isNotificationWindowVisible

    private val _selectedNotification = mutableStateOf<Event?>(null)
    val selectedNotification: State<Event?> = _selectedNotification

    fun openNotificationWindow() {
        _isNotificationWindowVisible.value = true
    }

    fun hideNotificationWindow() {
        _isNotificationWindowVisible.value = false
    }





    init {
        // Load events when the ViewModel is created
//        Log.d("HomeViewModel", "calendarId: $calendarId")
//        Log.d("HomeViewModel", "timeMin: $timeMin")
//        Log.d("HomeViewModel", "timeMax: $timeMax")
//        Log.d("HomeViewModel", "apiKey: $apiKey")

        loadEvents()
    }

    fun nextWeek() {
        _currentDate.value = _currentDate.value?.plusWeeks(1)
    }

    fun previousWeek() {
        _currentDate.value = _currentDate.value?.minusWeeks(1)
    }

    fun nextMonth() {
        _currentDate.value = _currentDate.value?.plusMonths(1)
    }

    fun previousMonth() {
        _currentDate.value = _currentDate.value?.minusMonths(1)
    }


    private val _selectedDateEvents = MutableLiveData<List<Event>>(listOf())
    val selectedDateEvents: LiveData<List<Event>> = _selectedDateEvents

    fun fetchEventsForDate(date: LocalDate) {
        // Fetch events for the selected date and post them to _selectedDateEvents
        // This is where you filter the _events list for the selected date
        _selectedDateEvents.value = _events.value?.filter { it.occursOnDate(date) }
    }


    fun fetchEventsForWeek(date: LocalDate) {
        val zoneId = ZoneId.of("America/New_York")
        val weekStart = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val weekEnd = weekStart.plusDays(6)

        val timeMin = ZonedDateTime.of(weekStart, LocalTime.MIN, zoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val timeMax = ZonedDateTime.of(weekEnd, LocalTime.MAX, zoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

        fetchCalendarEvents(timeMin, timeMax)
    }

    fun fetchEventsForMonth(yearMonth: YearMonth) {
        val zoneId = ZoneId.of("America/New_York")
        val monthStart = yearMonth.atDay(1)
        val monthEnd = yearMonth.atEndOfMonth()

        val timeMin = ZonedDateTime.of(monthStart, LocalTime.MIN, zoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val timeMax = ZonedDateTime.of(monthEnd, LocalTime.MAX, zoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

        fetchCalendarEvents(timeMin, timeMax)
    }

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events


    private fun fetchCalendarEvents(timeMin: String, timeMax: String) {
        // Check if any of the required parameters are null or empty and log an error if they are
        if (calendarId.isEmpty() || timeMin.isEmpty() || timeMax.isEmpty() || apiKey.isEmpty()) {
            Log.d("HomeViewModel", "calendarId: $calendarId")
            Log.d("HomeViewModel", "timeMin: $timeMin")
            Log.d("HomeViewModel", "timeMax: $timeMax")
            Log.d("HomeViewModel", "apiKey: $apiKey")
            //Log.e("HomeViewModel", "Invalid API parameters")
            return
        }

        Log.d("HomeViewModel", "calendarId: $calendarId")
        Log.d("HomeViewModel", "timeMin: $timeMin")
        Log.d("HomeViewModel", "timeMax: $timeMax")
        Log.d("HomeViewModel", "apiKey: $apiKey")
        //Log.e("HomeViewModel", "Invalid API parameters")

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
                            etag = calendarEvent.etag,
                            colorResId = determinedColor
                       )
                    }
                    // Update the LiveData with the new list of events
                    withContext(Dispatchers.Main) {
                        _events.postValue(events!!)
                    }

                    // display all event information
                    for (event in events!!) {
                        Log.d("HomeViewModel", "Event: ${event.summary} ${event.start.dateTime} - ${event.end.dateTime}")
                        Log.d("HomeViewModel", "EventId: ${event.id}")
                        Log.d("HomeViewModel", "EventDescription: ${event.description}")
                        Log.d("HomeViewModel", "EventLocation: ${event.location}")
                        Log.d("HomeViewModel", "EventType: ${event.eventType}")
                        Log.d("HomeViewModel", "EventEtag: ${event.etag}")
                        Log.d("HomeViewModel", "EventColorResId: ${event.colorResId}")
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

    private fun loadEvents() {
        // Fetch events for the current date when the ViewModel is create
        fetchEventsForMonth(YearMonth.now())
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
        val etag : String? = null

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

