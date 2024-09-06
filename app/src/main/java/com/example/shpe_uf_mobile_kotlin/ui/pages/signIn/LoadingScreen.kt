package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.example.shpe_uf_mobile_kotlin.ui.theme.ThemeColors

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoadingScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OrangeSHPE),
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(painter = painterResource(R.drawable.gator),
                contentDescription = "Gator",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(312.dp))
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "SHPE UF",
                style = TextStyle(
                    fontSize = 64.sp,
                    fontWeight = FontWeight(400),
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(223.dp))
            Box(modifier = Modifier
                    .width(129.dp)
                    .height(126.dp)
                    .clip(CircleShape)
                    .background(Color.White)) {
                Image(painter = painterResource(R.drawable.shpe_logo_full_color),
                    contentDescription = "SHPE Logo")
            }

            }
        }
    }
