package com.example.shpe_uf_mobile_kotlin.ui.pages.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme


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
            dateTime = "2021-10-10T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T11:00:00-04:00",
            timeZone = "America/New_York"
        )
    ),
    HomeViewModel.Event(
        id = "2",
        summary = "Event 2",
        description = "This is event 2",
        location = "Location 2",
        start = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T11:00:00-04:00",
            timeZone = "America/New_York"
        )
    ),
    HomeViewModel.Event(
        id = "3",
        summary = "Event 3",
        description = "This is event 3",
        location = "Location 3",
        start = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T11:00:00-04:00",
            timeZone = "America/New_York"
        )
    ),
    HomeViewModel.Event(
        id = "4",
        summary = "Event 4",
        description = "This is event 4",
        location = "Location 4",
        start = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T11:00:00-04:00",
            timeZone = "America/New_York"
        )
    ),
    HomeViewModel.Event(
        id = "5",
        summary = "Event 5",
        description = "This is event 5",
        location = "Location 5",
        start = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T11:00:00-04:00",
            timeZone = "America/New_York"
        )
    ),
HomeViewModel.Event(
        id = "6",
        summary = "Event 6",
        description = "This is event 6",
        location = "Location 6",
        start = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T11:00:00-04:00",
            timeZone = "America/New_York"
        )
    ),
    HomeViewModel.Event(
        id = "7",
        summary = "Event 7",
        description = "This is event 7",
        location = "Location 7",
        start = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T11:00:00-04:00",
            timeZone = "America/New_York"
        )
    ),
    HomeViewModel.Event(
        id = "8",
        summary = "Event 8",
        description = "This is event 8",
        location = "Location 8",
        start = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T10:00:00-04:00",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2021-10-10T11:00:00-04:00",
            timeZone = "America/New_York"
        )
    )
)

// Google Calendar API Portion
// Here we need to call the google calendar API to get events, send them to view model, and display them
// this could be in a form of a list in addition to a calendar view

// This class will be used to handle all the logic for the home screen
// get items from homeViewModel and display them, code:
@Composable
fun HomeScreen()   {
    // gets events that are displayed in cards from the view model
    val viewModel = HomeViewModel()
    val events = viewModel.events.value
    // Add a logo at the top of the screen
    Column {
        CircularLogoPlaceholder()
        // Add a card feed
        CardFeed(cardItems = sampleCardItems)
    }
    // This will call the fetchCalendarEvents function in the view model
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
            )
        ))
    }
}