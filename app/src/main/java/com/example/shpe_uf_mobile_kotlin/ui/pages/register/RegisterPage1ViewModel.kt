package com.example.shpe_uf_mobile_kotlin.ui.pages.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterPage1ViewModel: ViewModel() {
    //Mutable State Flow that holds the state of the Register Screen View
    private val _uiState = MutableStateFlow(RegisterPage1State())
    val uiState: StateFlow<RegisterPage1State> = _uiState.asStateFlow()

    //Function to validate and register the user
    fun validateAndRegisterUser() {

        val currentState = _uiState.value

        //Validate user input fields
        val isValidUsername = validateUsername(currentState.username ?: "")
        val isValidFirstName = validateFirstName(currentState.firstName ?: "")
        val isValidLastName = validateLastName(currentState.lastName ?: "")
        val isValidEmail = validateEmail(currentState.email ?: "")
        val isValidPassword = validatePassword(currentState.password ?: "")
        val isPasswordConfirmed = validateConfirmPassword(currentState.password ?: "",
            // ADD IN FOR GENDER and other dropdown menus items
            currentState.confirmPassword ?: "")

        //Update state with error messages
        _uiState.value = currentState.copy(
            usernameErrorMessage = isValidUsername,
            firstNameErrorMessage = isValidFirstName,
            lastNameErrorMessage = isValidLastName,
            emailErrorMessage = isValidEmail,
            passwordErrorMessage = isValidPassword,
            confirmPasswordErrorMessage = isPasswordConfirmed
            // ADD IN FOR GENDER and other Dropdown menus
        )

        //Register user if all validations are passed
        if (currentState.usernameErrorMessage == null
            && currentState.firstNameErrorMessage == null
            && currentState.lastNameErrorMessage == null
            && currentState.emailErrorMessage == null
            && currentState.passwordErrorMessage == null
            // ADD IN FOR GENDER and other dropdown menus
            ) {
            registerUser()
        }

    }

    //Function to handle user registration
    private fun registerUser() {

        //TODO Handle user registration

    }

    // Function to validate user inputs

    private fun validateUsername(username: String): String? {
        if (username.isBlank()) return "Username is required."

        val usernameValidator = "^(?=.{6,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$".toRegex()
        return if (username.matches(usernameValidator)) null else "Username must be at least 6 characters, max 20. No special characters, except for periods (.) and underscores (_)."
    }

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


    //TODO IMPLEMENT THE VALIDATE GENDER FUNCTION FROM SHPE SERVER and others for all dropdown menus

    //Functions to update the state for each input field
    fun onUsernameChanged(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

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


    // New adddtion by me
    fun onGenderChanged(gender: String){
        _uiState.value = _uiState.value.copy(gender = gender)
    }

    fun toggleGenderMenuExpansion(){
        _uiState.value = _uiState.value.copy(isGenderMenuExpanded = !_uiState.value.isGenderMenuExpanded)
    }



    fun onEthnicityChanged(ethnicity: String){
        _uiState.value = _uiState.value.copy(ethnicity = ethnicity)
    }

    fun toggleEthnicityMenuExpansion(){
        _uiState.value = _uiState.value.copy(isEthnicityMenuExpanded = !_uiState.value.isEthnicityMenuExpanded)
    }



    fun onCountryOriginChanged(countryOrigin: String){
        _uiState.value = _uiState.value.copy(countryOrigin = countryOrigin)
    }

    fun toggleCountryOriginMenuExpansion(){
        _uiState.value = _uiState.value.copy(isCountryOriginMenuExpanded = !_uiState.value.isCountryOriginMenuExpanded)
    }



    fun onMajorChanged(major: String){
        _uiState.value = _uiState.value.copy(major = major)
    }

    fun toggleMajorMenuExpansion(){
        _uiState.value = _uiState.value.copy(isMajorMenuExpanded = !_uiState.value.isMajorMenuExpanded)
    }



    fun onYearChanged(year: String){
        _uiState.value = _uiState.value.copy(year = year)
    }

    fun toggleYearMenuExpansion(){
        _uiState.value = _uiState.value.copy(isYearMenuExpanded = !_uiState.value.isYearMenuExpanded)
    }




    fun onGraduationYearChanged(graduationYear: String){
        _uiState.value = _uiState.value.copy(graduationYear = graduationYear)
    }

    fun toggleGraduationMenuExpansion(){
        _uiState.value = _uiState.value.copy(isGraduationMenuExpanded = !_uiState.value.isGraduationMenuExpanded)
    }

}
