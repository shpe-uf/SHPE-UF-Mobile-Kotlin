package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.res.painterResource
import com.shpeuf.shpe_uf_mobile_kotlin.R
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable

enum class SortOrder { Descending, Ascending }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminLeaderboard(
    isDarkMode: Boolean,
    onBack: () -> Unit,
    viewModel: AdminLeaderboardViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var sortOrder by remember { mutableStateOf(SortOrder.Descending) }
    val tabs = listOf(Semester.FALL, Semester.SPRING, Semester.SUMMER)

    val orangeHeight = 150.dp
    val bgSection = if (isDarkMode) Color(0xFF002139) else Color(0xFFF5F5F5)
    val sortedRows = remember(state.rows, sortOrder) {
        when (sortOrder) {
            SortOrder.Descending -> state.rows.sortedWith(compareByDescending<LeaderboardRow> { it.points }.thenBy { it.name })
            SortOrder.Ascending -> state.rows.sortedWith(compareBy<LeaderboardRow> { it.points }.thenBy { it.name })
        }
    }.mapIndexed { idx, r -> r.copy(rank = idx + 1) }

    LaunchedEffect(Unit) { viewModel.load() }

    Box(modifier = Modifier.fillMaxSize()) {

        // Orange top
        Box(
            Modifier
                .fillMaxWidth()
                .height(orangeHeight)
                .background(Color(0xFFD25917))
        )

        // Header with Back Button + Title + Gator
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
                text = "LEADERBOARD",
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = orangeHeight)
        ) {

            Spacer(Modifier.height(16.dp))

            // Semester buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                tabs.forEach { sem ->
                    SemesterPill(
                        semester = sem,
                        selected = state.selectedSemester == sem,
                        onClick = { viewModel.selectSemester(sem) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Table header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                TableHeaderRow(isDarkMode, sortOrder,
                    onSortToggle = {
                        sortOrder = if (sortOrder == SortOrder.Descending)
                            SortOrder.Ascending
                        else
                            SortOrder.Descending
                    }
                )
                HorizontalDivider(thickness = 1.dp)
            }

            // table data
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                items(sortedRows) { row ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        TableDataRow(row, isDarkMode)
                        HorizontalDivider(thickness = 0.5.dp)
                    }
                }

                if (state.isLoading) {
                    item {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                state.error?.let { error ->
                    item {
                        Text(
                            text = "Error: $error",
                            modifier = Modifier.padding(24.dp)
                        )
                    }
                }

                item { Spacer(Modifier.height(40.dp)) }
            }
        }
    }
}

@Composable
private fun SemesterPill(
    semester: Semester,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val orange = Color(0xFFD25917)
    val bg = if (selected) orange else orange.copy(alpha=0.75f) // light unselected
    val fg = if (selected) Color.White else MaterialTheme.colorScheme.onSurface

    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = bg, contentColor = fg),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        Text(
            text = when (semester) {
                Semester.FALL -> "Fall Points"
                Semester.SPRING -> "Spring Points"
                Semester.SUMMER -> "Summer Points"
            },
            fontFamily = FontFamily(Font(R.font.viga)),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun TableHeaderRow(isDarkMode: Boolean, sortOrder: SortOrder, onSortToggle: () -> Unit) {
    val headerBg = if (isDarkMode) Color(0xFF0A2A40) else Color(0xFFE6E8ED)
    val headerTextColor = if (isDarkMode) Color.White else Color(0xFF4F5D75)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(headerBg)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaderCell("RANK", Modifier.weight(0.8f), headerTextColor)
        HeaderCell("NAME", Modifier.weight(2.6f), headerTextColor)
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "POINTS",
                fontFamily = FontFamily(Font(R.font.viga)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = headerTextColor
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = if (sortOrder == SortOrder.Descending) "↓" else "↑",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = headerTextColor,
                modifier = Modifier
                    .padding(start = 2.dp)
                    .clickable { onSortToggle() }
            )
        }
    }
}

@Composable
private fun TableDataRow(row: LeaderboardRow, isDarkMode: Boolean) {
    val primaryText = if (isDarkMode) Color.White else Color.Black
    val secondaryText = if (isDarkMode) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant
    val tableBg = if (isDarkMode) Color(0xFF0F3048) else Color(0xFFFFFFFF)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(tableBg)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Rank
        Box(
            modifier = Modifier
                .weight(0.8f),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = row.rank.toString(),
                color = primaryText,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }

        // Name + email
        Column(modifier = Modifier.weight(2.6f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = row.name,
                color = primaryText,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            // made email selectable (?) so admins can copy it
            SelectionContainer {
                Text(
                    text = row.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = secondaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Points
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = row.points.toString(),
                color = primaryText,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
private fun HeaderCell(text: String, modifier: Modifier, color:Color, alignEnd: Boolean = false) {
    Box(modifier = modifier) {
        Box(modifier = modifier) {
            Text(
                text = text,
                fontFamily = FontFamily(Font(R.font.viga)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = color,
                modifier =
                    if (alignEnd) Modifier.align(Alignment.CenterEnd) else Modifier.align(Alignment.CenterStart)
            )
        }
    }
}
