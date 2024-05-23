package com.example.shpe_uf_mobile_kotlin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shpe_uf_mobile_kotlin.data.models.Event
import com.example.shpe_uf_mobile_kotlin.data.DAO.EventDao

@Database(entities = [Event::class], version = 1)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDAO(): EventDao
    companion object {
        @Volatile
        private var INSTANCE: EventDatabase? = null

        fun getInstance(context: Context): EventDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "events.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}