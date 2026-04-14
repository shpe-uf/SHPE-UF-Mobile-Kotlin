package com.shpeuf.shpe_uf_mobile_kotlin

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.ViewModelProvider
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.points.PointsView
import org.junit.Rule
import org.junit.Test

class PointsPageUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

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
}
