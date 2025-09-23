package com.shpeuf.shpe_uf_mobile_kotlin.repository

import android.content.Context
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.NotificationSelection

class NotificationRepository(private val context: Context?) {

    // used to load notification settings
    fun loadNotificationSettings(): NotificationSelection {
        val sharedPreferences = context?.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        return NotificationSelection(
            gbmNotification = sharedPreferences?.getBoolean(HomeViewModel.EventType.GBM.name, false)
                ?: false, // Default to false if sharedPreferences is null
            socialNotification = sharedPreferences?.getBoolean(HomeViewModel.EventType.Social.name, false)
                ?: false, // Default to false if sharedPreferences is null
            workshopNotification = sharedPreferences?.getBoolean(HomeViewModel.EventType.Workshop.name, false)
                ?: false, // Default to false if sharedPreferences is null
            infoSessionNotification = sharedPreferences?.getBoolean(HomeViewModel.EventType.InfoSession.name, false)
                ?: false, // Default to false if sharedPreferences is null
            volunteeringNotification = sharedPreferences?.getBoolean(HomeViewModel.EventType.Volunteering.name, false)
                ?: false, // Default to false if sharedPreferences is null
            allNotificationSelection = sharedPreferences?.getBoolean("AllNotifications", false) ?: false // Default to false if sharedPreferences is null
        )
    }
}
