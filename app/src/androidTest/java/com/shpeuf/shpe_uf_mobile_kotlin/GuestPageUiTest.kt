package com.shpeuf.shpe_uf_mobile_kotlin.guest_tests

import androidx.navigation.compose.rememberNavController
import com.shpeuf.shpe_uf_mobile_kotlin.R
import com.shpeuf.shpe_uf_mobile_kotlin.data.AppState
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.guest.GuestPlaceholderPage
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.HomeScreen
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModelFactory
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.opening.OpeningPage
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.points.PointsView
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.profile.ProfilePagePreview
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.profile.ProfileViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.profile.StaticProfilePagePreview
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.admin.AdminPanelScreen
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.events.CreateEventsScreen
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.events.CreateEventViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegisterPage1ViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegisterRoutes
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage1
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage1Preview
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage2
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage2Preview
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage3
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegistrationPage3Preview
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.signIn.SignIn
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.sponsors.SponsorsPage
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.wrapped.WrappedScreen
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.wrapped.WrappedViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.blueDarkModeBackground
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.ViewModelProvider
import org.junit.Rule
import org.junit.Test


class GuestPlaceholderPageUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun guestScreen_rendersRoot_byTag_ifProvided() {
        val vm = ViewModelProvider(composeRule.activity, SHPEUFAppViewModel.Factory)[SHPEUFAppViewModel::class.java]

        composeRule.setContent {GuestPlaceholderPage(navController = rememberNavController(), shpeufAppViewModel = vm) }

        composeRule.onRoot(useUnmergedTree = true).printToLog("SEMANTICS")

        //val title = composeRule.activity.getString(R.string.who_we_are)
        //composeRule.onNodeWithText(title).assertExists()
        composeRule.onNodeWithText("Who We Are").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("SHPE Logo").assertIsDisplayed()
    }

}