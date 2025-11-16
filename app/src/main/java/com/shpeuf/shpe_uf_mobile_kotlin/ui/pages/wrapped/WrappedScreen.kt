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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.WhiteSHPE
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.wrapped.DiagonalMarqueeScaffold
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.wrapped.MarqueeRow
import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.wrapped.marqueeColors

/**
 * Public entry point: call from NavHost with composable(NavRoute.WRAPPED) { WrappedScreen() }
 */
@Composable
fun WrappedScreen(
    modifier: Modifier = Modifier,
    shpeufAppViewModel: SHPEUFAppViewModel,
    onExit: () -> Unit = {}
) {
    // this is disjoint from page transition logic.
    val segments = remember {
        listOf(
            WrappedPage("marquee_shpe", "SHPE Wrapped", 2500L),           // seg 0
            WrappedPage("marquee_most_active", "Most Active Month", 2500L), // seg 1
            WrappedPage("stats1", "Most Active Month: October", 3500L),  // seg 2
        )
    }

    val uiState by shpeufAppViewModel.uiState.collectAsState()
    val isDarkMode = uiState.isDarkMode

    // ---- 1) Segment timings (same as before but for `segments`) ----
    val segmentTimings = remember(segments) {
        var acc = 0L
        segments.mapIndexed { index, seg ->
            val start = acc
            val end = acc + seg.durationMs
            acc = end
            PageTiming(index, start, end)
        }
    }

    val totalDuration = remember(segmentTimings) {
        segmentTimings.lastOrNull()?.endMs ?: 0L
    }

    // page 0: marquee (both SHPE + Most Active)
    // page 1: stats1
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })

    // Global timeline
    var globalTimeMs by remember { mutableLongStateOf(0L) }
    var lastTickRealTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    // Pausing flags
    var isHolding by remember { mutableStateOf(false) }
    var isSettling by remember { mutableStateOf(false) }
    var autoPlayEnabled by remember { mutableStateOf(true) }

    // ---- 2) Current segment + fraction (timeline-based) ----
    val currentSegmentIndex by remember {
        derivedStateOf {
            if (segmentTimings.isEmpty()) 0
            else {
                val t = globalTimeMs.coerceIn(0L, totalDuration)
                val timing = segmentTimings.firstOrNull { t < it.endMs } ?: segmentTimings.last()
                timing.index
            }
        }
    }

    val currentSegmentFraction by remember {
        derivedStateOf {
            val timing = segmentTimings.getOrNull(currentSegmentIndex) ?: return@derivedStateOf 0f
            val clamped = globalTimeMs.coerceIn(timing.startMs, timing.endMs)
            val dur = (timing.endMs - timing.startMs).coerceAtLeast(1L)
            (clamped - timing.startMs).toFloat() / dur.toFloat()
        }
    }

    // Derived pager page from segment index
    // seg 0 & 1 => page 0 (marquee), seg 2 => page 1 (stats)
    val currentPagerPageIndex by remember {
        derivedStateOf {
            when (currentSegmentIndex) {
                0, 1 -> 0
                else -> 1
            }
        }
    }

    // ---- 3) Global timer (unchanged logic, but uses new paused flag) ----
    LaunchedEffect(totalDuration) {
        lastTickRealTime = System.currentTimeMillis()
        while (true) {
            val now = System.currentTimeMillis()
            val delta = now - lastTickRealTime
            lastTickRealTime = now

            val paused = !autoPlayEnabled || isHolding || isSettling || totalDuration <= 0L

            if (!paused) {
                if (globalTimeMs < totalDuration) {
                    globalTimeMs = (globalTimeMs + delta).coerceAtMost(totalDuration)
                    if (globalTimeMs >= totalDuration) {
                        autoPlayEnabled = false
                    }
                }
            }

            delay(16L)
        }
    }

    // ---- 4) Keep pager aligned with current *segment* ----
    LaunchedEffect(currentPagerPageIndex) {
        if (!pagerState.isScrollInProgress && pagerState.currentPage != currentPagerPageIndex) {
            pagerState.animateScrollToPage(currentPagerPageIndex)
        }
    }

    // ---- 5) User scroll → snap timeline to appropriate segment start ----
    LaunchedEffect(pagerState, totalDuration) {
        var wasScrolling = false

        snapshotFlow { pagerState.currentPage to pagerState.isScrollInProgress }
            .collect { (page, scrolling) ->
                if (scrolling) {
                    if (!wasScrolling) {
                        wasScrolling = true
                        isSettling = true
                    }
                } else {
                    if (wasScrolling) {
                        wasScrolling = false

                        // Map pager page -> segment index whose start we want
                        // page 0 => first marquee segment (index 0)
                        // page 1 => stats segment (index 2)
                        val targetSegmentIndex = when (page) {
                            0 -> 0
                            else -> 2
                        }

                        segmentTimings.getOrNull(targetSegmentIndex)?.let { timing ->
                            globalTimeMs = timing.startMs
                            lastTickRealTime = System.currentTimeMillis()
                            autoPlayEnabled = true
                        }

                        delay(250L)
                        isSettling = false
                    }
                }
            }
    }

    // 6) Values wired into animations
    // seg 0: still SHPE Wrapped
    // seg 1: sliding SHPE -> Most Active
    // seg >=2: fully Most Active
    val centerTransitionProgress by remember {
        derivedStateOf {
            when (currentSegmentIndex) {
                0 -> 0f
                1 -> currentSegmentFraction
                else -> 1f
            }
        }
    }

    val statsPageProgress by remember {
        derivedStateOf {
            when {
                currentSegmentIndex < 2 -> 0f
                currentSegmentIndex == 2 -> currentSegmentFraction
                else -> 1f
            }
        }
    }

    val isMarqueePaused = !autoPlayEnabled || isHolding || isSettling

    // Progress bar still has 3 segments (for the 3 timeline segments above)
    val bgColor =
        if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    val trackColor =
        if (isDarkMode) Color.White.copy(alpha = 0.25f) else Color.Black.copy(alpha = 0.12f)
    val progressColor =
        if (isDarkMode) WhiteSHPE else Color.Black

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(bgColor),
        color = bgColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            val anyPressed = event.changes.any { it.pressed }
                            if (anyPressed != isHolding) {
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
                // Top progress bar uses segment indices
                WrappedProgressBar(
                    modifier = Modifier.fillMaxWidth(),
                    pageCount = segments.size,              // 3 segments
                    currentPage = currentSegmentIndex,
                    currentFraction = currentSegmentFraction,
                    trackColor = trackColor,
                    progressColor = progressColor
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onExit,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = if (isDarkMode) Color.White else Color.Black
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // ---- 7) Pager with only 2 visual pages ----
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    userScrollEnabled = true
                ) { index ->
                    when (index) {
                        0 -> {
                            MarqueeCombinedPage(
                                isDarkMode = isDarkMode,
                                isMarqueePaused = isMarqueePaused,
                                progress = centerTransitionProgress
                            )
                        }

                        else -> {
                            // stats1 page, driven by statsPageProgress
                            SimpleWrappedPage(
                                page = segments[2], // "stats1"
                                progress = statsPageProgress,
                                isDarkMode = isDarkMode
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WrappedProgressBar(
    modifier: Modifier,
    pageCount: Int,
    currentPage: Int,
    currentFraction: Float,
    trackColor: Color,
    progressColor: Color
) {
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

/* ------------------------------ Page Content ------------------------------ */

/**
 * PAGE 1:
 * Center line just says "SHPE Wrapped" in orange, statically.
 */
@Composable
private fun MarqueeCombinedPage(
    isMarqueePaused: Boolean,
    progress: Float,      // treat this as centerTransitionProgress 0f..1f
    isDarkMode: Boolean
) {
    val (_, _, accent) = marqueeColors(isDarkMode)
    val baseFontSize = 28.sp

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val maxW = constraints.maxWidth.toFloat()
        var scale by remember { mutableFloatStateOf(1f) }
        var baseTextWidthPx by remember { mutableFloatStateOf(0f) }

        // invisible text just to compute scale
        Text(
            text = "SHPE Wrapped",
            fontSize = baseFontSize,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier.alpha(0f),
            onTextLayout = { layout ->
                if (layout.size.width > 0 && maxW > 0) {
                    val target = maxW * 0.75f
                    val newScale = (target / layout.size.width).coerceAtMost(3f)
                    if (newScale != scale) scale = newScale
                }
            }
        )

        // The scaffold itself is responsible for drawing the flanked center text
        // and animating SHPE Wrapped -> Most Active Month.
        DiagonalMarqueeScaffold(
            isDarkMode = isDarkMode,
            scale = scale,
            fontSize = baseFontSize,
            isMarqueePaused = isMarqueePaused,
            topSpawnProgress = 0f,        // you can later tie these to a phase
            bottomSpawnProgress = 0f,
            centerTransitionProgress = progress,
            centerBaseTextWidthPx = baseTextWidthPx
        )
    }
}


@Composable
private fun SimpleWrappedPage(
    page: WrappedPage,
    progress: Float,
    isDarkMode: Boolean
) {
    val bg =
        if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    val textColor =
        if (isDarkMode) Color.White else Color.Black

    val animatedScale by animateFloatAsState(
        targetValue = 0.95f + 0.05f * (1f - progress),
        animationSpec = tween(durationMillis = 250, easing = LinearEasing),
        label = "pageScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(24.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .blur(Dp(0f))
        ) {
            Text(
                text = page.title,
                color = textColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scaleX = animatedScale
                        scaleY = animatedScale
                    }
            )
        }
    }
}