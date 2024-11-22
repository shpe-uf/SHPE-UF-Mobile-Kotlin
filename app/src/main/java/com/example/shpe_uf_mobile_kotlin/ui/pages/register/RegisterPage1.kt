package com.example.shpe_uf_mobile_kotlin.ui.pages.register

//ignore this commit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.navigation.NavRoute


/*
Function is used to preview the whole UI of the page but also to pass in
the viewModel into the RegistrationPage1 function to make the
 */


@Composable
fun RegistrationPage1Preview(navController: NavHostController, registerPage1ViewModel: RegisterPage1ViewModel){


    RegistrationPage1(registerPage1ViewModel = registerPage1ViewModel, navController = navController)

}


/*
Function is used for housing all the different separate components that
ultimately make up the final UI
 */
@Composable
fun RegistrationPage1(registerPage1ViewModel: RegisterPage1ViewModel, navController: NavHostController){

    val uiState by registerPage1ViewModel.uiState.collectAsState()

    RegisterBackground()

    Box(
        modifier = Modifier
            .padding(top = 83.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF011F35)),
            horizontalAlignment = Alignment.CenterHorizontally

        )

        {
            Spacer(modifier = Modifier.height(42.dp))

            // This is the progression bar
            Row{
                Row{
                    Box(
                        modifier = Modifier
                            .size(width = 106.dp, height = 5.dp)
                            .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
                    )

                }

                Spacer(modifier = Modifier.width(8.dp))

                Row{
                    Box(
                        modifier = Modifier
                            .size(width = 106.dp, height = 5.dp)
                            .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
                    )

                }

                Spacer(modifier = Modifier.width(8.dp))

                Row{
                    Box(
                        modifier = Modifier
                            .size(width = 106.dp, height = 5.dp)
                            .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
                    )
                }
            }
            // End of progression bar

            // Start of Welcome text and SHPE Logo component
            Spacer(modifier = Modifier.height(42.dp))



            Text(
                text = "Welcome to SHPE!",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp)
            )


            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Row{
                    Text(
                        text = "REGISTER",
                        style = TextStyle(
                            fontSize = 46.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFD25917)
                        )
                    )
                }

                Spacer(modifier = Modifier.width(76.dp))

                Row{
                    Image(
                        painter = painterResource(id = R.drawable.shpe_logo_full_color),
                        contentDescription = "shpeLogo",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
            // End of welcome text and shpe logo component


//            Spacer(modifier = Modifier.height(58.dp))
            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = "UF Email",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            RegisterEmail(
                value = uiState.email ?: "",
                isError = uiState.emailErrorMessage != null,
                errorMessage = uiState.emailErrorMessage ?: "",
                onValueChange = { registerPage1ViewModel.onEmailChanged(it) })

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Username",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            RegisterUsername(
                value = uiState.username ?: "",
                isError = uiState.usernameErrorMessage != null,
                errorMessage = uiState.usernameErrorMessage ?: "",
                onValueChange = { registerPage1ViewModel.onUsernameChanged(it) }
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Password",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            RegisterPassword(
                value = uiState.password ?: "",
                isPasswordVisible = uiState.isPasswordVisible,
                isError = uiState.passwordErrorMessage != null,
                errorMessage = uiState.passwordErrorMessage ?: "",
                onValueChange = { registerPage1ViewModel.onPasswordChanged(it) },
                onTogglePasswordVisibility = { registerPage1ViewModel.togglePasswordVisibility() }
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Confirm Password",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            RegisterConfirmPassword(
                value = uiState.confirmPassword ?: "",
                isConfirmPasswordVisible = uiState.isConfirmPasswordVisible,
                isError = uiState.confirmPasswordErrorMessage != null,
                errorMessage = uiState.confirmPasswordErrorMessage ?: "",
                onValueChange = { registerPage1ViewModel.onConfirmPasswordChanged(it) },
                onToggleConfirmPasswordVisibility = { registerPage1ViewModel.toggleConfirmPasswordVisibility() }
            )

//            Spacer(modifier = Modifier.height(82.dp))
            Spacer(modifier = Modifier.height(3.dp))

            CreateAccountButton(

                onClick = {
                    if (registerPage1ViewModel.validateRegisterPage1Fields() == true) {
                        navController.navigate(NavRoute.REGISTER_2)
                    }
                    else {
                        // Do nothing don't go to next page
                    }
                }

            )

            // BELOW CREATES TEXT AND SIGN IN LINK TO NAVIGATE TO LOGIN PAGE

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account?",
                    color = Color(0xFFFFFFFF),
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.labelLarge
                )
                TextButton(onClick = {
                    navController.navigate(NavRoute.LOGIN)
                }) {
                    Text(
                        text = "Sign In",
                        color = Color(0XFF93E1FF),
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

        }

    }
}


