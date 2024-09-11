package com.example.shpe_uf_mobile_kotlin

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.repository.EventRepository
import com.example.shpe_uf_mobile_kotlin.repository.NotificationRepository
import com.example.shpe_uf_mobile_kotlin.ui.navigation.BottomNavigationBar
import com.example.shpe_uf_mobile_kotlin.ui.navigation.NavHostContainer
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModelFactory
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme
import com.example.shpe_uf_mobile_kotlin.ui.theme.blueDarkModeBackground

class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            //enableEdgeToEdge()
            SHPEUFMobileKotlinTheme {
                val mainViewModel = initializeViewModel()
                val UserState by mainViewModel.uiState.collectAsState()

                val viewModelFactory = HomeViewModelFactory(
                    NotificationRepository(applicationContext),
                    EventRepository(applicationContext)
                )
                val navController = rememberNavController()

                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.light(
                        Color(0xFFD25917).toArgb(),
                        Color(0xFFD25917).toArgb()
                    ),
                    navigationBarStyle = SystemBarStyle.light(
                        blueDarkModeBackground.toArgb(),
                        blueDarkModeBackground.toArgb()
                    )
                )

//                val start = if(UserState.isLoggedIn) Routes.points else Routes.login
//
//                NavHost(
//                    navController = navController,
//                    startDestination = start,
//                ) {
//                    composable(Routes.loading){
//                        mainLoadingScreen()
//                    }
//                    composable(Routes.login) {
//                        SignIn(navController, shpeUFAppViewModel = mainViewModel)
//                    }
//                    composable(Routes.points) {
//                        FullView(shpeufAppViewModel = mainViewModel)
//                    }
//                }

                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = blueDarkModeBackground,
//                ) {
//                    Scaffold(
//                        bottomBar = { if(!UserState.isLoggedIn && UserState.isLoggedOut) null else BottomNavigationBar(navController) }
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .padding(it),
//                        ) {
//                            NavHostContainer(navController, viewModelFactory, mainViewModel, UserState)
//                        }
//                    }
//                }
            }
        }
    }
}

@Composable
fun initializeViewModel(shpeUFAppViewModel: SHPEUFAppViewModel = viewModel(factory = SHPEUFAppViewModel.Factory)): SHPEUFAppViewModel {
    return shpeUFAppViewModel
}