package com.example.shpe_uf_mobile_kotlin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.ui.pages.opening.OpeningPage
import com.example.shpe_uf_mobile_kotlin.ui.pages.signIn.LoadingScreen
import com.example.shpe_uf_mobile_kotlin.ui.pages.signIn.SignIn

class SHPEUFNavController {

    @Composable
    fun Navigation(isLoggedIn: Boolean){
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Routes.opening,
            builder = {
                composable(Routes.loading) {
                    LoadingScreen(navController)
                }
                composable(Routes.login) {
                    SignIn()
                }
                composable(Routes.opening){
                    OpeningPage(navController)
                }
            })
    }
}