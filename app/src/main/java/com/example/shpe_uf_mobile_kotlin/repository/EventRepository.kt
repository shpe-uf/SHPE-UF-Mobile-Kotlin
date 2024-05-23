package com.example.shpe_uf_mobile_kotlin.repository

import android.content.Context
import com.example.shpe_uf_mobile_kotlin.data.DAO.EventDao
import com.example.shpe_uf_mobile_kotlin.data.database.EventDatabase
import com.example.shpe_uf_mobile_kotlin.data.models.toEventEntity
import com.example.shpe_uf_mobile_kotlin.data.models.toHomeViewModelEvent
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel


class EventRepository(context: Context) {
    private val eventDao: EventDao

    init {
        val eventDatabase = EventDatabase.getInstance(context)
        eventDao = eventDatabase.eventDAO()
    }

    suspend fun insert(event: HomeViewModel.Event) {
        eventDao.insert(event.toEventEntity())
    }

    suspend fun getALlEvents(): List <HomeViewModel.Event> {
        return eventDao.getAllEvents().map { it.toHomeViewModelEvent() }
    }

}