package com.example.shpe_uf_mobile_kotlin.ui.pages.loading

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R

@Composable
fun SplashView() {

    val bgColor = if(isSystemInDarkTheme()) Color.Blue else Color.White
    val textColor = if(isSystemInDarkTheme()) Color.White else Color(0xFF011F35)

    Column(
        modifier = Modifier
            .background(bgColor)
            .fillMaxSize()
            .padding(top=265.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(modifier = Modifier
            .clip(CircleShape)
            .border(width = 3.dp, color = Color(0xFFD25917), shape = CircleShape)){
            Image(
                modifier = Modifier.width(200.dp).height(195.dp),
                painter = painterResource(id = R.drawable.shpe_logo_full_color),
                contentDescription = "SHPE Logo",
                contentScale = ContentScale.FillBounds)
        }
        Text(
            modifier = Modifier.padding(top=101.dp),
            text = "SHPE",
            style = TextStyle(
                fontSize = 90.sp,
                //fontFamily = FontFamily(Font(R.font.volkhov)),
                fontWeight = FontWeight(400),
                color = textColor
            )
        )
        Text(
            text = "Leading Hispanics in STEM",
            style = TextStyle(
                fontSize = 18.sp,
                //fontFamily = FontFamily(Font(R.font.volkhov)),
                fontWeight = FontWeight(400),
                color = Color(0xFFF2652F)
            )
        )
        Text(
            modifier = Modifier.padding(21.dp),
            text = "University of \nFlorida",
            style = TextStyle(
                fontSize = 30.sp,
                //fontFamily = FontFamily(Font(R.font.volkhov)),
                fontWeight = FontWeight(400),
                color = textColor,
                textAlign = TextAlign.Center,
            )
        )
    }
}