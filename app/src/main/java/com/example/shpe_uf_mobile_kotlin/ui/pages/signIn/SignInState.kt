package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

data class SignInState(
    val email: String? = null,
    val password: String? = null,

    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null,

    val isPasswordVisible: Boolean = true
)

