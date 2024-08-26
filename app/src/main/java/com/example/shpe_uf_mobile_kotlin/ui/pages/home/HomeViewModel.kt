package com.example.shpe_uf_mobile_kotlin.ui.pages.home
import android.content.Context
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shpe_uf_mobile_kotlin.BuildConfig
import com.example.shpe_uf_mobile_kotlin.repository.EventRepository
import com.example.shpe_uf_mobile_kotlin.repository.NotificationRepository
import com.example.shpe_uf_mobile_kotlin.ui.theme.GBMColor
import com.example.shpe_uf_mobile_kotlin.ui.theme.InfoSessionColor
import com.example.shpe_uf_mobile_kotlin.ui.theme.SocialColor
import com.example.shpe_uf_mobile_kotlin.ui.theme.VolunteeringColor
import com.example.shpe_uf_mobile_kotlin.ui.theme.WorkshopColor
import com.example.shpe_uf_mobile_kotlin.ui.theme.allNotificationsOff
import com.example.shpe_uf_mobile_kotlin.ui.theme.allNotificationsOn
import com.example.shpe_uf_mobile_kotlin.util.NotificationsUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class HomeViewModel(
    private val notificationRepo: NotificationRepository,
    private val eventRepo: EventRepository
) : ViewModel() {
    // API Keys
    private val calendarId = BuildConfig.CALENDAR_ID
    private val apiKey = BuildConfig.CALENDAR_API_KEY

    // HomeViewModel State
    private val _homeUIState = MutableStateFlow(HomeScreenState())
    val homeState: StateFlow<HomeScreenState> = _homeUIState.asStateFlow()

    // Event Type
    enum class EventType {
        Default, GBM, Social, Workshop, InfoSession, Volunteering
    }

    init {
        loadNotificationsSettings()
        fetchEventsMonths(localDate = LocalDate.now(), monthsToFetch = 4)
        loadEvents()
    }

    // UI
    fun updateMonthName(month: String) {
        _homeUIState.update { it.copy(monthDisplayedName = month) }
    }

    fun pullToRefresh() {
        _homeUIState.update { it.copy(isRefreshing = true) }
        fetchEventsMonths(localDate = LocalDate.now(), monthsToFetch = 4)
        // isRefreshing will be set to false in fetchCalendarEvents, it has to, otherwise breaks.
    }

    // UI - Window Visibility
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

    // UI - Permission Handling
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        if (visiblePermissionDialogQueue.isNotEmpty()) {
            Log.d("HomeViewModel", "Removing, ${visiblePermissionDialogQueue.last()}")
            visiblePermissionDialogQueue.removeLast()
        }
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }
    // -------------------


    // Notification Settings
    fun toggleNotificationSettings(context: Context, type: EventType, isEnabled: Boolean) {
        Log.d("HomeViewModel", "Toggled notification settings for $type: $isEnabled")

        // Update events' notificationEnabled based on type and isEnabled
        val updatedEvents = homeState.value.events.map { event ->
            if (event.eventType == type) event.copy(notificationEnabled = isEnabled) else event
        }

        // Update notificationSettings in the UI state, we could optimize this by only updating the specific notification setting with a switch
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

    fun toggleAllNotifications(context: Context) {
        Log.d("HomeViewModel", "Currently all notifications are ${homeState.value.notificationSettings.allNotificationSelection}")
        Log.d ("HomeViewModel", "Toggled all notifications")




        // all starts as false
        val allNotificationOn = homeState.value.notificationSettings.allNotificationSelection

        _homeUIState.update { currentState ->
            currentState.copy(notificationSettings = currentState.notificationSettings.copy(
                gbmNotification = !allNotificationOn,
                socialNotification = !allNotificationOn,
                workshopNotification = !allNotificationOn,
                infoSessionNotification = !allNotificationOn,
                volunteeringNotification = !allNotificationOn,
                allNotificationSelection = !allNotificationOn
            ))
        }

        if (!allNotificationOn) {
            // update button color and schedule all notifications
            _homeUIState.update { currentState ->
                currentState.copy(allNotificationCurrentColor = allNotificationsOn)
            }

            // schedule all notifications
            homeState.value.events.forEach { event ->
                if (shouldScheduleNotification(event)) {
                    NotificationsUtil.scheduleNotification(event)
                }
            }


        } else {
            // update color
            _homeUIState.update { currentState ->
                currentState.copy(allNotificationCurrentColor = allNotificationsOff)
            }

            // cancel all notifications
            homeState.value.events.forEach { event ->
                if (shouldScheduleNotification(event)) {
                    NotificationsUtil.cancelNotification(event)
                }
            }
        }



        // save the settings
        saveNotificationSettings(context, EventType.GBM, !allNotificationOn)
        saveNotificationSettings(context, EventType.Social, !allNotificationOn)
        saveNotificationSettings(context, EventType.Workshop, !allNotificationOn)
        saveNotificationSettings(context, EventType.InfoSession, !allNotificationOn)
        saveNotificationSettings(context, EventType.Volunteering, !allNotificationOn)
        saveNotificationSettings(context, EventType.Default, !allNotificationOn)

        // shared preferences for all notifications color
        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("AllNotifications", !allNotificationOn)
            apply()
        }
    }

    private fun handleNotificationsForEvents(context: Context, events: List<Event>, type: EventType, isEnabled: Boolean) {
        val notificationsUtil = NotificationsUtil

        events.filter { it.eventType == type && it.notificationEnabled == isEnabled }.forEach { event ->
            if (isEnabled) {
                // Schedule notification
                notificationsUtil.scheduleNotification(event)
            } else {
                // Assume cancelNotification is implemented and event.id is a unique identifier
                //notificationsUtil.cancelNotification(event.id)
                // Log for debugging
                Log.d("HomeViewModel", "Canceled notification for ${event.summary}")
            }
        }
    }

    private fun shouldScheduleNotification(event: HomeViewModel.Event): Boolean {
        return when (event.eventType) {
            EventType.GBM -> _homeUIState.value.notificationSettings.gbmNotification
            EventType.Social -> _homeUIState.value.notificationSettings.socialNotification
            EventType.Workshop -> _homeUIState.value.notificationSettings.workshopNotification
            EventType.InfoSession -> _homeUIState.value.notificationSettings.infoSessionNotification
            EventType.Volunteering -> _homeUIState.value.notificationSettings.volunteeringNotification
            else -> false
        } || _homeUIState.value.notificationSettings.allNotificationSelection
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

    private fun loadNotificationsSettings() {
        viewModelScope.launch {
            val settings = notificationRepo.loadNotificationSettings()
            _homeUIState.update { currentState ->
                currentState.copy(notificationSettings = settings)
            }
        }
        if (homeState.value.notificationSettings.allNotificationSelection) {
            _homeUIState.update { currentState ->
                currentState.copy(allNotificationCurrentColor = allNotificationsOn)
            }
        } else {
            _homeUIState.update { currentState ->
                currentState.copy(allNotificationCurrentColor = allNotificationsOff)
            }
        }
    }


    // Event Caching
    private fun saveEventToDataBase(event: Event) {
        viewModelScope.launch {
            eventRepo.insert(event)
        }
    }

     fun eraseEvents() {
        _homeUIState.update { it.copy(events = emptyList()) }
    }

    private fun loadEvents() {
        Log.d("HomeViewModel", "Loading events")

        viewModelScope.launch {
            if (_homeUIState.value.events.isEmpty()) {
                val events = eventRepo.getALlEvents()
                Log.d("HomeViewModel", "Events fetched from database")

                _homeUIState.update { it.copy(events = events) }

                // Only fetch for the current month if the initial fetch is empty
                if (events.isEmpty()) {
                    Log.d("HomeViewModel", "Fetching events from server")
                    fetchEventsMonths(localDate = LocalDate.now(), 4) // Fetch 4 months ahead
                }
            } else {
                Log.d("HomeViewModel", "Using cached events")
            }
        }
    }

    fun fetchEventsMonths(localDate: LocalDate = LocalDate.now(), monthsToFetch: Int) {
        val zoneId = ZoneId.of("America/New_York")

          // yearMonth is time timeMin begins

         // I want to load starting from today to the end of a semester 4 months away
        val dataAtLoad = localDate
        val periodEnd = localDate.plusMonths(monthsToFetch.toLong())

        val timeMin = ZonedDateTime.of(dataAtLoad, LocalTime.MIN, zoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val timeMax = ZonedDateTime.of(periodEnd, LocalTime.MAX, zoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

        _homeUIState.update { it.copy(lastDateLoaded = periodEnd) }

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

        Log.d("HomeViewModel", "Fetching from: $timeMin to $timeMax")
        _homeUIState.update { it.copy(isRefreshing = true) }

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

                if (response.isSuccessful) {
                    // Go through all the steps to determine event details
                    val events = response.body()?.items?.map { calendarEvent ->
                        val eventTypeDetermined = determineEventType(calendarEvent.summary)
                        val determinedColor = when (eventTypeDetermined) {
                            EventType.GBM -> GBMColor
                            EventType.Social -> SocialColor
                            EventType.Workshop -> WorkshopColor
                            EventType.InfoSession -> InfoSessionColor
                            EventType.Volunteering -> VolunteeringColor
                            else -> SocialColor
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
                        _homeUIState.update { it.copy(events = events.orEmpty()) }

                        events?.forEach { event ->
                            saveEventToDataBase(event)

                            // Schedule notifications for events that should have them
                            if (shouldScheduleNotification(event)) {
                                NotificationsUtil.scheduleNotification(event)
                            }

                        }
                    }
                }
                else {
                    Log.e("HomeViewModel", "Failed to fetch calendar events: ${response.errorBody()?.string()}")
                }
            }
            catch (e: HttpException) {
                Log.e("HomeViewModel", "HttpException in fetchCalendarEvents", e)
            }
            catch (e: Throwable) {
                Log.e("HomeViewModel", "Failure in fetchCalendarEvents", e)
            }
            finally {
                // need to update refresh status, used for pull to refresh
                _homeUIState.update { it.copy(isRefreshing = false) }
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
            else -> EventType.Social
        }
    }

    // Event Data Class
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
    )

    // update to handle all day events, needs to look for date in addition to dateTime
    data class EventDateTime(
        val dateTime: String?,
        val date: String?,
        val timeZone: String?
    )

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

class HomeViewModelFactory(
    private val notificationRepo: NotificationRepository,
    private val eventRepo: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(notificationRepo, eventRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

