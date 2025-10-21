package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.wrapped

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Public entry point: call from NavHost with composable(NavRoute.WRAPPED) { WrappedScreen() }
 */
@Composable
fun WrappedScreen(
    modifier: Modifier = Modifier,
    onExit: () -> Unit = {}
) {
    val pages = remember {
        listOf(
            WrappedPage(
                id = "marquees",
                title = "Your SHPE\nWrapped is\nhere.",
                durationMs = 4200L // how long this page auto-plays
            ),
            WrappedPage(
                id = "straighten",
                title = "Let’s look\nat your year.",
                durationMs = 4200L
            ),
            WrappedPage(
                id = "stats1",
                title = "Most Active\nMonth: October",
                durationMs = 4200L
            )
        )
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )

    val scope = rememberCoroutineScope()

    // --- Auto-advance + cooldown logic ---
    val cooldownMs = 500L // time after a swipe before we allow another instant-advance
    var lastUserSwipeTs by remember { mutableStateOf(0L) }

    // auto-advance with per-page durations
    LaunchedEffect(pagerState.currentPage) {
        val targetDuration = pages[pagerState.currentPage].durationMs
        val startedAt = System.currentTimeMillis()
        while (System.currentTimeMillis() - startedAt < targetDuration) {
            // break early if user swipes
            if (!pagerState.isScrollInProgress) {
                delay(16L)
            } else break
        }
        if (!pagerState.isScrollInProgress) {
            val next = (pagerState.currentPage + 1) % pages.size
            pagerState.animateScrollToPage(next)
        }
    }

    // capture swipe finish to enforce cooldown
    LaunchedEffect(pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            lastUserSwipeTs = System.currentTimeMillis()
        }
    }

    // top progress fraction (for the overall linear indicator if you want it)
    val overallProgress by remember {
        derivedStateOf {
            (pagerState.currentPage + pagerState.currentPageOffsetFraction.coerceIn(0f, 1f)) / pages.size
        }
    }

    Surface(modifier = modifier.fillMaxSize(), color = Color.Black) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 14.dp, start = 12.dp, end = 12.dp, bottom = 16.dp)
        ) {

            // Segmented "stories" progress bar (like Instagram/Spotify Wrapped)
            WrappedProgressBar(
                modifier = Modifier.fillMaxWidth(),
                pageCount = pages.size,
                currentPage = pagerState.currentPage,
                currentPageOffset = pagerState.currentPageOffsetFraction.coerceIn(0f, 1f),
                trackColor = Color.White.copy(0.25f),
                progressColor = Color.White
            )

            Spacer(Modifier.height(12.dp))

            // The pager with animated content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    beyondViewportPageCount = 1,
                    userScrollEnabled = true // user can swipe; cooldown handled above
                ) { pageIndex ->
                    val page = pages[pageIndex]

                    // Per-page content/animations
                    WrappedPageContent(
                        page = page,
                        pagerState = pagerState,
                        index = pageIndex
                    )
                }

                // Optional: small "X" to exit
                TextButton(
                    onClick = onExit,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Text("Exit", color = Color.White)
                }
            }

            // Optional overall linear indicator (subtle)
            LinearProgressIndicator(
                progress = overallProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp)),
                trackColor = Color.White.copy(0.08f),
                color = Color(0xFF0B70BA)
            )
        }
    }
}

/* ------------------------------- Models ---------------------------------- */

/* ------------------------------ Progress UI ------------------------------- */

@Composable
fun WrappedProgressBar(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int,
    currentPageOffset: Float,
    gap: Dp = 6.dp,
    height: Dp = 4.dp,
    trackColor: Color = Color.LightGray,
    progressColor: Color = Color.White
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(gap)
    ) {
        repeat(pageCount) { i ->
            val fraction = when {
                i < currentPage -> 1f
                i == currentPage -> currentPageOffset
                else -> 0f
            }
            StorySegment(
                modifier = Modifier.weight(1f),
                height = height,
                fraction = fraction,
                trackColor = trackColor,
                progressColor = progressColor
            )
        }
    }
}

@Composable
fun StorySegment(
    modifier: Modifier,
    height: Dp,
    fraction: Float,
    trackColor: Color,
    progressColor: Color
) {
    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(percent = 50))
            .background(trackColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction.coerceIn(0f, 1f))
                .background(progressColor)
        )
    }
}

/* ------------------------------ Page Content ------------------------------ */

@Composable
private fun WrappedPageContent(
    page: WrappedPage,
    pagerState: PagerState,
    index: Int
) {
    // Shared transition factor for subtle depth/scale between pages
    val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
    val scale by animateFloatAsState(
        targetValue = if (index == pagerState.currentPage) 1f else 0.95f,
        animationSpec = tween(350, easing = LinearOutSlowInEasing)
    )
    val blurAmount = if (index == pagerState.currentPage) 0.dp else 2.dp

    when (page.id) {
        "marquees" -> FirstAnimationPage(title = page.title, scale = scale, blur = blurAmount)
        "straighten" -> SecondAnimationPage(title = page.title, scale = scale, blur = blurAmount)
        else -> StatsPage(title = page.title, scale = scale, blur = blurAmount)
    }
}

