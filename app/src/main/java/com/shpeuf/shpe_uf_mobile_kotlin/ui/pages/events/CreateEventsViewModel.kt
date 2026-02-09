package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpeuf.shpe_uf_mobile_kotlin.CreateEventMutation
import com.shpeuf.shpe_uf_mobile_kotlin.apolloClient
import com.shpeuf.shpe_uf_mobile_kotlin.type.CreateEventInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateEventViewModel : ViewModel() {

    private val _createEventState = MutableStateFlow<CreateEventState>(CreateEventState.Idle)
    val createEventState: StateFlow<CreateEventState> = _createEventState

    fun createEvent(eventDraft: EventDraft) {
        viewModelScope.launch {
            _createEventState.value = CreateEventState.Loading

            try {
                val pointsValue = eventDraft.points.ifBlank { "0" }
                val expirationValue = eventDraft.expiresIn
                    .replace(Regex("[^0-9]"), "")
                    .ifBlank { "1" }

                val input = CreateEventInput(
                    name = eventDraft.title,
                    code = eventDraft.code,
                    category = eventDraft.category,
                    points = pointsValue,
                    expiration = expirationValue,
                    request = ""
                )

                Log.d("CreateEventViewModel", "Creating event: ${input.name}")

                val response = apolloClient.mutation(CreateEventMutation(input)).execute()

                if (response.hasErrors()) {
                    // Extract the actual error message from extensions
                    val actualError = response.errors?.firstOrNull()?.let { error ->
                        // Try to get the detailed error from extensions.exception.errors
                        val extensions = error.extensions
                        val exception = extensions?.get("exception") as? Map<*, *>
                        val errors = exception?.get("errors") as? Map<*, *>

                        // Get the first error message from the errors map
                        errors?.values?.firstOrNull()?.toString()
                            ?: error.message // Fallback to the main error message
                    } ?: "Unknown error"

                    Log.e("CreateEventViewModel", "Error: $actualError")
                    _createEventState.value = CreateEventState.Error(actualError)
                } else {
                    Log.d("CreateEventViewModel", "Event created successfully")
                    val createdEvents = response.data?.createEvent
                    val firstEvent = createdEvents?.firstOrNull()
                    _createEventState.value = CreateEventState.Success(firstEvent)
                }
            } catch (e: Exception) {
                Log.e("CreateEventViewModel", "Exception: ${e.message}", e)
                _createEventState.value =
                    CreateEventState.Error(e.message ?: "Failed to create event")
            }
        }
    }

    fun resetState() {
        _createEventState.value = CreateEventState.Idle
    }
}

sealed class CreateEventState {
    object Idle : CreateEventState()
    object Loading : CreateEventState()
    data class Success(val event: CreateEventMutation.CreateEvent?) : CreateEventState()
    data class Error(val message: String) : CreateEventState()
}