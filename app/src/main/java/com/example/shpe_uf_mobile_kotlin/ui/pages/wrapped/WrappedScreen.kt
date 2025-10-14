package com.example.shpe_uf_mobile_kotlin.ui.pages.wrapped

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MarqueeIntroScene(progress: Float) {
    // Start state: diagonal ±35°, centered near middle
    val baseAngle = 35f
    val angleTop = lerp(baseAngle, 0f, 0f)     // static here; actual change happens in split scene
    val angleBottom = lerp(-baseAngle, 0f, 0f)

    Box(Modifier.fillMaxSize()) {
        // Two top-leaning and two bottom-leaning, alternating directions
        MarqueeStrip("SHPEWRAPPED", rotationDeg = angleTop,    yOffset = (-90).dp, speed = 110f, reverse = false)
        MarqueeStrip("SHPEWRAPPED", rotationDeg = angleBottom, yOffset = (-30).dp, speed = 110f, reverse = true)
        MarqueeStrip("SHPEWRAPPED", rotationDeg = angleTop,    yOffset = (30).dp,  speed = 110f, reverse = false)
        MarqueeStrip("SHPEWRAPPED", rotationDeg = angleBottom, yOffset = (90).dp,  speed = 110f, reverse = true)

        // Center headline (static in this first scene)
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Your Year in SHPE",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFFD25917)
            )
        }
    }
}

@Composable
fun MarqueeSplitScene(progress: Float) {
    // Animate stripes from diagonal to straight (0°) and move them to top/bottom bands.
    val angleFrom = 35f
    val angleTo = 0f
    val topAngle    = lerp(angleFrom, 0f, progress)
    val bottomAngle = lerp(-angleFrom, 0f, progress)

    // Y offsets: start near center, end outside center to form distinct top/bottom groups
    val top1Y    = lerpDp((-90).dp, (-160).dp, progress)
    val top2Y    = lerpDp((-30).dp, (-120).dp, progress)
    val bottom1Y = lerpDp((30).dp,   (120).dp,  progress)
    val bottom2Y = lerpDp((90).dp,   (160).dp,  progress)

    // Center text: 0 → 360°; swap phrase at halfway
    val spin = lerp(0f, 360f, progress)
    val phraseA = "Your Year in SHPE"
    val phraseB = "Most Active Month: October"

    Box(Modifier.fillMaxSize()) {
        // Top group
        MarqueeStrip("SHPEWRAPPED", rotationDeg = topAngle,    yOffset = top1Y,    speed = 110f, reverse = false, height = 36.dp)
        MarqueeStrip("SHPEWRAPPED", rotationDeg = topAngle,    yOffset = top2Y,    speed = 110f, reverse = true,  height = 36.dp)
        // Bottom group
        MarqueeStrip("SHPEWRAPPED", rotationDeg = bottomAngle, yOffset = bottom1Y, speed = 110f, reverse = false, height = 36.dp)
        MarqueeStrip("SHPEWRAPPED", rotationDeg = bottomAngle, yOffset = bottom2Y, speed = 110f, reverse = true,  height = 36.dp)

        // Center headline with spin & phrase swap; quick crossfade
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val showA = progress < 0.5f
            val alphaA = 1f - (progress * 2f).coerceIn(0f, 1f)
            val alphaB = ((progress - 0.5f) * 2f).coerceIn(0f, 1f)

            Text(
                text = if (showA) phraseA else phraseB,
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFFD25917),
                modifier = Modifier.graphicsLayer {
                    rotationZ = spin
                    alpha = if (showA) alphaA else alphaB
                }
            )
        }
    }
}

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

@Composable
private fun MarqueeStrip(
    text: String,
    rotationDeg: Float,
    yOffset: Dp,
    speed: Float,
    reverse: Boolean = false,
    height: Dp = 40.dp
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .offset(y = yOffset)
            .graphicsLayer { rotationZ = rotationDeg }
            .clipToBounds()
    ) {
        MarqueeLazyRow(
            items = listOf(text, text),
            speedDpPerSec = speed,
            direction = if (reverse) MarqueeDirection.LeftToRight else MarqueeDirection.RightToLeft,
            gap = 8.dp
        )
    }
}

@Composable
fun WrappedHost(
    steps: List<WrappedStep>,
    modifier: Modifier = Modifier,
    viewModel: WrappedViewModel = WrappedViewModel(),
    onFinished: () -> Unit = {}
) {
    val ui by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { steps.size })
    val scope = rememberCoroutineScope()

    // Load storyboard on first composition
    LaunchedEffect(steps) { viewModel.loadTimeline(steps) }

    // Sync pager with current step
    LaunchedEffect(ui.index) {
        if (pagerState.currentPage != ui.index) {
            scope.launch { pagerState.animateScrollToPage(ui.index) }
        }
        if (ui.finished) onFinished()
    }

    // Tap gestures (pause/resume + manual navigation)
    val tapModifier = Modifier.pointerInput(ui.index, ui.progress) {
        detectTapGestures(
            onPress = {
                viewModel.pause()
                tryAwaitRelease()
                viewModel.play()
            },
            onTap = { offset ->
                if (!viewModel.canManuallyAdvance()) return@detectTapGestures
                val width = size.width
                if (offset.x < width / 3f) viewModel.prev()
                else viewModel.next()
            }
        )
    }

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(Modifier.fillMaxSize()) {

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds()
                    .then(tapModifier),
                userScrollEnabled = false
            ) { page ->
                val sceneId = steps[page].id
                val p = when {
                    page < ui.index -> 1f
                    page > ui.index -> 0f
                    else -> ui.progress
                }
                Box(Modifier.fillMaxSize()) {
                    when (sceneId) {
                        "marquee_intro" -> MarqueeIntroScene(progress = p)
                        "marquee_split" -> MarqueeSplitScene(progress = p)
                        else -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Page ${page + 1}")
                        }
                    }
                }
            }

            // Top segmented progress bar
            SegmentedProgressBar(
                progresses = viewModel.segmentProgresses(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .align(Alignment.TopCenter),
                activeColor = Color(0xFFD25917),
                inactiveColor = Color(0x44FFFFFF),
                height = 4.dp,
                gap = 4.dp
            )
        }
    }
}

@Composable
private fun SegmentedProgressBar(
    progresses: List<Float>,
    modifier: Modifier = Modifier,
    activeColor: Color,
    inactiveColor: Color,
    height: Dp,
    gap: Dp
) {
    Row(
        modifier = modifier
            .height(height)
            .clipToBounds(),
        horizontalArrangement = Arrangement.spacedBy(gap)
    ) {
        progresses.forEach { p ->
            val animatedP by animateFloatAsState(targetValue = p, label = "progressSeg")
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(inactiveColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = animatedP.coerceIn(0f, 1f))
                        .background(activeColor)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WrappedHostFirstTwoPreview() {

    val steps = listOf(
        WrappedStep("marquee_intro", durationMs = 3000, cooldownMs = 600),
        WrappedStep("marquee_split", durationMs = 3000, cooldownMs = 600),
        )
    WrappedHost(steps = steps)
}


private fun lerp(start: Float, end: Float, t: Float) = start + (end - start) * t
private fun lerpDp(start: Dp, end: Dp, t: Float) = (start.value + (end.value - start.value) * t).dp
