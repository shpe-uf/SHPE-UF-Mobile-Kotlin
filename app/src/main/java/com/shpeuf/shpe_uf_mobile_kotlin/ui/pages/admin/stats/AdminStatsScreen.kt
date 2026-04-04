package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.admin.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shpeuf.shpe_uf_mobile_kotlin.R
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminStatsScreen(
    viewModel: StatsViewModel,
    mainViewModel: SHPEUFAppViewModel,
    isDarkMode: Boolean,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val orangeHeight   = 150.dp
    val bgSection      = if (isDarkMode) Color(0xFF002139) else Color(0xFFF5F5F5)
    val textColor      = if (isDarkMode) Color.White else Color.Black
    val cardBg         = if (isDarkMode) Color(0xFF0F3048) else Color(0xFFFFFFFF)
    val secondaryText  = if (isDarkMode) Color.White.copy(alpha = 0.7f) else Color.Gray
    val orangeAccent   = Color(0xFFD25917)

    val tabs = listOf("Year", "Sex", "Major", "Ethnicity", "Country")
    var selectedTab by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {

        // ── Orange top band ───────────────────────────────────────────────────
        Box(
            Modifier
                .fillMaxWidth()
                .height(orangeHeight)
                .background(orangeAccent)
        )

        // ── Header: back button + title + gator ───────────────────────────────
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 28.dp)
        ) {
            IconButton(
                onClick  = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 8.dp)
                    .offset(x = (-8).dp, y = (-10).dp)
            ) {
                Icon(
                    painter            = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint               = Color.White,
                    modifier           = Modifier.size(26.dp)
                )
            }

            Text(
                text       = "Statistics",
                color      = Color.White,
                fontSize   = 32.sp,
                fontFamily = FontFamily(Font(R.font.viga)),
                modifier   = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 24.dp)
            )

            Image(
                painter            = painterResource(
                    id = if (isDarkMode) R.drawable.gator_dark_mode
                    else R.drawable.gator_light_mode
                ),
                contentDescription = null,
                modifier           = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = 80.dp)
                    .width(90.dp)
                    .height(90.dp)
            )
        }

        // ── Background under header ───────────────────────────────────────────
        Box(
            Modifier
                .fillMaxSize()
                .padding(top = orangeHeight)
                .background(bgSection)
        )

        // ── Content ───────────────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = orangeHeight)
        ) {
            Spacer(Modifier.height(16.dp))

            // ── Tab buttons (matching leaderboard pill style) ─────────────────
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    StatTabPill(
                        label    = title,
                        selected = selectedTab == index,
                        onClick  = { selectedTab = index },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // ── Body ──────────────────────────────────────────────────────────
            when {
                uiState.isLoading -> {
                    Box(
                        modifier        = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = orangeAccent)
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier        = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text     = "Error: ${uiState.error}",
                                color    = orangeAccent,
                                fontSize = 14.sp
                            )
                            Spacer(Modifier.height(12.dp))
                            Button(
                                onClick = { viewModel.fetchAdminStats() },
                                colors  = ButtonDefaults.buttonColors(
                                    containerColor = orangeAccent
                                )
                            ) {
                                Text("Retry", color = Color.White)
                            }
                        }
                    }
                }

                else -> {
                    val entries: Map<String, Pair<Int, Float>> = when (selectedTab) {
                        0    -> uiState.stats.years
                        1    -> uiState.stats.genders
                        2    -> uiState.stats.majors
                        3    -> uiState.stats.ethnicities
                        4    -> uiState.stats.countries
                        else -> emptyMap()
                    }

                    StatsList(
                        entries       = entries,
                        cardBg        = cardBg,
                        primaryText   = textColor,
                        secondaryText = secondaryText
                    )
                }
            }
        }
    }
}

// ── Tab pill button (matches leaderboard SemesterPill) ────────────────────────
@Composable
private fun StatTabPill(
    label    : String,
    selected : Boolean,
    onClick  : () -> Unit,
    modifier : Modifier = Modifier
) {
    val orange = Color(0xFFD25917)
    val bg     = if (selected) orange else orange.copy(alpha = 0.75f)

    Button(
        onClick         = onClick,
        modifier        = modifier.height(44.dp),
        shape           = RoundedCornerShape(10.dp),
        colors          = ButtonDefaults.buttonColors(containerColor = bg),
        contentPadding  = PaddingValues(horizontal = 4.dp)
    ) {
        Text(
            text       = label,
            fontFamily = FontFamily(Font(R.font.viga)),
            fontSize   = 13.sp,
            fontWeight = FontWeight.Bold,
            color      = Color.White,
            maxLines   = 1,
            overflow   = TextOverflow.Ellipsis
        )
    }
}

// ── Stat list ─────────────────────────────────────────────────────────────────
@Composable
private fun StatsList(
    entries       : Map<String, Pair<Int, Float>>,
    cardBg        : Color,
    primaryText   : Color,
    secondaryText : Color
) {
    if (entries.isEmpty()) {
        Box(
            modifier        = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text  = "No data available.",
                color = primaryText.copy(alpha = 0.6f)
            )
        }
        return
    }

    val sorted = entries.entries.sortedByDescending { it.value.first }

    LazyColumn(
        modifier            = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding      = PaddingValues(vertical = 8.dp)
    ) {
        items(sorted) { (label, pair) ->
            val (count, percentage) = pair
            StatRow(
                label         = label,
                count         = count,
                percentage    = percentage,
                cardBg        = cardBg,
                primaryText   = primaryText,
                secondaryText = secondaryText
            )
        }
    }
}

// ── Single stat row ───────────────────────────────────────────────────────────
@Composable
private fun StatRow(
    label         : String,
    count         : Int,
    percentage    : Float,
    cardBg        : Color,
    primaryText   : Color,
    secondaryText : Color
) {
    Card(
        shape     = RoundedCornerShape(10.dp),
        colors    = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier  = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            // Label — left
            Text(
                text       = label,
                color      = primaryText,
                fontSize   = 15.sp,
                fontFamily = FontFamily(Font(R.font.universltstd)),
                fontWeight = FontWeight.Medium,
                modifier   = Modifier.weight(1f),
                maxLines   = 1,
                overflow   = TextOverflow.Ellipsis
            )

            // Count + percentage — right
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text       = "$count members",
                    color      = primaryText,
                    fontSize   = 13.sp,
                    fontFamily = FontFamily(Font(R.font.universltstd)),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text       = "${"%.1f".format(percentage)}%",
                    color      = secondaryText,
                    fontSize   = 12.sp,
                    fontFamily = FontFamily(Font(R.font.universltstd))
                )
            }
        }
    }
}