package com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
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
import com.shpeuf.shpe_uf_mobile_kotlin.R
import com.shpeuf.shpe_uf_mobile_kotlin.data.AppState
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.guest.GuestPlaceholderPage
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.HomeScreen
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModelFactory
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.opening.OpeningPage
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.points.PointsView
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.profile.ProfilePagePreview
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.profile.ProfileViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.profile.StaticProfilePagePreview
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegisterPage1ViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage1Preview
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage2Preview
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage3Preview
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.signIn.SignIn
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.sponsors.SponsorsPage
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.wrapped.WrappedScreen
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

val userItems = listOf(
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

//Alternative list for guest navbar
//TODO: add icons and routing for community and corporate sponsors
val guestItems = listOf(
    BottomNavigationItem(
        title = NavRoute.GUEST_PLACEHOLDER,
        selectedIcon = R.drawable.communityselected,
        unselectedIcon = R.drawable.communityicon,
        hasNews = false
    ),
    BottomNavigationItem(
        title = NavRoute.HOME,  // Calendar for guests
        selectedIcon = R.drawable.calendar_dm_on,
        unselectedIcon = R.drawable.calendar_dm_off,
        hasNews = false
    ),
    BottomNavigationItem(
        title = NavRoute.SPONSORS,
        selectedIcon = R.drawable.handshakeselected,
        unselectedIcon = R.drawable.handshakeicon,
        hasNews = false
    )
)

@Composable
fun BottomNavigationBar(navController: NavHostController, isDarkMode: Boolean, isGuest: Boolean = false) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val items = if (isGuest) guestItems else userItems // Set items based on isGuest

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
        backgroundColor = if(isDarkMode) ThemeColors.Night.navBar else ThemeColors.Day.navBar,
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
                        tint = if(currentRoute == item.title) OrangeSHPE else if(isDarkMode) Color.White else Color.Black,
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
    registerViewModel: RegisterPage1ViewModel,
    profileViewModel: ProfileViewModel,
    notificationSummary: String
) {
    val homeViewModel: HomeViewModel =
        viewModel(factory = homeViewModelFactory, key = "HomeViewModel")

    LaunchedEffect(userState.isLoggedIn) {
        val isLoggedIn = userState.isLoggedIn
        Log.d("NavHostContainer", "isLoggedIn: $isLoggedIn")
        Log.d("Logged In True", "isLoggedIn: ${isLoggedIn == true}")

        if (isLoggedIn) {
            navHostController.navigate(NavRoute.HOME) {
                popUpTo(0) { inclusive = true }  // Clears all back stack entries including login and opening pages
                launchSingleTop = true
            }
        } else {
            Log.d("Current Screen", "Current Screen: ${navHostController.currentDestination?.route}")
            navHostController.navigate(NavRoute.OPENING) {
                popUpTo(0) { inclusive = true }  // Clears all back stack entries
            }
        }
    }

    LaunchedEffect(notificationSummary, userState.isLoggedIn) {
        val event = homeViewModel.getEventBySummary(notificationSummary)
        Log.d("NotificationsTest", "Passed In, Inside: $notificationSummary")
        Log.d("NotificationsTest", "Event, Inside: $event")
        Log.d("NotificationsTest", "Event ID, Inside: ${event?.id}")
        Log.d("NotificationsTest", "Event ID, Inside: ${event?.summary}")

        if (event != null) {
            Log.d("NotificationsTest", "Event ID, Inside: $notificationSummary")
            homeViewModel.selectEvent(event)
        }

        // Quick fix for notifications
        if (userState.isLoggedIn) {
            navHostController.navigate(NavRoute.HOME)
        }
    }


    NavHost(
        navController = navHostController,
        startDestination = NavRoute.OPENING,
    ) {
        composable(NavRoute.OPENING) {
            OpeningPage(navHostController, mainViewModel)
        }
        composable(NavRoute.LOGIN) {
            SignIn(navHostController, mainViewModel)
        }
        composable(NavRoute.REGISTER){
            RegistrationPage1Preview(registerPage1ViewModel = registerViewModel, navController = navHostController)
        }
        composable(NavRoute.HOME)
        {
            HomeScreen(viewModel = homeViewModel, shpeufAppViewModel = mainViewModel, navController = navHostController)
        }
        composable(NavRoute.POINTS)
        {
            PointsView(shpeufAppViewModel = mainViewModel)
        }
        composable(NavRoute.PROFILE)
        {
            StaticProfilePagePreview(viewModel = profileViewModel, navController = navHostController, mainViewModel = mainViewModel)
        }
        composable(NavRoute.EDITPROFILE)
        {
            ProfilePagePreview(viewModel = profileViewModel, navController = navHostController, mainViewModel = mainViewModel)
        }
        composable(NavRoute.REGISTER_2)
        {
            RegistrationPage2Preview(navController = navHostController, registerPage1ViewModel = registerViewModel )
        }
        composable(NavRoute.REGISTER_3)
        {
            RegistrationPage3Preview(navController = navHostController, registerPage1ViewModel = registerViewModel )
        }
        composable(NavRoute.SPONSORS) {
            SponsorsPage(navController = navHostController, mainViewModel = mainViewModel)
        }
        composable(NavRoute.GUEST_PLACEHOLDER) {
            GuestPlaceholderPage(navController = navHostController, shpeufAppViewModel = mainViewModel
            )
        }
        composable(NavRoute.WRAPPED) {
            WrappedScreen(onExit = { navHostController.popBackStack() })
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(navController = rememberNavController(), isDarkMode = false, isGuest = true)
}