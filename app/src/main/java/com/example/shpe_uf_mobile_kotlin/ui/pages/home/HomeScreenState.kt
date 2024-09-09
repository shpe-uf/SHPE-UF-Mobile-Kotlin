package com.example.shpe_uf_mobile_kotlin.ui.pages.home

import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.YearMonth

data class HomeScreenState(
    val events: List<HomeViewModel.Event> = emptyList(),
    val currentDate: LocalDate = LocalDate.now(),
    val monthDisplayedName : String = LocalDate.now().month.name,
    val isRefreshing: Boolean = false,
    val lastDateLoaded: LocalDate = LocalDate.now(),
    var isFetching: Boolean = false,
    var nextPage: Int = 0,

    // Window visibility
    val isEventDetailsVisible: Boolean = false,
    val selectedEvent: HomeViewModel.Event? = null,

    val isNotificationWindowVisible: Boolean = false,
    val notificationSettings: NotificationSelection = NotificationSelection(),
    val allNotificationCurrentColor: Color = Color(0xFF933815),
    // -------------------

)

data class NotificationSelection (
    var gbmNotification: Boolean = false,
    var socialNotification: Boolean = false,
    var workshopNotification: Boolean = false,
    var infoSessionNotification: Boolean = false,
    var volunteeringNotification: Boolean = false,
    var allNotificationSelection: Boolean = false
)



