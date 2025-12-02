package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.wrapped

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.min
import kotlinx.coroutines.flow.collectLatest
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import kotlinx.coroutines.delay

@Composable
fun marqueeColors(isDarkMode: Boolean): Triple<Color, Color, Color> {
    val bg = if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    val white = Color.White
    val accent = OrangeSHPE
    return Triple(bg, white, accent)
}

@Composable
fun DiagonalMarqueeScaffold(
    isDarkMode: Boolean,
    scale: Float,
    fontSize: TextUnit,
    isMarqueePaused: Boolean,
    topSpawnProgress: Float = 0f,
    bottomSpawnProgress: Float = 0f,
    centerTransitionProgress: Float,
    centerBaseTextWidthPx: Float,
    settleProgress: Float = 0f
) {
    val (bgColor, whiteText, shpeOrange) = marqueeColors(isDarkMode)

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(bgColor)
    ) {
        val spawnTop = topSpawnProgress < 1f
        val spawnBottom = bottomSpawnProgress < 1f

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .requiredWidth(screenWidth * 1.3f)
                .graphicsLayer { rotationZ = -18f
                    clip = false}
        ) {
            val baseTopFar = (-110).dp
            val baseTopNear = (-55).dp
            val baseBottomNear = 55.dp
            val baseBottomFar = 110.dp

            // As settleProgress -> 1, rows spread farther toward the poles
            val spread = 100.dp * (2 * settleProgress * settleProgress - (settleProgress * settleProgress * settleProgress))

            val topFarOffset = baseTopFar - spread
            val topNearOffset = baseTopNear - spread
            val bottomNearOffset = baseBottomNear + spread
            val bottomFarOffset = baseBottomFar + spread

            val leftDir = -1f
            val rightDir = 1f

            // 2 TOP MARQUEES
            MarqueeRow(
                isDarkMode = isDarkMode,
                text = "SHPE Wrapped ",
                color = whiteText,
                verticalOffset = topFarOffset,
                horizontalShift = leftDir,
                scale = scale,
                fontSize = fontSize,
                spawnMore = spawnTop,
                isPaused = isMarqueePaused
            )
            MarqueeRow(
                isDarkMode = isDarkMode,
                text = "SHPE Wrapped ",
                color = whiteText,
                verticalOffset = topNearOffset,
                horizontalShift = rightDir,
                scale = scale,
                fontSize = fontSize,
                spawnMore = spawnTop,
                isPaused = isMarqueePaused
            )

// 2 BOTTOM MARQUEES
            MarqueeRow(
                isDarkMode = isDarkMode,
                text = "SHPE Wrapped ",
                color = whiteText,
                verticalOffset = bottomNearOffset,
                horizontalShift = leftDir,
                scale = scale,
                fontSize = fontSize,
                spawnMore = spawnBottom,
                isPaused = isMarqueePaused
            )
            MarqueeRow(
                isDarkMode = isDarkMode,
                text = "SHPE Wrapped ",
                color = whiteText,
                verticalOffset = bottomFarOffset,
                horizontalShift = rightDir,
                scale = scale,
                fontSize = fontSize,
                spawnMore = spawnBottom,
                isPaused = isMarqueePaused
            )

            // CENTER LINE: animated from "SHPE Wrapped" "Most Active Month"
            AnimatedCenterFlankedTextRow(
                isDarkMode = isDarkMode,
                startText = "SHPE Wrapped",
                endText = "Most Active Month",
                flankText = "SHPE Wrapped",
                fontSize = fontSize,
                scale = scale,
                baseTextWidthPx = centerBaseTextWidthPx,
                transitionProgress = centerTransitionProgress
            )
        }

        val yearAlpha = 1f - settleProgress

        Text(
            text = "2025",
            color = whiteText.copy(alpha = 0.85f),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .graphicsLayer { alpha = yearAlpha }
        )
    }
}

