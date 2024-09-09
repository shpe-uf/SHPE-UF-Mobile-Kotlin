package com.example.shpe_uf_mobile_kotlin

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.shpe_uf_mobile_kotlin.util.NotificationsUtil

class SHPEUFApp : Application() {
    override fun onCreate() {
        super.onCreate()
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