package com.example.shpe_uf_mobile_kotlin.ui.pages.home

import androidx.compose.ui.graphics.Color
import java.time.LocalDate

data class HomeScreenState(
    val events: List<HomeViewModel.Event> = emptyList(),
    val currentDate: LocalDate = LocalDate.now(),

    val isEventDetailsVisible: Boolean = false,
    val selectedEvent: HomeViewModel.Event? = null,

    val isNotificationWindowVisible: Boolean = false,
    val notificationSettings: NotificationSelection = NotificationSelection()


)

data class NotificationSelection (
    var gbmNotification: Boolean = false,
    var socialNotification: Boolean = false,
    var workshopNotification: Boolean = false,
    var infoSessionNotification: Boolean = false,
    var volunteeringNotification: Boolean = false
)



