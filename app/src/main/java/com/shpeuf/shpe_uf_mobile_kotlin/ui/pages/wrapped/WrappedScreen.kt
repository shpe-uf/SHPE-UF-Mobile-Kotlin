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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import kotlin.math.tan

/**
 * Public entry point: call from NavHost with composable(NavRoute.WRAPPED) { WrappedScreen() }
 */
@Composable
fun WrappedScreen(
    modifier: Modifier = Modifier, shpeufAppViewModel: SHPEUFAppViewModel,
    onExit: () -> Unit = {}
) {
    val pages = remember {
        listOf(
            WrappedPage("intro",  "Your SHPE\nWrapped is\nhere.",       4200L),
            WrappedPage("story",  "Let’s look\nat your year.",         4200L),
            WrappedPage("stats1", "Most Active\nMonth: October",       4200L)
            // add your other pages here as before
        )
    }

    val uiState by shpeufAppViewModel.uiState.collectAsState()
    val isDarkMode = uiState.isDarkMode

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pages.size })

    var settledPage by remember { mutableIntStateOf(0) }
    var timeProgress by remember { mutableFloatStateOf(0f) }
    var pageStartTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var autoScrollEnabled by remember { mutableStateOf(true) }
    var isHolding by remember { mutableStateOf(false) }

    // Initial alignment with pager
    LaunchedEffect(Unit) {
        settledPage = pagerState.currentPage
        pageStartTime = System.currentTimeMillis()
    }

    // Timer + auto-scroll loop, driven by the "settled" page, and paused while
    LaunchedEffect(pagerState, pages.size, isHolding) {
        while (true) {
            delay(16L)

            // Pause timer while the pager is moving, or while user is pressing to "pause"
            if (pagerState.isScrollInProgress || isHolding) continue

            val now = System.currentTimeMillis()
            val duration = pages[settledPage].durationMs

            if (!autoScrollEnabled) {
                // When we've finished the last page, keep its bar full
                if (settledPage == pages.lastIndex) {
                    timeProgress = 1f
                }
                continue
            }

            val elapsed = now - pageStartTime
            timeProgress = (elapsed.toFloat() / duration).coerceIn(0f, 1f)

            if (timeProgress >= 1f) {
                val next = settledPage + 1
                if (next < pages.size) {
                    // Keep current segment full during the scroll animation
                    timeProgress = 1f
                    pagerState.animateScrollToPage(next)
                    // We do NOT reset here; that happens when the scroll settles.
                } else {
                    autoScrollEnabled = false
                    timeProgress = 1f
                }
            }
        }
    }

    // Detect when scrolling has finished and the displayed page has actually changed.
    // Only then do we adopt the new page as "settled" and reset its timer.
    LaunchedEffect(pagerState) {
        var lastPage = pagerState.currentPage

        snapshotFlow { pagerState.currentPage to pagerState.isScrollInProgress }
            .collect { (page, scrolling) ->
                if (!scrolling && page != lastPage) {
                    lastPage = page
                    settledPage = page
                    pageStartTime = System.currentTimeMillis()
                    timeProgress = 0f
                    autoScrollEnabled = page < pages.lastIndex
                }
            }
    }

    // Purely time-based fraction for the current settled page
    val segmentFraction = timeProgress.coerceIn(0f, 1f)

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        color = Color.Black
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                // "Hold to pause" – any finger down on the screen freezes the timer
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            val anyPressed = event.changes.any { it.pressed }
                            if (isHolding != anyPressed) {
                                isHolding = anyPressed
                            }
                        }
                    }
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 16.dp)
            ) {
                // Top segmented progress bar (one segment per "slide/animation")
                WrappedProgressBar(
                    modifier = Modifier.fillMaxWidth(),
                    pageCount = pages.size,
                    currentPage = settledPage,
                    currentFraction = segmentFraction,
                    trackColor = Color.White.copy(alpha = 0.25f),
                    progressColor = Color.White
                )

                Spacer(Modifier.height(12.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) { index ->
                    val page = pages[index]
                    val perPageProgress = if (index == settledPage) segmentFraction else 0f
                    WrappedPageContent(
                        page = page, pagerState = pagerState, index = index,
                        isDarkMode = isDarkMode, segmentProgress = perPageProgress
                    )
                }
            }
        }
    }
}

