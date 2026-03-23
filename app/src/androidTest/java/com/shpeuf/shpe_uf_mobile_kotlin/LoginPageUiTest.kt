package com.shpeuf.shpe_uf_mobile_kotlin

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.NavRoute
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.guest.GuestPlaceholderPage
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.signIn.SignIn
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class LoginPageUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loginPage_rendersRoot_byTag_ifProvided() {
        val vm = ViewModelProvider(composeRule.activity, SHPEUFAppViewModel.Factory)[SHPEUFAppViewModel::class.java]

        composeRule.setContent { SignIn(navController = rememberNavController(), shpeUFAppViewModel = vm) }

        composeRule.onRoot(useUnmergedTree = true).printToLog("SEMANTICS")

        //val title = composeRule.activity.getString(R.string.who_we_are)
        //composeRule.onNodeWithText(title).assertExists()
        composeRule.onNodeWithText("SIGN IN").assertIsDisplayed()
        composeRule.onNodeWithTag("Sign in button").assertExists()
    }

    @Test
    fun signIn_withGoogleTestCredentials_navigatesToHome_thenPoints() {
        val vm = ViewModelProvider(composeRule.activity, SHPEUFAppViewModel.Factory)[SHPEUFAppViewModel::class.java]

        //create test navhost controller
        val navController = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        //the fact you can build a navigation tree like this is nuts.
        composeRule.setContent {
            NavHost(
                navController = navController,
                startDestination = NavRoute.LOGIN
            ) {
                composable(NavRoute.LOGIN) { SignIn(navController, vm) }
                composable(NavRoute.HOME) { Text("HOME_SCREEN") }
                composable(NavRoute.POINTS) { Text("POINTS_SCREEN") }
            }
        }

        // type credentials
        composeRule.onAllNodes(hasSetTextAction())[0]
            .performTextInput("googletest@ufl.edu")

        composeRule.onAllNodes(hasSetTextAction())[1]
            .performTextInput("GoogleTestAccount123!")

        // click sign in
        composeRule.onNodeWithTag("Sign in button")
            .performClick()

        // Wait until navigation happens
        composeRule.waitUntil(timeoutMillis = 15_000) {
            navController.currentBackStackEntry?.destination?.route == NavRoute.HOME
        }

        // Assert HOME was also navigated to (exists in back stack)
        val homeWasVisited = runCatching {
            navController.getBackStackEntry(NavRoute.HOME)
        }.isSuccess
        assertTrue("Expected HOME to be visited at least once", homeWasVisited)
    }
}