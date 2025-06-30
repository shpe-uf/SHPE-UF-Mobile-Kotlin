package com.example.shpe_uf_mobile_kotlin.ui.pages.guest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.sponsors.GuestTopHeader
import com.example.shpe_uf_mobile_kotlin.ui.theme.headerOrange

@Composable
fun GuestPlaceholderPage(navController: NavHostController, shpeufAppViewModel: SHPEUFAppViewModel) {
    val uiState by shpeufAppViewModel.uiState.collectAsState()
    val isDarkMode = uiState.isDarkMode

    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = if(isDarkMode) Color.Black else Color.White
    ) {
        Box{
            GuestTopHeader(navController = navController)
        }
    }
}
