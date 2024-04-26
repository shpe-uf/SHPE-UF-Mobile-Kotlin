package com.example.shpe_uf_mobile_kotlin

import android.graphics.Color
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.repository.EventRepository
import com.example.shpe_uf_mobile_kotlin.repository.NotificationRepository
import com.example.shpe_uf_mobile_kotlin.ui.navigation.NavHostContainer
import com.example.shpe_uf_mobile_kotlin.ui.navigation.items
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModelFactory
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.NewHomeScreen
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme
import com.example.shpe_uf_mobile_kotlin.ui.theme.*

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SHPEUFMobileKotlinTheme {
                val viewModelFactory = HomeViewModelFactory(NotificationRepository(applicationContext), EventRepository(applicationContext))
                val homeViewModel: HomeViewModel = viewModel(factory = viewModelFactory)

                val navController = rememberNavController()
                items

                enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT))



                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigation(
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                                elevation = 8.dp,
                                modifier = Modifier.navigationBarsPadding()
                            ) {
                                items.forEach { item ->
                                    BottomNavigationItem(
                                        icon = { Icon(item.selectedIcon, contentDescription = null) },
                                        label = {
                                            Text(text = item.title)
                                        },
                                        selected = false,
                                        onClick = {
                                            navController.navigate(item.title)
                                        }
                                    )
                                }
                            }
                        }
                    ) {
                        Box (modifier = Modifier.padding(it)) {
                            NavHostContainer(navController, viewModelFactory, homeViewModel)
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