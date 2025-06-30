package com.example.shpe_uf_mobile_kotlin.ui.pages.sponsors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.ui.navigation.NavRoute
import com.example.shpe_uf_mobile_kotlin.ui.theme.headerOrange
import com.example.shpe_uf_mobile_kotlin.ui.theme.Viga

@Composable
fun GuestTopHeader(modifier: Modifier = Modifier, navController: NavHostController) {

    val currentDate = java.time.LocalDate.now()
    val monthName = currentDate.month.name.lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(83.dp)
            .background(color = headerOrange)
            .padding(
                top = WindowInsets.navigationBars
                    .asPaddingValues(LocalDensity.current)
                    .calculateTopPadding()
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = monthName,
            style = TextStyle(
                color = Color.White,
                fontFamily = Viga,
                fontSize = 24.sp,
                fontWeight = FontWeight(400)
            ),
            color = Color.White,
            modifier = modifier
                .weight(1f)
                .align(Alignment.Bottom)
                .padding(vertical = 15.dp, horizontal = 32.dp)
        )
        TextButton(
            onClick = { navController.navigate(NavRoute.LOGIN) },
            modifier = modifier
                .align(Alignment.Bottom)
                .offset(y = (-10).dp, x = (-18).dp)
        ) {
            Text("Login", color = Color.White)
            Icon(
                painter = painterResource(id = R.drawable.guestloginicon),
                contentDescription = "Login Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun SponsorsPage(navController: NavHostController, shpeufAppViewModel: SHPEUFAppViewModel) {
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


