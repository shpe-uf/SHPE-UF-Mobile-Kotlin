package com.example.shpe_uf_mobile_kotlin.ui.pages.points

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.shpe_uf_mobile_kotlin.PointsMutation
import com.example.shpe_uf_mobile_kotlin.type.RedeemPointsInput
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.apollographql.apollo3.api.Optional
import com.example.shpe_uf_mobile_kotlin.apolloClient


class PointsPageViewModel : ViewModel() {
    // Initial state
    private val _uiState = MutableStateFlow(PointsPageState())
    val uiState: StateFlow<PointsPageState> = _uiState.asStateFlow()

    //Getter for current state
    fun currentState(): PointsPageState {
        return _uiState.value
    }

    // Function to update the guests count
    fun updateGuestsCount(newCount: Int) {
        _uiState.value = _uiState.value.copy(guestsCount = newCount)
    }

    // Function to update the event code
    fun updateEventCode(newCode: String) {
        _uiState.value = _uiState.value.copy(eventCode = newCode)
    }

    //Function to redeem an event based on inputted code, guest count, and the
    //username of current user, returns the propagated error message from
    // validateEventRedeem() or "null" if there is none.
     suspend fun redeemEvent(username: String): String?{
        val pointsInput = RedeemPointsInput(
            code = currentState().eventCode,
            username = username,
            guests = currentState().guestsCount
        )
        return validateEventRedeem(Optional.presentIfNotNull(pointsInput))
    }



    //Function to ensure that event code is valid, and to return appropriate error
    //messages for when it is not.
    suspend fun validateEventRedeem(pointsInput: Optional<RedeemPointsInput>): String? {
        val response = apolloClient.mutation(PointsMutation(pointsInput)).execute()
        return response.errors?.get(0)?.message
    }

}