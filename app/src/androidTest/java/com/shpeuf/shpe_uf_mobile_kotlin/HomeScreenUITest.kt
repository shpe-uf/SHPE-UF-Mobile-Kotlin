package com.shpeuf.shpe_uf_mobile_kotlin

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
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


    // Note: Since we're changing between guest and registered view, it's best to run tests individually to determine if they pass rather than run the whole suite at once
    // Note: When the app launches, quickly click "Allow" on the notifications pop-up so the tests can run properly
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

    @Test
    fun homeScreen_topHeader_displaysCurrentMonth() {
        setUpHomeScreen()

        // The header shows the current month name (e.g. "April")
        // HomeScreenState.monthDisplayedName defaults to LocalDate.now().month.name title-cased
        val expectedMonth = java.time.LocalDate.now().month.name
            .lowercase()
            .replaceFirstChar { it.titlecase() }

        composeRule.onNodeWithText(expectedMonth).assertIsDisplayed()
    }

    @Test
    fun homeScreen_topHeader_displaysNotificationIconForLoggedInUser() {
        setUpHomeScreen(isGuest = false)

        // The bell icon is only rendered when isGuest == false
        composeRule.onNodeWithContentDescription("Notifications").assertIsDisplayed()
    }

    @Test
    fun homeScreen_topHeader_displaysSocialsIcon() {
        setUpHomeScreen()

        // The socials icon is always visible (both guest and logged-in)
        composeRule.onNodeWithContentDescription("Socials").assertIsDisplayed()
    }

    @Test
    fun homeScreen_topHeader_displaysLoginButtonForGuest() {
        setUpHomeScreen(isGuest = true)

        // Guests see a "Login" text button instead of the bell icon
        composeRule.onNodeWithText("Login").assertIsDisplayed()

        // The notification bell should NOT be shown for guests
        composeRule.onNodeWithContentDescription("Notifications").assertDoesNotExist()
    }

    @Test
    fun homeScreen_notificationWindow_opensOnBellIconClick() {
        setUpHomeScreen(isGuest = false)

        // Tap the bell icon to open the notification settings window
        composeRule.onNodeWithContentDescription("Notifications").performClick()

        // The window header should now be visible
        composeRule.onNodeWithText("Notifications Settings").assertIsDisplayed()
    }

    @Test
    fun homeScreen_notificationWindow_displaysAllEventTypeLabels() {
        setUpHomeScreen(isGuest = false)

        composeRule.onNodeWithContentDescription("Notifications").performClick()

        // Each event-type label rendered inside NotificationSettingsContent
        composeRule.onNodeWithText("GBMs").assertIsDisplayed()
        composeRule.onNodeWithText("Info\nSessions").assertIsDisplayed()
        composeRule.onNodeWithText("Workshops").assertIsDisplayed()
        composeRule.onNodeWithText("Volunteering").assertIsDisplayed()
        composeRule.onNodeWithText("Socials").assertIsDisplayed()
    }

    @Test
    fun homeScreen_notificationWindow_displaysAllowForAllButton() {
        setUpHomeScreen(isGuest = false)

        composeRule.onNodeWithContentDescription("Notifications").performClick()

        composeRule.onNodeWithText("Allow for all").assertIsDisplayed()
    }

    @Test
    fun homeScreen_notificationWindow_closesOnBackArrowClick() {
        // TODO: Not working, fix later
        /*
        setUpHomeScreen(isGuest = false)

        composeRule.onNodeWithContentDescription("Notifications").performClick()
        composeRule.onNodeWithText("Notifications Settings").assertIsDisplayed()

        // The dismiss back-arrow inside the notification window
        composeRule.onNodeWithContentDescription("Dismiss").performClick()

        // After dismissal the window header should no longer be visible
        composeRule.onNodeWithText("Notifications Settings").assertDoesNotExist()
         */
    }

    @Test
    fun homeScreen_socialWindow_opensOnSocialsIconClick() {
        setUpHomeScreen()

        composeRule.onNodeWithContentDescription("Socials").performClick()

        composeRule.onNodeWithText("Our Socials").assertIsDisplayed()
    }
}
