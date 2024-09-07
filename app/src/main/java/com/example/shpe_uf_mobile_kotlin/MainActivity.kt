package com.example.shpe_uf_mobile_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.ui.custom.TextFields
import com.example.shpe_uf_mobile_kotlin.ui.navigation.Routes
import com.example.shpe_uf_mobile_kotlin.ui.pages.signIn.LoadingScreen
import com.example.shpe_uf_mobile_kotlin.ui.pages.signIn.SignIn
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SHPEUFMobileKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignIn()
                    //TextFields()
//                    val navController = rememberNavController()
//                    NavHost(navController = navController, startDestination = Routes.loading, builder= {
//                        composable(Routes.loading, ){
//                            LoadingScreen(navController)
//                        }
//                        composable(Routes.login, ){
//                            SignIn()
//                        }
//                    })
                }
            }
        }
    }
}