package com.example.shpe_uf_mobile_kotlin.data.models

import androidx.compose.ui.graphics.Color
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey val id: String,
    val summary: String,
    val description: String?,
    val location: String?,
    @Embedded (prefix = "start_")
    val start: EventDateTimeEntity,
    @Embedded (prefix = "end_")
    val end: EventDateTimeEntity,
    val colorResId: Long,
    val eventType: String,
    val notificationEnabled: Boolean
)

data class EventDateTimeEntity(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int
)

