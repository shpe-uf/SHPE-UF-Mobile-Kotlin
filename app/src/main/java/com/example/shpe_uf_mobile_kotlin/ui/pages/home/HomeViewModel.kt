package com.example.shpe_uf_mobile_kotlin.ui.pages.home
import android.app.Application
import android.content.Context
import android.util.Log
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
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.shpe_uf_mobile_kotlin.ui.theme.*
import com.example.shpe_uf_mobile_kotlin.util.NotificationsUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.format.DateTimeParseException

class HomeViewModel : ViewModel() {
//class HomeViewModel(application: Application) : AndroidViewModel(application) {
    // API Keys
    private val calendarId = BuildConfig.CALENDAR_ID
    private val apiKey = BuildConfig.REACT_APP_API_KEY

    // HomeViewModel State
    private val _homeUIState = MutableStateFlow(HomeScreenState())
    val homeState: StateFlow<HomeScreenState> = _homeUIState.asStateFlow()

    // Event Type
    enum class EventType {
        Default, GBM, Social, Workshop, InfoSession, Volunteering
    }

    // Window Visibility
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

    // Notification Settings
    fun toggleNotificationSettings(context: Context, type: EventType, isEnabled: Boolean) {
        Log.d("HomeViewModel", "Toggled notification settings for $type: $isEnabled")


        // Update events' notificationEnabled based on type and isEnabled
        val updatedEvents = homeState.value.events.map { event ->
            if (event.eventType == type) event.copy(notificationEnabled = isEnabled) else event
        }

        // Update notificationSettings in the UI state
        val updatedNotificationSettings = homeState.value.notificationSettings.copy(
            gbmNotification = if (type == EventType.GBM) isEnabled else homeState.value.notificationSettings.gbmNotification,
            infoSessionNotification = if (type == EventType.InfoSession) isEnabled else homeState.value.notificationSettings.infoSessionNotification,
            workshopNotification = if (type == EventType.Workshop) isEnabled else homeState.value.notificationSettings.workshopNotification,
            volunteeringNotification = if (type == EventType.Volunteering) isEnabled else homeState.value.notificationSettings.volunteeringNotification,
            socialNotification = if (type == EventType.Social) isEnabled else homeState.value.notificationSettings.socialNotification
        )

        // Apply updated events and notification settings to state
        _homeUIState.value = homeState.value.copy(events = updatedEvents, notificationSettings = updatedNotificationSettings)

        // Handle scheduling or canceling notifications
        handleNotificationsForEvents(context,updatedEvents, type, isEnabled)
        saveNotificationSettings(context, type, isEnabled)

    }

    private fun handleNotificationsForEvents(context: Context, events: List<Event>, type: EventType, isEnabled: Boolean) {
        val notificationsUtil =  NotificationsUtil()

        events.filter { it.eventType == type && it.notificationEnabled == isEnabled }.forEach { event ->
            if (isEnabled) {
                // Schedule notification
                notificationsUtil.scheduleNotification(context, event)
            } else {
                // Assume cancelNotification is implemented and event.id is a unique identifier
                //notificationsUtil.cancelNotification(event.id)
                // Log for debugging
                Log.d("HomeViewModel", "Canceled notification for ${event.summary}")
            }
        }
    }

    // Saving States for reboot
    private fun saveNotificationSettings(context: Context, eventType: EventType, isEnabled: Boolean){
        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean(eventType.name, isEnabled)
            apply()
        }

