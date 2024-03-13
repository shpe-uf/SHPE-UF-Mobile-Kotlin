package com.example.shpe_uf_mobile_kotlin.ui.pages.home

import java.time.LocalDate

data class HomeScreenState(
    val events: List<HomeViewModel.Event> = emptyList(),
    val currentDate: LocalDate = LocalDate.now(),
    val isEventDetailsVisible: Boolean = false,
    val selectedEvent: HomeViewModel.Event? = null,
    val isNotificationWindowVisible: Boolean = false,



)


