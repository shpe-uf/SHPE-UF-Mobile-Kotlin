package com.example.shpe_uf_mobile_kotlin.ui.pages.wrapped

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

//@Composable
//fun WrappedPage(shpeufAppViewModel: SHPEUFAppViewModel)

//@Composable
//fun WrappedCarousel

//@Composable
//fun OpeningPage

//@Composable
//fun MonthPrelude

//@Composable
//fun Month

//@Composable
//fun PointsPrelude

//@Composable
//fun Points

//@Composable
//fun CategoryPrelude

//@Composable
//fun Category

//@Composable
//fun MemberSincePrelude

//@Composable
//fun MemberSince

//@Composable
//fun OverallPrelude

//@Composable
//fun Overall

//@Composable
//fun WrappedPage(shpeufAppViewModel: SHPEUFAppViewModel, wrappedViewModel: WrappedViewModel) {
//    val UserState by shpeufAppViewModel.uiState.collectAsState()
//    val id = UserState.id
//    val isDarkMode = UserState.isDarkMode
//    val UsernameState by shpeufAppViewModel.userState.collectAsState()
//    val username = UsernameState.username
//}

enum class MarqueeDirection { RightToLeft, LeftToRight }

@Composable
fun MarqueeLazyRow(
    items: List<String>,
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(Color(0xFFD25917), Color(0xFF0B70BA)),
    gap: Dp = 24.dp,
    speedDpPerSec: Float = 90f,
    direction: MarqueeDirection = MarqueeDirection.RightToLeft,
    autoReverseEveryMs: Long? = null,
    textStyle: TextStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
) {
    if (items.isEmpty()) return

    val repetitions = 1000
    val total = items.size * repetitions
    val startIndex = total / 2
    val state = rememberLazyListState(startIndex)

    val speedPxPerSec = with(LocalDensity.current) { speedDpPerSec.dp.toPx() }
    var dir by remember { mutableStateOf(direction) } // supports ping-pong

    LaunchedEffect(autoReverseEveryMs) {
        if (autoReverseEveryMs == null) return@LaunchedEffect
        while (true) {
            delay(autoReverseEveryMs)
            dir = if (dir == MarqueeDirection.RightToLeft) MarqueeDirection.LeftToRight
            else MarqueeDirection.RightToLeft
        }
    }

    LaunchedEffect(items, speedPxPerSec, dir) {
        var last = 0L
        while (true) {
            val now = withFrameNanos { it }
            if (last != 0L && speedPxPerSec > 0f) {
                val dt = (now - last) / 1_000_000_000f
                val sign = if (dir == MarqueeDirection.RightToLeft) 1f else -1f
                state.scrollBy(sign * speedPxPerSec * dt)
            }
            last = now

            val guard = items.size * 4
            val i = state.firstVisibleItemIndex
            val o = state.firstVisibleItemScrollOffset
            when {
                i < guard -> {
                    val jump = items.size * (repetitions / 2)
                    state.scrollToItem(i + jump, o)
                }
                i > total - guard -> {
                    val jump = items.size * (repetitions / 2)
                    state.scrollToItem(i - jump, o)
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .clipToBounds()
    ) {
        LazyRow(
            state = state,
            horizontalArrangement = Arrangement.spacedBy(gap),
            contentPadding = PaddingValues(horizontal = gap)
        ) {
            items(total, key = { it }) { idx ->
                val base = idx % items.size
                Text(
                    text = items[base],
                    color = colors[base % colors.size],
                    style = textStyle,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Visible
                )
            }
        }
    }
}

enum class VerticalMarqueeDirection { Up, Down }

@Composable
fun MarqueeLazyColumn(
    items: List<String>,
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(Color(0xFFD25917), Color(0xFF0B70BA)), // orange / blue
    gap: Dp = 10.dp,
    speedDpPerSec: Float = 60f,
    direction: VerticalMarqueeDirection = VerticalMarqueeDirection.Up,
    autoReverseEveryMs: Long? = null,
    textStyle: TextStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
) {
    if (items.isEmpty()) return

    val repetitions = 1000
    val total = items.size * repetitions
    val startIndex = total / 2
    val state = rememberLazyListState(startIndex)

    val speedPxPerSec = with(LocalDensity.current) { speedDpPerSec.dp.toPx() }
    var dir by remember { mutableStateOf(direction) }

    LaunchedEffect(autoReverseEveryMs) {
        if (autoReverseEveryMs == null) return@LaunchedEffect
        while (true) {
            delay(autoReverseEveryMs)
            dir = if (dir == VerticalMarqueeDirection.Up) VerticalMarqueeDirection.Down
            else VerticalMarqueeDirection.Up
        }
    }

    LaunchedEffect(items, speedPxPerSec, dir) {
        var last = 0L
        while (true) {
            val now = withFrameNanos { it }
            if (last != 0L && speedPxPerSec > 0f) {
                val dt = (now - last) / 1_000_000_000f
                val sign = if (dir == VerticalMarqueeDirection.Up) 1f else -1f
                state.scrollBy(sign * speedPxPerSec * dt)
            }
            last = now

            val guard = items.size * 4
            val i = state.firstVisibleItemIndex
            val o = state.firstVisibleItemScrollOffset
            when {
                i < guard -> {
                    val jump = items.size * (repetitions / 2)
                    state.scrollToItem(i + jump, o)
                }
                i > total - guard -> {
                    val jump = items.size * (repetitions / 2)
                    state.scrollToItem(i - jump, o)
                }
            }
        }
    }

    Box(
        modifier = modifier
            .clipToBounds()
    ) {
        LazyColumn(
            state = state,
            verticalArrangement = Arrangement.spacedBy(gap),
            contentPadding = PaddingValues(vertical = gap),
            modifier = Modifier.fillMaxSize()
        ) {
            items(total, key = { it }) { idx ->
                val base = idx % items.size
                val color = colors[idx % colors.size]
                Text(
                    text = items[base],
                    color = color,
                    style = textStyle,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Visible
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarqueeLazyRowPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        MarqueeLazyRow(
            items = listOf("SHPEWRAPPED", "SHPEWRAPPED"),
            speedDpPerSec = 110f,
            gap = 8.dp
        )

        MarqueeLazyRow(
            items = listOf("SHPEWRAPPED", "SHPEWRAPPED"),
            speedDpPerSec = 110f,
            direction = MarqueeDirection.LeftToRight,
            gap = 8.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VerticalMarqueeInRowPreview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(180.dp)
                .fillMaxHeight()
        ) {
            MarqueeLazyColumn(
                items = listOf("21","21","21","21"),
                speedDpPerSec = 70f,
                gap = 8.dp
            )
        }

        Box(
            modifier = Modifier
                .width(180.dp)
                .fillMaxHeight()
        ) {
            MarqueeLazyColumn(
                items = listOf("21","21","21","21"),
                direction = VerticalMarqueeDirection.Down,
                speedDpPerSec = 70f,
                gap = 6.dp
            )
        }
    }
}

