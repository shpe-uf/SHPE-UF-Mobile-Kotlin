package com.example.shpe_uf_mobile_kotlin.ui.pages.home
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SHPEUFMobileKotlinTheme  {
                // Replace this with your card data
                HomeScreen()
            }
        }
    }
}

// create data class for card items
data class CardItem(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val date: String,
    val time: String,
    val image: String
)
// create sample card items
val sampleCardItems = listOf(
    HomeViewModel.Event(
        id = "1",
        summary = "Event 1",
        description = "This is event 1",
        location = "Location 1",
        start = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T11:00:00-04:00",
            timeZone = "America/New_York"
        ),
        colorResId = 0
    ),
    HomeViewModel.Event(
        id = "2",
        summary = "Event 2",
        description = "This is event 2",
        location = "Location 2",
        start = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T11:00:00-04:00",
            timeZone = "America/New_York"
        ),
        colorResId = 0
    ),
    HomeViewModel.Event(
        id = "3",
        summary = "Event 3",
        description = "This is event 3",
        location = "Location 3",
        start = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T11:00:00-04:00",
            timeZone = "America/New_York"
        ),
        colorResId = 0
    ),
    HomeViewModel.Event(
        id = "4",
        summary = "Event 4",
        description = "This is event 4",
        location = "Location 4",
        start = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T11:00:00-04:00",
            timeZone = "America/New_York"
        ),
        colorResId = 0
    ),
    HomeViewModel.Event(
        id = "5",
        summary = "Event 5",
        description = "This is event 5",
        location = "Location 5",
        start = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T11:00:00-04:00",
            timeZone = "America/New_York"
        ),
        colorResId = 0
    ),
HomeViewModel.Event(
        id = "6",
        summary = "Event 6",
        description = "This is event 6",
        location = "Location 6",
        start = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T11:00:00-04:00",
            timeZone = "America/New_York"
        ),
    colorResId = 0
    ),
    HomeViewModel.Event(
        id = "7",
        summary = "Event 7",
        description = "This is event 7",
        location = "Location 7",
        start = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T11:00:00-04:00",
            timeZone = "America/New_York"
        ),
        colorResId = 0
    ),
    HomeViewModel.Event(
        id = "8",
        summary = "Event 8",
        description = "This is event 8",
        location = "Location 8",
        start = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2023-12-19T11:00:00-04:00",
            timeZone = "America/New_York"
        ),
        colorResId = 0
    )
)

// Google Calendar API Portion
// Here we need to call the google calendar API to get events, send them to view model, and display them
// this could be in a form of a list in addition to a calendar view
fun getDatesOfWeek(date: LocalDate): List<LocalDate> {
    val firstDayOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
    return (0..6).map { firstDayOfWeek.plusDays(it.toLong()) }
}
// STOPS HERE

// New Method to render calendar
@Composable
fun DayCard(
    date: LocalDate,
    events: List<HomeViewModel.Event>,
    isWeekView: Boolean,
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .wrapContentWidth()
            .height(75.dp)
            .fillMaxWidth()
    ) {
        // Display the day of the month
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.titleMedium
        )

        EventDisplay(events, isWeekView)
    }
}

