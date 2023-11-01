package com.example.shpe_uf_mobile_kotlin.ui.pages.register

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RegistrationPage() {
    SHPEUFMobileKotlinTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 30.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(75.dp)
            ) {

                Column {
                    Image(
                        painter = painterResource(id = R.drawable.shpe_logo_full_color),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxSize()
                    )

                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        text = "Register Your SHPE-UF Account",
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center
                    )
                }

                Column (
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {

                    RegisterFirstName()

                    //TODO Change these two components to function calls to keep the main component simple and divide each composable

                    var lastName by remember { mutableStateOf("") }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedTextField(
                            modifier = Modifier,
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = {
                                Text(
                                    text = "Enter your Last Name...",
                                    fontSize = 11.sp,
                                )
                            }
                        )
                    }

                    var email by remember { mutableStateOf("") }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedTextField(
                            modifier = Modifier,
                            value = email,
                            onValueChange = { email = it },
                            label = {
                                Text(
                                    text = "Enter your Email...",
                                    fontSize = 11.sp,
                                )
                            }
                        )
                    }

                    RegisterPassword()

                }


                Column (
                    modifier = Modifier
                        .fillMaxWidth(),
                ){

                    GradientButton(
                        nameButton = "Create Account",
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Already have an account?",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.labelLarge
                        )
                        TextButton(onClick = {

                            //TODO Navigate to the sign-in page for users that already have an account

                        }) {
                            Text(
                                text = "Sign In",
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GradientButton(
    nameButton: String,
) {
    val gradientColors = listOf(Color(0xFFD23C20), Color(0xFFF1652F))

    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            //TODO Handle user registration
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nameButton,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterFirstName() {
    var firstName by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(0.8f),
        value = firstName,
        onValueChange = {firstName = it},
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null)
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        label = {
            Text(
                text = "First Name",
                fontSize = 20.sp
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterLastName() {

    //TODO Replicate from first name outlineTextField

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterEmail() {

    //TODO Create OutlineTextBox and same email to state

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterPassword() {

    var password by remember { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(0.8f),
            value = password,
            onValueChange = {password = it},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null)
            },
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) Icons.Filled.Visibility
                        else  Icons.Filled.VisibilityOff
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(
                        imageVector = visibilityIcon,
                        contentDescription = description
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            label = {
                Text(
                    text = "Password",
                    fontSize = 20.sp
                )
            }
        )
    }
}