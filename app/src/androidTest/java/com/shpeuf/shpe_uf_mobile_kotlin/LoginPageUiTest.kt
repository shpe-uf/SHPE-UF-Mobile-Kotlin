package com.shpeuf.shpe_uf_mobile_kotlin

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.guest.GuestPlaceholderPage
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.signIn.SignIn
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
}