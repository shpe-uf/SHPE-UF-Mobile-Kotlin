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
import androidx.lifecycle.viewmodel.compose.viewModel
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
            WrappedPage("marquee_shpe", "SHPE Wrapped", 2500L), // seg 0
            WrappedPage("marquee_most_active", "Most Active Month", 2500L), // seg 1
            WrappedPage("marquee_settle", "Most Active Month (settle)", 2000L), //seg 2
            WrappedPage("stats1", "Most Active Month: October", 6000L), // seg 3
            WrappedPage("points_marquees", "Total SHPoints marquees", 3000L), // seg 4
            WrappedPage("points_stats", "Total SHPoints stats", 6000L),  // seg 5
        )
    }

    val pageGroups = remember {
        listOf(
            0..2,
            3..3,
            4..5,

        )
    }

    val segmentToPage = remember(pageGroups, segments.size) {
        IntArray(segments.size) { segIndex ->
            pageGroups.indexOfFirst { segIndex in it }.coerceAtLeast(0)
        }
    }

    val pageSnapSegment = remember(pageGroups) {
        pageGroups.map { range -> range.first } // change .first to .last if you want “end of lump”
    }

    val uiState by shpeufAppViewModel.uiState.collectAsState()
    val isDarkMode = uiState.isDarkMode

    val wrappedViewModel: WrappedViewModel = viewModel()
    val wrappedState by wrappedViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.id) {
        if (uiState.id.isNotBlank()) {
            wrappedViewModel.getWrappedData(uiState.id)
        }
    }

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

    // One pager page per segment
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pageGroups.size }
    )

    // Global timeline
    var globalTimeMs by remember { mutableLongStateOf(0L) }
    var lastTickRealTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    // Pausing flags
    var isHolding by remember { mutableStateOf(false) }
    var isSettling by remember { mutableStateOf(false) }
    var autoPlayEnabled by remember { mutableStateOf(true) }

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

    val currentPagerPageIndex by remember {
        derivedStateOf {
            segmentToPage
                .getOrNull(currentSegmentIndex)
                ?: 0
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

    // ---- 5) User scroll snap timeline to appropriate segment start ----
    LaunchedEffect(pagerState, totalDuration, segmentTimings) {
        var wasScrolling = false
        var lastSettledPage = pagerState.currentPage

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

                        if (page != lastSettledPage) {
                            val snapSeg = pageSnapSegment
                                .getOrNull(page)
                                ?.coerceIn(0, segmentTimings.lastIndex)

                            if (snapSeg != null) {
                                val timing = segmentTimings[snapSeg]
                                globalTimeMs = timing.startMs   // jump to that segment’s start
                                lastTickRealTime = System.currentTimeMillis()
                                autoPlayEnabled = true
                            }

                            lastSettledPage = page
                        }

                        delay(250L)
                        isSettling = false
                    }
                }
            }
    }

    val centerTransitionProgress by remember {
        derivedStateOf {
            when (currentSegmentIndex) {
                0 -> 0f
                1 -> currentSegmentFraction
                else -> 1f
            }
        }
    }

    val settleProgress by remember {
        derivedStateOf {
            when {
                currentSegmentIndex < 2 -> 0f
                currentSegmentIndex == 2 -> currentSegmentFraction
                else -> 1f
            }
        }
    }

    val mostActiveStatsProgress by remember {
        derivedStateOf {
            when {
                currentSegmentIndex < 3 -> 0f
                currentSegmentIndex == 3 -> currentSegmentFraction
                else -> 1f
            }
        }
    }

    val pointsMarqueeProgress by remember {
        derivedStateOf {
            when {
                currentSegmentIndex < 4 -> 0f
                currentSegmentIndex == 4 -> currentSegmentFraction
                else -> 1f
            }
        }
    }

    val pointsStatsProgress by remember {
        derivedStateOf {
            when {
                currentSegmentIndex < 5 -> 0f
                currentSegmentIndex == 5 -> currentSegmentFraction
                else -> 1f
            }
        }
    }

    val isMarqueePaused = isHolding || isSettling

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
                    pageCount = segments.size,
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

                //7) Pager
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
                                isMarqueePaused = isMarqueePaused,
                                progress = centerTransitionProgress,
                                isDarkMode = isDarkMode,
                                settleProgress = settleProgress,
                            )
                        }

                        1 -> {
                            MostActiveMonthStatsPage(
                                progress = mostActiveStatsProgress,
                                isDarkMode = isDarkMode,
                                topMonth = wrappedState.topMonth,
                                secondMonth = wrappedState.secondMonth,
                                thirdMonth = wrappedState.thirdMonth
                            )
                        }

                        else -> {
                            when (currentSegmentIndex) {
                                4 -> {
                                    PointsVerticalMarqueePage(
                                        progress = pointsMarqueeProgress,
                                        isDarkMode = isDarkMode,
                                        pointsText = wrappedState.points,
                                        isPaused = isMarqueePaused
                                    )
                                }

                                5 -> {
                                    PointsStatsPage(
                                        progress = pointsStatsProgress,
                                        isDarkMode = isDarkMode,
                                        pointsText = wrappedState.points,
                                        percentileText = wrappedState.percentile
                                    )
                                }

                                // If we somehow land here with another segment index, just
                                // default to the stats page so we don't show the wrong thing.
                                else -> {
                                    PointsStatsPage(
                                        progress = pointsStatsProgress,
                                        isDarkMode = isDarkMode,
                                        pointsText = wrappedState.points,
                                        percentileText = wrappedState.percentile
                                    )
                                }
                            }
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
    isDarkMode: Boolean,
    settleProgress: Float,
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
                    baseTextWidthPx = layout.size.width.toFloat()
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
            centerTransitionProgress = progress,
            centerBaseTextWidthPx = baseTextWidthPx,
            settleProgress = settleProgress
        )
    }
}

