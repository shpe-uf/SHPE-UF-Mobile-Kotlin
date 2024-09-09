package com.example.shpe_uf_mobile_kotlin.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shpe_uf_mobile_kotlin.SHPEUFApp
import com.example.shpe_uf_mobile_kotlin.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UiState(
    val id: String
)

class SHPEUFAppViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    val uiState: StateFlow<UiState> =
        userRepository.currentUserId.map { id ->
            UiState(id)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState("")
        )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SHPEUFApp)
                SHPEUFAppViewModel(application.userRepository)
            }
        }
    }

    fun saveUserId(id: String){
        viewModelScope.launch {
            userRepository.saveUserId(id)
        }
    }
}