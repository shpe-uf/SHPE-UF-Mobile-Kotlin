package com.example.shpe_uf_mobile_kotlin.ui.pages.register

data class RegisterPage1State (
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,


    // New addition by me for gender dropdown menus
    val gender: String? = null,
    val genderErrorMessage: String? = null,
    val isGenderMenuExpanded: Boolean = false,

    val ethnicity: String? = null,
    val ethnicityErrorMessage: String? = null,
    val isEthnicityMenuExpanded: Boolean = false,

    val countryOrigin: String? = null,
    val countryOriginErrorMessage: String? = null,
    val isCountryOriginMenuExpanded: Boolean = false,

    val major: String? = null,
    val majorErrorMessage: String? = null,
    val isMajorMenuExpanded: Boolean = false,

    val year: String? = null,
    val yearErrorMessage: String? = null,
    val isYearMenuExpanded: Boolean = false,

    val graduationYear: String? = null,
    val graduationYearErrorMessage: String? = null,
    val isGraduationMenuExpanded: Boolean = false,

    val listServ: String? = "false",



    val usernameErrorMessage: String? = null,
    val emailErrorMessage: String? = null,
    val firstNameErrorMessage: String? = null,
    val lastNameErrorMessage: String? = null,
    val passwordErrorMessage: String? = null,
    val confirmPasswordErrorMessage: String? = null,

    val isPasswordVisible: Boolean = true,
    val isConfirmPasswordVisible: Boolean = true


)