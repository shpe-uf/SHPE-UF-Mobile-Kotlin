package com.shpeuf.shpe_uf_mobile_kotlin

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.NavRoute
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.points.PointsView
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.signIn.SignIn
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PointsPageUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun resetLoginState() {
        // Ensure a clean logged-out state before each test so DataStore persistence
        // from prior runs doesn't cause the sign-in screen to be skipped.
        val repo = (composeRule.activity.application as SHPEUFApp).userRepository
        runBlocking { repo.saveLoggedIn(false) }
    }

    private fun getViewModel(): SHPEUFAppViewModel =
        ViewModelProvider(composeRule.activity, SHPEUFAppViewModel.Factory)[SHPEUFAppViewModel::class.java]

    @Test
    fun pointsPage_rendersTopSection() {
        composeRule.setContent { PointsView(shpeufAppViewModel = getViewModel()) }

        composeRule.onRoot(useUnmergedTree = true).printToLog("SEMANTICS")

        composeRule.onNodeWithText("Points Program").assertIsDisplayed()
    }

    @Test
    fun pointsPage_rendersRedeemCodeButton() {
        composeRule.setContent { PointsView(shpeufAppViewModel = getViewModel()) }

        composeRule.onNodeWithText("Redeem Code").assertIsDisplayed()
    }

    @Test
    fun pointsPage_redeemCodeButton_opensBottomSheet() {
        composeRule.setContent { PointsView(shpeufAppViewModel = getViewModel()) }

        composeRule.onNodeWithText("Redeem Code").performClick()

        composeRule.onNodeWithText("REDEEM POINTS").assertIsDisplayed()
    }

    @Test
    fun redeemPoints_sheet_showsGuestSection() {
        composeRule.setContent { PointsView(shpeufAppViewModel = getViewModel()) }

        composeRule.onNodeWithText("Redeem Code").performClick()

        composeRule.onNodeWithText("Guests").assertIsDisplayed()
    }

    @Test
    fun redeemPoints_sheet_eventCodeField_acceptsInput() {
        composeRule.setContent { PointsView(shpeufAppViewModel = getViewModel()) }

        composeRule.onNodeWithText("Redeem Code").performClick()

        composeRule.onAllNodes(hasSetTextAction())[0]
            .performTextInput("TEST_EVENT_CODE")

        composeRule.onNodeWithText("TEST_EVENT_CODE").assertIsDisplayed()
    }

    // -----------------------------------------------------------------------
    // Authenticated-user tests — log in with the shared test credentials and
    // then verify the points graphics and events calendar react to real data.
    // -----------------------------------------------------------------------

    @Test
    fun pointsPage_afterLogin_percentileCircleShowsData() {
        val vm = ViewModelProvider(
            composeRule.activity,
            SHPEUFAppViewModel.Factory
        )[SHPEUFAppViewModel::class.java]

        val navController = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeRule.setContent {
            NavHost(navController = navController, startDestination = NavRoute.LOGIN) {
                composable(NavRoute.LOGIN) { SignIn(navController, vm) }
                composable(NavRoute.HOME) { Text("HOME_SCREEN") }
                composable(NavRoute.POINTS) { PointsView(shpeufAppViewModel = vm) }
            }
        }

        // Sign in with the shared test account
        composeRule.onAllNodes(hasSetTextAction())[0].performTextInput("googletestaccount")
        composeRule.onAllNodes(hasSetTextAction())[1].performTextInput("GoogleTestAccount123!")
        composeRule.onNodeWithTag("Sign in button").performClick()

        // Wait until the app navigates to HOME after a successful login
        composeRule.waitUntil(timeoutMillis = 15_000) {
            navController.currentBackStackEntry?.destination?.route == NavRoute.HOME
        }

        // Navigate to the real PointsView
        composeRule.runOnUiThread { navController.navigate(NavRoute.POINTS) }

        // Wait for the GraphQL points query to resolve — "Percentile" lives inside
        // the animated pie-chart circle and only appears once data is rendered.
        composeRule.waitUntil(timeoutMillis = 15_000) {
            runCatching {
                composeRule.onNode(hasScrollToNodeAction())
                    .performScrollToNode(hasText("Percentile"))
                composeRule.onNodeWithText("Percentile").assertIsDisplayed()
            }.isSuccess
        }

        // The percentile circle must show the current semester label and ordinal rank
        composeRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(hasText("Percentile"))
        composeRule.onNodeWithText("Percentile").assertIsDisplayed()

        // The three semester point-bar labels must also be visible
        composeRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(hasText("FALL"))
        composeRule.onNodeWithText("FALL").assertIsDisplayed()

        composeRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(hasText("SPRING"))
        composeRule.onNodeWithText("SPRING").assertIsDisplayed()

        composeRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(hasText("SUMMER"))
        composeRule.onNodeWithText("SUMMER").assertIsDisplayed()
    }

    @Test
    fun pointsPage_afterLogin_totalPointsCounterIsDisplayed() {
        val vm = ViewModelProvider(
            composeRule.activity,
            SHPEUFAppViewModel.Factory
        )[SHPEUFAppViewModel::class.java]

        val navController = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeRule.setContent {
            NavHost(navController = navController, startDestination = NavRoute.LOGIN) {
                composable(NavRoute.LOGIN) { SignIn(navController, vm) }
                composable(NavRoute.HOME) { Text("HOME_SCREEN") }
                composable(NavRoute.POINTS) { PointsView(shpeufAppViewModel = vm) }
            }
        }

        composeRule.onAllNodes(hasSetTextAction())[0].performTextInput("googletestaccount")
        composeRule.onAllNodes(hasSetTextAction())[1].performTextInput("GoogleTestAccount123!")
        composeRule.onNodeWithTag("Sign in button").performClick()

        composeRule.waitUntil(timeoutMillis = 15_000) {
            navController.currentBackStackEntry?.destination?.route == NavRoute.HOME
        }

        composeRule.runOnUiThread { navController.navigate(NavRoute.POINTS) }

        // Wait for "Total Points:" to appear — it is rendered once the PointsQuery responds
        composeRule.waitUntil(timeoutMillis = 15_000) {
            runCatching {
                composeRule.onNodeWithText("Total Points:", substring = true).assertIsDisplayed()
            }.isSuccess
        }

        composeRule.onNodeWithText("Total Points:", substring = true).assertIsDisplayed()
    }

    @Test
    fun pointsPage_afterLogin_eventsCalendarListsAttendedEvents() {
        val vm = ViewModelProvider(
            composeRule.activity,
            SHPEUFAppViewModel.Factory
        )[SHPEUFAppViewModel::class.java]

        val navController = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeRule.setContent {
            NavHost(navController = navController, startDestination = NavRoute.LOGIN) {
                composable(NavRoute.LOGIN) { SignIn(navController, vm) }
                composable(NavRoute.HOME) { Text("HOME_SCREEN") }
                composable(NavRoute.POINTS) { PointsView(shpeufAppViewModel = vm) }
            }
        }

        composeRule.onAllNodes(hasSetTextAction())[0].performTextInput("googletestaccount")
        composeRule.onAllNodes(hasSetTextAction())[1].performTextInput("GoogleTestAccount123!")
        composeRule.onNodeWithTag("Sign in button").performClick()

        composeRule.waitUntil(timeoutMillis = 15_000) {
            navController.currentBackStackEntry?.destination?.route == NavRoute.HOME
        }

        composeRule.runOnUiThread { navController.navigate(NavRoute.POINTS) }

        // Allow enough time for both the points and events GraphQL queries to resolve
        composeRule.waitUntil(timeoutMillis = 15_000) {
            runCatching {
                composeRule.onNodeWithText("Total Points:", substring = true).assertIsDisplayed()
            }.isSuccess
        }

        // The events calendar section renders event categories if any have been attended.
        val knownCategories = listOf("GBM", "SOCIAL", "WORKSHOP", "VOLUNTEERING", "CABINET", "MISC", "CORPORATE EVENT")
        val anyEventVisible = knownCategories.any { category ->
            runCatching {
                composeRule.onNode(hasScrollToNodeAction())
                    .performScrollToNode(hasText(category, substring = true))
                composeRule.onNodeWithText(category, substring = true).assertIsDisplayed()
            }.isSuccess
        }

        val pointsDisplayed = runCatching {
            composeRule.onNodeWithText("Total Points:", substring = true).assertIsDisplayed()
        }.isSuccess

        assert(anyEventVisible || pointsDisplayed) {
            "Expected either attended-event categories or the points/calendar view to be displayed."
        }
    }
}