@Composable
fun DayLabelsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DayOfWeek.values().forEach { dayOfWeek ->
            Text(
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EventDisplay(events: List<HomeViewModel.Event>, isWeekView: Boolean) {
    if (isWeekView) {
        events.take(2).forEach { event ->
            EventSummary(event = event)
        }
        if (events.isEmpty()) {
            Text(
                text = "No SHPE :(",
                style = MaterialTheme.typography.bodySmall
            )
        }
        if (events.size > 2) {
            Text(
                text = "+${events.size - 2}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun WeekView(
    weekDates: List<LocalDate>,
    events: List<HomeViewModel.Event>,
    onDaySelected: (LocalDate) -> Unit
) {
    // Wrap each DayCard in a Box with weight to ensure equal spacing
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly // Optional, for spacing between days
    ) {
        weekDates.forEach { date ->
            // Filter events for this particular day
            val dayEvents = events.filter { it.occursOnDate(date) }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(2.dp)
                    .wrapContentWidth()
                    .height(75.dp)
                    .fillMaxWidth()
                    .clickable { onDaySelected(date) }
            ) {
                DayCard(
                    date = date,
                    events = dayEvents,
                    isWeekView = true,
                )
            }
        }
    }
}

@Composable
fun MonthDayCard(
    date: LocalDate,
    events: List<HomeViewModel.Event>,
    onDaySelected: (LocalDate) -> Unit,
    isEventDay: Boolean // Assuming you have a way to determine if the day has events
) {
    // Define the background color based on whether the day has events
    val backgroundColor = if (isEventDay) {
        MaterialTheme.colorScheme.primary // Color for days with events
    } else {
        MaterialTheme.colorScheme.surface // Default color for days without events
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onDaySelected(date) },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display the day of the month
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.titleMedium
            )

        }
    }
}


