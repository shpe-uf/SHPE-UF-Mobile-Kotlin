package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

data class SignInState(
    val username: String? = null,
    val password: String? = null,

    val usernameErrorMessage: String? = null,
    val passwordErrorMessage: String? = null,
    val loginErrorMessage: String? = null,

    val isPasswordVisible: Boolean = true
)

