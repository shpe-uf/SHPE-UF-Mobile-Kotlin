package com.example.shpe_uf_mobile_kotlin.ui.navigation

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.repository.NotificationRepository
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModelFactory
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.NewHomeScreen
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegisterViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage
import com.example.shpe_uf_mobile_kotlin.ui.theme.blueDarkModeBackground

object NavRoute {
    const val HOME = "home"
    const val POINTS = "points"
    const val PROFILE = "profile"
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)
val items = listOf(
    BottomNavigationItem(
        title = NavRoute.HOME,
        selectedIcon = R.drawable.calendar_dm_on,
        unselectedIcon = R.drawable.calendar_dm_off,
        hasNews = false,
    ),
    BottomNavigationItem(
        title = NavRoute.POINTS,
        selectedIcon = R.drawable.leaderboard_dm_on,
        unselectedIcon = R.drawable.leaderboard_dm_off,
        hasNews = false,
        badgeCount = 45
    ),
    BottomNavigationItem(
        title = NavRoute.PROFILE,
        selectedIcon = R.drawable.pfp_dm_on,
        unselectedIcon = R.drawable.pfp_dm_off,
        hasNews = true,
    ),
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = remember { items }
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomNavigation(
        modifier = Modifier
            .navigationBarsPadding()
            .border(1.dp, Color(0x29FFFFFF)),
        backgroundColor = blueDarkModeBackground,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    val icon = if (currentRoute == item.title) item.selectedIcon else item.unselectedIcon
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = item.title,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .height(35.dp)
                            .width(35.dp)
                    )
                },
                selected = currentRoute == item.title,
                onClick = {
                    navController.navigate(item.title) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}



@Composable
fun NavHostContainer(navHostController: NavHostController, homeViewModelFactory: HomeViewModelFactory) {
    NavHost(
        navController = navHostController,
        startDestination = NavRoute.HOME
    ) {
        composable(NavRoute.HOME)
        {
            val homeViewModel: HomeViewModel = viewModel(factory = homeViewModelFactory)
            NewHomeScreen(
                viewModel = homeViewModel
            )
        }
        composable(NavRoute.POINTS)
        {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Points")
            }
        }
        composable(NavRoute.PROFILE)
        {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Profile")
            }
        }
    }
}

