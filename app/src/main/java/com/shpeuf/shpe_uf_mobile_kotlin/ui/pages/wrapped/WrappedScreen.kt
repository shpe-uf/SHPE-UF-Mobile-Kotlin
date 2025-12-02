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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
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
import kotlin.math.PI
import kotlin.math.sin
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb


/**
 * Public entry point: call from NavHost with composable(NavRoute.WRAPPED) { WrappedScreen() }
 */
@Composable
fun WrappedScreen(modifier: Modifier = Modifier,
    shpeufAppViewModel: SHPEUFAppViewModel,
    onExit: () -> Unit = {}
) {
    val segments = remember {
        listOf(
            WrappedPage("marquee_shpe", "SHPE Wrapped", 2500L), // seg 0
            WrappedPage("marquee_most_active", "Most Active Month", 2500L), // seg 1
            WrappedPage("marquee_settle", "Most Active Month (settle)", 2000L), //seg 2
            WrappedPage("stats1", "Most Active Month: October", 6000L), // seg 3
            WrappedPage("points_marquees", "Total SHPoints marquees", 6000L), // seg 4
            WrappedPage("points_stats", "Total SHPoints stats", 6000L), // seg 5
            WrappedPage("top_category_intro", "Top Category intro", 5500L), // seg 6
            WrappedPage("top_category_stats", "Top Category stats", 6000L), // seg 7
            WrappedPage("years_radar", "Years radar animation", 8000L), // seg 8
            WrappedPage("years_stats", "Years stat page", 6000L), // seg 9
            WrappedPage("overall_intro", "Overall persona intro", 5000L), // seg 10
            WrappedPage("overall_persona", "Overall persona result", 6000L), // seg 11
        )
    }

    val pageGroups = remember {
        listOf(
            0..2,
            3..3,
            4..5,
            6..7, //bluh
            8..9,
            10..11,
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

    val topCategoryIntroProgress by remember {
        derivedStateOf {
            when {
                currentSegmentIndex < 6 -> 0f
                currentSegmentIndex == 6 -> currentSegmentFraction
                else -> 1f
            }
        }
    }

    val topCategoryStatsProgress by remember {
        derivedStateOf {
            when {
                currentSegmentIndex < 7 -> 0f
                currentSegmentIndex == 7 -> currentSegmentFraction
                else -> 1f
            }
        }
    }

    val yearsRadarProgress by remember {
        derivedStateOf {
            when {
                currentSegmentIndex < 8 -> 0f
                currentSegmentIndex == 8 -> currentSegmentFraction
                else -> 1f
            }
        }
    }

    val yearsStatsProgress by remember {
        derivedStateOf {
            when {
                currentSegmentIndex < 9 -> 0f
                currentSegmentIndex == 9 -> currentSegmentFraction
                else -> 1f
            }
        }
    }

    val overallIntroProgress by remember {
        derivedStateOf {
            when {
                currentSegmentIndex < 10 -> 0f
                currentSegmentIndex == 10 -> currentSegmentFraction
                else -> 1f
            }
        }
    }

    val overallPersonaProgress by remember {
        derivedStateOf {
            when {
                currentSegmentIndex < 11 -> 0f
                currentSegmentIndex == 11 -> currentSegmentFraction
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

                        2 -> {
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

                                //Edge case shi
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

                        3 -> {
                            when (currentSegmentIndex) {
                                6 -> {
                                    TopCategoryIntroPage(
                                        progress = topCategoryIntroProgress,
                                        isDarkMode = isDarkMode
                                    )
                                }

                                7 -> {
                                    TopCategoryStatsPage(
                                        progress = topCategoryStatsProgress,
                                        isDarkMode = isDarkMode,
                                        topCategory = wrappedState.topCategory,
                                        countLabel = wrappedState.topCategoryCount.toString(),
                                        eventTypeLabel = wrappedState.topCategory
                                    )
                                }

                                else -> {
                                    TopCategoryStatsPage(
                                        progress = topCategoryStatsProgress,
                                        isDarkMode = isDarkMode,
                                        topCategory = wrappedState.topCategory,
                                        countLabel = wrappedState.topCategoryCount.toString(),
                                        eventTypeLabel = wrappedState.topCategory
                                    )
                                }
                            }
                        }

                        4 -> {
                            when (currentSegmentIndex) {
                                8 -> {
                                    YearsRadarMarqueePage(
                                        progress = yearsRadarProgress,
                                        isDarkMode = isDarkMode,
                                        years = wrappedState.yearsInShpe,
                                        isPaused = isMarqueePaused
                                    )
                                }

                                9 -> {
                                    YearsAsShpeitoStatsPage(
                                        progress = yearsStatsProgress,
                                        isDarkMode = isDarkMode,
                                        yearsInShpe = wrappedState.yearsInShpe
                                    )
                                }

                                else -> {
                                    YearsAsShpeitoStatsPage(
                                        progress = yearsStatsProgress,
                                        isDarkMode = isDarkMode,
                                        yearsInShpe = wrappedState.yearsInShpe
                                    )
                                }
                            }
                        }

                        5 -> {
                            when (currentSegmentIndex) {
                                10 -> {
                                    OverallIntroPage(
                                        progress = overallIntroProgress,
                                        isDarkMode = isDarkMode
                                    )
                                }
                                11 -> {
                                    OverallPersonaPage(
                                        progress = overallPersonaProgress,
                                        isDarkMode = isDarkMode,
                                        topEventType = wrappedState.topCategory   // your most frequent type
                                    )
                                }
                                else -> {
                                    OverallPersonaPage(
                                        progress = overallPersonaProgress,
                                        isDarkMode = isDarkMode,
                                        topEventType = wrappedState.topCategory
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
    val orange = OrangeSHPE
    val lightBlue = if (isDarkMode) Color(0xFF93E1FF) else Color(0xFF0B70BA)

    val digits = remember(pointsText) { pointsText.toString().length.coerceAtLeast(1) }

    val (columnCount, fontSize) = remember(digits) {
        when (digits) {
            1 -> 7 to 64.sp
            2 -> 4 to 56.sp
            3 -> 3 to 48.sp
            else -> 3 to 44.sp
        }
    }

    val t = progress.coerceIn(0f, 1f)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 4.dp, vertical = 24.dp)
    ) {
        // A bit larger than height so at t=0 and t=1 nothing is visible
        val distancePx = with(LocalDensity.current) { (maxHeight * 1.4f).toPx() }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 0.dp)
        ) {
            repeat(columnCount) { index ->
                val isOdd = ((index + 1) % 2 == 1)
                val accent = if (isOdd) orange else lightBlue

                // Up columns: +D → -D, Down columns: -D → +D, all linear in t
                val offsetY = if (isOdd) {
                    distancePx * (1f - 2f * t)        // +D at 0, 0 at .5, -D at 1
                } else {
                    distancePx * (2f * t - 1f)        // -D at 0, 0 at .5, +D at 1
                }

                VerticalMarqueeColumn(
                    text = pointsText.toString(),
                    baseTextColor = primaryText,
                    accentColor = accent,
                    fontSize = fontSize,
                    isMovingUp = isOdd,
                    spawnMore = true,           // kept for API compatibility
                    isPaused = isPaused,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .graphicsLayer {
                            translationY = offsetY
                        }
                        .clipToBounds()
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


@Composable
private fun TopCategoryIntroPage(
    progress: Float,
    isDarkMode: Boolean
) {
    val bg =
        if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    val primaryText =
        if (isDarkMode) Color.White else Color.Black

    val message = "Your Top Category\nof the Semester is..."

    val lines = remember(message) { message.split("\n") }

    // Global appear / disappear windows
    val appearEnd = 0.55f
    val disappearStart = 0.75f

    // Phases
    val appearPhase = (progress / appearEnd).coerceIn(0f, 1f)
    val disappearPhase =
        ((progress - disappearStart) / (1f - disappearStart)).coerceIn(0f, 1f)

    // For indexing letters globally (to stagger pop in/out)
    val totalChars = remember(lines) { lines.sumOf { it.length } }

    // Bobbing animation (shared)
    val density = LocalDensity.current
    val maxBobPx = with(density) { 6.dp.toPx() }
    val infiniteTransition = rememberInfiniteTransition(label = "topCategoryBob")
    val bobPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2f * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bobPhase"
    )

    val orange = OrangeSHPE
    val lightBlue = Color(0xFF93E1FF)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var globalIndex = 0
            var colorIndex = 0

            lines.forEachIndexed { lineIndex, line ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    line.forEach { ch ->
                        val myIndex = globalIndex
                        globalIndex++

                        val isSpace = ch == ' '

                        val baseFrac = if (totalChars <= 1) 0f
                        else myIndex.toFloat() / (totalChars - 1).toFloat()

                        val letterSpan = 0.18f

                        // ---- PHASED ALPHA / SCALE ----
                        var alpha: Float
                        var scale: Float

                        if (progress < appearEnd) {
                            // Appear phase: staggered pop-in
                            val appearLocal =
                                ((appearPhase - baseFrac) / letterSpan).coerceIn(0f, 1f)
                            alpha = appearLocal
                            scale = 0.7f + 0.3f * appearLocal
                        } else {
                            // Middle plateau: everything fully solid
                            alpha = 1f
                            scale = 1f
                        }

                        if (progress >= disappearStart) {
                            // Disappear phase: staggered pop-out
                            val disappearLocal =
                                ((disappearPhase - baseFrac) / letterSpan).coerceIn(0f, 1f)
                            if (disappearLocal > 0f) {
                                alpha *= (1f - disappearLocal)
                                scale *= (1f + 0.25f * disappearLocal)
                            }
                        }

                        if (alpha <= 0.01f) {
                            alpha = 0f
                        }

                        // Alternate orange / light blue on non-space letters
                        val baseColor = if (isSpace) {
                            primaryText
                        } else {
                            val c = if (colorIndex % 2 == 0) orange else lightBlue
                            colorIndex++
                            c
                        }

                        val bobOffsetPx =
                            if (alpha > 0f && !isSpace) {
                                sin(bobPhase + myIndex * 0.4f) * maxBobPx
                            } else 0f

                        Text(
                            text = ch.toString(),
                            color = baseColor, // keep color fully opaque; alpha is in layer
                            fontSize = 26.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.graphicsLayer {
                                this.alpha = alpha
                                scaleX = scale
                                scaleY = scale
                                translationY = bobOffsetPx
                            }
                        )
                    }
                }

                if (lineIndex != lines.lastIndex) {
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}


@Composable
private fun TopCategoryStatsPage(
    progress: Float,
    isDarkMode: Boolean,
    topCategory: String,
    countLabel: String,
    eventTypeLabel: String
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
    val bottomEnter =
        ((progress - bottomEnterStart) / (bottomEnterEnd - bottomEnterStart))
            .coerceIn(0f, 1f)
    val bottomOffset = (1f - bottomEnter) * 200f
    val bottomAlpha = bottomEnter

    val exitStart = 0.8f
    val exitProgress =
        ((progress - exitStart) / (1f - exitStart)).coerceIn(0f, 1f)
    val exitOffset = -200f * exitProgress
    val exitAlphaFactor = 1f - exitProgress

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // Center block: "Top Category\n[Top event type]"
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
                text = "Top Category",
                color = primaryText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = topCategory,
                color = OrangeSHPE,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }

        // Bottom block: "You attended X\n[Event Type] events"
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
                text = "You attended $countLabel",
                color = primaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "$eventTypeLabel events",
                color = primaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun YearsRadarMarqueePage(
    progress: Float,
    isDarkMode: Boolean,
    years: Int,
    isPaused: Boolean
) {
    val (bgColor, whiteText, shpeOrange) = marqueeColors(isDarkMode)
    val clampedYears = years.coerceAtLeast(1)
    val phrase = "$clampedYears YEARS"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val textSizePx = 24.sp.toPx()
            fun makePaint(color: Color) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                this.color = color.toArgb()
                textSize = textSizePx
                textAlign = Paint.Align.CENTER
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }

            val orangePaint = makePaint(shpeOrange)
            val whitePaint = makePaint(whiteText)
            val phraseWidth = orangePaint.measureText(phrase)
            val spacingFactor = 1.3f
            val phraseSpacing = phraseWidth * spacingFactor

            val layerCount = 9
            val centerX = size.width / 2f
            val centerY = size.height * 1.5f
            val minRadius = size.height * 0.6f
            val maxRadius = size.height * 1.4f
            val radiusStep =
                if (layerCount > 1) (maxRadius - minRadius) / (layerCount - 1) else 0f

            // --- rotate the slice left so its leading edge begins at screen edge ---
            val baseAngle = 230f           // instead of 270°
            val halfSweep = 80f            // wider arc to span full width
            val startAngle = baseAngle - halfSweep
            val sweepAngle = halfSweep * 2f

            val nativeCanvas = drawContext.canvas.nativeCanvas
            val path = Path()
            val rect = RectF()
            val pathMeasure = PathMeasure()
            val clampedProgress = progress.coerceIn(0f, 1f)

            for (layer in 0 until layerCount) {
                val radius = minRadius + layer * radiusStep
                rect.set(
                    centerX - radius,
                    centerY - radius,
                    centerX + radius,
                    centerY + radius
                )
                path.reset()
                path.addArc(rect, startAngle, sweepAngle)
                pathMeasure.setPath(path, false)
                val pathLength = pathMeasure.length

                val margin = phraseSpacing * 1f
                val baseStart = -margin
                val baseEnd = pathLength + margin
                val scrollStart = -(pathLength + margin + phraseSpacing)
                val scrollEnd = pathLength + margin + phraseSpacing
                val scroll = scrollStart + (scrollEnd - scrollStart) * clampedProgress

                var base = baseStart
                var indexOnLayer = 0
                while (base < baseEnd) {
                    val d = base + scroll
                    if (d in 0f..pathLength) {
                        val paint =
                            if ((layer + indexOnLayer) % 2 == 0) orangePaint else whitePaint
                        nativeCanvas.drawTextOnPath(phrase, path, d, 0f, paint)
                    }
                    base += phraseSpacing
                    indexOnLayer++
                }
            }
        }
    }
}


@Composable
private fun YearsAsShpeitoStatsPage(
    progress: Float,
    isDarkMode: Boolean,
    yearsInShpe: Int
) {
    val bg =
        if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    val primaryText =
        if (isDarkMode) Color.White else Color.Black

    val yearsLabel = when {
        yearsInShpe <= 0 -> "1 Year!"
        yearsInShpe == 1 -> "1 Year!"
        else -> "$yearsInShpe Years!"
    }

    val mainEnterEnd = 0.25f
    val mainEnter = (progress / mainEnterEnd).coerceIn(0f, 1f)
    val mainOffset = (1f - mainEnter) * 200f
    val mainAlpha = mainEnter

    val bottomEnterStart = 0.20f
    val bottomEnterEnd = 0.35f
    val bottomEnter =
        ((progress - bottomEnterStart) / (bottomEnterEnd - bottomEnterStart))
            .coerceIn(0f, 1f)
    val bottomOffset = (1f - bottomEnter) * 200f
    val bottomAlpha = bottomEnter

    val exitStart = 0.8f
    val exitProgress =
        ((progress - exitStart) / (1f - exitStart)).coerceIn(0f, 1f)
    val exitOffset = -200f * exitProgress
    val exitAlphaFactor = 1f - exitProgress

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // Center block: "[X] Years!\nSince you first\nbecame a SHPEito"
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
                text = yearsLabel,
                color = OrangeSHPE,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Since you first",
                color = primaryText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "became a SHPEito",
                color = primaryText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }

        // Bottom block: "Thank you for being\na part of the familia :)"
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
                text = "Thank you for being",
                color = primaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "a part of the familia :)",
                color = primaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
private fun OverallIntroPage(
    progress: Float,
    isDarkMode: Boolean
) {
    val (bgColor, whiteText, _) = marqueeColors(isDarkMode)
    val primaryText = whiteText

    val appearEnd = 0.45f
    val disappearStart = 0.7f

    val appearPhase = (progress / appearEnd).coerceIn(0f, 1f)
    val disappearPhase = ((progress - disappearStart) / (1f - disappearStart)).coerceIn(0f, 1f)

    val density = LocalDensity.current
    val arcHeightPx = with(density) { 18.dp.toPx() }
    val youAreDownPx = with(density) { 40.dp.toPx() }
    val youAreUpPx = with(density) { 24.dp.toPx() }

    val letters = remember { "Overall..." }.toList()
    val totalLetters = letters.size.coerceAtLeast(1)

    // "You are a..." enter + shift up
    val youAppearStart = 0.15f
    val youAppearEnd = 0.5f
    val youAppear = ((progress - youAppearStart) / (youAppearEnd - youAppearStart)).coerceIn(0f, 1f)

    val youShiftStart = 0.8f
    val youShift = ((progress - youShiftStart) / (1f - youShiftStart)).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // Optional center dark stripe (like your screenshot)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(36.dp)
                .align(Alignment.Center)
                .background(Color(0xFF2B2B2B))
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Rainbow "Overall..."
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                letters.forEachIndexed { index, ch ->
                    val baseFrac =
                        if (totalLetters == 1) 0f else index.toFloat() / (totalLetters - 1).toFloat()

                    val letterAppear =
                        ((appearPhase - baseFrac) / 0.22f).coerceIn(0f, 1f)
                    val letterDisappear =
                        ((disappearPhase - baseFrac) / 0.22f).coerceIn(0f, 1f)

                    var alpha = letterAppear * (1f - letterDisappear)
                    if (alpha < 0.01f) alpha = 0f

                    // Simple rainbow arc: center letters highest
                    val centered = baseFrac - 0.5f
                    val arcOffset = -kotlin.math.cos(centered * PI).toFloat() * arcHeightPx

                    Text(
                        text = ch.toString(),
                        color = primaryText.copy(alpha = alpha),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.graphicsLayer {
                            this.alpha = alpha
                            translationY = arcOffset
                        }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // "You are a..." – fades in from bottom, then drifts slightly up
            val youAlpha = youAppear
            val youTranslationY =
                (1f - youAppear) * youAreDownPx - youShift * youAreUpPx

            Text(
                text = "You are a...",
                color = primaryText.copy(alpha = youAlpha),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.graphicsLayer {
                    alpha = youAlpha
                    translationY = youTranslationY
                }
            )
        }
    }
}


private fun eventTypeToPersona(topType: String): String {
    return when (topType) {
        "General Body Meeting" -> "GBM Pleb!"
        "Cabinet Meeting" -> "Cabinet Enthusiast!"
        "Workshop" -> "Avid Learner!"
        "Form/Survey" -> "Data Collector!"
        "Social" -> "Party Animal!"
        "Corporate Event" -> "Quarterzip Fiend!"
        "Fundraising" -> "Funds Acquirer!"
        "Tabling" -> "Door to Door Salesperson!"
        "Volunteering" -> "Kind and Loving Person!"
        "Miscellaneous" -> "Unique and Unrepeatable!"
        else -> "Unique and Unrepeatable!"
    }
}


@Composable
private fun OverallPersonaPage(
    progress: Float,
    isDarkMode: Boolean,
    topEventType: String
) {
    val (bgColor, whiteText, accent) = marqueeColors(isDarkMode)
    val primaryText = whiteText

    val personaLabel = remember(topEventType) {
        eventTypeToPersona(topEventType)
    }

    val config = LocalConfiguration.current
    val screenWidthDp = config.screenWidthDp.dp
    val horizontalMargin = screenWidthDp / 8f

    // Simple enter animation
    val mainEnterEnd = 0.35f
    val mainEnter = (progress / mainEnterEnd).coerceIn(0f, 1f)
    val offsetY = (1f - mainEnter) * 160f
    val alpha = mainEnter

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(vertical = 32.dp)
    ) {
        // Center stripe to match intro page
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(36.dp)
                .align(Alignment.Center)
                .background(Color(0xFF2B2B2B))
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxWidth()
                .padding(horizontal = horizontalMargin)
                .graphicsLayer {
                    translationY = offsetY
                    this.alpha = alpha
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "You are a...",
                color = primaryText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = personaLabel,
                color = accent,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