@Preview
@Composable
fun RegisterBackground() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD25917))
    ) {
        Image(
            painter = painterResource(R.drawable.gatordark),
            contentDescription = "Gator",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
        )
    }
}



//@Preview
//@Composable
//fun RegistrationPage1ProgressionBar(modifier: Modifier = Modifier){
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//
//    ){
//
//        Spacer(modifier = Modifier.height(132.dp))
//
//        Row{
//            Row{
//                Box(
//                    modifier = Modifier
//                        .size(width = 106.dp, height = 5.dp)
//                        .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
//                )
//
//            }
//
//            Spacer(modifier = Modifier.width(8.dp))
//
//            Row{
//                Box(
//                    modifier = Modifier
//                        .size(width = 106.dp, height = 5.dp)
//                        .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
//                )
//
//            }
//
//            Spacer(modifier = Modifier.width(8.dp))
//
//            Row{
//                Box(
//                    modifier = Modifier
//                        .size(width = 106.dp, height = 5.dp)
//                        .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
//                )
//            }
//        }
//    }
//}



//@Preview
//@Composable
//fun RegistrationPage1WelcomeText(modifier: Modifier = Modifier){
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(start = 30.dp)
//
//    ){
//
//        Spacer(modifier = Modifier.height(174.dp))
//
//        Text(
//            text = "Welcome to SHPE!",
//            style = TextStyle(
//                fontSize = 14.sp,
//                fontWeight = FontWeight(400),
//                color = Color(0xFFFFFFFF)
//            )
//        )
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ){
//            Row{
//                Text(
//                    text = "REGISTER",
//                    style = TextStyle(
//                        fontSize = 46.sp,
//                        fontWeight = FontWeight(400),
//                        color = Color(0xFFD25917)
//                    )
//                )
//            }
//
//            Spacer(modifier = Modifier.width(76.dp))
//
//            Row{
//                Image(
//                    painter = painterResource(id = R.drawable.shpe_logo_full_color),
//                    contentDescription = "shpeLogo",
//                    modifier = Modifier.size(50.dp)
//                )
//            }
//        }
//    }
//}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterUsername(

    /* Below are parameters for the function RegisterUsername, which is responsible for rendering
    a text field to allow users to register their username for an account. value is used for the
    current value of the username, isError is used as a bool to indicated whether a user has made
    an error while filling out the username field, errorMessage is used to store the error
    message to be displayed if there is an error, and onValueChange is a callback function invoked
    when the value of the username field changes
     */

    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit
){


    TextField(modifier = Modifier
//        .height(50.dp)
        .fillMaxWidth(0.7f),
        value = value,
        onValueChange = {onValueChange(it)},
        isError = isError,

        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.usericon),
                contentDescription = "UserIcon",
                modifier = Modifier.size(24.dp)
            )
        },
        shape = RoundedCornerShape(10.dp),
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
    TextField(
        modifier = Modifier
//            .height(50.dp)
            .fillMaxWidth(0.7f),
        value = value,
        onValueChange = {onValueChange(it)},
        isError = isError,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.emailicon),
                contentDescription = "EmailIcon",
                modifier = Modifier.size(24.dp)
            )
        },
        shape = RoundedCornerShape(10.dp),
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


    TextField(
        modifier = Modifier
//            .height(50.dp)
            .fillMaxWidth(0.7f),
        value = value,
        onValueChange = { onValueChange(it) },
        isError = isError,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.passwordicon),
                contentDescription = "PasswordIcon",
                modifier = Modifier.size(24.dp)
            )
        },
        visualTransformation =
        if (isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { onTogglePasswordVisibility() }) {
                val visibilityIcon =
                    if (isPasswordVisible) painterResource(id = R.drawable.openeyeicon)
                    else painterResource(id = R.drawable.closedeyeicon)
                val description = if (isPasswordVisible) "Hide Password" else "Show Password"
                Image(
                    painter = visibilityIcon,
                    modifier = Modifier.size(28.dp),
                    contentDescription = description

                )
            }
        },
        shape = RoundedCornerShape(10.dp),
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

    TextField(
        modifier = Modifier
//            .height(50.dp)
            .fillMaxWidth(0.7f),
        value = value,
        onValueChange = { onValueChange(it) },
        isError = isError,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.passwordicon),
                contentDescription = "PasswordIcon",
                modifier = Modifier.size(24.dp)
            )
        },
        visualTransformation =
        if (isConfirmPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { onToggleConfirmPasswordVisibility() }) {
                val visibilityIcon =
                    if (isConfirmPasswordVisible) painterResource(id = R.drawable.openeyeicon)
                    else painterResource(id = R.drawable.closedeyeicon)
                val description = if (isConfirmPasswordVisible) "Hide Password" else "Show Password"
                Image(
                    painter = visibilityIcon,
                    modifier = Modifier.size(28.dp),
                    contentDescription = description
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
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
        }
    )
}



