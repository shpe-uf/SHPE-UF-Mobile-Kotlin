package com.example.shpe_uf_mobile_kotlin.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shpe_uf_mobile_kotlin.repository.NotificationRepository
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModelFactory
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.NewHomeScreen
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegisterViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage

object NavRoute {
    const val HOME = "home"
    const val POINTS = "points"
    const val PROFILE = "profile"
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)
val items = listOf(
    BottomNavigationItem(
        title = NavRoute.HOME,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,
    ),
    BottomNavigationItem(
        title = NavRoute.POINTS,
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        hasNews = false,
        badgeCount = 45
    ),
    BottomNavigationItem(
        title = NavRoute.PROFILE,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        hasNews = true,
    ),
)

@Composable
fun NavHostContainer(navHostController: NavHostController, homeViewModelFactory: HomeViewModelFactory, viewModel: HomeViewModel) {
    NavHost(
        navController = navHostController,
        startDestination = NavRoute.HOME
    ) {
        composable(NavRoute.HOME)
        {
            val homeViewModel: HomeViewModel = viewModel(factory = homeViewModelFactory)
            NewHomeScreen(
                viewModel = viewModel
            )
        }
        composable(NavRoute.POINTS)
        {
            RegistrationPage(registerViewModel = viewModel())
        }
        composable(NavRoute.PROFILE)
        {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Profile")
            }
        }
    }
}

