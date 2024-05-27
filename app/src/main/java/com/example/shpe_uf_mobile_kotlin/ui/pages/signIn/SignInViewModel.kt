package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apolloClient
import com.example.shpe_uf_mobile_kotlin.LoginMutation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    // Contains the ui state, the UI is composed of its state and visual components.
    private val _uiState = MutableStateFlow(SignInState())

    // _uiState is only accessible within the viewmodel, this makes it read only.
    val uiState: StateFlow<SignInState> = _uiState

    /* This function checks the username and password fields for nullness (emptyness) and performs a login mutation accessing the database to login.

        Of course, if the username or password is not found, it won't login.
    */

    fun validateAndLoginUser() {
        val currentState = getCurrentState()

        // Validate user input fields.
        val isValidUsername = validateUsername(currentState.username ?: "")
        val isValidPassword = validatePassword(currentState.password ?: "")

        // Update state with error messages.
        _uiState.value = currentState.copy(
            usernameErrorMessage = if(isValidUsername) null else "Username is required.",
            passwordErrorMessage = if(isValidPassword) null else "Password is required."
        )

        // Get the current username and password values.
        val username = getUsername()
        val password = getPassword()

        // Login user if validations passed.
        if (isValidUsername && isValidPassword) {
            Log.d("Validating", "$username | $password")

            // Calls a function to perform login.
            performLogin(username.toString(), password.toString())
        } else {
            Log.w("Validation", "${currentState.usernameErrorMessage} | ${currentState.passwordErrorMessage}")
        }
    }

    private fun performLogin(username: String, password: String) {

        /*
            Launches a new coroutine in the viewModelScope, provided by the ViewModel.
            Ensures the coroutine is cancelled when the ViewModel is cleared. Important for avoiding memory leaks.
        */
        viewModelScope.launch {// Everything in the {} runs asynchronously.
            val loginSuccess = loginUser(username, password) // This call will suspend the coroutine until the login operation is complete.

            // If login is unsuccessful, do nothing, else change it to logged in.
            if (loginSuccess) updateErrorMessage("Logged in.") else updateErrorMessage("Could not login.")
        }
    }

    // It's defined as a suspend function b/c it uses a network request which could take some time, and we don't want to pause the UI while the mutation is run.
    private suspend fun loginUser(username: String, password: String): Boolean {
        // Mutation for logging in, returns the user's id on success.
        val response = apolloClient.mutation(LoginMutation(username, password, "true")).execute()

        // If the response doesn't throw an error, then it successfully logged in.
        if (!response.hasErrors()) {
            val id = response.data?.login?.id
            Log.d("GraphQL", "$id")
            return true
        } else { // Else, the user provided incorrect credentials.
            Log.w("GraphQL", "Could not login.")
            return false
        }
    }

    // Updates the login error message
    private fun updateErrorMessage(message: String) {
        val currentState = getCurrentState()
        _uiState.value = currentState.copy(
            loginErrorMessage = message
        )
    }

    // Getters for username, password, and the current app state.
    private fun getUsername(): String? {
        return getCurrentState().username
    }
    private fun getPassword(): String? {
        return getCurrentState().password
    }
    private fun getCurrentState(): SignInState{
        return _uiState.value
    }

    // Username and password validations are simply checking if it's null or blank.
    private fun validateUsername(username: String): Boolean {
        return !username.isNullOrBlank()
    }
    private fun validatePassword(password: String): Boolean {
        return !password.isNullOrBlank()
    }

    // Change the username value to whatever the user typed in.
    fun onUsernameChanged(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername)
    }

    // Change the password value to whatever the user typed in.
    fun onPasswordChanged(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    // TODO: Implement password visibility in UI.
    // Changes the password's visibility.
    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }
}
