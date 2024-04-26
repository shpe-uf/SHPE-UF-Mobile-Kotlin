package com.example.shpe_uf_mobile_kotlin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shpe_uf_mobile_kotlin.data.models.Event
import com.example.shpe_uf_mobile_kotlin.data.DAO.EventDAO

@Database(entities = [Event::class], version = 1, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDAO(): EventDAO
}

object EventDBProvider {
    @Volatile
    private var INSTANCE: EventDatabase? = null

    fun getInstance(context: Context): EventDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                EventDatabase::class.java,
                "events_database"
            ).fallbackToDestructiveMigration()  // Handle migrations sensibly in a real app
                .build()
            INSTANCE = instance
            instance
        }
    }
}