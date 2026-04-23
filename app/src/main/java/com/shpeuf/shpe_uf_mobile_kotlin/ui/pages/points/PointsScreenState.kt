package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.points

data class PointsPageState(
    val guestsCount: Int = 0,
    val eventCode: String = "",
    val showDialog: Boolean = false, // Add this
    val dialogMessage: String = ""   // Add this
)