package com.example.shpe_uf_mobile_kotlin.data.models

import androidx.compose.ui.graphics.Color
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel



@Entity(tableName = "events", indices =[Index(value = ["calendarID"], unique = true)])
@TypeConverters(Converters::class)
data class Event(
    // i want the primary key to be the summary string
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val calendarID: String? = null,
    val summary: String,
    val description: String?,
    val location: String?,
    val startDateTime: String?,
    val startDate: String?,
    val startTimeZone: String?,
    val endDateTime: String?,
    val endDate: String?,
    val endTimeZone: String?,
    val colorResId: Int,  // Store color as an integer
    val eventType: String,
    val notificationEnabled: Boolean = false
)

// mapper functions
fun HomeViewModel.Event.toEventEntity(): Event {
    return Event(
        calendarID = this.id,
        summary = this.summary,
        description = this.description,
        location = this.location,
        startDateTime = this.start.dateTime,
        startDate = this.start.date,
        startTimeZone = this.start.timeZone,
        endDateTime = this.end.dateTime,
        endDate = this.end.date,
        endTimeZone = this.end.timeZone,
        colorResId = this.colorResId.hashCode(),
        eventType = this.eventType.name,
        notificationEnabled = this.notificationEnabled
    )
}

fun Event.toHomeViewModelEvent(): HomeViewModel.Event {
    return HomeViewModel.Event(
        id = this.id.toString(),
        summary = this.summary,
        description = this.description,
        location = this.location,
        start = HomeViewModel.EventDateTime(
            dateTime = this.startDateTime,
            date = this.startDate,
            timeZone = this.startTimeZone
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = this.endDateTime,
            date = this.endDate,
            timeZone = this.endTimeZone
        ),
        colorResId = Color(this.colorResId),  // Convert integer back to color
        eventType = HomeViewModel.EventType.valueOf(this.eventType),
        notificationEnabled = this.notificationEnabled
    )
}

class Converters {
    @TypeConverter
    fun fromEventType(value: HomeViewModel.EventType): String {
        return value.name
    }

    @TypeConverter
    fun toEventType(value: String): HomeViewModel.EventType {
        return HomeViewModel.EventType.valueOf(value)
    }

    @TypeConverter
    fun fromColor(color: Color): Int {
        return color.hashCode()
    }

    @TypeConverter
    fun toColor(value: Int): Color {
        return Color(value)
    }
}
