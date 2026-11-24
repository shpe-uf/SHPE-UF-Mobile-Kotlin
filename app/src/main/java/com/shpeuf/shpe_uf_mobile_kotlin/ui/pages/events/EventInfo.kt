package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.events

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shpeuf.shpe_uf_mobile_kotlin.R
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors

/*
 * Event Info page (Admin Panel -> Event Info)
 *
 * Shows every event for the selected semester in a table, similar to the Events page
 * on the website. Data comes from EventInfoViewModel.
 *
 * How the composables in this file fit together:
 *
 *   EventInfoScreen        hooks up the ViewModel, toasts, and the delete popup
 *    ├─ EventInfoContent   the page layout (header, tabs, table card)
 *    │   ├─ SemesterTabs   Fall / Spring / Summer tabs
 *    │   └─ EventTable     column headers + list of rows, scrolls sideways
 *    │       ├─ HeaderCell one column title
 *    │       └─ EventRow   one event
 *    │           ├─ BodyCell        text cell
 *    │           └─ TableIconButton the Manual Input / Info / Delete buttons
 *    └─ DeleteEventDialog  "are you sure?" popup before deleting
 *
 * EventInfoContent doesn't touch the ViewModel directly, so the previews at the bottom
 * can render it with fake data.
 */

// Column widths. Together they're wider than a phone screen, which is why the table
// scrolls sideways. The last few were bumped up so the bold header words don't wrap.
private val NameWidth = 160.dp
private val CategoryWidth = 110.dp
private val ExpirationWidth = 100.dp
private val RequestWidth = 96.dp
private val AttendanceWidth = 124.dp
private val PointsWidth = 86.dp
private val ManualInputWidth = 96.dp
private val InfoWidth = 72.dp
private val DeleteWidth = 86.dp
private val TableWidth = NameWidth + CategoryWidth + ExpirationWidth + RequestWidth +
        AttendanceWidth + PointsWidth + ManualInputWidth + InfoWidth + DeleteWidth

private val ShpeOrange = Color(0xFFD25917)
private val DeleteRed = Color(0xFFD32F2F)

// Entry point for the page, called from the nav graph.
// onManualInputClick and onInfoClick are left open on purpose so the Manual Input and
// Event Information popups can be plugged in. Both get the clicked row's EventInfoItem,
// which has the event id, name, attendee list, etc. If a popup changes anything,
// call viewModel.loadEvents() afterwards so the table picks up the new data.
@Composable
fun EventInfoScreen(
    isDarkMode: Boolean,
    viewModel: EventInfoViewModel = viewModel(),
    onBack: () -> Unit = {},
    onManualInputClick: (EventInfoItem) -> Unit = {},
    onInfoClick: (EventInfoItem) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // The event whose Delete button was tapped. While this isn't null the popup is showing.
    var pendingDelete by remember { mutableStateOf<EventInfoItem?>(null) }

    // Show a toast whenever the ViewModel has something to say (e.g. "Event deleted"),
    // then clear it so it doesn't show again on recomposition.
    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            showCustomToast(context, it.text, isError = it.isError)
            viewModel.messageShown()
        }
    }

    EventInfoContent(
        uiState = uiState,
        isDarkMode = isDarkMode,
        onBack = onBack,
        onRefresh = viewModel::loadEvents,
        onSemesterSelected = viewModel::selectSemester,
        onManualInputClick = onManualInputClick,
        onInfoClick = onInfoClick,
        onDeleteClick = { pendingDelete = it }
    )

    // Nothing gets deleted until the admin confirms here.
    pendingDelete?.let { event ->
        DeleteEventDialog(
            eventName = event.name,
            isDarkMode = isDarkMode,
            onConfirm = {
                viewModel.deleteEvent(event)
                pendingDelete = null
            },
            onDismiss = { pendingDelete = null }
        )
    }
}

