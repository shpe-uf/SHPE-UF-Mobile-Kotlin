package com.example.shpe_uf_mobile_kotlin.ui.pages.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apolloClient
import com.apollographql.apollo3.api.Optional
import com.example.shpe_uf_mobile_kotlin.RegisterMutation
import com.example.shpe_uf_mobile_kotlin.type.RegisterInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterPage1ViewModel: ViewModel() {
    //Mutable State Flow that holds the state of the Register Screen View
    private val _uiState = MutableStateFlow(RegisterPage1State())
    val uiState: StateFlow<RegisterPage1State> = _uiState.asStateFlow()

    // Validation Function for each Separate Text Field
    fun validateEmailField(){
        val emailFieldState = _uiState.value

        val isValidEmail = validateEmail(emailFieldState.email ?: "")

        _uiState.value = emailFieldState.copy(emailErrorMessage = isValidEmail)
    }

    fun validateUsernameField(){
        val usernameFieldState = _uiState.value

        val isValidUsername = validateUsername(usernameFieldState.username ?: "")

        _uiState.value = usernameFieldState.copy(usernameErrorMessage = isValidUsername)
    }

    fun validatePasswordField(){
        val passwordFieldState = _uiState.value

        val isValidPassword = validatePassword(passwordFieldState.password ?: "")

        _uiState.value = passwordFieldState.copy(passwordErrorMessage = isValidPassword)
    }

    fun validateConfirmPasswordField(){
        val passwordFieldState = _uiState.value

        val confirmPasswordFieldState = _uiState.value

        val isValidConfirmPassword = validateConfirmPassword(passwordFieldState.password ?: "",confirmPasswordFieldState.confirmPassword ?: "")

        _uiState.value = confirmPasswordFieldState.copy(confirmPasswordErrorMessage = isValidConfirmPassword)

    }

    fun validateRegisterPage1Fields(): Boolean {

        val emailFieldState = _uiState.value

        val usernameFieldState = _uiState.value

        val passwordFieldState = _uiState.value

        val confirmPasswordFieldState = _uiState.value

        validateEmailField()
        validateUsernameField()
        validatePasswordField()
        validateConfirmPasswordField()

        Log.d("validate Page 1", "You hit the end of the validatePage1Fields function")

        if (validateEmail(emailFieldState.email ?: "") == null &&
            validateUsername(usernameFieldState.username ?: "") == null &&
            validatePassword(passwordFieldState.password ?: "") == null &&
            validateConfirmPassword(passwordFieldState.password ?: "",confirmPasswordFieldState.confirmPassword ?: "") == null
            ){
            return true
        }
        else return false


    }









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
            currentState.confirmPassword ?: "")
        // Latest added by Ant
        val isValidGender = validateGender(currentState.gender ?: "")
        val isValidEthnicity = validateEthnicity(currentState.ethnicity ?: "")
        val isValidCountryOrigin = validateCountryOrigin(currentState.countryOrigin ?: "")
        val isValidMajor = validateMajor(currentState.major ?: "")
        val isValidYear = validateYear(currentState.year ?: "")
        val isValidGraduationYear = validateGraduationYear(currentState.graduationYear ?: "")



        //Update state with error messages
        _uiState.value = currentState.copy(
            usernameErrorMessage = isValidUsername,
            firstNameErrorMessage = isValidFirstName,
            lastNameErrorMessage = isValidLastName,
            emailErrorMessage = isValidEmail,
            passwordErrorMessage = isValidPassword,
            confirmPasswordErrorMessage = isPasswordConfirmed,

            // ADD IN FOR GENDER and other Dropdown menus
            genderErrorMessage = isValidGender,
            ethnicityErrorMessage = isValidEthnicity,
            countryOriginErrorMessage = isValidCountryOrigin,
            majorErrorMessage = isValidMajor,
            yearErrorMessage = isValidYear,
            graduationYearErrorMessage = isValidGraduationYear
        )

        //Register user if all validations are passed
        if (currentState.usernameErrorMessage == null
            && currentState.firstNameErrorMessage == null
            && currentState.lastNameErrorMessage == null
            && currentState.emailErrorMessage == null
            && currentState.passwordErrorMessage == null
            && currentState.confirmPasswordErrorMessage == null
            // ADD IN FOR GENDER and other dropdown menus
            && currentState.genderErrorMessage == null
            && currentState.ethnicityErrorMessage == null
            && currentState.countryOriginErrorMessage == null
            && currentState.majorErrorMessage == null
            && currentState.yearErrorMessage == null
            && currentState.graduationYearErrorMessage == null
            ) {
            // pass in individual fields into the performRegister function
            // TODO: Here is space after validation has been done checking for errors
            // TODO: Need to make create a map/array so that it can be passed in as input to the graphQL function
            // TODO: Call performRegister function w/ input to make the

            if (currentState.firstName is String
                && currentState.lastName is String
                && currentState.username is String
                && currentState.email is String
                && currentState.password is String
                && currentState.confirmPassword is String
                && currentState.gender is String
                && currentState.ethnicity is String
                && currentState.countryOrigin is String
                && currentState.major is String
                && currentState.year is String
                && currentState.graduationYear is String
                && currentState.listServ is String) { val registerInput = RegisterInput(
                    firstName = currentState.firstName,
                    lastName = currentState.lastName,
                    username = currentState.username,
                    email = currentState.email,
                    password = currentState.password,
                    confirmPassword = currentState.confirmPassword,
                    sex = currentState.gender,
                    ethnicity = currentState.ethnicity,
                    country = currentState.countryOrigin,
                    major = currentState.major,
                    year = currentState.year,
                    graduating = currentState.graduationYear,
                    listServ = "false")
                Log.d("Hello", "yOu are in the validate and function")


                performRegister(Optional.presentIfNotNull(registerInput))

                Log.d("Hello", "yOu completed the performRegister()")



            }



        }

    }

