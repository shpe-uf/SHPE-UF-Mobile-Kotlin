package com.example.shpe_uf_mobile_kotlin


import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegisterPage1ViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegisterRoutes
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage1
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.repository.EventRepository
import com.example.shpe_uf_mobile_kotlin.repository.NotificationRepository
import com.example.shpe_uf_mobile_kotlin.ui.navigation.BottomNavigationBar
import com.example.shpe_uf_mobile_kotlin.ui.navigation.NavHostContainer
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.ui.navigation.Routes
import com.example.shpe_uf_mobile_kotlin.ui.pages.signIn.LoadingScreen
import com.example.shpe_uf_mobile_kotlin.ui.pages.signIn.SignIn
import com.example.shpe_uf_mobile_kotlin.ui.pages.opening.OpeningPage
import com.example.shpe_uf_mobile_kotlin.ui.pages.opening.OpeningPageViewModel
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import apolloClient
import com.example.shpe_uf_mobile_kotlin.ui.pages.points.FullView
import com.example.shpe_uf_mobile_kotlin.ui.pages.points.PointsCalendar
import com.example.shpe_uf_mobile_kotlin.ui.pages.points.PointsPageViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.points.RedeemPoints
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme
import com.example.shpe_uf_mobile_kotlin.ui.theme.blueDarkModeBackground

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SHPEUFMobileKotlinTheme {
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

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = blueDarkModeBackground,
                ) {
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController) }
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(it),
                        ) {
                            NavHostContainer(navController, viewModelFactory)
                        }
                    }
                    val pointsPageViewModel = PointsPageViewModel()
                    FullView(pointsPageViewModel = pointsPageViewModel)


//                    val navController = rememberNavController()
//                    NavHost(navController = navController, startDestination = Routes.loading, builder= {
////                        composable(Routes.loading, ){
////                            LoadingScreen(navController)
////                        }
//                        composable(Routes.login, ){
//                            SignIn()
//                        }
//                    })


//                val navController = rememberNavController()
//                val registerPageViewModel = RegisterPage1ViewModel()
//                NavHost(navController = navController, startDestination = RegisterRoutes.registerPage1, builder = {
//                    composable(RegisterRoutes.registerPage1){
//                        RegistrationPage1Preview(navController, registerPageViewModel)
//                    }
//                    composable(RegisterRoutes.registerPage2){
//                        RegistrationPage2Preview(navController, registerPageViewModel)

//                    composable(RegisterRoutes.registerPage3){
//                        RegistrationPage3Preview(navController, registerPageViewModel)
//                    }
//                })

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}