package com.example.shpe_uf_mobile_kotlin.ui.pages.home
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
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
    private val apiKey = BuildConfig.API_KEY

    // update to make dynamic, better performance
    private val timeMin = "2023-09-01T00:00:00-04:00"
    private val timeMax = "2024-12-01T00:00:00-04:00"

    init {
        // Load events when the ViewModel is created
        Log.d("HomeViewModel", "calendarId: $calendarId")
        Log.d("HomeViewModel", "timeMin: $timeMin")
        Log.d("HomeViewModel", "timeMax: $timeMax")
        Log.d("HomeViewModel", "apiKey: $apiKey")

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


    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events


    private fun fetchCalendarEvents() {
        // Check if any of the required parameters are null or empty and log an error if they are
        if (calendarId.isNullOrEmpty() || timeMin.isNullOrEmpty() || timeMax.isNullOrEmpty() || apiKey.isNullOrEmpty()) {
            Log.d("HomeViewModel", "calendarId: $calendarId")
            Log.d("HomeViewModel", "timeMin: $timeMin")
            Log.d("HomeViewModel", "timeMax: $timeMax")
            Log.d("HomeViewModel", "apiKey: $apiKey")
            Log.e("HomeViewModel", "Invalid API parameters")
            return
        }

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/calendar/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an instance of the API service
        val service = retrofit.create(GoogleCalendarService::class.java)

        // Make the network call
        val call = service.getCalendarEvents(calendarId, timeMin, timeMax, apiKey)

        call.enqueue(object : Callback<CalendarEventsResponse> {
            override fun onResponse(
                call: Call<CalendarEventsResponse>,
                response: Response<CalendarEventsResponse>
            ) {
                // Log request and response details for debugging
                Log.d("HomeViewModel", "Request URL: ${call.request().url()}")
                Log.d("HomeViewModel", "Response Code: ${response.code()}")
                Log.d("HomeViewModel", "Response Body: ${response.body()}")

                // Check if the response is successful
                if (response.isSuccessful) {
                    // Parse the response body
                    val events = response.body()?.items?.map { calendarEvent ->
                        Event(
                            id = calendarEvent.id,
                            summary = calendarEvent.summary,
                            description = calendarEvent.description,
                            location = calendarEvent.location,
                            start = calendarEvent.start,
                            end = calendarEvent.end,
                            colorResId = when (calendarEvent.summary) {
                                "SHPE GBM" -> android.R.color.holo_blue_light
                                "SHPE Social" -> android.R.color.holo_green_light
                                "SHPE Study Session" -> android.R.color.holo_orange_light
                                else -> android.R.color.holo_red_light
                            }
                        )
                    }
                    // Update the LiveData with the new list of events
                    _events.postValue(events)
                } else {
                    // Log an error if the response is not successful
                    Log.e("HomeViewModel", "Failed to fetch calendar events: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<CalendarEventsResponse>, t: Throwable) {
                // Log an error if the call fails
                Log.e("HomeViewModel", "Network call failed", t)
            }
        })
    }

    fun saveEvent(event: Event) {
        // Implement the logic to save the event
        // This could involve database operations or other state changes
    }

    private fun loadEvents() {
        fetchCalendarEvents()
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
        fun getCalendarEvents(
            @Path("calendarId") calendarId: String,
            @Query("timeMin") timeMin: String,
            @Query("timeMax") timeMax: String,
            @Query("key") apiKey: String
        ): Call<CalendarEventsResponse>
    }
    data class CalendarEventsResponse(
        val items: List<Event>
    )
}

