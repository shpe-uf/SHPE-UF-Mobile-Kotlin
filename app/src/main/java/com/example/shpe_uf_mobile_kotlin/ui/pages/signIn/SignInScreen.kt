package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.ui.custom.SuperiorTextField
import com.example.shpe_uf_mobile_kotlin.ui.navigation.NavRoute
import com.example.shpe_uf_mobile_kotlin.ui.theme.ThemeColors

@Composable
fun SignIn(navController: NavHostController, shpeUFAppViewModel: SHPEUFAppViewModel) {
    SignInBackground()
    SignInScreen(navController, shpeUFAppViewModel)
}

@Composable
fun SignInBackground() {

    // Dark mode support
    val gator = if (isSystemInDarkTheme()) {
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

@Composable
fun SignInScreen(navController: NavHostController, shpeUFAppViewModel: SHPEUFAppViewModel) {

    val signInViewModel = remember { SignInViewModel() }
    val uiState by signInViewModel.uiState.collectAsState()
    val shpeLogo = R.drawable.shpe_logo_full_color
    val context = LocalContext.current

    // Dark mode support
    val background = if (isSystemInDarkTheme()) {
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
            //Spacer(modifier = Modifier.height(30.dp))
            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = "SIGN IN",
                style = TextStyle(
                    fontSize = 50.sp,
                    //fontFamily = FontFamily(Font(R.font.viga)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFD25917)
                )
            )
            //Spacer(modifier = Modifier.height(88.dp))
            Row(modifier = Modifier.padding(top = 88.dp)) {
                UserNameInput(
                    value = uiState.username ?: "",
                    onValueChange = { signInViewModel.onUsernameChanged(it) }
                )
            }

            //Spacer(Modifier.height(24.dp))
            Row(modifier = Modifier.padding(top = 24.dp)) {
                PasswordInput(
                    value = uiState.password ?: "",
                    onValueChange = { signInViewModel.onPasswordChanged(it) },
                    isPasswordVisible = uiState.isPasswordVisible,
                    viewModel = signInViewModel
                )
            }

            //Spacer(modifier = Modifier.height(85.dp))
            Row(modifier = Modifier.padding(top = 85.dp)) {
                SignInButton(
                    onClick = { OnSignInClick(navController,signInViewModel, shpeUFAppViewModel) }
                )
            }
            Row(modifier = Modifier.padding(top = 1.dp)) {
                SignUp(navController)
            }

            // display toast if the login fails
            if (uiState.loginErrorMessage != null) {
                uiState.loginErrorMessage?.let { it ->
                    val text = it
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(context, text, duration)
                    toast.show()

                    signInViewModel.updateErrorMessage(null)
                }
            }
        }
    }

}

fun OnSignInClick(navController: NavHostController, signInViewModel: SignInViewModel, shpeUFAppViewModel: SHPEUFAppViewModel){
    signInViewModel.validateAndLoginUser(shpeUFAppViewModel)
    val success = shpeUFAppViewModel.uiState.value
    if(success.isLoggedIn){ navController.navigate(NavRoute.HOME) }
    if(success.isLoggedIn){ navController.navigate(NavRoute.POINTS) }
}

fun onSignUpClick(navController: NavHostController){
    navController.navigate(NavRoute.REGISTER)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameInput(
    value: String,
    onValueChange: (String) -> Unit,
) {
    SuperiorTextField(
        label = "Username",
        labelModifier = Modifier.padding(horizontal = 11.dp, vertical = 6.53.dp),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = R.drawable.profile_circle,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        leadingIconModifier = Modifier.size(28.dp).padding(start = 12.dp)
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    viewModel: SignInViewModel,
) {

    val labelColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    val image = if (isPasswordVisible)
        R.drawable.state_selected
    else
        R.drawable.state_default

    // Custom built text field that matches figma design.
    SuperiorTextField(
        label = "Password",
        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = R.drawable.lock_3,
        trailingIcon = image,
        visualTransformation = { if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation() },
        trailingIconOnClick = { viewModel.togglePasswordVisibility() },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIconModifier = Modifier.size(28.dp).padding(start = 12.dp)
    )
}

@Composable
fun SignInButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 65.dp, vertical = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFD25917),
            contentColor = Color.White // Set text color
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

@Composable
fun SignUp(navController: NavHostController) {
    // Dark mode support
    val labelColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    val signUpColor = if (isSystemInDarkTheme()) {
        Color(0xFF93E1FF)
    } else {
        Color(0xFF0B70BA)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
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
        }){
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

