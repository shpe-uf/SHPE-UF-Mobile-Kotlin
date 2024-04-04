package com.example.shpe_uf_mobile_kotlin.ui.pages.opening

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
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

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun OpeningPage() {
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

    val pagerState = rememberPagerState(
        initialPage = 0
    )

    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3250)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 500, easing = FastOutLinearInEasing)
        )

    ) {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            userScrollEnabled = false, // Prevents user from scrolling on the page.
            modifier = Modifier.fillMaxSize()
        ) { currentPage ->
            // Text, image will change based on the current page.
            Image(
                painter = painterResource(id = images[currentPage]),
                contentDescription = "opening image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                alpha = 0.5f
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = subText[currentPage],
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight(700),
                        color = Color(0xFFB0B0B0),
                    ),
                    modifier = Modifier
                        .offset(y = 80.dp)
                        .height(40.dp)
                )
            }
            Box(modifier = Modifier
                .fillMaxSize()){
                EllipseBar()
            }
        }
    }

    // SHPE Text
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SHPE UF",
            style = TextStyle(
                fontSize = 48.sp,
                fontWeight = FontWeight(700),
                color = White
            ),
            modifier = Modifier
                .offset(y = 21.dp)
                .width(203.dp)
                .height(58.dp)
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.shpe_1),
            contentDescription = "shpe logo",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(250.dp)
                .height(233.dp)
                .offset(y = 150.dp)
        )
    }

    GettingStartedBtn {
        Log.d("Button", "Pressed!")
    }

}

@Composable
fun GettingStartedBtn(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF011F35)),
            modifier = Modifier
                .width(350.dp)
                .height(69.dp)
                .align(Alignment.BottomCenter)
                .offset(y=(-66).dp),
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

@Composable
fun EllipseBar(){
    Row(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)){
        Box(
            modifier = Modifier
                .width(14.4186.dp)
                .height(14.dp)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 14.4186.dp))
        )
    }
}