package com.shpeuf.shpe_uf_mobile_kotlin

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.NavRoute
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegisterPage1ViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage1
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage2
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage3
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MultiStepRegistrationFlowUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var registerViewModel: RegisterPage1ViewModel

    @Before
    fun setUp() {
        navController = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }
        registerViewModel = RegisterPage1ViewModel()
    }

    private fun setRegistrationNavHost() {
        composeRule.setContent {
            NavHost(
                navController = navController,
                startDestination = NavRoute.REGISTER
            ) {
                composable(NavRoute.LOGIN) { Text("LOGIN_DESTINATION") }
                composable(NavRoute.REGISTER) { RegistrationPage1(registerViewModel, navController) }
                composable(NavRoute.REGISTER_2) { RegistrationPage2(registerViewModel, navController) }
                composable(NavRoute.REGISTER_3) { RegistrationPage3(registerViewModel, navController) }
            }
        }
    }

    @Test
    fun registerPage1_rendersElements_and_validatesInput() {
        setRegistrationNavHost()

        composeRule.onNodeWithText("REGISTER").assertIsDisplayed()
        composeRule.onNodeWithText("UF Email").assertIsDisplayed()
        composeRule.onNodeWithText("Username").assertIsDisplayed()
        composeRule.onNodeWithText("Password").assertIsDisplayed()
        composeRule.onNodeWithText("Confirm Password").assertIsDisplayed()
        composeRule.onNodeWithText("Create Account").assertIsDisplayed()

        // Click "Create Account" with blank fields -> remains on REGISTER route
        composeRule.onNodeWithText("Create Account").performClick()
        assertEquals(NavRoute.REGISTER, navController.currentBackStackEntry?.destination?.route)

        // Fill invalid email
        composeRule.onAllNodes(hasSetTextAction())[0].performTextInput("invalid@gmail.com")
        composeRule.onNodeWithText("Create Account").performClick()
        assertEquals(NavRoute.REGISTER, navController.currentBackStackEntry?.destination?.route)

        // Fill valid Page 1 fields
        composeRule.onAllNodes(hasSetTextAction())[0].performTextInput("")
        registerViewModel.onEmailChanged("gator@ufl.edu")
        registerViewModel.onUsernameChanged("shpe_gator_2025")
        registerViewModel.onPasswordChanged("StrongPass123!")
        registerViewModel.onConfirmPasswordChanged("StrongPass123!")

        // Click "Create Account" -> navigates to REGISTER_2
        composeRule.onNodeWithText("Create Account").performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            navController.currentBackStackEntry?.destination?.route == NavRoute.REGISTER_2
        }
        composeRule.onNodeWithText("Personal Details").assertIsDisplayed()
    }

    @Test
    fun multiStepRegistration_flow_and_statePersistenceAcrossSteps() {
        setRegistrationNavHost()

        // Page 1: Input user data
        registerViewModel.onEmailChanged("albert@ufl.edu")
        registerViewModel.onUsernameChanged("albert_gator")
        registerViewModel.onPasswordChanged("GatorPride123!")
        registerViewModel.onConfirmPasswordChanged("GatorPride123!")

        // Navigate to Page 2
        composeRule.onNodeWithText("Create Account").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            navController.currentBackStackEntry?.destination?.route == NavRoute.REGISTER_2
        }

        // Page 2: Input personal details
        registerViewModel.onFirstNameChanged("Albert")
        registerViewModel.onLastNameChanged("Gator")
        registerViewModel.onGenderChanged("Male")
        registerViewModel.onEthnicityChanged("Hispanic/Latino")
        registerViewModel.onCountryOriginChanged("United States")

        // Navigate to Page 3
        composeRule.onNodeWithText("Continue").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            navController.currentBackStackEntry?.destination?.route == NavRoute.REGISTER_3
        }

        // Verify Page 1 and Page 2 data still persisted in ViewModel while on Page 3
        assertEquals("albert@ufl.edu", registerViewModel.uiState.value.email)
        assertEquals("albert_gator", registerViewModel.uiState.value.username)
        assertEquals("Albert", registerViewModel.uiState.value.firstName)
        assertEquals("Gator", registerViewModel.uiState.value.lastName)
        assertEquals("Hispanic/Latino", registerViewModel.uiState.value.ethnicity)

        // Navigate Back to Page 2 using Back Button
        composeRule.onNodeWithContentDescription("BackButtonIcon").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            navController.currentBackStackEntry?.destination?.route == NavRoute.REGISTER_2
        }

        // Navigate Back to Page 1
        composeRule.onNodeWithContentDescription("BackButtonIcon").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            navController.currentBackStackEntry?.destination?.route == NavRoute.REGISTER
        }

        // Verify values are preserved after navigating back
        assertEquals("albert@ufl.edu", registerViewModel.uiState.value.email)
        assertEquals("albert_gator", registerViewModel.uiState.value.username)
    }

    @Test
    fun registerPage3_completion_navigatesToLogin() {
        setRegistrationNavHost()

        // Set all valid inputs across all 3 pages
        registerViewModel.onEmailChanged("alberta@ufl.edu")
        registerViewModel.onUsernameChanged("alberta_gator")
        registerViewModel.onPasswordChanged("GoGators123!")
        registerViewModel.onConfirmPasswordChanged("GoGators123!")

        registerViewModel.onFirstNameChanged("Alberta")
        registerViewModel.onLastNameChanged("Gator")
        registerViewModel.onGenderChanged("Female")
        registerViewModel.onEthnicityChanged("Hispanic/Latino")
        registerViewModel.onCountryOriginChanged("United States")

        // Directly navigate to Page 3
        composeRule.runOnUiThread {
            navController.navigate(NavRoute.REGISTER_3)
        }

        // Fill Page 3 details
        registerViewModel.onMajorChanged("Computer Science")
        registerViewModel.onYearChanged("3rd Year")
        registerViewModel.onGraduationYearChanged("2026")

        // Click "Complete Registration"
        composeRule.onNodeWithText("Complete Registration").performClick()

        // Verify navigation to LOGIN destination
        composeRule.waitUntil(timeoutMillis = 5_000) {
            navController.currentBackStackEntry?.destination?.route == NavRoute.LOGIN
        }
        composeRule.onNodeWithText("LOGIN_DESTINATION").assertIsDisplayed()
    }
}
