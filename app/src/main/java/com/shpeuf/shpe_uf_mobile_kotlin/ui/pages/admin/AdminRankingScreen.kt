package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.admin

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
import androidx.compose.ui.graphics.Color // custom color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

enum class SortOrder { Descending, Ascending }
private val Fall = Color(0xFF1c437e)
private val Spring = Color(0xFFcc4f33)
private val Summer = Color(0xFF509ede)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRankingScreen(
    isDarkMode: Boolean,
    onBack: () -> Unit,
    viewModel: AdminRankingViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var sortOrder by remember { mutableStateOf(SortOrder.Descending) }
    val tabs = listOf(Semester.FALL, Semester.SPRING, Semester.SUMMER)

    val sortedRows = remember(state.rows, sortOrder) {
        when (sortOrder) {
            SortOrder.Descending -> state.rows.sortedWith(compareByDescending<LeaderboardRow> { it.points }.thenBy { it.name })
            SortOrder.Ascending -> state.rows.sortedWith(compareBy<LeaderboardRow> { it.points }.thenBy { it.name })
        }
    }.mapIndexed { idx, r -> r.copy(rank = idx + 1) } // re-rank after sorting

    LaunchedEffect(Unit) { viewModel.load() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ranking") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Text("←") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            // Semester buttons (Fall/Spring/Summer) + Sort control row
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = when (state.selectedSemester) {
                        Semester.FALL -> "FALL POINTS"
                        Semester.SPRING -> "SPRING POINTS"
                        Semester.SUMMER -> "SUMMER POINTS"
                    },
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                    color = semesterAccent(state.selectedSemester)
                )

                SortMenu(sortOrder = sortOrder, onChange = { sortOrder = it })
            }

            when {
                state.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.error != null -> {
                    Column(Modifier.padding(16.dp)) {
                        Text("Error: ${state.error}")
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { viewModel.load() }) { Text("Retry") }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        item {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                tonalElevation = 1.dp
                            ) {
                                TableHeaderRow()
                            }
                            Spacer(Modifier.height(8.dp))
                        }

                        items(sortedRows.size) { index ->
                            val row = sortedRows[index]

                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(0.dp),
                                tonalElevation = 0.dp
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                if (index % 2 == 0) MaterialTheme.colorScheme.surface
                                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                                            )
                                    ) {
                                        TableDataRow(row = row)
                                    }

                                    Divider()
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
private fun SemesterPill(
    semester: Semester,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedColor = when (semester) {
        Semester.FALL -> Fall
        Semester.SPRING -> Spring
        Semester.SUMMER -> Summer
    }

//    val fg = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    val bg = if (selected) selectedColor else selectedColor.copy(alpha=0.75f) // light unselected
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
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun SortMenu(sortOrder: SortOrder, onChange: (SortOrder) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = { expanded = true },
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = when (sortOrder) {
                SortOrder.Descending -> "Descending"
                SortOrder.Ascending -> "Ascending"
            },
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(Modifier.width(6.dp))
        Text("▾")
    }

    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(
            text = { Text("Descending") },
            onClick = { onChange(SortOrder.Descending); expanded = false }
        )
        DropdownMenuItem(
            text = { Text("Ascending") },
            onClick = { onChange(SortOrder.Ascending); expanded = false }
        )
    }
}

@Composable
private fun TableHeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaderCell("RANK", Modifier.weight(0.8f))
        HeaderCell("NAME", Modifier.weight(2.6f))
        HeaderCell("POINTS", Modifier.weight(1f), alignEnd = true)
    }
}

@Composable
private fun TableDataRow(row: LeaderboardRow) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }

        // Name + email
        Column(modifier = Modifier.weight(2.6f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = row.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            // made email selectable (?) so admins can copy it
            SelectionContainer {
                Text(
                    text = row.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
private fun HeaderCell(text: String, modifier: Modifier, alignEnd: Boolean = false) {
    Box(modifier = modifier) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            modifier = if (alignEnd) Modifier.align(Alignment.CenterEnd) else Modifier.align(Alignment.CenterStart)
        )
    }
}

@Composable
private fun semesterAccent(semester: Semester) = when (semester) {
    Semester.FALL -> Fall
    Semester.SPRING -> Spring
    Semester.SUMMER -> Summer
}