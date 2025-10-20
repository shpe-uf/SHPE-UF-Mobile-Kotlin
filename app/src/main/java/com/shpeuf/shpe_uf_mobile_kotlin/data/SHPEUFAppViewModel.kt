package com.shpeuf.shpe_uf_mobile_kotlin.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.shpeuf.shpe_uf_mobile_kotlin.SHPEUFApp
import com.shpeuf.shpe_uf_mobile_kotlin.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AppState(
    val id: String,
    val isLoggedIn: Boolean,
    val isRegistered: Boolean,
    val isLoggedOut: Boolean,
    val isDarkMode: Boolean,
    val isGuest: Boolean // Field for guest view
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
    private val guestFlow = userRepository.currentGuest

    val uiState: StateFlow<AppState> =
        combine( //combine in lambda form has a limit of 5 flows, I turned it into an array to accommodate for guestFlow
            listOf(idFlow, loggedInFlow, registeredFlow, loggedOutFlow, darkModeFlow, guestFlow)
        ) { array ->
            AppState(id = array[0] as String, isLoggedIn = array[1] as Boolean,
                isRegistered = array[2] as Boolean, isLoggedOut = array[3] as Boolean,
                isDarkMode = array[4] as Boolean, isGuest = array[5] as Boolean
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppState(id = "", isLoggedIn = false, isRegistered = false, isLoggedOut = true, isDarkMode = false, isGuest = false)
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

    // save user guest
    fun saveGuest(isGuest: Boolean){
        viewModelScope.launch {
            userRepository.saveGuest(isGuest)
        }
    }

    fun logoutUser(){
        saveUserId("")
        saveLoggedIn(false)
        saveLoggedOut(true)
        saveGuest(false)
        Log.d("id", "id:${uiState.value.id}")
        Log.d("loggedIn", "loggedIn:${uiState.value.isLoggedIn}")
        Log.d("loggedOut", "loggedOut:${uiState.value.isLoggedOut}")
        Log.d("guest", "guest:${uiState.value.isGuest}")
    }

}

sealed class ViewState {
    object Loading: ViewState()
    object LoggedIn: ViewState()
    object NotLoggedIn: ViewState()
}