@Composable
fun WrappedProgressBar(modifier: Modifier, pageCount: Int, currentPage: Int,
    currentFraction: Float, trackColor: Color, progressColor: Color) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        repeat(pageCount) { i ->
            val fraction = when {
                i < currentPage -> 1f
                i == currentPage -> currentFraction
                else -> 0f
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(trackColor)
            ) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction)
                        .background(progressColor)
                )
            }
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
    index: Int,
    segmentProgress: Float,
    isDarkMode: Boolean
) {
    val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
    val scale by animateFloatAsState(
        targetValue = if (index == pagerState.currentPage) 1f else 0.95f,
        animationSpec = tween(350, easing = LinearOutSlowInEasing),
        label = "pageScale"
    )
    val blurAmount = if (index == pagerState.currentPage) 0.dp else 2.dp

    when (page.id) {
        "marquees" -> FirstAnimationPage(
            scale = scale,
            blur = blurAmount,
            segmentProgress = segmentProgress,
            isDarkMode = isDarkMode
        )
    }
}

/**
 * Page 1 demo: four diagonal marquees moving in alternating directions, with center text.
 */
@Composable
private fun FirstAnimationPage(
    scale: Float,
    blur: Dp,
    segmentProgress: Float,
    isDarkMode: Boolean,
    mostActiveMonth: String = "October",
    secondMonth: String = "November",
    thirdMonth: String = "September"
) {
    val bgColor = if (isDarkMode) Color(0xFF001B2F) else Color(0xFF022642)
    val primaryTextColor = if (isDarkMode) Color(0xFFFF7A2F) else Color(0xFFFF7A2F)
    val secondaryTextColor = if (isDarkMode) Color.White else Color.White

    // Helper to remap [start, end] → 0..1
    fun phase(start: Float, end: Float): Float {
        if (segmentProgress <= start) return 0f
        if (segmentProgress >= end) return 1f
        return ((segmentProgress - start) / (end - start)).coerceIn(0f, 1f)
    }

    val spinPhase = phase(0.25f, 0.40f)       // center text spin / morph
    val straightenPhase = phase(0.40f, 0.65f) // marquees + center text straighten
    val exitPhase = phase(0.65f, 0.80f)       // marquees + center text exit
    val statsPhase = phase(0.80f, 1.0f)       // stats appear

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .blur(blur)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        val maxH = maxHeight
        val maxW = maxWidth
        val density = LocalDensity.current

        // ───────────── Diagonal marquee bands (top & bottom) ──────────────
        AnimatedMarqueeBand(
            text = "SHPE Wrapped   ",
            isTop = true,
            reverse = false,
            straightenPhase = straightenPhase,
            exitPhase = exitPhase,
            baseColor = secondaryTextColor.copy(alpha = 0.20f),
            containerWidth = maxW,
            containerHeight = maxH
        )

        AnimatedMarqueeBand(
            text = "SHPE Wrapped   ",
            isTop = false,
            reverse = true,
            straightenPhase = straightenPhase,
            exitPhase = exitPhase,
            baseColor = secondaryTextColor.copy(alpha = 0.20f),
            containerWidth = maxW,
            containerHeight = maxH
        )

        // ───────────── Center text: SHPE Wrapped → Most Active Month ──────
        val centerStartAngle =  -18f
        val centerEndAngle = 0f
        val centerAngle = centerStartAngle * (1f - straightenPhase) + centerEndAngle * straightenPhase

        // Spin amount: 0 → 360 during spinPhase
        val spinRotation = 360f * spinPhase

        // Crossfade between the two labels
        val wrappedAlpha = 1f - spinPhase
        val mostActiveAlpha = spinPhase

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Centered stack
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer {
                        rotationZ = centerAngle + spinRotation
                    }
            ) {
                // Base "SHPE Wrapped" in orange with clipped white copies behind
                if (wrappedAlpha > 0f) {
                    Box(
                        modifier = Modifier.graphicsLayer {
                            alpha = wrappedAlpha
                        }
                    ) {
                        // Orange focus text
                        Text(
                            text = "SHPE Wrapped",
                            color = primaryTextColor,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )

                        // Faint white copies offset slightly (to mimic your old look)
                        Text(
                            text = "SHPE Wrapped   SHPE Wrapped   SHPE Wrapped",
                            color = secondaryTextColor.copy(alpha = 0.25f),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(x = (-12).dp)
                        )
                    }
                }

                // "Most Active Month" text
                if (mostActiveAlpha > 0f) {
                    Text(
                        text = "Most Active Month",
                        color = primaryTextColor,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .graphicsLayer {
                                alpha = mostActiveAlpha
                            }
                    )
                }
            }

            // ───────────── Stats block (slides in after center exits) ──────
            if (statsPhase > 0f) {
                val statsOffsetY = with(density) { (maxH * (0.25f + (1f - statsPhase) * 0.15f)).toPx() }

                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .graphicsLayer {
                            translationY = statsOffsetY
                            alpha = statsPhase
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your most active",
                        color = secondaryTextColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Month was:",
                        color = secondaryTextColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = mostActiveMonth,
                        color = primaryTextColor,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                }

                // Bottom "followed closely by" text
                val bottomPhase = phase(0.85f, 1.0f) // slightly delayed within statsPhase
                if (bottomPhase > 0f) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 32.dp)
                            .graphicsLayer {
                                alpha = bottomPhase
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "followed closely by:",
                            color = secondaryTextColor.copy(alpha = 0.9f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = secondMonth,
                            color = secondaryTextColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = thirdMonth,
                            color = secondaryTextColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
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
        val diag = tan(Math.toRadians(angle.toDouble())).toFloat()
        val dir = if (reverse) -1f else 1f

        // draw repeated slanted rows of text using translate + drawContext.canvas.nativeCanvas if needed
        // For now, draw simple bands as a lightweight placeholder (swap for real text if you have a text-on-path util)
        for (i in -2..10) {
            val y = (i * step) + (offset * step * dir)
            translate(left = 0f, top = y) {
                drawRect(
                    color = Color.Black.copy(alpha = alpha),
                    size = Size(width = size.width, height = 10f)
                )
            }
        }
    }
}

