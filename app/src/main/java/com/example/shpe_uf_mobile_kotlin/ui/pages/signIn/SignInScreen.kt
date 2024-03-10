package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.example.shpe_uf_mobile_kotlin.ui.theme.WhiteSHPE

@Preview(showBackground = true)
@Composable
fun SignInUIPreview(){
    //SignInLayout()
    //SignInBackground()
}

@Composable
fun SignIn(){
    SignInLayout()
    SignInBackground()
    EmailTextField()
}

@Preview
@Composable
fun TextFieldsAndButton(){
    EmailTextField()
    PasswordTextField()
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

@Composable
fun SignInLayout(modifier: Modifier = Modifier){
    Column(
        Modifier
            .padding(25.dp)
            .fillMaxWidth()) {
//        Text(
//            text = "SIGN IN",
//            style = TextStyle(
//                fontSize = 50.sp,
//                fontWeight = FontWeight(400),
//                color = OrangeSHPE,
//            ),
//            modifier = Modifier
//                .width(180.dp)
//                .height(42.dp)
//        )
        EmailTextField()
        PasswordTextField()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(modifier: Modifier = Modifier){
    var text by remember { mutableStateOf("shpeito@ufl.edu") }

    TextField(
        value = text,
        onValueChange = {text=it},
        textStyle = TextStyle(fontSize = 25.sp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(modifier: Modifier = Modifier){
    var password by remember { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = {password=it}
    )
}