package com.example.shpe_uf_mobile_kotlin.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val TextColor = Color(0xFFB7B7B7)

// Event Card Colors
val GBMColor = Color(0xFF3A3A3A)
val GBMColorLight = Color(0xFF3A3A3A)
val InfoSessionColor = Color(0xFF42757C)
val InfoSessionColorLight = Color(0xFF42757C)
val WorkshopColor = Color(0xFFD25917)
val WorkshopColorLight = Color(0xFFD25917)
val SocialColor = Color(0xFF0171C1)
val SocialColorLight = Color(0xFF0171C1)
val VolunteeringColor = Color(0xFF2A5616)
val VolunteeringColorLight = Color(0xFF2A5616)

// Notification Colors
val allNotificationsOn = Color(0xFFD25917)
val allNotificationsOff = Color(0xFF933815)

// Background Colors
val blueDarkModeBackground = Color(0xFF011F35)
val headerOrange = Color(0xFFD25917)


val OrangeSHPE = Color(0xFFD25917)
val WhiteSHPE = Color(0xFFEDEDED)

val dark_bg = Color(0xFF011F35)
val light_bg = Color(0xFFEDEDED)

sealed class ThemeColors(
    val background: Color,
    val text: Color,
) {
    object Night : ThemeColors(
        background = dark_bg,
        text = Color.White
    )

    object Day : ThemeColors(
        background = light_bg,
        text = Color.Black
    )
}