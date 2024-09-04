package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
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
import com.example.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.example.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import com.example.shpe_uf_mobile_kotlin.ui.theme.WhiteSHPE

@Preview
@Composable
fun SignIn(){
    SignInBackground()
    SignInScreen()
}

@Composable
fun SignInBackground() {

    // Dark mode support
    val gator = if (isSystemInDarkTheme()){
        painterResource(R.drawable.gator)
    }
    else {
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
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
        )
    }
}

@Preview(device = "spec:width=1080px,height=2340px,dpi=240")
@Composable
fun SignInScreen() {

    val signInViewModel = remember { SignInViewModel() }
    val uiState by signInViewModel.uiState.collectAsState()
    val shpeLogo = R.drawable.shpe_logo_full_color

    // Dark mode support
    val background = if (isSystemInDarkTheme()){
        ThemeColors.Night.background
    }
    else {
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
            Spacer(modifier = Modifier.height(41.dp))
            Row{
                Image(
                    painter = painterResource(shpeLogo),
                    contentDescription = shpeLogo.toString(),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(93.dp)
                        .width(86.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row{
                Text(
                    text = "SIGN IN",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD25917)
                )
            }
            Spacer(modifier = Modifier.height(88.dp))
            Row{
                UserNameInput(
                    value = uiState.username ?: "",
                    onValueChange = { signInViewModel.onUsernameChanged(it) }
                )
            }
            Row{
                PasswordInput(
                    value = uiState.password ?: "",
                    onValueChange = { signInViewModel.onPasswordChanged(it) },
                    isPasswordInvisible = uiState.isPasswordInvisible,
                    signInViewModel
                )
            }
            Spacer(modifier = Modifier.height(85.dp))

            Row{
                SignInButton(
                    onClick = { signInViewModel.validateAndLoginUser() }
                )
            }

//            Row{
//                LoginErrorMessage(
//                    value = uiState.loginErrorMessage ?: ""
//                )
//            }

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
    // Dark mode support
    val labelColor = if (isSystemInDarkTheme()){
        Color.White
    }
    else {
        Color.Black
    }


    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Username",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            ),
            color = labelColor,
            modifier = Modifier
                .padding(horizontal = 11.dp, vertical = 6.53.dp)
        )

            TextField(
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors( // These colors match the FIGMA design.
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent, // Transparent to not show.
                    unfocusedIndicatorColor = Color.Transparent // Transparent to not show.
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.profile_circle),
                        contentDescription = "Person",
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                value = value,
                onValueChange = { onValueChange(it) },
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp),
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
    isPasswordInvisible: Boolean,
    viewModel: SignInViewModel
) {
    // Dark mode support
    val labelColor = if (isSystemInDarkTheme()){
        Color.White
    }
    else {
        Color.Black
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Password",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            ),
            color = labelColor,
            modifier = Modifier
                .padding(horizontal = 9.22.dp, vertical = 5.53.dp)
        )

        TextField(
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors( // These colors match the FIGMA design.
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent, // Transparent to not show.
                unfocusedIndicatorColor = Color.Transparent // Transparent to not show.
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.lock_3),
                    contentDescription = "Lock",
                )
            },
            trailingIcon = {
                val image = if(isPasswordInvisible)
                    painterResource(R.drawable.state_default)
                else
                    painterResource(R.drawable.state_selected)

                IconButton(onClick = { viewModel.togglePasswordVisibility() }){
                       Icon(painter = image, contentDescription = image.toString())
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = { onValueChange(it) },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
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
    // Dark mode support
    val labelColor = if (isSystemInDarkTheme()){
        Color.White
    }
    else {
        Color.Black
    }

    val signUpColor = if(isSystemInDarkTheme()){
        Color(0xFF93E1FF)
    }
    else {
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