        // Log for debugging
        Log.d("HomeViewModel", "Saved notification settings for $eventType: $isEnabled")
    }

    fun loadNotificationSettings(context: Context) {
        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val settings = homeState.value.notificationSettings.copy(
            gbmNotification = sharedPreferences.getBoolean(EventType.GBM.name, false),
            socialNotification = sharedPreferences.getBoolean(EventType.Social.name, false),
            workshopNotification = sharedPreferences.getBoolean(EventType.Workshop.name, false),
            infoSessionNotification = sharedPreferences.getBoolean(EventType.InfoSession.name, false),
            volunteeringNotification = sharedPreferences.getBoolean(EventType.Volunteering.name, false)
        )
        _homeUIState.value = homeState.value.copy(notificationSettings = settings)
    }

    // Init Might Not Need to do this
    init {
        val currentMonth = YearMonth.now()
       // fetchEventsForMonth(currentMonth.minusMonths(1)) // Previous month
    }


    // Event fetching
    private fun loadEvents() {
        // Fetch events for the current date when the ViewModel is create
        fetchEventsForMonth(YearMonth.now().minusMonths(1))
    }

    fun getDaysInMonthArray(loadedMonths: List<YearMonth>): List<LocalDate> {
        return loadedMonths.sorted().flatMap { month ->
            (1..month.lengthOfMonth()).map { day ->
                LocalDate.of(month.year, month.monthValue, day)
            }
        }
    }


    fun fetchEventsForMonth(yearMonth: YearMonth) {
        val zoneId = ZoneId.of("America/New_York")
        val monthStart = yearMonth.atDay(1)
        val monthEnd = yearMonth.atEndOfMonth()

        Log.d("HomeViewModel", "Month Start: $monthStart")
        Log.d("HomeViewModel", "Month End: $monthEnd")

        val timeMin = ZonedDateTime.of(monthStart, LocalTime.MIN, zoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val timeMax = ZonedDateTime.of(monthEnd, LocalTime.MAX, zoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

        fetchCalendarEvents(timeMin, timeMax, yearMonth)
    }

    private fun fetchCalendarEvents(timeMin: String, timeMax: String, yearMonth: YearMonth) {
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


                    // display all the events in the log in an organized manner
                    response.body()?.items?.forEach { calendarEvent ->
                        Log.d("HomeViewModel", "CalendarEvent: $calendarEvent")
                        Log.d("HomeViewModel", "-----------------------------------")
                    }

                    // Parse the response body
                    val events = response.body()?.items?.map { calendarEvent ->
                        val eventTypeDetermined = determineEventType(calendarEvent.summary)
                        val determinedColor = when (eventTypeDetermined) {
                            EventType.GBM -> GBMColor
                            EventType.Social -> SocialColor
                            EventType.Workshop -> WorkshopColor
                            EventType.InfoSession -> InfoSessionColor
                            EventType.Volunteering -> VolunteeringColor
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

                    withContext(Dispatchers.Main) {
                        // add new events on top of old ones
                        _homeUIState.update { it.copy(events = events.orEmpty() + homeState.value.events) }
                        _homeUIState.update { it.copy(loadedMonths = it.loadedMonths + yearMonth) }

                        if (events.isNullOrEmpty()) {
                            _homeUIState.update { it.copy(monthsWithNoEvents = it.monthsWithNoEvents + yearMonth) }
                            // remove the month from the loaded months
                            _homeUIState.update { it.copy(loadedMonths = it.loadedMonths - yearMonth) }
                        }


                    }
                }
                else {
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

    private fun determineEventType(summary: String): EventType {
        return when {
            summary.contains(
                "GBM",
                ignoreCase = true
            ) -> EventType.GBM

            summary.contains(
                "Social",
                ignoreCase = true
            ) -> EventType.Social

            summary.contains(
                "Bootcamp",
                ignoreCase = true
            ) -> EventType.Workshop

            summary.contains(
                "Work",
                ignoreCase = true
            ) -> EventType.Workshop

            summary.contains(
                "Study Session",
                ignoreCase = true
            ) -> EventType.Workshop

            summary.contains(
                "Info",
                ignoreCase = true
            ) -> EventType.InfoSession

            summary.contains(
                "Volunteer",
                ignoreCase = true
            ) -> EventType.Volunteering

            // Return default event type if no keywords matched
            else -> EventType.Default
        }
    }

    fun saveEvent(event: Event) {
        // Implement the logic to save the event
        // This could involve database operations or other state changes
    }

    // update this and
    data class Event(
        val id: String?,
        val summary: String,
        val description: String?,
        val location: String?,
        val start: EventDateTime,
        val end: EventDateTime,
        val colorResId: Color,
        val eventType: EventType,
        val notificationEnabled: Boolean = false
    ) {
        fun matchesDate(date: LocalDate): Boolean {
            return this.start.toLocalDate() == date
        }

        fun occursOnDate(date: LocalDate): Boolean {
            val eventStartDate = start.toLocalDate()
            val eventEndDate = end.toLocalDate()

            val adjustedEventEndDate = when {
                // If it's an all-day event (date is not null but dateTime is), adjust the end date to be inclusive.
                eventEndDate != null && start.dateTime == null && start.date != null -> eventEndDate.minusDays(1)
                else -> eventEndDate
            }

            return (date.isEqual(eventStartDate) || (eventStartDate != null && date.isAfter(eventStartDate))) &&
                    (adjustedEventEndDate == null || date.isEqual(adjustedEventEndDate) || date.isBefore(adjustedEventEndDate))
        }
    }

    // update to handle all day events, needs to look for date in addition to dateTime
    data class EventDateTime(
        val dateTime: String?,
        val date: String?,
        val timeZone: String?
    ) {
        fun toLocalDate(): LocalDate? {
            dateTime?.let {
                return try {
                    LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME).toLocalDate()
                } catch (e: DateTimeParseException) {
                    null
                }
            }
            date?.let {
                return try {
                    LocalDate.parse(it, DateTimeFormatter.ISO_DATE)
                } catch (e: DateTimeParseException) {
                    null
                }
            }
            return null
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

