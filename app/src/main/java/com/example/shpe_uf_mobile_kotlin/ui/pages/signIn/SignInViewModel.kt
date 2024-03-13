package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.example.shpe_uf_mobile_kotlin.LoginMutation
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel: ViewModel() {

    // Mutable State Flow that holds the state of the Login Screen View.
    private val _uiState = MutableStateFlow(SignInState())
    val uiState: StateFlow<SignInState> = _uiState.asStateFlow()

    // Function to validate and login the user.
    fun validateAndLoginUser(){
        val currentState = _uiState.value

        // Validate user input fields.
        val isValidUsername = validateUsername(currentState.username ?: "")
        val isValidPassword = validatePassword(currentState.password ?: "")

        // Update state with error messages.
        _uiState.value = currentState.copy(
            usernameErrorMessage = isValidUsername,
            passwordErrorMessage = isValidPassword
        )

        // Login user if validations passed.
        if(currentState.usernameErrorMessage == null && currentState.passwordErrorMessage == null){
            Log.d("Validating", currentState.username.toString() + " | " + currentState.password.toString())
            performLogin(currentState.username.toString(),currentState.password.toString())
        }
        else
            Log.d("Validating", "Failure!")
    }



    private fun performLogin(username: String, password: String){
        viewModelScope.launch {
            val loginSuccess = loginUser(username,password)

            if(!loginSuccess) updateErrorMessage("Could not login.") else updateErrorMessage("Logged in.")

        }
    }

    // Handles user login using graphQL.
    private suspend fun loginUser(username: String, password: String): Boolean{
            // Mutation for logging in, returns the user's id on success.
            val response = apolloClient.mutation(LoginMutation(username,password,"true")).execute()

            if(!response.hasErrors()){
                val id = response.data?.login?.id
                Log.d("GraphQL", "${id}")
                return true
            }
            else{
                Log.w("GraphQL", "Could not login.")
                return false
            }
    }

    private fun updateErrorMessage(message: String){
        val currentState = _uiState.value

        _uiState.value = currentState.copy(
            loginErrorMessage = message
        )

    }

    // Validates user inputs.
    private fun validateUsername(username: String) : String?{
        return if(username.isBlank()) "Username is required." else null
    }

    private fun validatePassword(password: String): String?{
        return if(password.isBlank()) "Password is required." else null
    }

    // Functions to update the state for each input field.
    fun onUsernameChanged(username: String){
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun onPasswordChanged(password: String){
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun togglePasswordVisibility(){
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }
}