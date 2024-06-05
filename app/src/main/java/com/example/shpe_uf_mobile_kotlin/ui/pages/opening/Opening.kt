package com.example.shpe_uf_mobile_kotlin.ui.pages.opening

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.DragInteraction
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlin.math.absoluteValue


@Preview
@Composable
fun OpeningPagePreview(){
    val viewModel = OpeningViewModel()
    OpeningPage(viewModel)
}

// The goat source: https://blog.protein.tech/jetpack-compose-auto-image-slider-with-dots-indicator-45dfeba37712

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun OpeningPage(viewModel: OpeningViewModel) {

    val pagerState = viewModel.updatePage()

    // Start Pager
    HorizontalPager(state = pagerState) { page ->
        Card(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    translationX = pageOffset * size.width
                    alpha = 1 - pageOffset.absoluteValue
                },
        ){

            Image(
                painter = painterResource(id = viewModel.getPage(page).first),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                alpha = 0.5f
            )

        }

    } // end pager

    Column(
        Modifier
            .fillMaxSize()
            .navigationBarsPadding()){


        Box(modifier = Modifier.fillMaxSize()){
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ){
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color.White else Color.DarkGray
                    IndicatorDot(color = color)
                }
            }
        }

        GettingStartedBtn {
            Log.d("Button", "Pressed!")
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
                .offset(y = 75.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 42.dp))
        Text(
            text = "SHPE UF",
            style = TextStyle(
                fontSize = 48.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight(700),
                color = Color.White
            ),
            modifier = Modifier
                .offset(x = 5.dp, y = 21.dp)
                .width(203.dp)
                .height(58.dp)
        )
        AnimatedContent(
            targetState = pagerState,
            transitionSpec = {
                (slideInHorizontally { height -> height } + fadeIn()).togetherWith(
                    slideOutHorizontally { height -> -height } + fadeOut())
                    .using(SizeTransform(clip = false))
            }
        ){
            target ->
            Text(
                text = viewModel.getPage(target.currentPage).second,
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFB0B0B0),
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

        }

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
                .align(Alignment.BottomCenter),
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
fun IndicatorDot(color: Color){
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(CircleShape)
            .background(color)
            .size(14.dp)
    )
}