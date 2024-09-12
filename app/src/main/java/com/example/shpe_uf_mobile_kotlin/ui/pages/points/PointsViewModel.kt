package com.example.shpe_uf_mobile_kotlin.ui.pages.points

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.shpe_uf_mobile_kotlin.PointsMutation
import com.example.shpe_uf_mobile_kotlin.type.RedeemPointsInput
import com.example.shpe_uf_mobile_kotlin.GetUserNameQuery
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.apollographql.apollo3.api.Optional
import com.example.shpe_uf_mobile_kotlin.apolloClient
import kotlinx.coroutines.launch


class PointsPageViewModel : ViewModel() {
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

     suspend fun redeemEvent(username: String): String?{
        val pointsInput = RedeemPointsInput(
            code = currentState().eventCode,
            username = username,
            guests = currentState().guestsCount
        )

         Log.d("User", username)
         var result: String? = null

        result = validateEventRedeem(Optional.presentIfNotNull(pointsInput))

        return result
    }



    suspend fun validateEventRedeem(pointsInput: Optional<RedeemPointsInput>): String? {
        val response = apolloClient.mutation(PointsMutation(pointsInput)).execute()
        return response.errors?.get(0)?.message
    }

}