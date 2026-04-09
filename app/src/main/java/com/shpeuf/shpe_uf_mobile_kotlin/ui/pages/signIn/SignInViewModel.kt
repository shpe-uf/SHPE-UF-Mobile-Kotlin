package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.signIn

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpeuf.shpe_uf_mobile_kotlin.apolloClient
import com.shpeuf.shpe_uf_mobile_kotlin.LoginMutation
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author Gabriel Munoz
 * @date April 6, 2025
 *
 * ---- SignInViewModel Class member Variables -----
 *
 * Inheriting class from view model dealing with middle logic specifically
 * pertaining to the login functionality of the SHPE UF Android App
 *
 * @constructor inherets from default
 *
 * @property _uiState mutable containing the Sign in state onject of the UI and visual components
 * within a flow object, this object holds vital information like username/password and error messages
 * @property uiState Conversion to a state flow for the private variable
 * @property _toastMessage an error message, shown on screen
 * @property toastMessage public conversion for the error message as LiveData
 * */
class SignInViewModel : ViewModel() {

    // Contains the ui state, the UI is composed of its state and visual components.
    private val _uiState = MutableStateFlow(SignInState())

    // _uiState is only accessible within the viewmodel, this makes it read only.
    val uiState: StateFlow<SignInState> = _uiState

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    /* This function checks the username and password fields for nullness (emptyness) and performs a login mutation accessing the database to login.

        Of course, if the username or password is not found, it won't login.
    */

    /**
     *  @author Gabriel Munoz
     *  @date April 6, 2025
     *
     * Validates the username and password fields and performs login if they are valid.
     *
     * @param shpeUFAppViewModel ViewModel for saving user session data upon successful login.
     */
    fun validateAndLoginUser(shpeUFAppViewModel: SHPEUFAppViewModel) {
        val currentState = getCurrentState()

        // Validate user input fields.
        val isValidUsername = validateUsername(currentState.username ?: "")
        val isValidPassword = validatePassword(currentState.password ?: "")

        // Update state with error messages.
        _uiState.value = currentState.copy(
            usernameErrorMessage = if (isValidUsername) null else "Username is required.",
            passwordErrorMessage = if (isValidPassword) null else "Password is required."
        )

        // Get the current username and password values.
        val username = getUsername()
        val password = getPassword()

        // Login user if validations passed.
        if (isValidUsername && isValidPassword) {
            Log.d("Validating", "$username | $password")

            // Calls a function to perform login.
            performLogin(username.toString(), password.toString(), shpeUFAppViewModel)
        } else {
            _toastMessage.value = "Username and password is required."
            Log.w(
                "Validation",
                "${currentState.usernameErrorMessage} | ${currentState.passwordErrorMessage}"
            )
        }
    }

    /**
     * @author Gabriel Munoz
     * @date April 6, 2025
     *
     * calls a couroutine and calls later defined login function to perform a comparison
     * with the information with the database to confirm if this login is successful,
     * update state as logged in
     *
     * @param username The username inputted by the User to attempt login
     * @param password The password inputted by the User to attempt login
     * @param shpeUFAppViewModel View model where all the data will be stored to perform other operations
     *
     */
    private fun performLogin(
        username: String,
        password: String,
        shpeUFAppViewModel: SHPEUFAppViewModel
    ) {

        /*
            Launches a new coroutine in the viewModelScope, provided by the ViewModel.
            Ensures the coroutine is cancelled when the ViewModel is cleared. Important for avoiding memory leaks.
        */
        viewModelScope.launch {// Everything in the {} runs asynchronously.
            val id = loginUser(
                username,
                password
            ) // This call will suspend the coroutine until the login operation is complete.

            // If login is unsuccessful, do nothing, else change it to logged in.
            //if (loginSuccess) updateErrorMessage("Logged in.") else updateErrorMessage("Could not login.")
            if(id != null){
                shpeUFAppViewModel.saveUserId(id)
                shpeUFAppViewModel.saveUsername(username)
                shpeUFAppViewModel.saveLoggedIn(true)
                shpeUFAppViewModel.saveLoggedOut(false)
                shpeUFAppViewModel.saveGuest(false) //some foresight
            }
        }
    }

