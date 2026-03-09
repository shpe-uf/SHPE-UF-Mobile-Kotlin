package com.shpeuf.shpe_uf_mobile_kotlin.profile_tests

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.profile.ProfileViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.profile.StaticProfileScreen
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performScrollToNode

class StaticProfilePageUiTest {

    // Set up the Compose testing environment
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun staticProfileScreen_rendersCoreCategoriesAndButtons() {
        // Initialize the ViewModels required by the screen
        val mainVm = ViewModelProvider(composeRule.activity, SHPEUFAppViewModel.Factory)[SHPEUFAppViewModel::class.java]
        val profileVm = ViewModelProvider(composeRule.activity)[ProfileViewModel::class.java]

        // Mount the UI
        composeRule.setContent {
            StaticProfileScreen(
                profileViewModel = profileVm,
                navController = rememberNavController(),
                mainViewModel = mainVm
            )
        }

        // Test Top-Level Categories (should be visible without scrolling)
        composeRule.onNodeWithText("ACCOUNT INFO").assertIsDisplayed()
        composeRule.onNodeWithText("NAME").assertIsDisplayed()
        composeRule.onNodeWithText("USERNAME").assertIsDisplayed()
        composeRule.onNodeWithText("EMAIL").assertIsDisplayed()
        // Edit button checked before scrolling bc it's at the top of the page
        composeRule.onNodeWithText("Edit Profile").assertIsDisplayed()

        // Test scrolling to elements further down the LazyColumn
        // Use performScrollTo() to ensure the test runner scrolls down to find them
        // Find the LazyColumn (bc it actually has a scroll action) and scroll to the text
        composeRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(hasText("EDUCATION INFO"))
        composeRule.onNodeWithText("EDUCATION INFO").assertIsDisplayed()

        composeRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(hasText("APPEARANCE"))
        composeRule.onNodeWithText("APPEARANCE").assertIsDisplayed()

        // Test Default Buttons (based on editable = listOf(false, true) default state)
        composeRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(hasText("Logout"))
        composeRule.onNodeWithText("Logout").assertIsDisplayed()

        composeRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(hasText("Delete Account"))
        composeRule.onNodeWithText("Delete Account").assertIsDisplayed()
    }
}