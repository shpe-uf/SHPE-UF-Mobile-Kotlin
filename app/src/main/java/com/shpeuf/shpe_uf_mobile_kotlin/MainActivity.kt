package com.shpeuf.shpe_uf_mobile_kotlin

//import android.util.Log
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import apolloClient
//import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage1
//import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.repository.EventRepository
import com.shpeuf.shpe_uf_mobile_kotlin.repository.NotificationRepository
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.BottomNavigationBar
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.NavHostContainer
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModelFactory
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.profile.ProfileViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegisterPage1ViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.blueDarkModeBackground
import android.util.Log
import androidx.navigation.compose.currentBackStackEntryAsState
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.NavRoute

class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Get notification intent, aka the notif we pressed.
        val notificationName = intent.getStringExtra("notification_name")
        Log.d("NotificationsTest", "Main: ${notificationName.toString()}")
        setContent {
            SHPEUFMobileKotlinTheme {
                val mainViewModel = initializeViewModel()
                val UserState by mainViewModel.uiState.collectAsState()
                val registerViewModel = RegisterPage1ViewModel()
                val profileViewModel = ProfileViewModel()

//                Log.d("Logged In", UserState.isLoggedIn.toString())
//                Log.d("Logged Out", UserState.isLoggedOut.toString())
//                Log.d("User Id", UserState.id)

                val viewModelFactory = HomeViewModelFactory(
                    application, // <-- THIS is of type Application, REQUIRED as the first parameter
                    NotificationRepository(applicationContext),
                    EventRepository(applicationContext),
                    BuildConfig.GOOGLEMAPS_API_KEY // Pass API key
                )
                val navController = rememberNavController()

                val isDarkMode = UserState.isDarkMode
                val isGuest = UserState.isGuest

                val currentBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry.value?.destination?.route

                enableEdgeToEdge(
                    statusBarStyle = if(isDarkMode) SystemBarStyle.dark(
                        ThemeColors.Night.topBar.toArgb()
                    ) else {
                        SystemBarStyle.light(
                            ThemeColors.Day.topBar.toArgb(),
                            ThemeColors.Day.topBar.toArgb()
                        )
                    },
                    navigationBarStyle = if(isDarkMode) SystemBarStyle.dark(
                        ThemeColors.Night.navBar.toArgb()
                    ) else {
                        SystemBarStyle.light(
                            ThemeColors.Day.navBar.toArgb(),
                            ThemeColors.Day.navBar.toArgb()
                        )
                    }
                )

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = blueDarkModeBackground,
                ) {
                    Scaffold(
                        bottomBar = {
                            if (
                                currentRoute in listOf(
                                    NavRoute.OPENING,
                                    NavRoute.LOGIN,
                                    NavRoute.REGISTER,
                                    NavRoute.REGISTER_2,
                                    NavRoute.REGISTER_3,
                                    NavRoute.ADMIN,
                                    NavRoute.CREATE_EVENTS,
                                    NavRoute.ADMIN_LEADERBOARD
                                ) ||
                                ((!UserState.isLoggedIn && UserState.isLoggedOut) && !UserState.isGuest)
                            ) {
                                null
                            } else {
                                BottomNavigationBar(navController, isDarkMode, isGuest)
                            }
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(it),
                        ) {
                            NavHostContainer(navController, viewModelFactory, mainViewModel, UserState, registerViewModel, profileViewModel, notificationName.toString())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun initializeViewModel(shpeUFAppViewModel: SHPEUFAppViewModel = viewModel(factory = SHPEUFAppViewModel.Factory)): SHPEUFAppViewModel {
    return shpeUFAppViewModel
}