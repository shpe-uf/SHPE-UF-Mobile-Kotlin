package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.example.shpe_uf_mobile_kotlin.ui.theme.WhiteSHPE
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme

@Preview(showBackground = true)
@Composable
fun SignInUIPreview(){
    //SignInLayout()
    //SignInBackground()
}

//@Composable
////fun SignIn(){
////    SignInLayout()
////    SignInBackground()
////    UsernameTextField()
////}

@Preview
@Composable
fun TextFieldsAndButton(signInViewModel: SignInViewModel = SignInViewModel()){
    SHPEUFMobileKotlinTheme {
        val uiState by signInViewModel.uiState.collectAsState()

        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
        ){
            UsernameTextField(
                value = uiState.username ?: "",
                onValueChange = { signInViewModel.onUsernameChanged(it)}
            )
            PasswordTextField(
                value = uiState.password ?: "",
                onValueChange = { signInViewModel.onPasswordChanged(it)},
                isPasswordVisible = uiState.isPasswordVisible
            )

            Submit(
                onClick = { signInViewModel.validateAndLoginUser() }
            )
        }
    }


}

@Composable
fun SignInBackground(modifier: Modifier = Modifier) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val orangeHeight = screenHeight * (1.01f / 11f)
    val whiteHeight = screenHeight * (10f / 11f)

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(orangeHeight)
                .background(OrangeSHPE)
        )
        Image(
            painter = painterResource(id = R.drawable.gatorshpe),
            contentDescription = "SHPE GATOR",
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = -20.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(whiteHeight)
                .align(Alignment.BottomCenter)
                .background(WhiteSHPE)
        )
    }
}

//@Composable
//fun SignInLayout(modifier: Modifier = Modifier){
//
//    Column(
//        Modifier
//            .padding(25.dp)
//            .fillMaxWidth()) {
////        Text(
////            text = "SIGN IN",
////            style = TextStyle(
////                fontSize = 50.sp,
////                fontWeight = FontWeight(400),
////                color = OrangeSHPE,
////            ),
////            modifier = Modifier
////                .width(180.dp)
////                .height(42.dp)
////        )
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameTextField(
    value: String,
    onValueChange: (String) -> Unit
){
    TextField(
        value = value,
        onValueChange = {onValueChange(it)},
        textStyle = TextStyle(fontSize = 25.sp),
        label = {Text("Username")},
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean
){

    TextField(
        value = value,
        onValueChange = {onValueChange(it)},
        label = {Text("Password")},
        visualTransformation =
        if(isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun Submit(onClick: () -> Unit){
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            onClick()
    }){
        Text("Submit")
    }
}
