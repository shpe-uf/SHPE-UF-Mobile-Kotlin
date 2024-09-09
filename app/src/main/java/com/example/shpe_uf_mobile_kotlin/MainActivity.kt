package com.example.shpe_uf_mobile_kotlin


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegisterPage1ViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage1Preview
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage2Preview
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage3Preview

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
import androidx.compose.ui.Modifier
import com.example.shpe_uf_mobile_kotlin.repository.EventRepository
import com.example.shpe_uf_mobile_kotlin.repository.NotificationRepository
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModelFactory
import com.example.shpe_uf_mobile_kotlin.ui.navigation.Routes
import com.example.shpe_uf_mobile_kotlin.ui.pages.points.FullView
import com.example.shpe_uf_mobile_kotlin.ui.pages.points.PointsPageViewModel
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SHPEUFMobileKotlinTheme {

                // Instance of nav controller for the whole app
                val navController = rememberNavController()

                // Viewmodel for Calender Page
                val viewModelFactory = HomeViewModelFactory(
                    NotificationRepository(applicationContext),
                    EventRepository(applicationContext)
                )

                // Viewmodel for Calender Page
                val pointsPageViewModel = PointsPageViewModel()

                // Viewmodel for Register Pages
                val registerPageViewModel = RegisterPage1ViewModel()




                FullView(pointsPageViewModel = pointsPageViewModel)








//                enableEdgeToEdge(
//                    statusBarStyle = SystemBarStyle.light(
//                        Color(0xFFD25917).toArgb(),
//                        Color(0xFFD25917).toArgb()
//                    ),
//                    navigationBarStyle = SystemBarStyle.light(
//                        blueDarkModeBackground.toArgb(),
//                        blueDarkModeBackground.toArgb()
//                    )
//                )
//
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = blueDarkModeBackground,
//                ) {
//                    Scaffold(
//                        bottomBar = { BottomNavigationBar(navController) }
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .padding(it),
//                        ) {
//                            NavHostContainer(navController, viewModelFactory)
//                        }
//                    }
//                }




//                    val navController = rememberNavController()
//                    NavHost(navController = navController, startDestination = Routes.loading, builder= {
//                        composable(Routes.loading, ){
//                            LoadingScreen(navController)
//                        }
//                        composable(Routes.login, ){
//                            SignIn()
//                        }
//                    })




//                val navController = rememberNavController()
//                val registerPageViewModel = RegisterPage1ViewModel()

                NavHost(navController = navController, startDestination = Routes.registerPage1, builder = {
                    composable(Routes.registerPage1){
                        RegistrationPage1Preview(navController, registerPageViewModel)
                    }
                    composable(Routes.registerPage2){
                        RegistrationPage2Preview(navController, registerPageViewModel)
                    }

                    composable(Routes.registerPage3){
                        RegistrationPage3Preview(navController, registerPageViewModel)
                    }


                })
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}