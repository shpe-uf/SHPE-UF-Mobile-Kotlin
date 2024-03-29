package com.example.shpe_uf_mobile_kotlin.ui.pages.opening

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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

//@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
//@Composable
//fun AutoSlidingCarousel(
//    modifier: Modifier = Modifier,
//    pagerState: PagerState = remember { PagerState() },
//    itemContent: @Composable (index: Int) -> Unit,
//){
//    LaunchedEffect(pagerState.currentPage){
//        delay(3000L)
//        pagerState.animateScrollToPage((pagerState.currentPage + 1) % 8)
//    }
//
//    Box(
//        modifier = modifier.fillMaxWidth(),
//    ){
//        HorizontalPager(count = 8, state = pagerState){
//            page -> itemContent(page)
//        }
//    }
//}

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
            userScrollEnabled = false,
            modifier = Modifier.fillMaxSize()
        ) { currentPage ->
            Text(
                text = subText[currentPage],
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFB0B0B0),
                )
            )
            Image(
                painter = painterResource(id = images[currentPage]),
                contentDescription = "opening image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                alpha = 0.6f
            )
        }
    }
// Shpe Logo
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.shpe_1),
            contentDescription = "shpe logo",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(250.dp)
                .height(233.dp)
        )
        Text(
            text = "SHPE UF",
            style = TextStyle(
                fontSize = 48.sp,
                fontWeight = FontWeight(700),
                color = White
            )
        )
//        HorizontalPager(
//            count = subText.size,
//            state = pagerState,
//            userScrollEnabled = false,
//        ) { currentPage ->
//
//        }

    }

//    Box(
//        modifier = Modifier.fillMaxSize()
//    ){
//        Image(
//            painter = painterResource(id = R.drawable.dots_1),
//            contentDescription = "dots",
//            contentScale = ContentScale.None,
//            modifier = Modifier
//                .width(101.4186.dp)
//                .height(14.dp)
//                .scale(2.25f)
//                .align(Alignment.BottomCenter)
//                .offset(y = (-10).dp)
//        )
//    }
}
