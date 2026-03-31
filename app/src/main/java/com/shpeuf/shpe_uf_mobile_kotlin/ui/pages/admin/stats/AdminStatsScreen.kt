package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.admin.stats

// (Check the folder path above, it might be .ui.pages.stats depending on your folder structure)
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

    val backgroundColor = if (isDarkMode) Color(0xFF011F35) else Color(0xFFF5F5F5)
    val textColor       = if (isDarkMode) Color.White else Color(0xFF011F35)
    val cardBlue        = if (isDarkMode) Color(0xFF2457C5) else Color(0xFF1E4A94)
    val tabIndicator    = Color(0xFFD25917) // orange accent for selected tab

    val tabs = listOf("Year", "Sex", "Major", "Ethnicity", "Country")
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // ── Top bar (matches AdminPanelScreen style) ──────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    painter           = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint              = textColor,
                    modifier          = Modifier.size(26.dp)
                )
            }
        }

        Box(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment  = Alignment.Center
        ) {
            Text(
                text       = "Statistics",
                color      = textColor,
                fontSize   = 28.sp,
                fontFamily = FontFamily(Font(R.font.viga))
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Tab Row ───────────────────────────────────────────────────────────
        ScrollableTabRow(
            selectedTabIndex  = selectedTab,
            containerColor    = backgroundColor,
            contentColor      = textColor,
            indicator         = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier  = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color     = tabIndicator
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected      = selectedTab == index,
                    onClick       = { selectedTab = index },
                    text          = {
                        Text(
                            text       = title,
                            fontFamily = FontFamily(Font(R.font.universltstd)),
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            color      = if (selectedTab == index) tabIndicator else textColor
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Body ──────────────────────────────────────────────────────────────
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = cardBlue)
                }
            }

            uiState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text     = "Error: ${uiState.error}",
                            color    = Color(0xFFD25917),
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick  = { viewModel.fetchAdminStats() },
                            colors   = ButtonDefaults.buttonColors(containerColor = cardBlue)
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
                    entries      = entries,
                    cardColor    = cardBlue,
                    textColor    = textColor
                )
            }
        }
    }
}

// ── Stat list ─────────────────────────────────────────────────────────────────
@Composable
private fun StatsList(
    entries   : Map<String, Pair<Int, Float>>,
    cardColor : Color,
    textColor : Color
) {
    if (entries.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No data available.", color = textColor.copy(alpha = 0.6f))
        }
        return
    }

    // Sort descending by count so most-common appears first
    val sorted = entries.entries.sortedByDescending { it.value.first }

    LazyColumn(
        modifier            = Modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding      = PaddingValues(vertical = 8.dp)
    ) {
        items(sorted) { (label, pair) ->
            val (count, percentage) = pair
            StatRow(
                label      = label,
                count      = count,
                percentage = percentage,
                cardColor  = cardColor
            )
        }
    }
}

// ── Single stat row ───────────────────────────────────────────────────────────
@Composable
private fun StatRow(
    label      : String,
    count      : Int,
    percentage : Float,
    cardColor  : Color
) {
    Card(
        shape  = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier            = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text(
                text       = label,
                color      = Color.White,
                fontSize   = 15.sp,
                fontFamily = FontFamily(Font(R.font.universltstd)),
                fontWeight = FontWeight.Medium,
                modifier   = Modifier.weight(1f)
            )

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text       = "$count members",
                    color      = Color.White,
                    fontSize   = 13.sp,
                    fontFamily = FontFamily(Font(R.font.universltstd))
                )
                Text(
                    text       = "${"%.1f".format(percentage)}%",
                    color      = Color.White.copy(alpha = 0.75f),
                    fontSize   = 12.sp,
                    fontFamily = FontFamily(Font(R.font.universltstd))
                )
            }
        }
    }
}

