package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.signIn

/**
 * @author Gabriel Munoz
 * @date April 6, 2025
 *
 * Class used to organize various conditions on the sign in conditions
 *
 * @property username Stores the username of the SHPEito, defaulted to null when nothing has been typed
 * @property password Stores the password of the SHPEito, defaulted to null when nothing has been typed
 * @property usernameErrorMessage Instantiates a default value for an error with the username a user entered
 * @property passwordErrorMessage Instantiates a default value for an error with the password a user entered
 * @property loginErrorMessage Variable used in the event of some other log in issue
 * @property isPasswordVisible Boolean that toggles whether the password a user is entering is visible
 * @property loginSuccess Boolean that indicates the login status of a user
 *
 * */

data class SignInState(

    // By default, username and password are null.
    val username: String? = null,
    val password: String? = null,

    // By default user should be unable to login.
    val usernameErrorMessage: String? = "Username is required.",
    val passwordErrorMessage: String? = "Password is required.",
    val loginErrorMessage: String? = null,

    // By default password should be invisible.
    val isPasswordVisible: Boolean = false,

    val loginSuccess: Boolean = false
)