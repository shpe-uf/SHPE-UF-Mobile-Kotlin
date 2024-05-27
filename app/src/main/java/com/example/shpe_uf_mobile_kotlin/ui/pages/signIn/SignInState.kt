package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

data class SignInState(

    // By default, username and password are null.
    val username: String? = null,
    val password: String? = null,

    // By default user should be unable to login.
    val usernameErrorMessage: String? = "Username is required.",
    val passwordErrorMessage: String? = "Password is required.",
    val loginErrorMessage: String? = null,

    // By default password should be invisible.
    val isPasswordVisible: Boolean = true
)

