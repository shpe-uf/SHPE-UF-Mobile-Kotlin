package com.example.shpe_uf_mobile_kotlin.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shpe_uf_mobile_kotlin.SHPEUFApp
import com.example.shpe_uf_mobile_kotlin.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AppState(
    val id: String,
    val isLoggedIn: Boolean,
    val isRegistered: Boolean,
    val isLoggedOut: Boolean,
    val isDarkMode: Boolean
)

data class UserState(
    val id: String,
    val username: String
)

class SHPEUFAppViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    private val idFlow = userRepository.currentUserId
    private val usernameFlow = userRepository.currentUsername
    private val loggedInFlow = userRepository.currentLoggedIn
    private val registeredFlow = userRepository.currentRegistered
    private val loggedOutFlow = userRepository.currentLoggedOut
    private val darkModeFlow = userRepository.currentDarkMode

    val uiState: StateFlow<AppState> =
        combine(idFlow, loggedInFlow, registeredFlow, loggedOutFlow, darkModeFlow){
            id, loggedIn, registered, loggedOut, darkMode ->
            AppState(id, loggedIn, registered, loggedOut, darkMode)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppState(id = "", isLoggedIn = false, isRegistered = false, isLoggedOut = true, isDarkMode = false)
        )

    val userState: StateFlow<UserState> =
        combine(idFlow, usernameFlow){
                id, username ->
            UserState(id,username)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserState(id ="", username = "")
        )



//        userRepository.currentUserId.map { id ->
//            AppState(id)
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = AppState("")
//        )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SHPEUFApp)
                SHPEUFAppViewModel(application.userRepository)
            }
        }
    }

    // save user id
    fun saveUserId(id: String){
        viewModelScope.launch {
            userRepository.saveUserId(id)
        }
    }

    // save user logged in
    fun saveLoggedIn(isLoggedIn: Boolean){
        viewModelScope.launch {
            userRepository.saveLoggedIn(isLoggedIn)
        }
    }

    // save user registered
    fun saveRegistered(isRegistered: Boolean){
        viewModelScope.launch {
            userRepository.saveRegistered(isRegistered)
        }
    }

    // save user logged out
    fun saveLoggedOut(isLoggedOut: Boolean){
        viewModelScope.launch {
            userRepository.saveLoggedOut(isLoggedOut)
        }
    }

    // save user dark mode
    fun saveDarkMode(isDarkMode: Boolean){
        viewModelScope.launch {
            userRepository.saveDarkMode(isDarkMode)
        }
    }

    fun saveUsername(username : String){
        viewModelScope.launch{
            userRepository.saveUsername(username)
        }
    }
}


sealed class ViewState {
    object Loading: ViewState()
    object LoggedIn: ViewState()
    object NotLoggedIn: ViewState()
}