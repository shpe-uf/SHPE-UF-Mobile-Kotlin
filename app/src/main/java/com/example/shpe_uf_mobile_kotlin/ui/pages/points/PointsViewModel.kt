package com.example.shpe_uf_mobile_kotlin.ui.pages.points

import androidx.lifecycle.ViewModel
import apolloClient
import com.apollographql.apollo3.api.Optional
import com.example.shpe_uf_mobile_kotlin.PointsMutation
import com.example.shpe_uf_mobile_kotlin.data.Session
import com.example.shpe_uf_mobile_kotlin.type.RedeemPointsInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class PointsPageViewModel(
    private val session: Session
) : ViewModel() {
    // Initial state
    private val _uiState = MutableStateFlow(PointsPageState())
    val uiState: StateFlow<PointsPageState> = _uiState.asStateFlow()

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

     suspend fun redeemEvent(id: String): String?{
        val currentState = currentState()
        val pointsInput = RedeemPointsInput(
            code = currentState.eventCode,
            username = "natalieandino",
            guests = currentState.guestsCount
        )
        var result: String? = null

        result = validateEventRedeem(Optional.presentIfNotNull(pointsInput))

        return result
    }

    suspend fun validateEventRedeem(pointsInput: Optional<RedeemPointsInput>): String? {
        val response = apolloClient.mutation(PointsMutation(pointsInput)).execute()
        return response.errors?.get(0)?.message
    }
}