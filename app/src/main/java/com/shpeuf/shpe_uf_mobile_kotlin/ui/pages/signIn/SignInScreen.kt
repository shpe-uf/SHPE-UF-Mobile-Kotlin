package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.signIn

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shpeuf.shpe_uf_mobile_kotlin.R
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.initializeViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.custom.SuperiorTextField
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.NavRoute
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors

private fun showCustomToast(
    context: Context,
    message: String,
    isError: Boolean = false
) {
    val view = LayoutInflater.from(context)
        .inflate(R.layout.custom_toast, null)

    val textView = view.findViewById<TextView>(R.id.toast_message)

    textView.text = message
    textView.setTextColor(android.graphics.Color.WHITE)

    val toast = Toast(context.applicationContext)
    toast.duration = if (isError) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)

    toast.view = view
    toast.show()
}

@Composable
fun SignIn(navController: NavHostController, shpeUFAppViewModel: SHPEUFAppViewModel) {
    val appState by shpeUFAppViewModel.uiState.collectAsState()
    val isDarkMode = appState.isDarkMode

    SignInBackground(isDarkMode)
    SignInScreen(navController, shpeUFAppViewModel, isDarkMode)
}

/**
 * @author Gabriel Munoz
 * @date April 6th, 2025
 * UI background
 */
@Composable
fun SignInBackground(isDarkMode: Boolean) {
    val gator = if (isDarkMode) {
        painterResource(R.drawable.gator)
    } else {
        painterResource(R.drawable.light_gator)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD25917))
    ) {
        Image(
            painter = gator,
            contentDescription = "Gator",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
        )
    }
}

// Displays the form in Android Studio's preview without running the app.
@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    val mockViewModel = initializeViewModel()
    SignInScreen(rememberNavController(), mockViewModel, isDarkMode = false)
}

/**
 * @author Gabriel Munoz
 * @date April 6th, 2025
 * Screen containing visible parts: logo, text, and organization for below composables
 */
@Composable
fun SignInScreen(
    navController: NavHostController,
    shpeUFAppViewModel: SHPEUFAppViewModel,
    isDarkMode: Boolean
) {
    val signInViewModel = remember { SignInViewModel() }
    val uiState by signInViewModel.uiState.collectAsState()
    val appState by shpeUFAppViewModel.uiState.collectAsState()
    val shpeLogo = R.drawable.shpe_logo_full_color
    val context = LocalContext.current

    // React to async login completion — navigate to HOME when isLoggedIn flips to true
    LaunchedEffect(appState.isLoggedIn) {
        if (appState.isLoggedIn) {
            navController.navigate(NavRoute.HOME)
        }
    }

    val background = if (isDarkMode) {
        ThemeColors.Night.background
    } else {
        ThemeColors.Day.background
    }

    Box(
        modifier = Modifier
            .padding(top = 82.dp)
    ) {
        Column(
            modifier = Modifier
                .background(background)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(shpeLogo),
                contentDescription = shpeLogo.toString(),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(93.dp)
                    .width(86.dp)
            )
            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = "SIGN IN",
                style = TextStyle(
                    fontSize = 50.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFD25917)
                )
            )
            Row(modifier = Modifier.padding(top = 88.dp)) {
                UserNameInput(
                    value = uiState.username ?: "",
                    onValueChange = { signInViewModel.onUsernameChanged(it) },
                    isDarkMode = isDarkMode
                )
            }

            Row(modifier = Modifier.padding(top = 24.dp)) {
                PasswordInput(
                    value = uiState.password ?: "",
                    onValueChange = { signInViewModel.onPasswordChanged(it) },
                    isPasswordVisible = uiState.isPasswordVisible,
                    viewModel = signInViewModel,
                    isDarkMode = isDarkMode
                )
            }

            Row(modifier = Modifier.padding(top = 85.dp)) {
                SignInButton(
                    onClick = {
                        OnSignInClick(navController, signInViewModel, shpeUFAppViewModel)
                    }
                )
            }
            Row(modifier = Modifier.padding(top = 1.dp)) {
                SignUp(navController, isDarkMode)
            }
        }
    }

    // Display custom toast if login fails
    LaunchedEffect(uiState.loginErrorMessage) {
        uiState.loginErrorMessage?.let { message ->
            showCustomToast(
                context = context,
                message = message,
                isError = true
            )
            signInViewModel.updateErrorMessage(null)
        }
    }
}

// Validates the input and starts the login request.
fun OnSignInClick(navController: NavHostController, signInViewModel: SignInViewModel, shpeUFAppViewModel: SHPEUFAppViewModel) {
    signInViewModel.validateAndLoginUser(shpeUFAppViewModel)
}

fun onSignUpClick(navController: NavHostController) {
    navController.navigate(NavRoute.REGISTER)
}

/**
 * @author Gabriel Munoz
 * @date April 6th, 2025
 * Custom text field used to enter the username
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameInput(
    value: String,
    onValueChange: (String) -> Unit,
    isDarkMode: Boolean,
) {
    SuperiorTextField(
        label = "Username or Email",
        labelModifier = Modifier.padding(horizontal = 11.dp, vertical = 6.53.dp),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = R.drawable.profile_circle,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        leadingIconModifier = Modifier.size(28.dp).padding(start = 12.dp),
        isDarkMode = isDarkMode
    )
}

/**
 * @author Gabriel Munoz
 * @date April 6th, 2025
 * Custom text field used to enter the password
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    viewModel: SignInViewModel,
    isDarkMode: Boolean,
) {
    val image = if (isPasswordVisible)
        R.drawable.state_selected
    else
        R.drawable.state_default

    SuperiorTextField(
        label = "Password",
        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = R.drawable.lock_3,
        trailingIcon = image,
        visualTransformation = {
            if (isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation()
        },
        trailingIconOnClick = { viewModel.togglePasswordVisibility() },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIconModifier = Modifier.size(28.dp).padding(start = 12.dp),
        isDarkMode = isDarkMode
    )
}

/**
 * @author Gabriel Munoz
 * @date April 6th, 2025
 * Button triggering the logic to perform login operation
 */
@Composable
fun SignInButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 65.dp, vertical = 16.dp)
            .testTag("Sign in button"),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFD25917),
            contentColor = Color.White
        ),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = "Sign In",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.universltstd)),
                fontWeight = FontWeight(400)
            )
        )
    }
}

/**
 * @author Gabriel Munoz
 * @date April 6th, 2025
 * Button triggering navcontroller logic to reroute user to sign up page
 */
@Composable
fun SignUp(navController: NavHostController, isDarkMode: Boolean) {
    val labelColor = if (isDarkMode) Color.White else Color.Black

    val signUpColor = if (isDarkMode) {
        Color(0xFF93E1FF)
    } else {
        Color(0xFF0B70BA)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Don't have an account?",
            color = labelColor,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.universltstd)),
                fontWeight = FontWeight(400),
            )
        )
        TextButton(onClick = {
            onSignUpClick(navController)
        }) {
            Text(
                text = "Sign Up",
                color = signUpColor,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.universltstd)),
                    fontWeight = FontWeight(400),
                )
            )
        }
    }
}