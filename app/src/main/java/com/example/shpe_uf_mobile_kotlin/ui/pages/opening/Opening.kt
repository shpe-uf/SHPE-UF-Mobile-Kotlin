package com.example.shpe_uf_mobile_kotlin.ui.pages.opening

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.White
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

// The goat source: https://blog.protein.tech/jetpack-compose-auto-image-slider-with-dots-indicator-45dfeba37712

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun OpeningPage() {
    // TODO: Add to ViewModel.
    val images = listOf(
        R.drawable.opening_1,
        R.drawable.opening_2,
        R.drawable.opening_3,
        R.drawable.opening_4,
        R.drawable.opening_5,
        R.drawable.opening_6,
        R.drawable.opening_7,
        R.drawable.opening_8
    )
    // TODO: Add to ViewModel.
    val subText = listOf(
        "Familia",
        "Leadership",
        "Professionalism",
        "Resilience",
        "Mentorship",
        "Education",
        "Technology",
        "Community"
    )
    // TODO: Add to ViewModel.
    val pagerState = rememberPagerState(
        initialPage = 0
    )
    // TODO: Add to ViewModel.
    var visible by remember { mutableStateOf(true) }
    // TODO: Add to ViewModel.
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState() // to prevent auto scrolling when dragging the screen


    LaunchedEffect(Unit) {
        while (true) {
            delay(3250)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    AnimatedVisibility( // TODO: Implement animation, a sort of fade in and out.
        visible = visible,

    ) {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { currentPage ->
            // Text, image will change based on the current page.
            // Offset each page to match figma design
            Log.d("Page", ""+currentPage)
            // TODO: Figure out how to match picture design on Figma if need be. Ask Yair.
            when(currentPage){
                0 -> Image(
                    painter = painterResource(id = images[currentPage]),
                    contentDescription = "opening image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    alpha = 0.5f
                )
                1 -> Image(
                    painter = painterResource(id = images[currentPage]),
                    contentDescription = "opening image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    alpha = 0.5f
                )
                2 -> Image(
                    painter = painterResource(id = images[currentPage]),
                    contentDescription = "opening image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    alpha = 0.5f
                )
                3 -> Image(
                    painter = painterResource(id = images[currentPage]),
                    contentDescription = "opening image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    alpha = 0.5f
                )
                4 -> Image(
                    painter = painterResource(id = images[currentPage]),
                    contentDescription = "opening image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    alpha = 0.5f
                )
                5 -> Image(
                    painter = painterResource(id = images[currentPage]),
                    contentDescription = "opening image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    alpha = 0.5f
                )
                6 -> Image(
                    painter = painterResource(id = images[currentPage]),
                    contentDescription = "opening image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    alpha = 0.5f
                )
                7 -> Image(
                    painter = painterResource(id = images[currentPage]),
                    contentDescription = "opening image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    alpha = 0.5f
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = subText[currentPage],
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight(700),
                        color = Color(0xFFB0B0B0),
                    ),
                    modifier = Modifier
                        .offset(y = 84.dp) // TODO: Fix spacing between SHPE UF and the sub text.
                        .height(40.dp)
                )
            }
            EllipseBar(selectedIndex = if (isDragged) pagerState.currentPage else pagerState.currentPage)
        }
    }

    // SHPE Text
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(21.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.shpe_1),
            contentDescription = "shpe logo",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(250.dp)
                .height(233.dp)
                .offset(y = 125.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 42.dp))
        Text(
            text = "SHPE UF",
            style = TextStyle(
                fontSize = 48.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight(700),
                color = White
            ),
            modifier = Modifier
                .offset(x=5.dp,y = 21.dp)
                .width(203.dp)
                .height(58.dp)
                .shadow(10.dp)
        )
    }

    GettingStartedBtn {
        Log.d("Button", "Pressed!")
    }

}

@Composable
fun GettingStartedBtn(onClick: () -> Unit) { // TODO: Implement navigation to Login page when pressing this button.
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF011F35)),
            modifier = Modifier
                .width(325.dp)
                .height(69.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-66).dp),
            shape = RoundedCornerShape(size = 20.dp)
        ) {
            Text(
                text = "Get Started",
                style = TextStyle(
                    fontSize = 25.sp,
                    //fontFamily = FontFamily(Font(R.font.univers lt std)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                )
            )
        }
    }
}

// Creates a row of Dots and determines its color based on the current page displayed.
@Composable
fun EllipseBar(
    selectedIndex: Int
){
    Box(modifier = Modifier.fillMaxSize()){
        LazyRow(modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .align(Alignment.BottomCenter)
            .offset(y = (-29).dp), // taken from figma
        ){
            items(8) {index ->
                Dot(color = if (index == selectedIndex) Color.White else Color(0xFF4c4c4c))

                if(index != 7){
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                }
            }



        }
    }

}

// Represents a dot that can either have a white or gray color. White = selected, gray = unselected.
@Composable
fun Dot(color: Color){
    Canvas(modifier = Modifier.size(14.dp), onDraw ={
        drawCircle(color = color)
    })
}

