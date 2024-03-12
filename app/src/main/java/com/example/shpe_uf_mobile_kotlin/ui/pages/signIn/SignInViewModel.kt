package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel: ViewModel() {

    // Mutable State Flow that holds the state of the Login Screen View.
    private val _uiState = MutableStateFlow(SignInState())
    val uiState: StateFlow<SignInState> = _uiState.asStateFlow()

    // Function to validate and login the user.
    fun validateAndLoginUser(){
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
        if(currentState.usernameErrorMessage == null && currentState.passwordErrorMessage == null)
            loginUser()
    }

    // Handles user login using graphQL.

    private fun loginUser(){
        // TODO: Handle user login with a GraphQL query. Check among the database to ensure the user's email and hashed password exists.
    }

// Validates user inputs.

    private fun validateUsername(username: String) : String?{
        return if(username.isBlank()) "Username is required." else null
    }

    private fun validatePassword(password: String): String?{
        if(password.isBlank()) return "Password is required."

        val passwordValidator = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-.]).{8,}$".toRegex()

        return if (password.matches(passwordValidator)) null else "Password must be at least 8 characters, include one lowercase, one uppercase, one number, and one special character."
    }

    // Functions to update the state for each input field.
    fun onUsernameChanged(username: String){
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun onPasswordChanged(password: String){
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun togglePasswordVisibility(){
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }
}