// TODO UNCOMMENT THE STUFF THAT MAKES ERRORS AND FIX BY MAKING MAP TO PASS THROUGH FUNCTION ASK ANDREI
// Add all inputs needed firstname, lastname, country, password, etc. and pass into registerUser


    private fun performRegister(registerInput: Optional<RegisterInput?>){
        /*
            Launches a new coroutine in the viewModelScope, provided by the ViewModel.
            Ensures the coroutine is cancelled when the ViewModel is cleared. Important for avoiding memory leaks.
        */
        viewModelScope.launch {// Everything in the {} runs asynchronously.
            val registerSuccess = registerUser(registerInput)

//            if(!loginSuccess) updateErrorMessage("Could not login.") else updateErrorMessage("Logged in.")
//            // make a compose function updateErrorMessage to know whether or not it registered using UI

        }
    }

    // Handles user login using graphQL.
    // use a map with key and values to have a vector of strings to input
    private suspend fun registerUser(registerInput: Optional<RegisterInput?>): Boolean {
        val response = apolloClient.mutation(RegisterMutation(registerInput)).execute()

        if (!response.hasErrors()) {
            val id = response.data?.register?.id
            Log.d("GraphQL:", " $id")
            return true
        } else { // Else, the user provided incorrect credentials.
            Log.d("GraphQL:", "Could not login.")
            return false
        }
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


    // TODO: IMPLEMENT VALIDATE FUNCTIONS for remaining "special" fields needed in sign up
    private fun validateGender(gender: String): String? {
        return null
    }

    private fun validateEthnicity(ethnicity: String): String? {
        return null
    }

    private fun validateCountryOrigin(countryOrigin: String): String? {
        return null
    }

    private fun validateMajor(major: String): String? {
        return null
    }

    private fun validateYear(year: String): String? {
        return null
    }

    private fun validateGraduationYear(graduationYear: String): String? {
        return null
    }



    //TODO IMPLEMENT THE VALIDATE GENDER FUNCTION FROM SHPE SERVER and others for all dropdown menus

    //Functions to update the state for each input field
    fun onUsernameChanged(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
        validateUsernameField()
    }

    fun onFirstNameChanged(firstName: String) {
        _uiState.value = _uiState.value.copy(firstName = firstName)
    }

    fun onLastNameChanged(lastName: String) {
        _uiState.value = _uiState.value.copy(lastName = lastName)
    }

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
        validateEmailField()
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
        validatePasswordField()
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
        validateConfirmPasswordField()
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