@Composable
private fun AnimatedMarqueeBand(
    text: String,
    isTop: Boolean,
    reverse: Boolean,
    straightenPhase: Float,
    exitPhase: Float,
    baseColor: Color,
    containerWidth: Dp,
    containerHeight: Dp
) {
    val density = LocalDensity.current
    val infiniteTransition = rememberInfiniteTransition(label = "marqueeBase")

    // Base horizontal scrolling (looping)
    val baseShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "marqueeShift"
    )

    // Angle from ±18° → 0°
    val startAngle = if (isTop) 18f else -18f
    val angle = startAngle * (1f - straightenPhase)

    // Move toward "poles"
    val startBias = if (isTop) 0.35f else 0.65f
    val endBias = if (isTop) 0.12f else 0.88f
    val bias = startBias * (1f - straightenPhase) + endBias * straightenPhase

    // Extra horizontal offset to exit off screen
    val exitDir = if (reverse) 1f else -1f
    val exitPx = with(density) { (containerWidth * 0.6f * exitPhase).toPx() }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val widthPx = constraints.maxWidth.toFloat()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .graphicsLayer {
                    rotationZ = angle
                    translationY = with(density) {
                        (containerHeight * (bias - 0.5f)).toPx()
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        val dir = if (reverse) -1f else 1f
                        translationX = dir * baseShift * widthPx + exitDir * exitPx
                    },
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Two copies to cover the width while scrolling
                repeat(3) {
                    Text(
                        text = text,
                        color = baseColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

private val OvershootInterpolatorLike = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1.2f)
