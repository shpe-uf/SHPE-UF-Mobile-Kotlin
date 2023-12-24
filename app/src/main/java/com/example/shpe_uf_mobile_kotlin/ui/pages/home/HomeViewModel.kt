package com.example.shpe_uf_mobile_kotlin.ui.pages.home
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

//   I need to call the google calendar API to get events
// This class will be used to handle all the logic for the home screen
// here we should be getting the google calendar events and displaying them
// this could be in a form of a list in addition to a calendar view

// variables


class HomeViewModel : ViewModel() {

    private val _selectedDateEvents = MutableLiveData<List<Event>>(listOf())
    val selectedDateEvents: LiveData<List<Event>> = _selectedDateEvents

    fun fetchEventsForDate(date: LocalDate) {
        // Fetch events for the selected date and post them to _selectedDateEvents
        // This is where you filter the _events list for the selected date
        _selectedDateEvents.value = _events.value?.filter { it.occursOnDate(date) }
    }


    private val _currentWeekDates = MutableLiveData<List<LocalDate>>()
    val currentWeekDates: LiveData<List<LocalDate>> = _currentWeekDates

    init {
        updateWeekDatesFor(LocalDate.now())  // Set initial week dates
    }

    // Call this method to update the week's dates
    fun updateWeekDatesFor(date: LocalDate) {
        _currentWeekDates.value = getDatesOfWeek(date)
    }



    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    private val _currentWeek = MutableLiveData<List<LocalDate>>()
    val currentWeek: LiveData<List<LocalDate>> = _currentWeek

    init {
        fetchCalendarEvents()
        setCurrentWeekDates(LocalDate.now())
    }

    fun setCurrentWeekDates(date: LocalDate) {
        _currentWeek.value = (0..6).map { date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).plusDays(it.toLong()) }
    }

    private fun fetchCalendarEvents() {

        // api


        val mockEvents = listOf(
            Event(
                id = "1",
                summary = "SHPE UF General Body Meeting",
                description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                location = "https://ufl.zoom.us/j/95895737986",
                start = EventDateTime(
                    dateTime = "2023-12-19T18:00:00-04:00",
                    timeZone = "America/New_York"
                ),
                end = EventDateTime(
                    dateTime = "2023-12-19T19:00:00-04:00",
                    timeZone = "America/New_York"
                ),
                colorResId = 0
            ),
            Event(
                id = "2",
                summary = "SHPE UF General Body Meeting",
                description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                location = "https://ufl.zoom.us/j/95895737986",
                start = EventDateTime(
                    dateTime = "2023-12-19T18:00:00-04:00",
                    timeZone = "America/New_York"
                ),
                end = EventDateTime(
                    dateTime = "2023-12-19T19:00:00-04:00",
                    timeZone = "America/New_York"
                ),
                colorResId = 0
            ),
            Event(
                id = "3",
                summary = "SHPE UF General Body Meeting",
                description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                location = "https://ufl.zoom.us/j/95895737986",
                start = EventDateTime(
                    dateTime = "2023-12-19T18:00:00-04:00",
                    timeZone = "America/New_York"
                ),
                end = EventDateTime(
                    dateTime = "2023-12-19T19:00:00-04:00",
                    timeZone = "America/New_York"),
                colorResId = 0
            ),
        )
        _events.value = mockEvents
    }

    fun saveEvent(event: Event) {
        // Implement the logic to save the event
        // This could involve database operations or other state changes
    };

    fun loadEvents() {
        val events = listOf(
            Event(
                id = "1",
                summary = "SHPE UF General Body Meeting",
                description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                location = "https://ufl.zoom.us/j/95895737986",
                start = EventDateTime(
                    dateTime = "2023-12-19T18:00:00-04:00",
                    timeZone = "America/New_York"
                ),
                end = EventDateTime(
                    dateTime = "2023-12-19T19:00:00-04:00",
                    timeZone = "America/New_York"
                ),
                colorResId = 0
            ),
            Event(
                id = "2",
                summary = "SHPE UF General Body Meeting",
                description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                location = "https://ufl.zoom.us/j/95895737986",
                start = EventDateTime(
                    dateTime = "2023-12-19T18:00:00-04:00",
                    timeZone = "America/New_York"
                ),
                end = EventDateTime(
                    dateTime = "2023-12-19T19:00:00-04:00",
                    timeZone = "America/New_York"
                ),
                colorResId = 0
            ),
            Event(
                id = "3",
                summary = "SHPE UF General Body Meeting",
                description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                location = "https://ufl.zoom.us/j/95895737986",
                start = EventDateTime(
                    dateTime = "2023-12-19T18:00:00-04:00",
                    timeZone = "America/New_York"
                ),
                end = EventDateTime(
                    dateTime = "2023-12-19T19:00:00-04:00",
                    timeZone = "America/New_York"),
                colorResId = 0
            ),
            )
    }

    fun navigateWeeks(isNext: Boolean) {
        val current = _currentWeek.value?.first() ?: return
        val offset = if (isNext) 7 else -7
        setCurrentWeekDates(current.plusDays(offset.toLong()))
    }


    data class Event(
        val id: String,
        val summary: String,
        val description: String?,
        val location: String?,
        val start: EventDateTime,
        val end: EventDateTime,
        val colorResId: Int,

    ) {
        fun matchesDate(date: LocalDate): Boolean {
            return this.start.toLocalDate() == date
        }

        fun occursOnDate(date: LocalDate): Boolean {
            val eventStartDate = LocalDate.parse(start.dateTime, DateTimeFormatter.ISO_DATE_TIME)
            val eventEndDate = LocalDate.parse(end.dateTime, DateTimeFormatter.ISO_DATE_TIME)
            return !date.isBefore(eventStartDate) && !date.isAfter(eventEndDate)
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

    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/calendar/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