// Confirmation popup for deleting an event. "Yes, delete" calls onConfirm,
// tapping Cancel or outside the popup calls onDismiss.
@Composable
private fun DeleteEventDialog(
    eventName: String,
    isDarkMode: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val containerColor = if (isDarkMode) Color(0xFF002139) else Color.White
    val textColor = if (isDarkMode) Color.White else Color(0xFF011F35)

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = containerColor,
        title = {
            Text(
                text = "Delete Event",
                color = textColor,
                fontFamily = FontFamily(Font(R.font.viga)),
                fontSize = 22.sp
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete \"$eventName\"? This can't be undone.",
                color = textColor,
                fontFamily = FontFamily(Font(R.font.universltstd)),
                fontSize = 15.sp
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = DeleteRed)
            ) { Text("Yes, delete", color = Color.White) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = textColor)
            }
        }
    )
}

// The actual page layout: orange header, semester tabs, then a card holding the table.
// The card shows a spinner on first load, an error + Retry if loading failed,
// and the EventTable otherwise. All clicks are passed back up to EventInfoScreen.
@Composable
fun EventInfoContent(
    uiState: EventInfoUiState,
    isDarkMode: Boolean,
    onBack: () -> Unit,
    onRefresh: () -> Unit,
    onSemesterSelected: (Semester) -> Unit,
    onManualInputClick: (EventInfoItem) -> Unit,
    onInfoClick: (EventInfoItem) -> Unit,
    onDeleteClick: (EventInfoItem) -> Unit
) {
    val pageBackground = if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    val textColor = if (isDarkMode) Color.White else Color(0xFF011F35)
    val tableBackground = if (isDarkMode) Color(0xFF002139) else Color.White
    // Same blues as the Admin Panel cards
    val accentBlue = if (isDarkMode) Color(0xFF2457C5) else Color(0xFF1E4A94)
    val headerTextColor = if (isDarkMode) Color(0xFF93E1FF) else Color(0xFF1E4A94)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pageBackground)
    ) {
        // Orange header like the Create Events page: back on the left, refresh on the right
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(ShpeOrange)
                .statusBarsPadding()
                .padding(top = 12.dp, bottom = 20.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            Text(
                text = "EVENT INFO",
                color = Color.White,
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.viga)),
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = onRefresh,
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Refresh events",
                    tint = Color.White
                )
            }
        }

        SemesterTabs(
            selected = uiState.selectedSemester,
            onSelected = onSemesterSelected,
            textColor = textColor,
            dividerColor = textColor.copy(alpha = 0.2f)
        )

        // Card that holds the table (or the loading / error state)
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = tableBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            when {
                uiState.isLoading && uiState.events.isEmpty() -> CenteredBox {
                    CircularProgressIndicator(color = ShpeOrange)
                }

                uiState.error != null && uiState.events.isEmpty() -> CenteredBox {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Couldn't load events",
                            color = textColor,
                            fontFamily = FontFamily(Font(R.font.universltstd)),
                            fontSize = 16.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = uiState.error,
                            color = textColor.copy(alpha = 0.7f),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = onRefresh,
                            colors = ButtonDefaults.buttonColors(containerColor = ShpeOrange)
                        ) { Text("Retry", color = Color.White) }
                    }
                }

                else -> EventTable(
                    events = uiState.visibleEvents,
                    semesterLabel = uiState.selectedSemester.label,
                    isDarkMode = isDarkMode,
                    textColor = textColor,
                    headerTextColor = headerTextColor,
                    buttonColor = accentBlue,
                    deletingEventId = uiState.deletingEventId,
                    onManualInputClick = onManualInputClick,
                    onInfoClick = onInfoClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }
    }
}

// Fall / Spring / Summer tabs above the table. The selected one is orange and underlined.
@Composable
private fun SemesterTabs(
    selected: Semester,
    onSelected: (Semester) -> Unit,
    textColor: Color,
    dividerColor: Color
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Semester.values().forEach { semester ->
                val isSelected = semester == selected
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelected(semester) }
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = semester.label,
                        color = if (isSelected) ShpeOrange else textColor,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.universltstd)),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1
                    )
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(if (isSelected) ShpeOrange else Color.Transparent)
                    )
                }
            }
        }
        HorizontalDivider(color = dividerColor)
    }
}

