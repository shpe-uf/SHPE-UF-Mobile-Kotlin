package com.example.shpe_uf_mobile_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.repository.EventRepository
import com.example.shpe_uf_mobile_kotlin.repository.NotificationRepository
import com.example.shpe_uf_mobile_kotlin.ui.navigation.BottomNavigationBar
import com.example.shpe_uf_mobile_kotlin.ui.navigation.NavHostContainer
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModelFactory
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme
import com.example.shpe_uf_mobile_kotlin.ui.theme.blueDarkModeBackground

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SHPEUFMobileKotlinTheme {
                val viewModelFactory = HomeViewModelFactory(NotificationRepository(applicationContext), EventRepository(applicationContext))
                val navController = rememberNavController()

                enableEdgeToEdge(statusBarStyle = SystemBarStyle.light( Color(0xFFD25917).toArgb(), Color(0xFFD25917).toArgb()),
                     navigationBarStyle = SystemBarStyle.light( blueDarkModeBackground.toArgb(), blueDarkModeBackground.toArgb()))

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = blueDarkModeBackground,
                ) {
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController) }
                    ) {
                        Box (modifier = Modifier
                            .padding(it),
                        ) {
                            NavHostContainer(navController, viewModelFactory)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SHPEUFMobileKotlinTheme {
        Greeting("Android")
    }
}