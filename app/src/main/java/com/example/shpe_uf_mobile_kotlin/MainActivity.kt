package com.example.shpe_uf_mobile_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.rememberNavController
import com.example.shpe_uf_mobile_kotlin.data.SHPE_DataStore
import com.example.shpe_uf_mobile_kotlin.data.SHPE_DataStore.Companion.ID
import com.example.shpe_uf_mobile_kotlin.data.preferenceDataStore
import com.example.shpe_uf_mobile_kotlin.ui.navigation.SHPEUFNavController
import com.example.shpe_uf_mobile_kotlin.ui.pages.points.FullView
import com.example.shpe_uf_mobile_kotlin.ui.pages.signIn.SignIn
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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
                    val dataStoreContext = LocalContext.current
                    val dataStoreManager = SHPE_DataStore(dataStoreContext)

                    AppContent(
                        this@MainActivity,
                        preferenceDataStore,
                        dataStoreManager
                    )

//                    val pointsPageViewModel = PointsPageViewModel()
//                    FullView(pointsPageViewModel = pointsPageViewModel)
//                    val navController = rememberNavController()
//                    NavHost(
//                        navController = navController,
//                        startDestination = Routes.opening,
//                        builder = {
//                            composable(Routes.loading) {
//                                LoadingScreen(navController)
//                            }
//                            composable(Routes.login) {
//                                SignIn()
//                            }
//                            composable(Routes.opening){
//                                OpeningPage(navController)
//                            }
//                        })
                    val navController = SHPEUFNavController()
                    navController.Navigation()
                }
            }
        }
    }

    @Composable
    fun AppContent(
        mainActivity: MainActivity,
        preferenceDataStore: DataStore<Preferences>,
        dataStoreManager: SHPE_DataStore) {

        var isLoggedIn by remember {
            mutableStateOf(false)
        }
        val scope = rememberCoroutineScope()
        val onLoggedInSuccess = {isLoggedIn = true}
        val onLogout = {
            isLoggedIn = false

            scope.launch{
                dataStoreManager.clearDataStore()
            }
        }

        LaunchedEffect(key1 = Unit){
            checkLoggedInState(preferenceDataStore) { it ->
                isLoggedIn = it
            }
        }

        if(isLoggedIn){
            FullView(rememberNavController(), dataStoreManager)
        } else {
            SignIn(rememberNavController(), dataStoreManager)
        }
    }
}

suspend fun checkLoggedInState(preferenceDataStore: DataStore<Preferences>, onResult: (Boolean) -> Unit)  {
    val preferences = preferenceDataStore.data.first()
    val id = preferences[ID]
    val isLoggedIn = id != null
    onResult(isLoggedIn)
}