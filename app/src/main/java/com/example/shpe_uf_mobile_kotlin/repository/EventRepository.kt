package com.example.shpe_uf_mobile_kotlin.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.shpe_uf_mobile_kotlin.data.database.EventDBProvider
import com.example.shpe_uf_mobile_kotlin.data.models.Event

class EventRepository(context: Context) {
    private val eventDao = EventDBProvider.getInstance(context).eventDAO()

    fun getAllEvents(): LiveData<List<Event>> {
        return eventDao.getAllEvents()
    }

    fun getEventById(id: String): LiveData<Event> {
        return eventDao.getEventById(id)
    }

    suspend fun insertEvent(event: Event) {
        eventDao.insertEvent(event)
    }

    suspend fun deleteEvent(event: Event) {
        eventDao.deleteEvent(event)
    }
}