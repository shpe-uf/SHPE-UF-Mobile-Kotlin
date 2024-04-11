package com.example.shpe_uf_mobile_kotlin.repository

import android.content.Context
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.NotificationSelection

class NotificationRepository(private val context: Context) {

    // used to load notification settings
     fun loadNotificationSettings(): NotificationSelection {
         val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
         return NotificationSelection(
             gbmNotification = sharedPreferences.getBoolean(HomeViewModel.EventType.GBM.name, false),
             socialNotification = sharedPreferences.getBoolean(HomeViewModel.EventType.Social.name, false),
             workshopNotification = sharedPreferences.getBoolean(HomeViewModel.EventType.Workshop.name, false),
             infoSessionNotification = sharedPreferences.getBoolean(HomeViewModel.EventType.InfoSession.name, false),
            volunteeringNotification = sharedPreferences.getBoolean(HomeViewModel.EventType.Volunteering.name, false)
            )
     }
}