package com.example.shpe_uf_mobile_kotlin.ui.navigation

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.data.AppState
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeScreen
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModelFactory
import com.example.shpe_uf_mobile_kotlin.ui.pages.points.PointsView
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegisterPage1ViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage1
import com.example.shpe_uf_mobile_kotlin.ui.pages.signIn.SignIn
import com.example.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.example.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import com.example.shpe_uf_mobile_kotlin.ui.theme.blueDarkModeBackground

object NavRoute {
    const val HOME = "home"
    const val POINTS = "points"
    const val PROFILE = "profile"
    const val LOGIN = "login"
    const val OPENING = "opening"
    const val REGISTER = "register"
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
            .fillMaxWidth()
            .drawWithContent {
                drawContent()
//                drawLine(
//                    color = Color(0x29FFFFFF),
//                    start = Offset(x = 0f, y = 0f),
//                    end = Offset(x = size.width, y = 0f),
//                    strokeWidth = 1.dp.toPx(),
//                )
            },
        backgroundColor = if(isSystemInDarkTheme()) ThemeColors.Night.navBar else ThemeColors.Day.navBar,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    val icon =
                        if (currentRoute == item.title) item.selectedIcon else item.unselectedIcon
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = item.title,
                        tint = if(currentRoute == item.title) OrangeSHPE else if(isSystemInDarkTheme()) Color.White else Color.Black,
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
fun NavHostContainer(
    navHostController: NavHostController,
    homeViewModelFactory: HomeViewModelFactory,
    mainViewModel: SHPEUFAppViewModel,
    userState: AppState,
    registerViewModel: RegisterPage1ViewModel
) {
    val homeViewModel: HomeViewModel =
        viewModel(factory = homeViewModelFactory, key = "HomeViewModel")

    LaunchedEffect(userState.isLoggedIn){
        val isLoggedIn = userState.isLoggedIn
        Log.d("NavHostContainer", "isLoggedIn: $isLoggedIn")
        navHostController.navigate(if(isLoggedIn) NavRoute.HOME else NavRoute.LOGIN)
    }

    NavHost(
        navController = navHostController,
        startDestination = NavRoute.LOGIN
    ) {
        composable(NavRoute.LOGIN) {
            SignIn(navHostController, mainViewModel)
        }
        composable(NavRoute.REGISTER){
            RegistrationPage1(registerPage1ViewModel = registerViewModel, navController = navHostController)
        }
        composable(NavRoute.HOME)
        {
            HomeScreen(
                viewModel = homeViewModel
            )
        }
        composable(NavRoute.POINTS)
        {
            PointsView(shpeufAppViewModel = mainViewModel)
        }
        composable(NavRoute.PROFILE)
        {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Profile")
            }
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(navController = rememberNavController())
}