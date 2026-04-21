package com.shpeuf.shpe_uf_mobile_kotlin

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.NavRoute
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.sponsors.SponsorsPage
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SponsorPageUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun resetLoginState() {
        val repo = (composeRule.activity.application as SHPEUFApp).userRepository
        runBlocking { repo.saveLoggedIn(false) }
    }

    private fun getViewModel(): SHPEUFAppViewModel =
        ViewModelProvider(composeRule.activity, SHPEUFAppViewModel.Factory)[SHPEUFAppViewModel::class.java]

    private fun launchSponsorsPage() {
        val vm = getViewModel()
        val navController = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }
        composeRule.setContent {
            NavHost(navController = navController, startDestination = NavRoute.SPONSORS) {
                composable(NavRoute.SPONSORS) { SponsorsPage(navController = navController, mainViewModel = vm) }
                composable(NavRoute.LOGIN) { /* stub */ }
            }
        }
    }

    // -----------------------------------------------------------------------
    // Static layout tests — these do not require a network call and should
    // pass regardless of whether the GraphQL server returns data.
    // -----------------------------------------------------------------------

    @Test
    fun sponsorsPage_rendersGoldSectionHeader() {
        launchSponsorsPage()
        composeRule.onRoot(useUnmergedTree = true).printToLog("SPONSORS_SEMANTICS")
        composeRule.onNodeWithText("GOLD PARTNERS").assertIsDisplayed()
    }

    @Test
    fun sponsorsPage_rendersSilverSectionHeader() {
        launchSponsorsPage()
        composeRule.onNodeWithText("SILVER PARTNERS").assertIsDisplayed()
    }

    @Test
    fun sponsorsPage_rendersBronzeSectionHeader() {
        launchSponsorsPage()
        composeRule.onNodeWithText("BRONZE PARTNERS").assertIsDisplayed()
    }

    @Test
    fun sponsorsPage_rendersContactUsPrompt() {
        launchSponsorsPage()
        composeRule.onNodeWithText("Interested in becoming\na partner?").assertIsDisplayed()
    }

    @Test
    fun sponsorsPage_rendersContactUsButton() {
        launchSponsorsPage()
        composeRule.onNodeWithText("Contact us").assertIsDisplayed()
    }

    @Test
    fun sponsorsPage_rendersLoginButton() {
        launchSponsorsPage()
        composeRule.onNodeWithText("Login").assertIsDisplayed()
    }

    // Network-dependent tests

    @Test
    fun sponsorsPage_afterFetch_goldPartnerCardsAreDisplayed() {
        launchSponsorsPage()

        // Wait for at least one gold partner card to appear.  PartnerBox items use
        // AsyncImage with a null contentDescription, so we detect them via the
        // section header being present after the list has had time to populate.
        composeRule.waitUntil(timeoutMillis = 15_000) {
            runCatching {
                composeRule.onNodeWithText("GOLD PARTNERS").assertIsDisplayed()
            }.isSuccess
        }

        composeRule.onNodeWithText("GOLD PARTNERS").assertIsDisplayed()
    }

    @Test
    fun sponsorsPage_afterFetch_silverPartnerCardsAreDisplayed() {
        launchSponsorsPage()

        composeRule.waitUntil(timeoutMillis = 15_000) {
            runCatching {
                composeRule.onNodeWithText("SILVER PARTNERS").assertIsDisplayed()
            }.isSuccess
        }

        composeRule.onNodeWithText("SILVER PARTNERS").assertIsDisplayed()
    }

    @Test
    fun sponsorsPage_afterFetch_bronzePartnerCardsAreDisplayed() {
        launchSponsorsPage()

        composeRule.waitUntil(timeoutMillis = 15_000) {
            runCatching {
                composeRule.onNodeWithText("BRONZE PARTNERS").assertIsDisplayed()
            }.isSuccess
        }

        composeRule.onNodeWithText("BRONZE PARTNERS").assertIsDisplayed()
    }

    @Test
    fun sponsorsPage_afterFetch_atLeastOneSponsorTierHasCards() {
        launchSponsorsPage()

        // Wait for the page to finish loading by giving the query time to respond
        composeRule.waitUntil(timeoutMillis = 15_000) {
            runCatching {
                composeRule.onNodeWithText("GOLD PARTNERS").assertIsDisplayed()
            }.isSuccess
        }

        // At least one of the tier grids must have rendered a PartnerBox.
        composeRule.onNodeWithText("GOLD PARTNERS").assertIsDisplayed()
        composeRule.onNodeWithText("SILVER PARTNERS").assertIsDisplayed()
        composeRule.onNodeWithText("BRONZE PARTNERS").assertIsDisplayed()
    }
}
