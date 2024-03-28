package com.example.shpe_uf_mobile_kotlin.ui.pages.opening

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R

@Composable
fun OpeningPage(){
    Image(
        painter = painterResource(id = R.drawable.open_1),
        contentDescription = "image description",
        contentScale = ContentScale.Crop,
    )
    Box{
        Image(
            painter = painterResource(id = R.drawable.shpe_logo_full_color),
            contentDescription = "shpe_logo",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(250.dp)
                .height(233.dp)
                .absoluteOffset(x=70.dp,y=168.dp)
        )
    }
    Column{
        Text(
            text = "SHPE UF",
            style = TextStyle(
                fontSize = 48.sp,
                //fontFamily = FontFamily(Font(R.font.univers lt std)),
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = "Familia",
            style = TextStyle(
                fontSize = 30.sp,
                //fontFamily = FontFamily(Font(R.font.pt serif)),
                fontWeight = FontWeight(700),
                color = Color(0xFFB0B0B0),
                )
        )
    }
}