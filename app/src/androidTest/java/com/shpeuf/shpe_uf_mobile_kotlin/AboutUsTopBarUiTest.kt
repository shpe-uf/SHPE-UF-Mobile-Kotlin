package com.shpeuf.shpe_uf_mobile_kotlin.guest_tests

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for the Guest About top bar composable(s).
 *
 * If you add a testTag like Modifier.testTag("guest_about_title"),
 * the tag-based assertion will be the most stable.
 */
class AboutUsTopBarUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun aboutTopBar_showsTitle_byTag_ifProvided() {
        composeRule.setContent {
            // TODO: replace with your actual composable call
            // AboutUsTopBar(isDarkMode = false)
        }

        // If you added Modifier.testTag("guest_about_title") on the title:
        composeRule.onNodeWithTag("guest_about_title")
            .assertIsDisplayed()
    }

    @Test
    fun aboutTopBar_showsTitle_byText_fallback() {
        composeRule.setContent {
            // TODO: replace with your actual composable call
            // AboutUsTopBar(isDarkMode = false)
        }

        // Fallback if you did not add test tags:
        // Replace "Who We Are" with the exact text shown in your top bar.
        composeRule.onNodeWithText("Who We Are", substring = true)
            .assertIsDisplayed()
    }
}