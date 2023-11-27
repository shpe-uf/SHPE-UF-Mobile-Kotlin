package com.example.shpe_uf_mobile_kotlin.ui.pages.home
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

//   I need to call the google calendar API to get events
// This class will be used to handle all the logic for the home screen
// here we should be getting the google calendar events and displaying them
// this could be in a form of a list in addition to a calendar view

// variables


class HomeViewModel : ViewModel() {
    // This function will be called to get the events from the google calendar API
    data class CalendarEventsResponse(
        val items: List<Event>
    )

    data class Event(
        val id: String,
        val summary: String,
        val description: String?,
        val location: String?,
        val start: EventDateTime,
        val end: EventDateTime,
    )

    data class EventDateTime(
        val dateTime: String?,
        val timeZone: String?
    )

    interface GoogleCalendarApi {
        @GET("calendars/{calendarId}/events")
        fun getCalendarEvents(
            @Path("calendarId") calendarId: String,
            @Header("Authorization") authHeader: String
        ): Call<CalendarEventsResponse>
    }

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    fun fetchCalendarEvents(calendarId: String, accessToken: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    googleCalendarApi.getCalendarEvents(calendarId, "Bearer $accessToken").execute()
                }
                if (response.isSuccessful) {
                    _events.postValue(response.body()?.items ?: listOf())
                } else {
                    // Handle errors
                }
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/calendar/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val googleCalendarApi = retrofit.create(GoogleCalendarApi::class.java)
}

