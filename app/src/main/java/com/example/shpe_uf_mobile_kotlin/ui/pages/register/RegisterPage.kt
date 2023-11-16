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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme

@Preview(showBackground = true)
@Composable
fun RegistrationPagePreview() {
    val viewModel = RegisterViewModel()
    RegistrationPage(viewModel)
}

@Composable
fun RegistrationPage(registerViewModel: RegisterViewModel) {
    SHPEUFMobileKotlinTheme {

        val uiState by registerViewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 30.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp)
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
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                RegisterFirstName(
                    value = uiState.firstName ?: "",
                    isError = uiState.firstNameErrorMessage != null,
                    errorMessage = uiState.firstNameErrorMessage ?: "",
                    onValueChange = { registerViewModel.onFirstNameChanged(it) }
                )

                RegisterLastName(
                    value = uiState.lastName ?: "",
                    isError = uiState.lastNameErrorMessage != null,
                    errorMessage = uiState.lastNameErrorMessage ?: "",
                    onValueChange = { registerViewModel.onLastNameChanged(it) }
                )

                RegisterEmail(
                    value = uiState.email ?: "",
                    isError = uiState.emailErrorMessage != null,
                    errorMessage = uiState.emailErrorMessage ?: "",
                    onValueChange = { registerViewModel.onEmailChanged(it) }
                )

                RegisterPassword(
                    value = uiState.password ?: "",
                    isPasswordVisible = uiState.isPasswordVisible,
                    isError = uiState.passwordErrorMessage != null,
                    errorMessage = uiState.passwordErrorMessage ?: "",
                    onValueChange = { registerViewModel.onPasswordChanged(it) },
                    onTogglePasswordVisibility = { registerViewModel.togglePasswordVisibility() }
                )

                RegisterConfirmPassword(
                    value = uiState.confirmPassword ?: "",
                    isConfirmPasswordVisible = uiState.isConfirmPasswordVisible,
                    isError = uiState.confirmPasswordErrorMessage != null,
                    errorMessage = uiState.confirmPasswordErrorMessage ?: "",
                    onValueChange = { registerViewModel.onConfirmPasswordChanged(it) },
                    onToggleConfirmPasswordVisibility = { registerViewModel.toggleConfirmPasswordVisibility() }
                )

            }


            Column (
                modifier = Modifier
                    .fillMaxWidth(),
            ){

                GradientButton(
                    onClick = { registerViewModel.validateAndRegisterUser() }
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

@Composable
private fun GradientButton(
    onClick: () -> Unit
) {
    val gradientColors = listOf(Color(0xFFD23C20), Color(0xFFF1652F))

    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            onClick()
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
                .padding(horizontal = 20.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Create Account",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterFirstName(
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = value,
        onValueChange = {onValueChange(it)},
        isError = isError,
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
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        label = {
            Text(
                text = "First Name",
                fontSize = 18.sp
            )
        },
        trailingIcon = {
            if (isError)
                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterLastName(
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = value,
        onValueChange = {onValueChange(it)},
        isError = isError,
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
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        label = {
            Text(
                text = "Last Name",
                fontSize = 18.sp
            )
        },
        trailingIcon = {
            if (isError)
                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterEmail(
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = value,
        onValueChange = {onValueChange(it)},
        isError = isError,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = null)
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        label = {
            Text(
                text = "Email",
                fontSize = 18.sp
            )
        },
        trailingIcon = {
            if (isError)
                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterPassword(
    value: String,
    isPasswordVisible: Boolean,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = value,
        onValueChange = { onValueChange(it) },
        isError = isError,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = null
            )
        },
        visualTransformation =
            if (isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { onTogglePasswordVisibility() }) {
                val visibilityIcon =
                    if (isPasswordVisible) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                val description = if (isPasswordVisible) "Hide Password" else "Show Password"
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
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        label = {
            Text(
                text = "Password",
                fontSize = 18.sp
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterConfirmPassword(
    value: String,
    isConfirmPasswordVisible: Boolean,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit,
    onToggleConfirmPasswordVisibility: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = value,
        onValueChange = { onValueChange(it) },
        isError = isError,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = null
            )
        },
        visualTransformation =
            if (isConfirmPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { onToggleConfirmPasswordVisibility() }) {
                val visibilityIcon =
                    if (isConfirmPasswordVisible) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                val description = if (isConfirmPasswordVisible) "Hide Password" else "Show Password"
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
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        label = {
            Text(
                text = "Confirm Password",
                fontSize = 18.sp
            )
        }
    )
}


