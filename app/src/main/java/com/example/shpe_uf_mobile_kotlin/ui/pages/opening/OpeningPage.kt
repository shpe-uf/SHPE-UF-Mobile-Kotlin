package com.example.shpe_uf_mobile_kotlin.ui.pages.opening

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import com.example.shpe_uf_mobile_kotlin.ui.theme.GREY
import com.example.shpe_uf_mobile_kotlin.ui.theme.WHITE


@Preview
@Composable
fun OpeningPagePreview() {
    val viewModel = OpeningPageViewModel()
    OpeningPage(viewModel)
}

// The goat source: https://blog.protein.tech/jetpack-compose-auto-image-slider-with-dots-indicator-45dfeba37712

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun OpeningPage(viewModel: OpeningPageViewModel) {

    val pagerState = viewModel.updatePage()

    // Images
    ImageCarousel(pagerState = pagerState, viewModel = viewModel)

    // All content is in a 1x5 grid. With 4 Spacers, to ensure spacing.
    Column(
        modifier = Modifier.safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            SHPELogo() // SHPE Logo is first.
        }
        Spacer(Modifier.height(21.dp)) // Space between logo and text.
        Row {
            SHPEUFText() // SHPE UF Text is second.
        }
        Spacer(Modifier.height(16.dp)) // Space between text and caption.
        Row {
            AnimatedCaption(pagerState = pagerState, viewModel = viewModel) // Caption is third.
        }
        Spacer(Modifier.weight(1f)) // Space between caption and button.
        Row {
            GettingStartedButton(onClick = { Log.d("Button", "Pressed") }) // Button is fourth.
        }
        Spacer(Modifier.height(23.dp))
        Row {
            IndicatorDots(pagerState = pagerState) // Dots are last.
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(pagerState: PagerState, viewModel: OpeningPageViewModel) {
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
            .width(250.dp)
            .height(233.dp)

    )
}

@Composable
fun SHPEUFText() {
    Text(
        text = "SHPE UF",
        style = TextStyle(
            fontSize = 48.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight(700),
            color = Color.White,
            shadow = Shadow(
                color = Color(0xFF000000),
                offset = Offset(x = 2f, y = 4f),
                blurRadius = 4f
            )
        ),
        modifier = Modifier
            .width(203.dp)
            .height(58.dp)
    )
}

// TODO: Incorporate animation.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedCaption(pagerState: PagerState, viewModel: OpeningPageViewModel) {

    Text(
        text = viewModel.getPage(pagerState.currentPage).second,
        style = TextStyle(
            fontSize = 30.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight(700),
            color = Color(0xFFB0B0B0),
            shadow = Shadow(
                color = Color(0xFF000000),
                offset = Offset(x = 2f, y = 4f),
                blurRadius = 4f
            ) // Taken from Figma, offset X was estimated.
        ),
    )
}

// Button
@Composable
fun GettingStartedButton(
    onClick: () -> Unit
) { // TODO: Implement navigation to Login page when pressing this button.
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF011F35)),
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
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
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF),
            ),
            modifier = Modifier.offset(y = (-4).dp) // Figma has a weird offset by 1 pixel.
        )
    }
}

// Represents a dot that can either have a white or gray color. White = selected, gray = unselected.
@Composable
fun IndicatorDot(color: Color) {
    Box(
        modifier = Modifier
            .padding(5.dp) // Space between dots.
            .clip(CircleShape) // Makes the dot round.
            .background(color) // Color of the dot.
            .size(10.dp) // Size of the dot.
            .shadow(elevation = 4.dp, shape = CircleShape)
    )
}

// Indicator Dots
// TODO: Add animation.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IndicatorDots(pagerState: PagerState) {
    Row(
        Modifier
            .wrapContentHeight() // Fill height.
            .fillMaxWidth(), // Fill width.
        horizontalArrangement = Arrangement.Center // Center horizontally.
    ) {
        // Dots based on current page.

        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) WHITE else GREY
            IndicatorDot(color = color)


        }
    }
}