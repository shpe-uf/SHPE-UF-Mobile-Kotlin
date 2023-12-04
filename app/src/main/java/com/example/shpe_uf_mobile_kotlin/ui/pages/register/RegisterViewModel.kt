package com.example.shpe_uf_mobile_kotlin.ui.pages.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class RegisterViewModel: ViewModel() {

    //Mutable State Flow that holds the state of the Register Screen View
    private val _uiState = MutableStateFlow(RegisterPageState())
    val uiState: StateFlow<RegisterPageState> = _uiState.asStateFlow()

    //Function to validate and register the user
    fun validateAndRegisterUser() {

        val currentState = _uiState.value

        //Validate user input fields
        val isValidFirstName = validateFirstName(currentState.firstName ?: "")
        val isValidLastName = validateLastName(currentState.lastName ?: "")
        val isValidEmail = validateEmail(currentState.email ?: "")
        val isValidPassword = validatePassword(currentState.password ?: "")
        val isPasswordConfirmed = validateConfirmPassword(currentState.password ?: "",
            currentState.confirmPassword ?: "")

        //Update state with error messages
        _uiState.value = currentState.copy(
            firstNameErrorMessage = isValidFirstName,
            lastNameErrorMessage = isValidLastName,
            emailErrorMessage = isValidEmail,
            passwordErrorMessage = isValidPassword,
            confirmPasswordErrorMessage = isPasswordConfirmed
        )

        //Register user if all validations are passed
        if (currentState.firstNameErrorMessage == null
            && currentState.lastNameErrorMessage == null
            && currentState.emailErrorMessage == null
            && currentState.passwordErrorMessage == null) {
            registerUser()
        }

    }

    //Function to handle user registration
    private fun registerUser() {

        //TODO Handle user registration

    }

    // Function to validate user inputs
    private fun validateFirstName(name: String): String? {
        if (name.isBlank()) return "First name is required."

        val nameValidator = "^[a-zA-Z ',.-]{3,20}$".toRegex()
        return if (name.matches(nameValidator)) null else "First Name must be at least 3 characters, max 20. No special characters or numbers."
    }

    private fun validateLastName(name: String): String? {
        if (name.isBlank()) return "Last name is required."

        val nameValidator = "^[a-zA-Z ',.-]{3,20}$".toRegex()
        return if (name.matches(nameValidator)) null else "Last name must be at least 3 characters, max 20. No special characters or numbers."
    }

    private fun validateEmail(email: String): String? {
        if (email.isBlank()) return "Email is required."

        val emailValidator = "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,12})\$".toRegex()

        if(!email.matches(emailValidator)) return "Invalid email address."

        val domains = listOf("@ufl.edu", "@sfcollege.edu")

        return if (domains.contains("@${email.substringAfterLast('@')}")) null else "University of Florida or Santa Fe College email required."
    }


    private fun validatePassword(password: String): String? {
        if (password.isBlank()) return "Password is required."

        val passwordValidator = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-.]).{8,}$".toRegex()

        return if (password.matches(passwordValidator)) null else "Password must be at least 8 characters, include one lowercase, one uppercase, one number, and one special character."
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return if (password == confirmPassword) null else "Passwords must match."
    }

    //Functions to update the state for each input field
    fun onFirstNameChanged(firstName: String) {
        _uiState.value = _uiState.value.copy(firstName = firstName)
    }

    fun onLastNameChanged(lastName: String) {
        _uiState.value = _uiState.value.copy(lastName = lastName)
    }

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }

    fun toggleConfirmPasswordVisibility() {
        _uiState.value = _uiState.value.copy(isConfirmPasswordVisible = !_uiState.value.isConfirmPasswordVisible)
    }

}