@Composable
private fun CreateAccountButton(
    onClick: () -> Unit) {

    Button(
        modifier = Modifier
            .width(351.dp),
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
                    color = Color(0xFFD25917),

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




// SUPERIOR FEILD VERSION OF REGISTER

//package com.example.shpe_uf_mobile_kotlin.ui.pages.register
//
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Error
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import com.example.shpe_uf_mobile_kotlin.R
//import com.example.shpe_uf_mobile_kotlin.ui.custom.SuperiorTextField
//import com.example.shpe_uf_mobile_kotlin.ui.navigation.NavRoute


/*
Function is used to preview the whole UI of the page but also to pass in
the viewModel into the RegistrationPage1 function to make the
 */

//@Composable
//fun RegistrationPage1Preview(navController: NavController, registerPage1ViewModel: RegisterPage1ViewModel){
//
//
//    RegistrationPage1(registerPage1ViewModel = registerPage1ViewModel, navController = navController)
//
//}

/*
Function is used for housing all the different separate components that
ultimately make up the final UI
 */
//@Composable
//fun RegistrationPage1(registerPage1ViewModel: RegisterPage1ViewModel, navController: NavHostController){
//
//    val uiState by registerPage1ViewModel.uiState.collectAsState()
//
//    RegisterBackground()
//
//    Box(
//        modifier = Modifier
//            .padding(top = 83.dp)
//    ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFF011F35)),
//            horizontalAlignment = Alignment.CenterHorizontally
//
//        )
//
//        {
//            Spacer(modifier = Modifier.height(42.dp))
//
//            // This is the progression bar
//            Row{
//                Row{
//                    Box(
//                        modifier = Modifier
//                            .size(width = 106.dp, height = 5.dp)
//                            .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
//                    )
//
//                }
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                Row{
//                    Box(
//                        modifier = Modifier
//                            .size(width = 106.dp, height = 5.dp)
//                            .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
//                    )
//
//                }
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                Row{
//                    Box(
//                        modifier = Modifier
//                            .size(width = 106.dp, height = 5.dp)
//                            .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
//                    )
//                }
//            }
//            // End of progression bar
//
//            // Start of Welcome text and SHPE Logo component
//            Spacer(modifier = Modifier.height(42.dp))
//
//            Text(
//                text = "Welcome to SHPE!",
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    fontFamily = FontFamily(Font(R.font.universltstd)),
//                    fontWeight = FontWeight(400),
//                    color = Color(0xFFFFFFFF),
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 30.dp)
//            )
//
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                Row{
//                    Text(
//                        text = "REGISTER",
//                        style = TextStyle(
//                            fontSize = 46.sp,
//                            fontFamily = FontFamily(Font(R.font.viga)),
//                            fontWeight = FontWeight(400),
//                            color = Color(0xFFD25917)
//                        )
//                    )
//                }
//
//                Spacer(modifier = Modifier.width(76.dp))
//
//                Row{
//                    Image(
//                        painter = painterResource(id = R.drawable.shpe_logo_full_color),
//                        contentDescription = "shpeLogo",
//                        modifier = Modifier.size(50.dp)
//                    )
//                }
//            }
//            // End of welcome text and shpe logo component
//
//
//            Spacer(modifier = Modifier.height(58.dp))
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            RegisterEmail(
//                value = uiState.email ?: "",
//                isError = uiState.emailErrorMessage != null,
//                errorMessage = uiState.emailErrorMessage ?: "",
//                onValueChange = { registerPage1ViewModel.onEmailChanged(it) })
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            RegisterUsername(
//                value = uiState.username ?: "",
//                isError = uiState.usernameErrorMessage != null,
//                errorMessage = uiState.usernameErrorMessage ?: "",
//                onValueChange = { registerPage1ViewModel.onUsernameChanged(it) }
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            RegisterPassword(
//                value = uiState.password ?: "",
//                isPasswordVisible = uiState.isPasswordVisible,
//                isError = uiState.passwordErrorMessage != null,
//                errorMessage = uiState.passwordErrorMessage ?: "",
//                onValueChange = { registerPage1ViewModel.onPasswordChanged(it) },
//                onTogglePasswordVisibility = { registerPage1ViewModel.togglePasswordVisibility() }
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            RegisterConfirmPassword(
//                value = uiState.confirmPassword ?: "",
//                isConfirmPasswordVisible = uiState.isConfirmPasswordVisible,
//                isError = uiState.confirmPasswordErrorMessage != null,
//                errorMessage = uiState.confirmPasswordErrorMessage ?: "",
//                onValueChange = { registerPage1ViewModel.onConfirmPasswordChanged(it) },
//                onToggleConfirmPasswordVisibility = { registerPage1ViewModel.toggleConfirmPasswordVisibility() }
//            )
//
////            Spacer(modifier = Modifier.height(82.dp))
//            Spacer(modifier = Modifier.height(32.dp))
//
//            CreateAccountButton(
//                onClick = {
//                    if (registerPage1ViewModel.validateRegisterPage1Fields()) {
//                    navController.navigate(RegisterRoutes.registerPage2)
//                }
//                    else {
//                    // Do nothing don't go to next page
//                    }
//                }
//
//            )
//
//            // BELOW CREATES TEXT AND SIGN IN LINK TO NAVIGATE TO LOGIN PAGE
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    text = "Already have an account?",
//                    color = Color(0xFFFFFFFF),
//                    fontSize = 18.sp,
//                    style = MaterialTheme.typography.labelLarge
//                )
//                TextButton(onClick = {
//                    navController.navigate(NavRoute.LOGIN)
//                }) {
//                    Text(
//                        text = "Sign In",
//                        color = Color(0XFF93E1FF),
//                        fontSize = 18.sp,
//                        style = MaterialTheme.typography.labelLarge
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun RegisterBackground() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFD25917))
//    ) {
//        Image(
//            painter = painterResource(R.drawable.gatordark),
//            contentDescription = "Gator",
//            contentScale = ContentScale.FillWidth,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 48.dp)
//        )
//    }
//}
//
//
//
////@Preview
////@Composable
////fun RegistrationPage1ProgressionBar(modifier: Modifier = Modifier){
////
////    Column(
////        modifier = Modifier
////            .fillMaxSize(),
////        horizontalAlignment = Alignment.CenterHorizontally
////
////    ){
////
////        Spacer(modifier = Modifier.height(132.dp))
////
////        Row{
////            Row{
////                Box(
////                    modifier = Modifier
////                        .size(width = 106.dp, height = 5.dp)
////                        .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
////                )
////
////            }
////
////            Spacer(modifier = Modifier.width(8.dp))
////
////            Row{
////                Box(
////                    modifier = Modifier
////                        .size(width = 106.dp, height = 5.dp)
////                        .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
////                )
////
////            }
////
////            Spacer(modifier = Modifier.width(8.dp))
////
////            Row{
////                Box(
////                    modifier = Modifier
////                        .size(width = 106.dp, height = 5.dp)
////                        .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
////                )
////            }
////        }
////    }
////}
//
//
//
////@Preview
////@Composable
////fun RegistrationPage1WelcomeText(modifier: Modifier = Modifier){
////    Column(
////        modifier = Modifier
////            .fillMaxSize()
////            .padding(start = 30.dp)
////
////    ){
////
////        Spacer(modifier = Modifier.height(174.dp))
////
////        Text(
////            text = "Welcome to SHPE!",
////            style = TextStyle(
////                fontSize = 14.sp,
////                fontWeight = FontWeight(400),
////                color = Color(0xFFFFFFFF)
////            )
////        )
////
////        Row(
////            verticalAlignment = Alignment.CenterVertically
////        ){
////            Row{
////                Text(
////                    text = "REGISTER",
////                    style = TextStyle(
////                        fontSize = 46.sp,
////                        fontWeight = FontWeight(400),
////                        color = Color(0xFFD25917)
////                    )
////                )
////            }
////
////            Spacer(modifier = Modifier.width(76.dp))
////
////            Row{
////                Image(
////                    painter = painterResource(id = R.drawable.shpe_logo_full_color),
////                    contentDescription = "shpeLogo",
////                    modifier = Modifier.size(50.dp)
////                )
////            }
////        }
////    }
////}
//
//
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun RegisterUsername(
//
//    /* Below are parameters for the function RegisterUsername, which is responsible for rendering
//    a text field to allow users to register their username for an account. value is used for the
//    current value of the username, isError is used as a bool to indicated whether a user has made
//    an error while filling out the username field, errorMessage is used to store the error
//    message to be displayed if there is an error, and onValueChange is a callback function invoked
//    when the value of the username field changes
//     */
//
//    value: String,
//    isError: Boolean,
//    errorMessage: String,
//    onValueChange: (String) -> Unit
//){
//
//    SuperiorTextField(
//        label = "Username",
//        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
//        value = value,
//        onValueChange = onValueChange,
//        leadingIcon = R.drawable.profile_circle,
//        iconTrailingIcon = { if (isError) Icon(Icons.Filled.Error,"error",tint = MaterialTheme.colorScheme.error) },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        isError = isError,
//        errorMessage = errorMessage,
//        leadingIconModifier = Modifier.size(32.dp).padding(start = 12.dp)
//    )
//
////    TextField(modifier = Modifier
//////        .height(50.dp)
////        .fillMaxWidth(0.7f),
////        value = value,
////        onValueChange = {onValueChange(it)},
////        isError = isError,
////
////        leadingIcon = {
////            Image(
////                painter = painterResource(id = R.drawable.usericon),
////                contentDescription = "UserIcon",
////                modifier = Modifier.size(24.dp)
////            )
////        },
////        shape = RoundedCornerShape(10.dp),
////        singleLine = true,
////        keyboardOptions = KeyboardOptions(
////            keyboardType = KeyboardType.Text
////        ),
////        supportingText = {
////            if (isError) {
////                Text(
////                    modifier = Modifier.fillMaxWidth(),
////                    text = errorMessage,
////                    color = MaterialTheme.colorScheme.error
////                )
////            }
////        },
////        trailingIcon = {
////            if (isError)
////                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
////        }
////
////    )
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun RegisterEmail(
//    value: String,
//    isError: Boolean,
//    errorMessage: String,
//    onValueChange: (String) -> Unit
//) {
//    SuperiorTextField(
//        label = "UF Email",
//        labelModifier = Modifier.padding(horizontal = 11.dp, vertical = 6.53.dp),
//        value = value,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//        onValueChange = onValueChange,
//        leadingIcon = R.drawable.emailicon,
//        leadingIconModifier = Modifier.size(32.dp).padding(start = 12.dp),
//        isError = isError,
//        errorMessage = errorMessage
//        )
////    TextField(
////        modifier = Modifier
//////            .height(50.dp)
////            .fillMaxWidth(0.7f),
////        value = value,
////        onValueChange = {onValueChange(it)},
////        isError = isError,
////        leadingIcon = {
////            Image(
////                painter = painterResource(id = R.drawable.emailicon),
////                contentDescription = "EmailIcon",
////                modifier = Modifier.size(24.dp)
////            )
////        },
////        shape = RoundedCornerShape(10.dp),
////        singleLine = true,
////        supportingText = {
////            if (isError) {
////                Text(
////                    modifier = Modifier.fillMaxWidth(),
////                    text = errorMessage,
////                    color = MaterialTheme.colorScheme.error
////                )
////            }
////        },
////        keyboardOptions = KeyboardOptions(
////            keyboardType = KeyboardType.Text
////        ),
////        trailingIcon = {
////            if (isError)
////                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
////        }
////
////    )
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun RegisterPassword(
//    value: String,
//    isPasswordVisible: Boolean,
//    isError: Boolean,
//    errorMessage: String,
//    onValueChange: (String) -> Unit,
//    onTogglePasswordVisibility: () -> Unit
//) {
//
//    val image = if(isPasswordVisible){
//        R.drawable.state_selected
//    } else {
//        R.drawable.state_default
//    }
//
//    SuperiorTextField(
//        label = "Password",
//        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
//        value = value,
//        onValueChange = onValueChange,
//        leadingIcon = R.drawable.lock_3,
//        trailingIcon = image,
//        trailingIconOnClick = onTogglePasswordVisibility,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        leadingIconModifier = Modifier.size(32.dp).padding(start = 12.dp),
//        isError = isError,
//        errorMessage = errorMessage
//    )
//
//
////    TextField(
////        modifier = Modifier
//////            .height(50.dp)
////            .fillMaxWidth(0.7f),
////        value = value,
////        onValueChange = { onValueChange(it) },
////        isError = isError,
////        leadingIcon = {
////            Image(
////                painter = painterResource(id = R.drawable.passwordicon),
////                contentDescription = "PasswordIcon",
////                modifier = Modifier.size(24.dp)
////            )
////        },
////        visualTransformation =
////        if (isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
////        trailingIcon = {
////            IconButton(onClick = { onTogglePasswordVisibility() }) {
////                val visibilityIcon =
////                    if (isPasswordVisible) painterResource(id = R.drawable.openeyeicon)
////                    else painterResource(id = R.drawable.closedeyeicon)
////                val description = if (isPasswordVisible) "Hide Password" else "Show Password"
////                Image(
////                    painter = visibilityIcon,
////                    modifier = Modifier.size(28.dp),
////                    contentDescription = description
////
////                )
////            }
////        },
////        shape = RoundedCornerShape(10.dp),
////        singleLine = true,
////        keyboardOptions = KeyboardOptions(
////            keyboardType = KeyboardType.Text
////        ),
////        supportingText = {
////            if (isError) {
////                Text(
////                    modifier = Modifier.fillMaxWidth(),
////                    text = errorMessage,
////                    color = MaterialTheme.colorScheme.error
////                )
////            }
////        }
////    )
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun RegisterConfirmPassword(
//    value: String,
//    isConfirmPasswordVisible: Boolean,
//    isError: Boolean,
//    errorMessage: String,
//    onValueChange: (String) -> Unit,
//    onToggleConfirmPasswordVisibility: () -> Unit
//) {
//
//    val image = if(isConfirmPasswordVisible){
//        R.drawable.state_selected
//    } else {
//        R.drawable.state_default
//    }
//
//    SuperiorTextField(
//        label = "Confirm Password",
//        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
//        value = value,
//        onValueChange = onValueChange,
//        leadingIcon = R.drawable.lock_3,
//        trailingIcon = image,
//        trailingIconOnClick = onToggleConfirmPasswordVisibility,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        leadingIconModifier = Modifier.size(32.dp).padding(start = 12.dp),
//        isError = isError,
//        errorMessage = errorMessage
//    )
//
////    TextField(
////        modifier = Modifier
//////            .height(50.dp)
////            .fillMaxWidth(0.7f),
////        value = value,
////        onValueChange = { onValueChange(it) },
////        isError = isError,
////        leadingIcon = {
////            Image(
////                painter = painterResource(id = R.drawable.passwordicon),
////                contentDescription = "PasswordIcon",
////                modifier = Modifier.size(24.dp)
////            )
////        },
////        visualTransformation =
////        if (isConfirmPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
////        trailingIcon = {
////            IconButton(onClick = { onToggleConfirmPasswordVisibility() }) {
////                val visibilityIcon =
////                    if (isConfirmPasswordVisible) painterResource(id = R.drawable.openeyeicon)
////                    else painterResource(id = R.drawable.closedeyeicon)
////                val description = if (isConfirmPasswordVisible) "Hide Password" else "Show Password"
////                Image(
////                    painter = visibilityIcon,
////                    modifier = Modifier.size(28.dp),
////                    contentDescription = description
////                )
////            }
////        },
////        shape = RoundedCornerShape(10.dp),
////        singleLine = true,
////        keyboardOptions = KeyboardOptions(
////            keyboardType = KeyboardType.Text
////        ),
////        supportingText = {
////            if (isError) {
////                Text(
////                    modifier = Modifier.fillMaxWidth(),
////                    text = errorMessage,
////                    color = MaterialTheme.colorScheme.error
////                )
////            }
////        }
////    )
//}
//
//
//
//@Composable
//private fun CreateAccountButton(
//    onClick: () -> Unit) {
//
//    Button(
//        modifier = Modifier
//            .width(351.dp),
//        onClick = {
//            onClick()
//        },
//        contentPadding = PaddingValues(),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = Color.Transparent
//        ),
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    color = Color(0xFFD25917),
//
//                    )
//                .padding(horizontal = 20.dp, vertical = 10.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Create Account",
//                fontSize = 16.sp,
//                fontFamily = FontFamily(Font(R.font.universltstd)),
//                fontWeight = FontWeight(400),
//                color = Color.White
//            )
//        }
//    }
//}