@Composable
private fun MostActiveMonthStatsPage(
    progress: Float,
    isDarkMode: Boolean,
    topMonth: String,
    secondMonth: String,
    thirdMonth: String
) {
    val bg =
        if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    val primaryText =
        if (isDarkMode) Color.White else Color.Black

    val mainEnterEnd = 0.25f
    val mainEnter = (progress / mainEnterEnd).coerceIn(0f, 1f)
    val mainOffset = (1f - mainEnter) * 200f
    val mainAlpha = mainEnter

    val bottomEnterStart = 0.20f
    val bottomEnterEnd = 0.35f
    val bottomEnter = ((progress - bottomEnterStart) / (bottomEnterEnd - bottomEnterStart)).coerceIn(0f, 1f)
    val bottomOffset = (1f - bottomEnter) * 200f
    val bottomAlpha = bottomEnter

    val exitStart = 0.8f
    val exitProgress = ((progress - exitStart) / (1f - exitStart)).coerceIn(0f, 1f)
    val exitOffset = -200f * exitProgress
    val exitAlphaFactor = 1f - exitProgress

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // Center block: "Your Most Active Month Was: [Month]"
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    translationY = mainOffset + exitOffset
                    alpha = mainAlpha * exitAlphaFactor
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Most Active\nMonth Was:",
                color = primaryText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = topMonth,
                color = OrangeSHPE,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        // Bottom block: "Followed closely by: ..."
        if (secondMonth.isNotBlank() || thirdMonth.isNotBlank()) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .graphicsLayer {
                        translationY = bottomOffset + exitOffset
                        alpha = bottomAlpha * exitAlphaFactor
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Followed closely by:",
                    color = primaryText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                if (secondMonth.isNotBlank()) {
                    Text(
                        text = secondMonth,
                        color = primaryText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                if (thirdMonth.isNotBlank()) {
                    Text(
                        text = thirdMonth,
                        color = primaryText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}


@Composable
private fun PointsVerticalMarqueePage(
    progress: Float,
    isDarkMode: Boolean,
    pointsText: Int,
    isPaused: Boolean
) {
    val bg =
        if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    val primaryText =
        if (isDarkMode) Color.White else Color.Black

    // After ~70% of the segment, stop spawning so the text can leave
    val spawnMore = progress < 0.7f

    val columnCount = if (pointsText.toString().length == 1) 5 else 4

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(columnCount) { index ->
                val movingUp = index % 2 == 0
                VerticalMarqueeColumn(
                    text = pointsText.toString(),
                    color = primaryText,
                    fontSize = 32.sp,
                    isMovingUp = movingUp,
                    spawnMore = spawnMore,
                    isPaused = isPaused,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        }
    }
}


@Composable
private fun PointsStatsPage(
    progress: Float,
    isDarkMode: Boolean,
    pointsText: Int,
    percentileText: Int
) {
    val bg =
        if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    val primaryText =
        if (isDarkMode) Color.White else Color.Black

    val mainEnterEnd = 0.25f
    val mainEnter = (progress / mainEnterEnd).coerceIn(0f, 1f)
    val mainOffset = (1f - mainEnter) * 200f
    val mainAlpha = mainEnter

    val bottomEnterStart = 0.20f
    val bottomEnterEnd = 0.35f
    val bottomEnter = ((progress - bottomEnterStart) / (bottomEnterEnd - bottomEnterStart))
        .coerceIn(0f, 1f)
    val bottomOffset = (1f - bottomEnter) * 200f
    val bottomAlpha = bottomEnter

    val exitStart = 0.8f
    val exitProgress = ((progress - exitStart) / (1f - exitStart)).coerceIn(0f, 1f)
    val exitOffset = -200f * exitProgress
    val exitAlphaFactor = 1f - exitProgress

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // Center block: "21 / Total SHPoints"
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    translationY = mainOffset + exitOffset
                    alpha = mainAlpha * exitAlphaFactor
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = pointsText.toString(),
                color = OrangeSHPE,
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Total SHPoints",
                color = primaryText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }

        // Bottom block: "This puts you in the TOP 85%"
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .graphicsLayer {
                    translationY = bottomOffset + exitOffset
                    alpha = bottomAlpha * exitAlphaFactor
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "This puts you in the",
                color = primaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "TOP ${percentileText}%",
                color = primaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }
    }
}
