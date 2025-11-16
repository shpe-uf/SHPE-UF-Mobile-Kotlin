package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.wrapped

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors


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
    centerBaseTextWidthPx: Float
) {
    val (bgColor, whiteText, shpeOrange) = marqueeColors(isDarkMode)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "marquee")

        // raw animated values
        val leftShiftRaw by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = -140f,
            animationSpec = infiniteRepeatable(
                animation = tween(6000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "leftShiftRaw"
        )
        val rightShiftRaw by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 140f,
            animationSpec = infiniteRepeatable(
                animation = tween(6500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "rightShiftRaw"
        )

        // frozen values when paused
        var leftShift by remember { mutableStateOf(0f) }
        var rightShift by remember { mutableStateOf(0f) }

        LaunchedEffect(leftShiftRaw, isMarqueePaused) {
            if (!isMarqueePaused) leftShift = leftShiftRaw
        }
        LaunchedEffect(rightShiftRaw, isMarqueePaused) {
            if (!isMarqueePaused) rightShift = rightShiftRaw
        }

        val spawnTop = topSpawnProgress < 1f
        val spawnBottom = bottomSpawnProgress < 1f

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer { rotationZ = -18f }
        ) {
            // vertical spacing between bands
            val topFarOffset = (-110).dp
            val topNearOffset = (-55).dp
            val bottomNearOffset = 55.dp
            val bottomFarOffset = 110.dp

            // 2 TOP MARQUEES
            MarqueeRow(
                isDarkMode = isDarkMode,
                text = "SHPE Wrapped SHPE Wrapped",
                color = whiteText,
                verticalOffset = topFarOffset,
                horizontalShift = leftShift,
                scale = scale,
                fontSize = fontSize,
                spawnMore = spawnTop
            )
            MarqueeRow(
                isDarkMode = isDarkMode,
                text = "SHPE Wrapped SHPE Wrapped",
                color = whiteText,
                verticalOffset = topNearOffset,
                horizontalShift = rightShift,
                scale = scale,
                fontSize = fontSize,
                spawnMore = spawnTop
            )

            // 2 BOTTOM MARQUEES
            MarqueeRow(
                isDarkMode = isDarkMode,
                text = "SHPE Wrapped SHPE Wrapped",
                color = whiteText,
                verticalOffset = bottomNearOffset,
                horizontalShift = leftShift,
                scale = scale,
                fontSize = fontSize,
                spawnMore = spawnBottom
            )
            MarqueeRow(
                isDarkMode = isDarkMode,
                text = "SHPE Wrapped SHPE Wrapped",
                color = whiteText,
                verticalOffset = bottomFarOffset,
                horizontalShift = rightShift,
                scale = scale,
                fontSize = fontSize,
                spawnMore = spawnBottom
            )

            // CENTER LINE: animated from "SHPE Wrapped" → "Most Active Month"
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

        Text(
            text = "2025",
            color = whiteText.copy(alpha = 0.85f),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
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
) {
    val maxItems = 20
    val minItems = 6
    val itemCount = if (spawnMore) maxItems else minItems

    Row(
        modifier = Modifier
            .offset(y = verticalOffset)
            .graphicsLayer { translationX = horizontalShift },
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        repeat(itemCount) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = text,
                    color = color,
                    fontSize = fontSize,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    softWrap = false,
                    modifier = Modifier.graphicsLayer {
                        scaleX = scale
                        scaleY = scale
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

    val peekPx = screenWidthPx * 0.00126f

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