@Composable
fun MarqueeRow(
    isDarkMode: Boolean,
    text: String,
    color: Color,
    verticalOffset: Dp,
    horizontalShift: Float,
    scale: Float,
    fontSize: TextUnit,
    spawnMore: Boolean,
    isPaused: Boolean
) {
    val movingLeft = horizontalShift < 0f

    val density = LocalDensity.current
    val config = LocalConfiguration.current
    val screenWidthDp = config.screenWidthDp.dp

    val rowWidth = screenWidthDp * 1.5f

    // Single phrase with trailing space so copies don't glue together
    val phrase = remember(text) {
        if (text.endsWith(" ")) text else "$text "
    }

    var phraseWidthPx by remember { mutableStateOf(0f) }
    var phase by remember { mutableStateOf(0f) }

    val currentIsPaused by rememberUpdatedState(isPaused)

    Box(
        modifier = Modifier
            .offset(y = verticalOffset)
            .width(rowWidth)
            .clipToBounds()
    ) {
        // Measure width of ONE phrase at base scale
        Text(
            text = phrase,
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier.graphicsLayer(alpha = 0f),
            onTextLayout = { layout ->
                if (layout.size.width > 0 && phraseWidthPx == 0f) {
                    phraseWidthPx = layout.size.width.toFloat()
                }
            }
        )

        if (phraseWidthPx > 0f) {
            val tileWidthPx = phraseWidthPx * scale

            val rowWidthPx = with(density) { rowWidth.toPx() }
            val copies = (rowWidthPx / tileWidthPx).toInt() + 1
            val speedPxPerSec = with(density) { 90.dp.toPx() }

            LaunchedEffect(movingLeft, spawnMore, tileWidthPx) {
                var last = withFrameNanos { it }
                while (true) {
                    val now = withFrameNanos { it }
                    var dt = (now - last) / 1_000_000_000f

                    if (currentIsPaused || tileWidthPx <= 0f) {
                        last = now
                        continue
                    }

                    if (dt > 0.05f) dt = 0.05f
                    last = now

                    val step = speedPxPerSec * dt

                    if (spawnMore) {
                        phase = (phase + step) % tileWidthPx
                    } else {
                        phase += step
                    }
                }
            }

            // Draw tiled phrases, each exactly tileWidthPx apart in visual space
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                for (i in -2 until copies) {
                    val baseX = if (movingLeft) {
                        -phase + i * tileWidthPx
                    } else {
                        phase + i * tileWidthPx
                    }

                    Text(
                        text = phrase,
                        color = color,
                        fontSize = fontSize,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Visible,
                        modifier = Modifier.graphicsLayer {
                            translationX = baseX
                            scaleX = scale
                            scaleY = scale
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun VerticalMarqueeColumn(
    text: String,
    baseTextColor: Color,
    accentColor: Color,
    fontSize: TextUnit,
    isMovingUp: Boolean,   // unused, kept for API compatibility
    spawnMore: Boolean,    // unused, kept for API compatibility
    isPaused: Boolean,     // unused, kept for API compatibility
    modifier: Modifier = Modifier
) {
    val phrase = remember(text) { text }
    val density = LocalDensity.current

    var phraseHeightPx by remember { mutableStateOf(0f) }

    BoxWithConstraints(
        // ⚠️ remove inner clipToBounds so we don't double-clip the text
        modifier = modifier
    ) {
        val columnHeightPx = with(density) { maxHeight.toPx() }

        // Measure one tile
        Text(
            text = phrase,
            color = Color.Transparent,
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier.onGloballyPositioned {
                phraseHeightPx = it.size.height.toFloat()
            }
        )

        if (phraseHeightPx <= 0f || columnHeightPx <= 0f) return@BoxWithConstraints

        // Extra copies so even at the extremes we never see gaps on-screen
        val copies = (columnHeightPx / phraseHeightPx).toInt() + 10

        // Phase shift so the tiles sit a bit *inside* the top/bottom,
        // instead of one tile’s baseline being exactly on the clipping edge.
        val phaseOffsetPx = -phraseHeightPx * 0.5f

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            for (i in -5 until copies + 5) {
                val baseY = i * phraseHeightPx + phaseOffsetPx
                val tileColor = if (i % 2 == 0) accentColor else baseTextColor

                Text(
                    text = phrase,
                    color = tileColor,
                    fontSize = fontSize,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    softWrap = false,
                    modifier = Modifier.graphicsLayer {
                        translationY = baseY
                    }
                )
            }
        }
    }
}


@Composable
private fun BoxScope.CenterFlankedTextRow(
    mainText: String,
    flankText: String,
    mainColor: Color,
    flankColor: Color,
    fontSize: TextUnit,
    scale: Float,
    baseTextWidthPx: Float,      // width of "SHPE Wrapped" at base size
    centerSlideOffsetPx: Float = 0f
) {
    val density = LocalDensity.current
    val config = LocalConfiguration.current

    val screenWidthPx = with(density) { config.screenWidthDp.dp.toPx() }
    val scaledWidthPx = baseTextWidthPx * scale

    val peekPx = screenWidthPx * 0.0024f

    val leftCenterX = (-screenWidthPx / peekPx) - scaledWidthPx / 2f

    val rightCenterX = (screenWidthPx / peekPx) + scaledWidthPx / 2f

    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .graphicsLayer { translationX = centerSlideOffsetPx }
    ) {
        // CENTER ORANGE TEXT
        Text(
            text = mainText,
            color = mainColor,
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        )

        // LEFT WHITE FLANK – mostly off-screen, just a tail peeking in
        Text(
            text = flankText,
            color = flankColor,
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    translationX = leftCenterX
                    scaleX = scale
                    scaleY = scale
                }
        )

        // RIGHT WHITE FLANK – mirror of the left
        Text(
            text = flankText,
            color = flankColor,
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    translationX = rightCenterX
                    scaleX = scale
                    scaleY = scale
                }
        )
    }
}

@Composable
private fun BoxScope.CenterSoloTextRow(
    text: String,
    color: Color,
    fontSize: TextUnit,
    scale: Float,
    centerSlideOffsetPx: Float = 0f
) {
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .graphicsLayer { translationX = centerSlideOffsetPx }
    ) {
        Text(
            text = text,
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        )
    }
}

@Composable
private fun BoxScope.AnimatedCenterFlankedTextRow(
    isDarkMode: Boolean,
    startText: String,
    endText: String,
    flankText: String,
    fontSize: TextUnit,
    scale: Float,
    baseTextWidthPx: Float,
    transitionProgress: Float
) {
    val (_, whiteText, shpeOrange) = marqueeColors(isDarkMode)
    val density = LocalDensity.current
    val config = LocalConfiguration.current
    val screenWidthPx = with(density) { config.screenWidthDp.dp.toPx() }

    val t = transitionProgress.coerceIn(0f, 1f)

    // tHoldOldEnd  = end of static hold for SHPE + flanks
    // tNewStart    = when "Most Active Month" starts sliding in
    // tEnd (1f)    = when everything is settled
    val tHoldOldEnd = 0.25f
    val tNewStart   = (1f + tHoldOldEnd) / 2f

    // --- OLD ROW (SHPE + flanks) OFFSET: always drawn, just moves off-screen ---

    val oldOffset = if (t <= tHoldOldEnd) {
        0f
    } else {
        // from 0 → 2 * screenWidthPx over the remainder of the animation
        val slideFrac = (t - tHoldOldEnd) / (1f - tHoldOldEnd)  // 0 → 1
        2f * screenWidthPx * slideFrac                          // 0 → 2W
    }

    // Draw SHPE + flanks for the entire 0f..1f range; once oldOffset is big,
    // the row is simply off-screen so it's invisible but never "pops".
    CenterFlankedTextRow(
        mainText = startText,
        flankText = flankText,
        mainColor = shpeOrange,
        flankColor = whiteText,
        fontSize = fontSize,
        scale = scale,
        baseTextWidthPx = baseTextWidthPx,
        centerSlideOffsetPx = oldOffset
    )

    if (t >= tNewStart) {
        val newSlideFrac = ((t - tNewStart) / (1f - tNewStart)).coerceIn(0f, 1f)
        // -W → 0 as t goes from tNewStart → 1f
        val newOffset = -screenWidthPx * (1f - newSlideFrac)

        CenterSoloTextRow(
            text = endText,
            color = shpeOrange,
            fontSize = fontSize,
            scale = scale,
            centerSlideOffsetPx = newOffset
        )
    }
}
