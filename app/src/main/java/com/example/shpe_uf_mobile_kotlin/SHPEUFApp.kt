package com.example.shpe_uf_mobile_kotlin

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.shpe_uf_mobile_kotlin.repository.UserRepository
import com.example.shpe_uf_mobile_kotlin.util.NotificationsUtil

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_pref"
)

class SHPEUFApp : Application() {
    // Adding data store stuff.
    lateinit var userRepository: UserRepository


    override fun onCreate() {
        super.onCreate()
        userRepository = UserRepository(dataStore)
        createNotificationChannel()
        NotificationsUtil.init(this)
    }

    private fun createNotificationChannel() {
        val name = NotificationsUtil.CHANNEL_ID
        val descriptionText = "SHPE UF Events Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(NotificationsUtil.CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}