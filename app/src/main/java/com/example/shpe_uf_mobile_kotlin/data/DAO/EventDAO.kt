package com.example.shpe_uf_mobile_kotlin.data.DAO

import androidx.room.*
import androidx.lifecycle.LiveData
import com.example.shpe_uf_mobile_kotlin.data.models.Event

@Dao
interface EventDAO {
    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: String): LiveData<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)
}

