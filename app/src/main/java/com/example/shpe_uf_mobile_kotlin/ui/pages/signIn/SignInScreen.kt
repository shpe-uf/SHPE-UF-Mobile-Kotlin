package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.custom.SuperiorTextField
import com.example.shpe_uf_mobile_kotlin.ui.navigation.Routes
import com.example.shpe_uf_mobile_kotlin.ui.theme.ThemeColors

@Composable
fun SignIn(navController: NavController) {
    SignInBackground()
    SignInScreen(navController)
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
fun SignInScreen(navController: NavController) {

    val signInViewModel = remember { SignInViewModel() }
    val uiState by signInViewModel.uiState.collectAsState()
    val shpeLogo = R.drawable.shpe_logo_full_color

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
                    onClick = { signInViewModel.validateAndLoginUser(navController) }
                )
            }
            Row(modifier = Modifier.padding(top = 20.dp)) {
                SignUp()
            }
        }
    }

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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
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

    val image = if (isPasswordVisible)
        R.drawable.state_selected
    else
        R.drawable.state_default

    // Custom built text field that matches figma design.
    SuperiorTextField(
        label = "Password",
        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
        onValueChange = onValueChange,
        value = value,
        leadingIcon = R.drawable.lock_3,
        trailingIcon = image,
        trailingIconOnClick = { viewModel.togglePasswordVisibility() },
        visualTransformation = { if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation() },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
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
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            )
        )
    }
}

@Composable
fun SignUp() {
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

    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(color = labelColor, fontSize = 14.sp, fontWeight = FontWeight(400))
        ) {
            append("Don't have an account? ")
        }

        // We attach this *URL* annotation to the following content
        // until `pop()` is called
        pushStringAnnotation(
            tag = "URL", annotation = "https://developer.android.com"
        )
        withStyle(
            style = SpanStyle(
                color = signUpColor, fontSize = 14.sp, fontWeight = FontWeight(400)
            )
        ) {
            append("Sign Up")
        }

        pop()
    }

    ClickableText(text = annotatedText, onClick = { offset ->
        // We check if there is an *URL* annotation attached to the text
        // at the clicked position
        annotatedText.getStringAnnotations(
            tag = "URL", start = offset, end = offset
        ).firstOrNull()?.let { annotation ->
            // If yes, we log its value
            Log.d("Clicked URL", annotation.item)
        }
    })
}