@Composable
fun MonthView(
    yearMonth: YearMonth,  // The year and month to display
    events: List<HomeViewModel.Event>,  // All events for the month
    onDaySelected: (LocalDate) -> Unit  // Callback when a day is selected
) {
    // Generate the days to display in the month view
    val daysInMonth = getDaysInMonthArray(yearMonth)

    // Define the number of columns in the grid (7 for a week)
    val columns = GridCells.Fixed(7)

    LazyVerticalGrid(
        columns = columns,
        contentPadding = PaddingValues(8.dp),
        content = {
            items(daysInMonth) { day ->
                if (day != null) {
                    // Determine if the day has events
                    val isEventDay = events.any { it.occursOnDate(day) }

                    MonthDayCard(
                        date = day,
                        events = events.filter { it.occursOnDate(day) },
                        isEventDay = isEventDay,
                        onDaySelected = onDaySelected
                    )
                } else {
                    // Empty placeholder for alignment
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
        }
    )
}

@Preview (showBackground = true)
@Composable
fun MonthViewPreview() {
    SHPEUFMobileKotlinTheme {
        MonthView(
            yearMonth = YearMonth.now(),
            events = listOf(
                HomeViewModel.Event(
                    id = "1",
                    summary = "SHPE UF General Body Meeting",
                    description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                    location = "https://ufl.zoom.us/j/95895737986",
                    start = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T18:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    end = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T19:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    colorResId = 0
                ),
                HomeViewModel.Event(
                    id = "2",
                    summary = "SHPE UF General Body Meeting",
                    description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                    location = "https://ufl.zoom.us/j/95895737986",
                    start = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T18:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    end = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T19:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    colorResId = 0
                ),
                HomeViewModel.Event(
                    id = "3",
                    summary = "SHPE UF General Body Meeting",
                    description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                    location = "https://ufl.zoom.us/j/95895737986",
                    start = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T18:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    end = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T19:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    colorResId = 0
                ),
            ),
            onDaySelected = { }
        )
    }
}


fun getDaysInMonthArray(yearMonth: YearMonth): List<LocalDate?> {
    val daysInMonth = mutableListOf<LocalDate?>()

    // Calculate the offset to align the start of the month with the correct day of the week
    val firstOfMonth = yearMonth.atDay(1)
    val dayOfWeekOffset = firstOfMonth.dayOfWeek.value % 7

    // Add nulls to create offset
    for (i in 0 until dayOfWeekOffset) {
        daysInMonth.add(null)
    }

    // Add actual days of the month
    for (day in 1..yearMonth.lengthOfMonth()) {
        daysInMonth.add(yearMonth.atDay(day))
    }

    return daysInMonth
}

enum class CalendarViewMode {
    WEEK,
    MONTH
}

@Preview (showBackground = true)
@Composable
fun WeekViewPreview() {
    SHPEUFMobileKotlinTheme {
        WeekView(
            weekDates = getDatesOfWeek(LocalDate.now()),
            events = listOf(
                HomeViewModel.Event(
                    id = "1",
                    summary = "SHPE UF General Body Meeting",
                    description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                    location = "https://ufl.zoom.us/j/95895737986",
                    start = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T18:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    end = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T19:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    colorResId = 0
                ),
                HomeViewModel.Event(
                    id = "2",
                    summary = "SHPE UF General Body Meeting",
                    description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                    location = "https://ufl.zoom.us/j/95895737986",
                    start = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T18:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    end = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T19:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    colorResId = 0
                ),
                HomeViewModel.Event(
                    id = "3",
                    summary = "SHPE UF General Body Meeting",
                    description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                    location = "https://ufl.zoom.us/j/95895737986",
                    start = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T18:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    end = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T19:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    colorResId = 0
                ),
            ),
            onDaySelected = { }
        )
    }
}


@Composable
fun EventSummary(event: HomeViewModel.Event) {
    // Basic representation of an event, you can expand this based on your Event data class
    Text(
        text = event.summary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodySmall
    )
}

// This is to display events under calendar
@Composable
fun EventCard(event: HomeViewModel.Event, onSaveClicked: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = event.summary, style = MaterialTheme.typography.titleMedium)
                Text(text = "${event.description}", style = MaterialTheme.typography.bodyMedium)
                Text(text = event.start.dateTime, style = MaterialTheme.typography.bodyMedium)

            }
            IconButton(onClick = onSaveClicked) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventCardPreview() {
    SHPEUFMobileKotlinTheme {
        EventCard(
            event = HomeViewModel.Event(
                id = "1",
                summary = "SHPE UF General Body Meeting",
                description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                location = "https://ufl.zoom.us/j/95895737986",
                start = HomeViewModel.EventDateTime(
                    dateTime = "2023-12-19T18:00:00-04:00",
                    timeZone = "America/New_York"
                ),
                end = HomeViewModel.EventDateTime(
                    dateTime = "2023-12-19T19:00:00-04:00",
                    timeZone = "America/New_York"
                ),
                colorResId = 0
            )
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun DayCardPreview() {
    SHPEUFMobileKotlinTheme {
        DayCard(
            date = LocalDate.now(),
            events = listOf(
                HomeViewModel.Event(
                    id = "1",
                    summary = "SHPE UF General Body Meeting",
                    description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                    location = "https://ufl.zoom.us/j/95895737986",
                    start = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T18:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    end = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T19:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    colorResId = 0
                ),
                HomeViewModel.Event(
                    id = "2",
                    summary = "SHPE UF General Body Meeting",
                    description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                    location = "https://ufl.zoom.us/j/95895737986",
                    start = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T18:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    end = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T19:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    colorResId = 0
                ),
                HomeViewModel.Event(
                    id = "3",
                    summary = "SHPE UF General Body Meeting",
                    description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                    location = "https://ufl.zoom.us/j/95895737986",
                    start = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T18:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    end = HomeViewModel.EventDateTime(
                        dateTime = "2023-12-19T19:00:00-04:00",
                        timeZone = "America/New_York"
                    ),
                    colorResId = 0
                ),
            ),
            isWeekView = true,
        )
    }
}


// get items from homeViewModel and display them, code:
@Composable
fun HomeScreen(viewModel: HomeViewModel = HomeViewModel()) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val viewModel = HomeViewModel()
    val events by viewModel.events.observeAsState(initial = listOf())// might need to change to update based on refresh or initial load

    var viewMode by remember { mutableStateOf(CalendarViewMode.WEEK) }
    val weekDates by viewModel.currentWeekDates.observeAsState(initial = emptyList())  // Example: List<LocalDate> representing the week

    val onDaySelected: (LocalDate) -> Unit = { date ->
        selectedDate = date
    }

    Column {
        CircularLogoPlaceholder()

        // Toggle Button to switch views
        ToggleViewButton(viewMode = viewMode) {
            viewMode = if (it == CalendarViewMode.WEEK) CalendarViewMode.MONTH else CalendarViewMode.WEEK
        }

        DayLabelsRow()

        // Conditional rendering based on the view mode
        if (viewMode == CalendarViewMode.WEEK) {
            WeekView(weekDates, events, onDaySelected)
        } else {
            MonthView(YearMonth.now(), events, onDaySelected)
        }

        // here, when a data is selected, we should display the events for that day, and allow the user to save them
        selectedDate?.let { date ->
            Card(
                modifier = Modifier.padding(8.dp)
            ) {
                Column {
                    // option to close
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text("Selected date: $date", modifier = Modifier.weight(1f))
                        Text(
                            "Close",
                            modifier = Modifier
                                .clickable { selectedDate = null }
                                .padding(8.dp),
                            color = Color.Red
                        )
                    }


                    selectedDate?.let { date ->
                        viewModel.fetchEventsForDate(date)

                        DayEventFeed(
                            date = date,
                            events = events,
                            onSaveEvent = { event ->
                                // Handle save event action
                                viewModel.saveEvent(event)
                            }
                        )
                    }
                }
            }
        }

        // This will be updated to be the posts feed rather than card feed.
        CardFeed(cardItems = sampleCardItems)
    }
}

@Composable
fun ToggleViewButton(viewMode: CalendarViewMode, onToggle: (CalendarViewMode) -> Unit) {
    Button(
        onClick = { onToggle(viewMode) },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = if (viewMode == CalendarViewMode.WEEK) "Switch to Month View" else "Switch to Week View")
    }
}

@Composable
fun DayEventFeed(
    date: LocalDate,
    events: List<HomeViewModel.Event>,
    onSaveEvent: (HomeViewModel.Event) -> Unit
) {
    val dayEvents = events.filter { it.matchesDate(date) }
    LazyColumn {
        items(dayEvents) { event ->
            EventItem(event, onSaveEvent)
        }
    }
}

@Composable
fun EventItem(event: HomeViewModel.Event, onSaveEvent: (HomeViewModel.Event) -> Unit) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Event: ${event.summary}")
            Text("Time: ${event.start.dateTime}")
            // ... other event details ...

            Button(onClick = { onSaveEvent(event) }) {
                Text("Save Event")
            }
        }
    }
}