// The table itself: a header row of HeaderCells, then one EventRow per event.
// deletingEventId is the row that's currently being deleted so it can show a spinner.
@Composable
private fun EventTable(
    events: List<EventInfoItem>,
    semesterLabel: String,
    isDarkMode: Boolean,
    textColor: Color,
    headerTextColor: Color,
    buttonColor: Color,
    deletingEventId: String?,
    onManualInputClick: (EventInfoItem) -> Unit,
    onInfoClick: (EventInfoItem) -> Unit,
    onDeleteClick: (EventInfoItem) -> Unit
) {
    val horizontalScroll = rememberScrollState()
    val headerBackground = if (isDarkMode) Color(0xFF01304F) else Color(0xFFF3F6F9)
    val stripeColor = if (isDarkMode) Color(0xFF012843) else Color(0xFFF8F9FB)
    val dividerColor = textColor.copy(alpha = 0.12f)

    // The header and the rows live inside the same horizontal scroll,
    // so the column titles always stay lined up with the data underneath.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(horizontalScroll)
    ) {
        Column(modifier = Modifier.width(TableWidth)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerBackground)
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeaderCell("NAME", NameWidth, headerTextColor, TextAlign.Start)
                HeaderCell("CATEGORY", CategoryWidth, headerTextColor)
                HeaderCell("EXPIRATION", ExpirationWidth, headerTextColor)
                HeaderCell("REQUEST", RequestWidth, headerTextColor)
                HeaderCell("ATTENDANCE", AttendanceWidth, headerTextColor)
                HeaderCell("POINTS", PointsWidth, headerTextColor)
                HeaderCell("MANUAL\nINPUT", ManualInputWidth, headerTextColor)
                HeaderCell("INFO", InfoWidth, headerTextColor)
                HeaderCell("DELETE", DeleteWidth, headerTextColor)
            }
            HorizontalDivider(color = dividerColor)

            if (events.isEmpty()) {
                Text(
                    text = "No events for $semesterLabel",
                    color = textColor.copy(alpha = 0.7f),
                    fontFamily = FontFamily(Font(R.font.universltstd)),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp)
                )
            } else {
                // Every other row gets a light stripe to make it easier to read across
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(events, key = { _, event -> event.id }) { index, event ->
                        EventRow(
                            event = event,
                            background = if (index % 2 == 1) stripeColor else Color.Transparent,
                            textColor = textColor,
                            buttonColor = buttonColor,
                            isDeleting = event.id == deletingEventId,
                            onManualInputClick = { onManualInputClick(event) },
                            onInfoClick = { onInfoClick(event) },
                            onDeleteClick = { onDeleteClick(event) }
                        )
                        HorizontalDivider(color = dividerColor)
                    }
                }
            }
        }
    }
}

// One column title in the header row.
@Composable
private fun HeaderCell(
    text: String,
    width: Dp,
    color: Color,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        color = color,
        fontSize = 12.sp,
        lineHeight = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.universltstd)),
        textAlign = textAlign,
        // Don't let a word get split onto two lines. "MANUAL\nINPUT" still breaks
        // because of the \n.
        softWrap = false,
        maxLines = 2,
        modifier = Modifier
            .width(width)
            .padding(horizontal = 8.dp)
    )
}

