package com.shpeuf.shpe_uf_mobile_kotlin

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.repository.EventRepository
import com.shpeuf.shpe_uf_mobile_kotlin.repository.NotificationRepository
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.HomeScreen
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModelFactory
import org.junit.Rule
import org.junit.Test

class HomeScreenUITest {

    // TODO: Server-dependent elements (live event cards fetched from Google Calendar) aren't tested here.
    //  These tests cover only static UI structure and local state-driven interactions.
    //  If a test environment with server access is set up in the future, add tests that verify event cards appear after a successful fetch.

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    // Mounts HomeScreen with fresh ViewModels and a test NavController
    private fun setUpHomeScreen(isGuest: Boolean = false) {
        val mainVm = ViewModelProvider(
            composeRule.activity,
            SHPEUFAppViewModel.Factory
        )[SHPEUFAppViewModel::class.java]

        // Put the app into guest mode before mounting
        if (isGuest) {
            mainVm.saveGuest(true)
        }

        val homeVm = ViewModelProvider(
            composeRule.activity,
            HomeViewModelFactory(
                notificationRepo = NotificationRepository(composeRule.activity),
                eventRepo = EventRepository(composeRule.activity)
            )
        )[HomeViewModel::class.java]

        composeRule.setContent {
            HomeScreen(
                viewModel = homeVm,
                shpeufAppViewModel = mainVm,
                navController = rememberNavController()
            )
        }
    }
}