    /**
     * @author Gabriel Munoz
     * @date April 6, 2025
     *
     * calls mutation function generated by apollo responsible for performing the comparison
     * with the database for login, comparinf username / password and returning the user id
     * in the case of success
     *
     * Given success a login message is shown ans the user logs in
     *
     * @param username The username inputted by the User to attempt login
     * @param password The password inputted by the User to attempt login
     *
     */
    // It's defined as a suspend function b/c it uses a network request which could take some time, and we don't want to pause the UI while the mutation is run.
    private suspend fun loginUser(
        username: String,
        password: String
    ): String? {
        // Mutation for logging in, returns the user's id on success.
        val response = apolloClient.mutation(LoginMutation(username, password, "true")).execute()

        // If the response doesn't throw an error, then it successfully logged in.
        if (!response.hasErrors()) {
            val id = response.data?.login?.id

            Log.d("GraphQL", "$id")

            Log.d("Welcome!", "Welcome $username to SHPE UF!")

            return id
        } else { // Else, the user provided incorrect credentials.

            // If login is unsuccessful, display to user why the error occurred.
            updateErrorMessage(response.errors?.firstOrNull()?.message?:"Could not login")
            //Log.d("Error Checker", response.errors?.firstOrNull()?.message?:"Could not login")
            Log.w("GraphQL", "Could not login.")
            return null
        }
    }

    /**
     * @author Gabriel Munoz
     *
     * setter that will update the error message within our UI state
     *
     * @param message string used to encode error message
     *
     */
    // Updates the login error message
    fun updateErrorMessage(message: String?) {
        val currentState = getCurrentState()
        _uiState.value = currentState.copy(
            loginErrorMessage = message
        )
        // see what the error message is
        Log.d("Error Message", "${currentState.loginErrorMessage}")
    }

    /**
     * @author Gabriel Munoz
     * @return gets the username from our UI state object
     */
    // Getters for username, password, and the current app state.
    private fun getUsername(): String? {
        return getCurrentState().username
    }

    /**
     * @author Gabriel Munoz
     *
     * @return gets the password from our UI state object
     */
    private fun getPassword(): String? {
        return getCurrentState().password
    }

    /**
     * @author Gabriel Munoz
     *
     * @return the value of our UI state object
     */
    private fun getCurrentState(): SignInState {
        return _uiState.value
    }

    /**
     * @author Gabriel Munoz
     *
     * @param string field passed in to see if username valid
     *
     * @return Boolean checking the username validity of login (null or blank)
     */
    // Username and password validations are simply checking if it's null or blank.
    private fun validateUsername(username: String): Boolean {
        return !username.isNullOrBlank()
    }

    /**
     * @author Gabriel Munoz
     *
     * @param string field passed in to see if password valid
     *
     * @return Boolean checking the password validity of login (null or blank)
     */
    private fun validatePassword(password: String): Boolean {
        return !password.isNullOrBlank()
    }

    // Change the username value to whatever the user typed in.
    /**
     * @author Gabriel Munoz
     *
     * Updates the username to whatever a user has typed in
     *
     * @param newUsername the typed in value that the user will be changing
     */
    fun onUsernameChanged(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername)
    }

    // Change the password value to whatever the user typed in.
    /**
     * @author Gabriel Munoz
     *
     * Updates the password to whatever a user has typed in
     *
     * @param newPassword the typed in value that the user will be changing
     */
    fun onPasswordChanged(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    // Changes the password's visibility.
    /**
     * @author Gabriel Munoz
     *
     * Changes password to be visible as a user is typing it in.
     */
    fun togglePasswordVisibility() {
        _uiState.value =
            _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }
}