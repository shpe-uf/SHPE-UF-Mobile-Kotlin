package com.shpeuf.shpe_uf_mobile_kotlin.guest_tests

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for the Guest placeholder/about screen.
 *
 * Best practice: add test tags to the major screen sections:
 * - guest_placeholder_root
 * - guest_about_title
 * - guest_instagram_pager
 * - guest_instagram_item_0, guest_instagram_item_1, ...
 */
class GuestPlaceholderPageUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun guestScreen_rendersRoot_byTag_ifProvided() {
        composeRule.setContent {
            // TODO: Replace with your actual top-level guest screen composable.
            // GuestPlaceholderPage(navController = ..., viewModel = ..., isDarkMode = false)
        }

        composeRule.onNodeWithTag("guest_placeholder_root")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun guestScreen_showsAboutSection_byText_fallback() {
        composeRule.setContent {
            // TODO: Replace with your actual composable call
        }

        // Replace with any stable text you know appears on that screen
        // (title, section header, etc.)
        composeRule.onNodeWithText("About", substring = true)
            .assertExists()
    }

    @Test
    fun guestScreen_instagramPager_exists_byTag_ifProvided() {
        composeRule.setContent {
            // TODO: Replace with your actual composable call
        }

        composeRule.onNodeWithTag("guest_instagram_pager")
            .assertExists()
    }
}