// One event in the table. Cells go in the same order (and widths) as the header row.
// While isDeleting is true the Delete button is swapped for a small spinner.
@Composable
private fun EventRow(
    event: EventInfoItem,
    background: Color,
    textColor: Color,
    buttonColor: Color,
    isDeleting: Boolean,
    onManualInputClick: () -> Unit,
    onInfoClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .heightIn(min = 56.dp)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyCell(event.name, NameWidth, textColor, TextAlign.Start, maxLines = 2)
        BodyCell(event.category, CategoryWidth, textColor)
        BodyCell(event.formattedExpiration, ExpirationWidth, textColor, fontSize = 11.sp)

        // Request column: green check if it's a request event, red X if not
        Box(modifier = Modifier.width(RequestWidth), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = if (event.request) Icons.Filled.Check else Icons.Filled.Close,
                contentDescription = if (event.request) "Request: yes" else "Request: no",
                tint = if (event.request) Color(0xFF2E9E44) else Color(0xFFD32F2F),
                modifier = Modifier.size(18.dp)
            )
        }

        BodyCell(event.attendance.toString(), AttendanceWidth, textColor)
        BodyCell(event.points.toString(), PointsWidth, textColor)

        // Manual Input / Info / Delete buttons
        Box(modifier = Modifier.width(ManualInputWidth), contentAlignment = Alignment.Center) {
            TableIconButton(
                icon = Icons.Filled.PersonAdd,
                contentDescription = "Manual input for ${event.name}",
                color = buttonColor,
                onClick = onManualInputClick
            )
        }
        Box(modifier = Modifier.width(InfoWidth), contentAlignment = Alignment.Center) {
            TableIconButton(
                icon = Icons.Filled.Info,
                contentDescription = "Info for ${event.name}",
                color = buttonColor,
                onClick = onInfoClick
            )
        }
        Box(modifier = Modifier.width(DeleteWidth), contentAlignment = Alignment.Center) {
            if (isDeleting) {
                CircularProgressIndicator(
                    color = DeleteRed,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                TableIconButton(
                    icon = Icons.Filled.Close,
                    contentDescription = "Delete ${event.name}",
                    color = DeleteRed,
                    onClick = onDeleteClick
                )
            }
        }
    }
}

// A plain text cell in a row. Long text gets cut off with "..." after maxLines.
@Composable
private fun BodyCell(
    text: String,
    width: Dp,
    color: Color,
    textAlign: TextAlign = TextAlign.Center,
    maxLines: Int = 2,
    fontSize: androidx.compose.ui.unit.TextUnit = 13.sp
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        lineHeight = fontSize * 1.25f,
        fontFamily = FontFamily(Font(R.font.universltstd)),
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .width(width)
            .padding(horizontal = 12.dp)
    )
}

// Small square button with a white icon, used for Manual Input, Info, and Delete.
@Composable
private fun TableIconButton(
    icon: ImageVector,
    contentDescription: String,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(color)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}

// Fills the card and centers whatever is inside (used for the spinner and error message).
@Composable
private fun CenteredBox(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = content
    )
}

// ---------- Previews ----------
// Fake events so the page can be checked in Android Studio's preview pane
// without logging in or hitting the backend.
private val previewEvents = listOf(
    EventInfoItem("1", "GBM 8 Volunteers", "gbm8", "Volunteering", 4, 6,
        "Wed Jan 21 2026 21:25:00 GMT+0000 (Coordinated Universal Time)", false,
        "Fall Semester", "2026-01-21T20:25:00.000Z", emptyList()),
    EventInfoItem("2", "New Member Welcome and Info Session Fall 2026", "welcome", "Miscellaneous", 1, 74,
        "Tue Aug 25 2026 03:46:54 GMT+0000 (Coordinated Universal Time)", true,
        "Fall Semester", "2026-08-24T21:46:54.905Z", emptyList()),
    EventInfoItem("3", "EPA FIU", "epa", "Workshop", 6, 2,
        "Fri Dec 12 2025 04:42:00 GMT+0000 (Coordinated Universal Time)", false,
        "Fall Semester", "2025-12-11T23:42:00.000Z", emptyList())
)

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
private fun EventInfoLightPreview() {
    EventInfoContent(
        uiState = EventInfoUiState(events = previewEvents, selectedSemester = Semester.FALL),
        isDarkMode = false,
        onBack = {}, onRefresh = {}, onSemesterSelected = {},
        onManualInputClick = {}, onInfoClick = {}, onDeleteClick = {}
    )
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
private fun EventInfoDarkPreview() {
    EventInfoContent(
        uiState = EventInfoUiState(events = previewEvents, selectedSemester = Semester.FALL),
        isDarkMode = true,
        onBack = {}, onRefresh = {}, onSemesterSelected = {},
        onManualInputClick = {}, onInfoClick = {}, onDeleteClick = {}
    )
}
