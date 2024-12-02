package com.example.shpe_uf_mobile_kotlin.data.DAO

import androidx.room.*
import com.example.shpe_uf_mobile_kotlin.data.models.Event

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)

    @Query("SELECT * FROM events")
    suspend fun getAllEvents(): List<Event>

    @Query("SELECT * FROM events WHERE summary = :summary LIMIT 1")
    suspend fun getEventBySummary(summary: String): Event?
}

