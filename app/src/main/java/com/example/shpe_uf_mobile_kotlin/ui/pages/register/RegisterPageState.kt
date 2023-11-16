package com.example.shpe_uf_mobile_kotlin.ui.pages.register;

data class RegisterPageState(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,

    val emailErrorMessage: String? = null,
    val firstNameErrorMessage: String? = null,
    val lastNameErrorMessage: String? = null,
    val passwordErrorMessage: String? = null,
    val confirmPasswordErrorMessage: String? = null,

    val isPasswordVisible: Boolean = true,
    val isConfirmPasswordVisible: Boolean = true

)