package com.example.shpe_uf_mobile_kotlin.data.DAO

import androidx.room.*
import com.example.shpe_uf_mobile_kotlin.data.models.Event

@Dao
interface EventDao {
    @Insert
    suspend fun insert(event: Event)

    @Query("SELECT * FROM events")
    suspend fun getAllEvents(): List<Event>
}

