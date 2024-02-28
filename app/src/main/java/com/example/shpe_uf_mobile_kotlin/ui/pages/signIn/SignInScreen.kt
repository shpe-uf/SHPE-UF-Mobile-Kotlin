package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun SignInUIPreview(){
    SignInLayout()
}

@Composable
fun SignInLayout(){
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 30.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ){
        Text(
            text= "Sign In."
        )
    }
}