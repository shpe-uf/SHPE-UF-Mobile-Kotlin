package com.example.shpe_uf_mobile_kotlin.ui.pages.opening

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import com.example.shpe_uf_mobile_kotlin.ui.theme.GREY
import com.example.shpe_uf_mobile_kotlin.ui.theme.WHITE


@Preview
@Composable
fun OpeningPagePreview() {
    val viewModel = OpeningViewModel()
    OpeningPage(viewModel)
}

// The goat source: https://blog.protein.tech/jetpack-compose-auto-image-slider-with-dots-indicator-45dfeba37712

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun OpeningPage(viewModel: OpeningViewModel) {

    val pagerState = viewModel.updatePage()

    // Images
    ImageCarousel(pagerState = pagerState, viewModel = viewModel)

    GettingStartedButton {

    }

    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            // Indicator Dots
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) WHITE else GREY
                    IndicatorDot(color = color)
                }
            }


        }

    }


    // SHPE Text
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(21.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SHPELogo()
        Spacer(modifier = Modifier.padding(21.dp))
        Text(
            text = "SHPE UF",
            style = TextStyle(
                fontSize = 48.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight(700),
                color = Color.White
            ),
            modifier = Modifier
                .width(203.dp)
                .height(58.dp)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        AnimatedCaption(pagerState = pagerState, viewModel = viewModel)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(pagerState: PagerState, viewModel: OpeningViewModel) {
    // Start Pager
    HorizontalPager(state = pagerState) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            Image(
                painter = painterResource(id = viewModel.getPage(page).first),
                contentDescription = "Image of SHPEITO(s) doing SHPE things.",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
            )

        }

    } // end pager
}

@Composable
fun SHPELogo() {
    Image(
        painter = painterResource(id = R.drawable.shpe_1),
        contentDescription = "SHPE logo.",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .safeDrawingPadding()
            .width(250.dp)
            .height(233.dp)

    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedCaption(pagerState: PagerState, viewModel: OpeningViewModel) {
    val offsetX = remember { Animatable(0f) }

    val animationSpec = tween<Float>(
        durationMillis = 300,
        easing = FastOutSlowInEasing,
    )

    LaunchedEffect(Unit) {
        offsetX.animateTo(
            targetValue = -100f,
            animationSpec = animationSpec
        )
    }

    Log.d("offsetX value", offsetX.value.toString())

    Text(
        text = viewModel.getPage(pagerState.currentPage).second,
        style = TextStyle(
            fontSize = 30.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight(700),
            color = Color(0xFFB0B0B0),
        ),
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
    )
}

@Composable
fun GettingStartedButton(onClick: () -> Unit) { // TODO: Implement navigation to Login page when pressing this button.
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF011F35)),
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .width(325.dp)
                .height(69.dp),
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

// Represents a dot that can either have a white or gray color. White = selected, gray = unselected.
@Composable
fun IndicatorDot(color: Color) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(CircleShape)
            .background(color)
            .size(10.dp)
    )
}