package com.example.shpe_uf_mobile_kotlin.ui.pages.opening

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shpe_uf_mobile_kotlin.R

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color
){
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = Color.Yellow,
    unSelectedColor: Color = Color.Gray,
    dotSize: Dp
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Preview
@Composable
fun OpeningPage(){
    var visible by remember { mutableStateOf(true) }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(initialAlpha = 0.3f),
        exit = fadeOut()

    ) {
        Image(
            painter = painterResource(id = R.drawable.opening_2),
            contentDescription = "image description",
            contentScale = ContentScale.Crop
        )
    }

    DotsIndicator(totalDots = 5, selectedIndex = 2, dotSize = 10.dp)

//    Box{
//        Image(
//            painter = painterResource(id = R.drawable.shpe_logo_full_color),
//            contentDescription = "shpe_logo",
//            contentScale = ContentScale.FillBounds,
//            modifier = Modifier
//                .width(250.dp)
//                .height(233.dp)
//                .absoluteOffset(x=70.dp,y=168.dp)
//        )
//    }
//    Column{
//        Text(
//            text = "SHPE UF",
//            style = TextStyle(
//                fontSize = 48.sp,
//                //fontFamily = FontFamily(Font(R.font.univers lt std)),
//                fontWeight = FontWeight(700),
//                color = Color(0xFFFFFFFF),
//                ),
//            modifier = Modifier
//                .fillMaxWidth()
//        )
//        Text(
//            text = "Familia",
//            style = TextStyle(
//                fontSize = 30.sp,
//                //fontFamily = FontFamily(Font(R.font.pt serif)),
//                fontWeight = FontWeight(700),
//                color = Color(0xFFB0B0B0),
//                )
//        )
//    }
}