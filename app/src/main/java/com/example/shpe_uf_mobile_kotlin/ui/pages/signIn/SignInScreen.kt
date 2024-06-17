package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R

@Preview
@Composable
fun SignIn() {
    SignInBackground()
    SignInScreen()
}

@Composable
fun SignInBackground() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD25917))
    ) {
        Image(
            painter = painterResource(R.drawable.gator),
            contentDescription = "Gator",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
        )
    }
}

@Composable
fun SignInScreen() {

    val signInViewModel = remember { SignInViewModel() }
    val uiState by signInViewModel.uiState.collectAsState()
    val shpeLogo = R.drawable.shpe_logo_full_color

    Box(
        modifier = Modifier
            .padding(top = 82.dp)
    ) {
        Spacer(modifier = Modifier.height(128.dp))

        Column(
            modifier = Modifier
                .background(Color(0xFF011F35))
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(shpeLogo),
                contentDescription = shpeLogo.toString(),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "SIGN IN",
                style = MaterialTheme.typography.displayLarge,
                color = Color(0xFFD25917)
            )

            Spacer(modifier = Modifier.height(64.dp))

            UserNameInput(
                value = uiState.username ?: "",
                onValueChange = { signInViewModel.onUsernameChanged(it) }
            )
            PasswordInput(
                value = uiState.password ?: "",
                onValueChange = { signInViewModel.onPasswordChanged(it) },
                isPasswordInvisible = uiState.isPasswordInvisible
            )

            Spacer(modifier = Modifier.height(16.dp))

            SignInButton(
                onClick = { signInViewModel.validateAndLoginUser() }
            )

            LoginErrorMessage(
                value = uiState.loginErrorMessage ?: ""
            )

            SignUp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameInput(
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Username",
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 40.dp, vertical = 4.dp)
        )

        TextField(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            value = value,
            onValueChange = { onValueChange(it) },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 25.sp),
            label = { Text("Username") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordInvisible: Boolean
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Password",
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 40.dp, vertical = 4.dp)
        )

        TextField(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text("Password") },
            visualTransformation =
            if (isPasswordInvisible) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}

@Composable
fun SignInButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 72.dp, vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFD25917),
            contentColor = Color.White // Set text color
        ),
        onClick = {
            onClick()
        }
    ) {
        Text("Sign In")
    }
}

@Composable
fun LoginErrorMessage(value: String) {
    Text(value)
}

@Composable
fun SignUp() {
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(Color.White)
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
                color = Color.Blue, fontWeight = FontWeight.Bold
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

