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

    val orangeHeight = 150.dp
    val bgSection = if (isDarkMode) Color(0xFF002139) else Color(0xFFF5F5F5)
    val textColor = if (isDarkMode) Color.White else Color.Black
    val cardBg = if (isDarkMode) Color(0xFF0F3048) else Color(0xFFFFFFFF)
    val secondaryText = if (isDarkMode) Color.White.copy(alpha = 0.7f) else Color.Gray
    val orangeAccent = Color(0xFFD25917)

    val tabs = listOf("Year", "Sex", "Major", "Ethnicity", "Country")
    var selectedTab by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {

        // Orange top banner
        Box(
            Modifier
                .fillMaxWidth()
                .height(orangeHeight)
                .background(orangeAccent)
        )

        // Header: back button, title, gator
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 28.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 8.dp)
                    .offset(x = (-8).dp, y = (-10).dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            Text(
                text = "Statistics",
                color = Color.White,
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.viga)),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 24.dp)
            )

            Image(
                painter = painterResource(
                    id = if (isDarkMode) R.drawable.gator_dark_mode
                    else R.drawable.gator_light_mode
                ),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = 80.dp)
                    .width(90.dp)
                    .height(90.dp)
            )
        }

        // Background under header
        Box(
            Modifier
                .fillMaxSize()
                .padding(top = orangeHeight)
                .background(bgSection)
        )

        // content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = orangeHeight)
        ) {
            Spacer(Modifier.height(16.dp))

            // Tab Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEachIndexed { index, title ->
                    StatTabPill(
                        label = tabs[index],
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                    if (index < tabs.lastIndex) Spacer(Modifier.width(10.dp))
                }
            }

            Spacer(Modifier.height(8.dp))

            // Body
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = orangeAccent)
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Error: ${uiState.error}",
                                color = orangeAccent,
                                fontSize = 14.sp
                            )
                            Spacer(Modifier.height(12.dp))
                            Button(
                                onClick = { viewModel.fetchAdminStats() },
                                colors = ButtonDefaults.buttonColors(
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
                        0 -> uiState.stats.years
                        1 -> uiState.stats.genders
                        2 -> uiState.stats.majors
                        3 -> uiState.stats.ethnicities
                        4 -> uiState.stats.countries
                        else -> emptyMap()
                    }
                    key (selectedTab) {
                        StatsList(
                            entries = entries,
                            isDarkMode = isDarkMode
                        )
                    }
                }
            }
        }
    }
}

// StatTabPill
@Composable
private fun StatTabPill(
    label : String,
    selected : Boolean,
    onClick : () -> Unit,
) {
    val orange = Color(0xFFD25917)
    val bg = if (selected) orange else orange.copy(alpha = 0.75f)

    Button(
        onClick = onClick,
        modifier = Modifier.height(44.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = bg),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        Text(
            text = label,
            fontFamily = FontFamily(Font(R.font.viga)),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge,
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}

// stat list
@Composable
private fun StatsList(
    entries : Map<String, Pair<Int, Float>>,
    isDarkMode : Boolean
) {
    val sorted = entries.entries.sortedByDescending { it.value.first }

    val primaryText = if (isDarkMode) Color.White else Color.Black
    val secondaryText = if (isDarkMode) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant
    val tableBg = if (isDarkMode) Color(0xFF0F3048) else Color(0xFFFFFFFF)
    val headerBg = if (isDarkMode) Color(0xFF0A2A40) else Color(0xFFE6E8ED)
    val headerTextColor = if (isDarkMode) Color.White else Color(0xFF4F5D75)

    if (entries.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No data available.", color = primaryText.copy(alpha = 0.6f))
        }
        return
    }

    // Table header
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerBg)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CATEGORY",
                fontFamily = FontFamily(Font(R.font.viga)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = headerTextColor,
                modifier = Modifier.weight(2.6f)
            )
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "MEMBERS",
                    fontFamily = FontFamily(Font(R.font.viga)),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = headerTextColor
                )
            }
        }
        HorizontalDivider(thickness = 1.dp)
    }

    // Table data
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(sorted) { (label, pair) ->
            val (count, percentage) = pair
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                // Table Data Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(tableBg)
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Label
                    Column(
                        modifier = Modifier.weight(2.6f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = label,
                            color = primaryText,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${"%.1f".format(percentage)}%",
                            style = MaterialTheme.typography.bodySmall,
                            color = secondaryText
                        )
                    }

                    // Count
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = count.toString(),
                            color = primaryText,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
                HorizontalDivider(thickness = 0.5.dp)
            }
        }

        item { Spacer(Modifier.height(40.dp)) }
    }
}