/**
 * Page 1 demo: four diagonal marquees moving in alternating directions, with center text.
 */
@Composable
private fun FirstAnimationPage(title: String, scale: Float, blur: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF7A2F))
            .blur(blur)
    ) {
        // Two diagonal layers moving in opposite directions
        DiagonalMarqueeLayer(
            text = "SHPE Wrapped   ",
            angle = 22f,
            speed = 28f,
            alpha = 0.18f,
            reverse = false
        )
        DiagonalMarqueeLayer(
            text = "SHPE Wrapped   ",
            angle = -22f,
            speed = 24f,
            alpha = 0.18f,
            reverse = true
        )

        // Center title with a subtle rotation pop on first appearance
        val rotation = remember { Animatable(0f) }
        LaunchedEffect(Unit) {
            rotation.snapTo(-6f)
            rotation.animateTo(0f, tween(600, easing = OvershootInterpolatorLike))
        }

        Text(
            text = title,
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 40.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .rotate(rotation.value)
                .padding(24.dp)
        )
    }
}

/**
 * Page 2 demo: the marquees "straighten" to top/bottom bands; center text rotates once.
 */
@Composable
private fun SecondAnimationPage(title: String, scale: Float, blur: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF7A2F))
            .blur(blur)
    ) {
        StraightMarqueeBand(
            text = "SHPE Wrapped   ",
            alignment = Alignment.TopCenter,
            speed = 30f,
            height = 90.dp,
            alpha = 0.18f
        )
        StraightMarqueeBand(
            text = "SHPE Wrapped   ",
            alignment = Alignment.BottomCenter,
            speed = 22f,
            height = 90.dp,
            alpha = 0.18f,
            reverse = true
        )

        val rotation = remember { Animatable(0f) }
        LaunchedEffect(Unit) {
            rotation.animateTo(360f, tween(900, easing = FastOutSlowInEasing))
            rotation.snapTo(0f)
        }

        Text(
            text = title,
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 38.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .rotate(rotation.value)
                .padding(24.dp)
        )
    }
}

/**
 * Placeholder stats/info page to iterate on later.
 */
@Composable
private fun StatsPage(title: String, scale: Float, blur: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B70BA))
            .blur(blur),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(22.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.W700,
                lineHeight = 34.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(18.dp))
            Text(
                text = "Coming next: real stats, charts, and transitions.\nThis is a placeholder screen to verify navigation and progress.",
                color = Color.White.copy(0.9f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

/* --------------------------- Marquee primitives --------------------------- */

@Composable
private fun DiagonalMarqueeLayer(
    text: String,
    angle: Float,
    speed: Float,
    alpha: Float,
    reverse: Boolean
) {
    val infinite = rememberInfiniteTransition(label = "diag-marquee")
    val offset by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween((60000 / speed).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "offset"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val step = size.minDimension / 6f
        val diag = kotlin.math.tan(Math.toRadians(angle.toDouble())).toFloat()
        val dir = if (reverse) -1f else 1f

        // draw repeated slanted rows of text using translate + drawContext.canvas.nativeCanvas if needed
        // For now, draw simple bands as a lightweight placeholder (swap for real text if you have a text-on-path util)
        for (i in -2..10) {
            val y = (i * step) + (offset * step * dir)
            translate(left = 0f, top = y) {
                drawRect(
                    color = Color.Black.copy(alpha = alpha),
                    size = androidx.compose.ui.geometry.Size(width = size.width, height = 10f)
                )
            }
        }
    }
}

@Composable
private fun StraightMarqueeBand(
    text: String,
    alignment: Alignment,
    speed: Float,
    height: Dp,
    alpha: Float,
    reverse: Boolean = false
) {
    val infinite = rememberInfiniteTransition(label = "straight-marquee")
    val offset by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween((60000 / speed).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "offset"
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = alignment
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        ) {
            val dir = if (reverse) -1f else 1f
            val bandHeight = size.height
            val tileW = size.minDimension / 3f
            val xShift = (offset * tileW * dir)

            // same lightweight placeholder bands; replace with repeated text as needed
            for (i in -2..8) {
                val x = (i * tileW) + xShift
                translate(left = x, top = bandHeight / 2f - 5f) {
                    drawRect(
                        color = Color.Black.copy(alpha = alpha),
                        size = androidx.compose.ui.geometry.Size(width = tileW * 0.8f, height = 10f)
                    )
                }
            }
        }
    }
}

private val OvershootInterpolatorLike = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1.2f)
