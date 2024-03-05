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
        val isValidEmail = validateEmail(currentState.email ?: "")
        val isValidPassword = validatePassword(currentState.password ?: "")

        // Update state with error messages.
        _uiState.value = currentState.copy(
            emailErrorMessage = isValidEmail,
            passwordErrorMessage = isValidPassword
        )

        // Login user if validations passed.
        if(currentState.emailErrorMessage == null && currentState.passwordErrorMessage == null)
            loginUser()
    }

    // Handles user login using graphQL.

    private fun loginUser(){
        // TODO: Handle user login with a GraphQL query. Check among the database to ensure the user's email and hashed password exists.
    }

// Validates user inputs.

    private fun validateEmail(email: String): String? {
        if(email.isBlank()) return "Email is required."

        val emailValidator = "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,12})\$".toRegex()

        if(!email.matches(emailValidator)) return "Invalid email address."

        val domains = listOf("@ufl.edu", "@sfcollege.edu")

        return if (domains.contains("@${email.substringAfterLast('@')}")) null else "University of Florida or Santa Fe College email required."
    }

    private fun validatePassword(password: String): String?{
        if(password.isBlank()) return "Password is required."

        val passwordValidator = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-.]).{8,}$".toRegex()

        return if (password.matches(passwordValidator)) null else "Password must be at least 8 characters, include one lowercase, one uppercase, one number, and one special character."
    }

    // Functions to update the state for each input field.
    fun onEmailChanged(email: String){
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChanged(password: String){
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun togglePasswordVisibility(){
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }
}

