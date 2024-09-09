package com.example.shpe_uf_mobile_kotlin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.data.SHPE_DataStore
import com.example.shpe_uf_mobile_kotlin.ui.pages.opening.OpeningPage
import com.example.shpe_uf_mobile_kotlin.ui.pages.points.FullView
import com.example.shpe_uf_mobile_kotlin.ui.pages.signIn.LoadingScreen
import com.example.shpe_uf_mobile_kotlin.ui.pages.signIn.SignIn

class SHPEUFNavController {

    @Composable
    fun Navigation(isLoggedIn: Boolean, dataStoreManager: SHPE_DataStore){
        val navController = rememberNavController()

        val dataStoreContext = LocalContext.current

        val start: String = if(isLoggedIn) Routes.login else Routes.opening

        NavHost(
            navController = navController,
            startDestination = start,
            builder = {
                composable(Routes.loading) {
                    LoadingScreen(navController)
                }
                composable(Routes.login) {
                    SignIn(navController, dataStoreManager)
                }
                composable(Routes.opening){
                    OpeningPage(navController)
                }
                composable(Routes.points){
                    FullView(navController, dataStoreManager)
                }
            })
    }
}