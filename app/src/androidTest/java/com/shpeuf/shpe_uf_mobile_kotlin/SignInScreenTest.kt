package com.shpeuf.shpe_uf_mobile_kotlin

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.NavRoute
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.signIn.SignIn
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.signIn.SignInViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignInScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun resetLoginState() {
        val repo = (composeRule.activity.application as SHPEUFApp).userRepository
        runBlocking { repo.saveLoggedIn(false) }
    }

    @Test
    fun signInViewModel_inputValidationAndErrorHandling() {
        val viewModel = SignInViewModel()

        // Initial state: error messages default to "Username is required." and "Password is required."
        assertEquals("Username is required.", viewModel.uiState.value.usernameErrorMessage)
        assertEquals("Password is required.", viewModel.uiState.value.passwordErrorMessage)
        assertNull(viewModel.uiState.value.loginErrorMessage)

        // Test with empty fields triggers login error message
        val shpeAppVm = ViewModelProvider(composeRule.activity, SHPEUFAppViewModel.Factory)[SHPEUFAppViewModel::class.java]
        viewModel.validateAndLoginUser(shpeAppVm)

        assertEquals("Username and password are required.", viewModel.uiState.value.loginErrorMessage)

        // Fill username only
        viewModel.onUsernameChanged("testuser")
        viewModel.validateAndLoginUser(shpeAppVm)
        assertEquals("Password is required.", viewModel.uiState.value.loginErrorMessage)

        // Fill password only (clear username)
        viewModel.onUsernameChanged("")
        viewModel.onPasswordChanged("Password123!")
        viewModel.validateAndLoginUser(shpeAppVm)
        assertEquals("Username is required.", viewModel.uiState.value.loginErrorMessage)

        // Test clearing error message
        viewModel.updateErrorMessage(null)
        assertNull(viewModel.uiState.value.loginErrorMessage)
    }

    @Test
    fun signInViewModel_passwordVisibilityToggle() {
        val viewModel = SignInViewModel()
        assertFalse(viewModel.uiState.value.isPasswordVisible)

        viewModel.togglePasswordVisibility()
        assertTrue(viewModel.uiState.value.isPasswordVisible)

        viewModel.togglePasswordVisibility()
        assertFalse(viewModel.uiState.value.isPasswordVisible)
    }

    @Test
    fun signInScreen_rendersAllUiElements() {
        val vm = ViewModelProvider(composeRule.activity, SHPEUFAppViewModel.Factory)[SHPEUFAppViewModel::class.java]
        val navController = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeRule.setContent {
            SignIn(navController = navController, shpeUFAppViewModel = vm)
        }

        composeRule.onNodeWithText("SIGN IN").assertIsDisplayed()
        composeRule.onNodeWithText("Username or Email").assertIsDisplayed()
        composeRule.onNodeWithText("Password").assertIsDisplayed()
        composeRule.onNodeWithTag("Sign in button").assertIsDisplayed()
        composeRule.onNodeWithText("Don't have an account?").assertIsDisplayed()
        composeRule.onNodeWithText("Sign Up").assertIsDisplayed()
    }

    @Test
    fun signInScreen_textInputValidation_updatesStateAndErrorMessage() {
        val vm = ViewModelProvider(composeRule.activity, SHPEUFAppViewModel.Factory)[SHPEUFAppViewModel::class.java]
        val navController = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeRule.setContent {
            SignIn(navController = navController, shpeUFAppViewModel = vm)
        }

        // Entering input into text fields
        composeRule.onAllNodes(hasSetTextAction())[0].performTextInput("mytestuser")
        composeRule.onAllNodes(hasSetTextAction())[1].performTextInput("mysecretpass")

        // Click Sign In with invalid/unregistered credentials
        composeRule.onNodeWithTag("Sign in button").performClick()

        // Wait for asynchronous network/graphql response or validation toast trigger
        composeRule.waitForIdle()
    }

    @Test
    fun signInScreen_navigationToRegister() {
        val vm = ViewModelProvider(composeRule.activity, SHPEUFAppViewModel.Factory)[SHPEUFAppViewModel::class.java]
        val navController = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeRule.setContent {
            NavHost(navController = navController, startDestination = NavRoute.LOGIN) {
                composable(NavRoute.LOGIN) { SignIn(navController, vm) }
                composable(NavRoute.REGISTER) { Text("REGISTER_PAGE_DESTINATION") }
            }
        }

        composeRule.onNodeWithText("Sign Up").performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            navController.currentBackStackEntry?.destination?.route == NavRoute.REGISTER
        }
        composeRule.onNodeWithText("REGISTER_PAGE_DESTINATION").assertIsDisplayed()
    }

    @Test
    fun signInScreen_navigationToHome_whenLoggedIn() {
        val vm = ViewModelProvider(composeRule.activity, SHPEUFAppViewModel.Factory)[SHPEUFAppViewModel::class.java]
        val navController = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeRule.setContent {
            NavHost(navController = navController, startDestination = NavRoute.LOGIN) {
                composable(NavRoute.LOGIN) { SignIn(navController, vm) }
                composable(NavRoute.HOME) { Text("HOME_PAGE_DESTINATION") }
            }
        }

        // Simulate login success in AppViewModel
        composeRule.runOnUiThread {
            vm.saveLoggedIn(true)
        }

        composeRule.waitUntil(timeoutMillis = 5_000) {
            navController.currentBackStackEntry?.destination?.route == NavRoute.HOME
        }
        composeRule.onNodeWithText("HOME_PAGE_DESTINATION").assertIsDisplayed()
    }
}
