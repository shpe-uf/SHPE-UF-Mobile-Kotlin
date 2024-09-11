package com.example.shpe_uf_mobile_kotlin.ui.pages.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Optional
import com.example.shpe_uf_mobile_kotlin.RegisterMutation
import com.example.shpe_uf_mobile_kotlin.apolloClient
import com.example.shpe_uf_mobile_kotlin.type.RegisterInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterPage1ViewModel: ViewModel() {
    //Mutable State Flow that holds the state of the Register Screen View
    private val _uiState = MutableStateFlow(RegisterPage1State())
    val uiState: StateFlow<RegisterPage1State> = _uiState.asStateFlow()



    // Validation Functions for each Separate Text Field
    fun validateEmailField(){
        val emailFieldState = _uiState.value

        val possibleEmailError = validateEmail(emailFieldState.email ?: "")

        _uiState.value = emailFieldState.copy(emailErrorMessage = possibleEmailError)
    }

    fun validateUsernameField(){
        val usernameFieldState = _uiState.value

        val possibleUsernameError = validateUsername(usernameFieldState.username ?: "")

        _uiState.value = usernameFieldState.copy(usernameErrorMessage = possibleUsernameError)
    }

    fun validatePasswordField(){
        val passwordFieldState = _uiState.value

        val possiblePasswordError = validatePassword(passwordFieldState.password ?: "")

        _uiState.value = passwordFieldState.copy(passwordErrorMessage = possiblePasswordError)
    }

    fun validateConfirmPasswordField(){
        val passwordFieldState = _uiState.value

        val confirmPasswordFieldState = _uiState.value

        val possibleConfirmPasswordError = validateConfirmPassword(passwordFieldState.password ?: "",confirmPasswordFieldState.confirmPassword ?: "")

        _uiState.value = confirmPasswordFieldState.copy(confirmPasswordErrorMessage = possibleConfirmPasswordError)

    }

    // Function to validate all fields on page 1 before being able to proceed
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

    fun validateFirstNameField(){
        val firstNameFieldState = _uiState.value

        val possibleFirstNameError = validateFirstName(firstNameFieldState.firstName ?: "")

        _uiState.value = firstNameFieldState.copy(firstNameErrorMessage = possibleFirstNameError)

    }

    fun validateLastNameField(){
        val lastNameFieldState = _uiState.value

        val possibleLastNameError = validateLastName(lastNameFieldState.lastName ?: "")

        _uiState.value = lastNameFieldState.copy(lastNameErrorMessage = possibleLastNameError)

    }

    fun validateGenderDropdownField(){
        val genderDropdownFieldState = _uiState.value

        val possibleGenderError = validateGender(genderDropdownFieldState.gender ?: "")

        _uiState.value = genderDropdownFieldState.copy(genderErrorMessage = possibleGenderError)

    }

    fun validateEthnicityDropdownField(){
        val ethnicityDropdownFieldState = _uiState.value

        val possibleEthnicityError = validateEthnicity(ethnicityDropdownFieldState.ethnicity ?: "")

        _uiState.value = ethnicityDropdownFieldState.copy(ethnicityErrorMessage = possibleEthnicityError)

    }

    fun validateCountryOriginDropdownField(){
        val countryOriginDropdownFieldState = _uiState.value

        val possibleCountryOriginError = validateCountryOrigin(countryOriginDropdownFieldState.countryOrigin ?: "")

        _uiState.value = countryOriginDropdownFieldState.copy(countryOriginErrorMessage = possibleCountryOriginError)

    }

    // Function to validate all fields on page 2 before being able to proceed
    fun validateRegisterPage2Fields(): Boolean {
        val firstNameFieldState = _uiState.value

        val lastNameFieldState = _uiState.value

        val genderDropdownFieldState = _uiState.value

        val ethnicityDropdownFieldState = _uiState.value

        val countryOriginDropdownFieldState = _uiState.value

        validateFirstNameField()
        validateLastNameField()
        validateGenderDropdownField()
        validateEthnicityDropdownField()
        validateCountryOriginDropdownField()


        Log.d("validate Page 2", "You hit the end of the validatePage2Fields function")

        Log.d("validate Page 2 Last name", "$")

        if (validateFirstName(firstNameFieldState.firstName ?: "") == null &&
            validateLastName(lastNameFieldState.lastName ?: "") == null &&
            validateGender(genderDropdownFieldState.gender ?: "") == null &&
            validateEthnicity(ethnicityDropdownFieldState.ethnicity ?: "") == null &&
            validateCountryOrigin(countryOriginDropdownFieldState.countryOrigin ?: "") == null

        ){
            return true
        }
        else return false
    }

    fun validateMajorDropdownField(){
        val majorDropdownFieldState = _uiState.value

        val possibleMajorError = validateMajor(majorDropdownFieldState.major ?: "")

        _uiState.value = majorDropdownFieldState.copy(majorErrorMessage = possibleMajorError)

    }

    fun validateYearDropdownField(){
        val yearDropdownFieldState = _uiState.value

        val possibleYearError = validateYear(yearDropdownFieldState.year ?: "")

        _uiState.value = yearDropdownFieldState.copy(yearErrorMessage = possibleYearError)

    }

    fun validateGraduationYearDropdownField(){
        val graduationYearDropdownFieldState = _uiState.value

        val possibleGraduationYearError = validateGraduationYear(graduationYearDropdownFieldState.graduationYear ?: "")

        _uiState.value = graduationYearDropdownFieldState.copy(graduationYearErrorMessage = possibleGraduationYearError)

    }



    // Function to validate all fields on page 3 before being able to proceed
    fun validateRegisterPage3Fields(): Boolean {
        val majorDropdownFieldState = _uiState.value

        val yearDropdownFieldState = _uiState.value

        val graduationYearDropdownFieldState = _uiState.value

        validateMajorDropdownField()
        validateYearDropdownField()
        validateGraduationYearDropdownField()


        Log.d("validate Page 3", "You hit the end of the validatePage3Fields function")

        if (validateMajor(majorDropdownFieldState.major ?: "") == null &&
            validateYear(yearDropdownFieldState.year ?: "") == null &&
            validateGraduationYear(graduationYearDropdownFieldState.graduationYear ?: "") == null

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
            && currentState.genderErrorMessage == null
            && currentState.ethnicityErrorMessage == null
            && currentState.countryOriginErrorMessage == null
            && currentState.majorErrorMessage == null
            && currentState.yearErrorMessage == null
            && currentState.graduationYearErrorMessage == null
            ) {
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

// Add all inputs needed firstname, lastname, country, password, etc. and pass into registerUser


    private fun performRegister(registerInput: Optional<RegisterInput?>){
        /*
            Launches a new coroutine in the viewModelScope, provided by the ViewModel.
            Ensures the coroutine is cancelled when the ViewModel is cleared. Important for avoiding memory leaks.
        */
        viewModelScope.launch {// Everything in the {} runs asynchronously.
            val registerSuccess = registerUser(registerInput)
        }
    }

    // Handles user login using graphQL.
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



    private fun validateGender(gender: String): String? {
        return if (gender.isBlank()) "Gender is required." else null
    }

    private fun validateEthnicity(ethnicity: String): String? {
        return if (ethnicity.isBlank()) "Ethnicity is required." else null
    }

    private fun validateCountryOrigin(countryOrigin: String): String? {
        return if (countryOrigin.isBlank()) "Country of origin is required." else null
    }

    private fun validateMajor(major: String): String? {
        return if (major.isBlank()) "Major is required." else null
    }

    private fun validateYear(year: String): String? {
        return if (year.isBlank()) "Year is required." else null
    }

    private fun validateGraduationYear(graduationYear: String): String? {
        return if (graduationYear.isBlank()) "Graduation year is required." else null
    }


    //Functions to update the state for each input field
    fun onUsernameChanged(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
        validateUsernameField()
    }

    fun onFirstNameChanged(firstName: String) {
        _uiState.value = _uiState.value.copy(firstName = firstName)
        validateFirstNameField()
    }

    fun onLastNameChanged(lastName: String) {
        _uiState.value = _uiState.value.copy(lastName = lastName)
        validateLastNameField()
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
        validateGenderDropdownField()
    }

    fun toggleGenderMenuExpansion(){
        _uiState.value = _uiState.value.copy(isGenderMenuExpanded = !_uiState.value.isGenderMenuExpanded)
    }



    fun onEthnicityChanged(ethnicity: String){
        _uiState.value = _uiState.value.copy(ethnicity = ethnicity)
        validateEthnicityDropdownField()
    }

    fun toggleEthnicityMenuExpansion(){
        _uiState.value = _uiState.value.copy(isEthnicityMenuExpanded = !_uiState.value.isEthnicityMenuExpanded)
    }



    fun onCountryOriginChanged(countryOrigin: String){
        _uiState.value = _uiState.value.copy(countryOrigin = countryOrigin)
        validateCountryOriginDropdownField()
    }

    fun toggleCountryOriginMenuExpansion(){
        _uiState.value = _uiState.value.copy(isCountryOriginMenuExpanded = !_uiState.value.isCountryOriginMenuExpanded)
    }



    fun onMajorChanged(major: String){
        _uiState.value = _uiState.value.copy(major = major)
        validateMajorDropdownField()
    }

    fun toggleMajorMenuExpansion(){
        _uiState.value = _uiState.value.copy(isMajorMenuExpanded = !_uiState.value.isMajorMenuExpanded)
    }



    fun onYearChanged(year: String){
        _uiState.value = _uiState.value.copy(year = year)
        validateYearDropdownField()
    }

    fun toggleYearMenuExpansion(){
        _uiState.value = _uiState.value.copy(isYearMenuExpanded = !_uiState.value.isYearMenuExpanded)
    }



    fun onGraduationYearChanged(graduationYear: String){
        _uiState.value = _uiState.value.copy(graduationYear = graduationYear)
        validateGraduationYearDropdownField()
    }

    fun toggleGraduationMenuExpansion(){
        _uiState.value = _uiState.value.copy(isGraduationMenuExpanded = !_uiState.value.isGraduationMenuExpanded)
    }

}
