package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.example.shpe_uf_mobile_kotlin.ui.theme.WhiteSHPE

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
        Box(
            modifier = Modifier
                .width(393.dp)
                .height(93.dp)
                .background(color = OrangeSHPE)
        )
        Box(
            modifier = Modifier
                .width(515.dp)
                .height(809.dp)
                .background(color = WhiteSHPE)
        )
        Text(
            text= "Sign In."
        )
    }
}