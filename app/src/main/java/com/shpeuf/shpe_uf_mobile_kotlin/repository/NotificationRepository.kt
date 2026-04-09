package com.shpeuf.shpe_uf_mobile_kotlin.repository

import android.content.Context
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.NotificationSelection

class NotificationRepository(private val context: Context?) {

    // used to load notification settings
     fun loadNotificationSettings(): NotificationSelection {
         val sharedPreferences = context?.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
         return NotificationSelection(
             gbmNotification = sharedPreferences?.getBoolean(HomeViewModel.EventType.GBM.name, true)
                 ?: true,
             socialNotification = sharedPreferences?.getBoolean(HomeViewModel.EventType.Social.name, true)
                 ?: true,
             workshopNotification = sharedPreferences?.getBoolean(HomeViewModel.EventType.Workshop.name, true)
                 ?: true,
             infoSessionNotification = sharedPreferences?.getBoolean(HomeViewModel.EventType.InfoSession.name, true)
                 ?: true,
             volunteeringNotification = sharedPreferences?.getBoolean(HomeViewModel.EventType.Volunteering.name, true)
                 ?: true,
             allNotificationSelection = sharedPreferences?.getBoolean("AllNotifications", true) ?: true
            )
     }
}
