package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apolloClient
import com.example.shpe_uf_mobile_kotlin.LoginMutation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignInState())
    val uiState: StateFlow<SignInState> = _uiState

    fun validateAndLoginUser() {
        val currentState = _uiState.value

        // Validate user input fields.
        val isValidUsername = validateUsername(currentState.username ?: "")
        val isValidPassword = validatePassword(currentState.password ?: "")

        // Update state with error messages.
        _uiState.value = currentState.copy(
            usernameErrorMessage = isValidUsername,
            passwordErrorMessage = isValidPassword
        )

        // Login user if validations passed.
        if (currentState.usernameErrorMessage == null && currentState.passwordErrorMessage == null) {
            Log.d("Validating", "${currentState.username} | ${currentState.password}")
            performLogin(currentState.username.toString(), currentState.password.toString())
        } else {
            Log.d("Validating", "Failure!")
        }
    }

    private fun performLogin(username: String, password: String) {
        viewModelScope.launch {
            val loginSuccess = loginUser(username, password)

            if (!loginSuccess) updateErrorMessage("Could not login.") else updateErrorMessage("Logged in.")
        }
    }

    private suspend fun loginUser(username: String, password: String): Boolean {
        // Mutation for logging in, returns the user's id on success.
        val response = apolloClient.mutation(LoginMutation(username, password, "true")).execute()

        if (!response.hasErrors()) {
            val id = response.data?.login?.id
            Log.d("GraphQL", "$id")
            return true
        } else {
            Log.w("GraphQL", "Could not login.")
            return false
        }
    }

    private fun updateErrorMessage(message: String) {
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            loginErrorMessage = message
        )
    }

    private fun validateUsername(username: String): String? {
        return if (username.isBlank()) "Username is required." else null
    }

    private fun validatePassword(password: String): String? {
        return if (password.isBlank()) "Password is required." else null
    }

    fun onUsernameChanged(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername)
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }
}
