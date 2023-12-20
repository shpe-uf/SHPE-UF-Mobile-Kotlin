package com.example.shpe_uf_mobile_kotlin.ui.pages.home
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
@Composable
fun CalendarView(
    weekDates: List<LocalDate>,
    events: List<HomeViewModel.Event>,
    onDateClicked: (LocalDate) -> Unit
) {
    Column {
        CalendarWeekDays()
        CalendarWeekRow(weekDates, events, onDateClicked)

//        getDaysInMonthArray(currentDate).chunked(7).forEach { week ->
//            CalendarWeekRow(week, onDateClicked)
//        }
    }
}

@Composable
fun CalendarWeekDays() {
    Row(modifier = Modifier.fillMaxWidth()) {
        // Reorder the days to start from Sunday
        val daysOfWeek = listOf(DayOfWeek.SUNDAY) + DayOfWeek.values().filter { it != DayOfWeek.SUNDAY }

        daysOfWeek.forEach { dayOfWeek ->
            Text(
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).uppercase(Locale.getDefault()),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    }
}
@Composable
fun CalendarWeekRow(
    week: List<LocalDate>,
    events: List<HomeViewModel.Event>,
    onDateClicked: (LocalDate) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        week.forEach { date ->

            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .clickable { date.let(onDateClicked) }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(text = date.dayOfMonth.toString(), fontSize = 16.sp)
                    events.take(2).forEach { event ->
                        Text(
                            text = event.summary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp
                        )
                    }
                    if (events.size > 2) {
                        Text("+${events.size - 2}", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}


fun getDaysInMonthArray(date: LocalDate): List<LocalDate?> {
    val yearMonth = YearMonth.from(date)
    val totalDays = yearMonth.lengthOfMonth()

    val firstOfMonth = date.with(TemporalAdjusters.firstDayOfMonth())
    val dayOfWeek = firstOfMonth.dayOfWeek  // DayOfWeek enum

    val daysInMonthArray = mutableListOf<LocalDate?>()

    // For a Sunday-start calendar, Sunday has an offset of 0
    val offset = dayOfWeek.value % DayOfWeek.SUNDAY.value

    // Fill in nulls for padding before the first day of the month
    for (i in 1..offset) {
        daysInMonthArray.add(null)
    }

    // Add actual days of the month
    for (i in 1..totalDays) {
        daysInMonthArray.add(LocalDate.of(date.year, date.month, i))
    }

    // Add nulls for padding after the last day of the month to complete the week
    while (daysInMonthArray.size % 7 != 0) {
        daysInMonthArray.add(null)
    }

    return daysInMonthArray
}

fun getDatesOfWeek(date: LocalDate): List<LocalDate> {
    val firstDayOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
    return (0..6).map { firstDayOfWeek.plusDays(it.toLong()) }
}


// STOPS HERE


// get items from homeViewModel and display them, code:
@Composable
fun HomeScreen() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    val currentDate = LocalDate.now()
    val weekDates = getDatesOfWeek(currentDate)

    val viewModel = HomeViewModel()
    val events = viewModel.events.observeAsState(initial = listOf()) // might need to change to update based on refresh or initial load


    Column {
        CircularLogoPlaceholder()
        CalendarView(
            weekDates = weekDates,
            events = events.value
        ) { date ->
            selectedDate = date
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

                    // events for the day
                    DayEventFeed(
                        date = date,
                        events = events.value,
                        onSaveEvent = { event ->
                            // Handle save event action
                            viewModel.saveEvent(event)
                        }
                    )
                }
            }
        }

        // This will be updated to be the posts feed rather than card feed.
        CardFeed(cardItems = sampleCardItems)
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
        painter = painterResource(id = R.drawable.logo_placeholder), // Replace with your placeholder image
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