// preview for HomeScreen
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SHPEUFMobileKotlinTheme {
        HomeScreen()
    }
}

// circular logo image at the top of the screen
@Composable
fun CircularLogoPlaceholder() {
    Image(
        painter = painterResource(id = R.drawable.shpe_logo_full_color), // Replace with your placeholder image
        contentDescription = "Logo Placeholder",
        modifier = Modifier
            .size(50.dp)
            .padding(8.dp)
            .clip(CircleShape),
        // Aligns the image to the top-left corner of the Box
        alignment = Alignment.TopStart,
        contentScale = ContentScale.Crop
    )
}

// The CardFeed is placed within the LazyColumn
@Composable
fun CardFeed(cardItems: List<HomeViewModel.Event>) {
    LazyColumn {
        items(cardItems) { item ->
            CardView(item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardFeedPreview() {
    SHPEUFMobileKotlinTheme {
        CardFeed(cardItems = sampleCardItems)
    }
}

@Composable
fun CardView(event: HomeViewModel.Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // events from google calendar
                Text(text = "Event Name: ${event.summary}")
                Text(text = "Description: ${event.description}")
                Text(text = "Where: ${event.location}")
                Text(text = "Start: ${event.start.dateTime}")
                Text(text = "End: ${event.end.dateTime}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardViewPreview() {
    SHPEUFMobileKotlinTheme {
        CardView(event = HomeViewModel.Event(
            id = "1",
            summary = "Event 1",
            description = "This is event 1",
            location = "Location 1",
            start = HomeViewModel.EventDateTime(
                dateTime = "2021-10-10T10:00:00-04:00",
                timeZone = "America/New_York"
            ),
            end = HomeViewModel.EventDateTime(
                dateTime = "2021-10-10T11:00:00-04:00",
                timeZone = "America/New_York"
            ),
            colorResId = 0
        ))
    }
}