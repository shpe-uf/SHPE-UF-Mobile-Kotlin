package com.example.shpe_uf_mobile_kotlin


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegisterPage1ViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage1
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage1Preview
import com.example.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage2Preview
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "register_Page1", builder = {
                composable("register_Page1"){
                    RegistrationPage1Preview(navController = navController)
                }
                composable("register_Page2"){
                    RegistrationPage2Preview()
                }
            })



//            SHPEUFMobileKotlinTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
//            }
        }
    }
}


//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//            .fillMaxSize()
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    SHPEUFMobileKotlinTheme {
//        Greeting("Android")
//    }
//}