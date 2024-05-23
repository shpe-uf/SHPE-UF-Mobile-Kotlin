package com.example.shpe_uf_mobile_kotlin.ui.pages.home
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shpe_uf_mobile_kotlin.repository.EventRepository
import com.example.shpe_uf_mobile_kotlin.repository.NotificationRepository
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


// create sample card items
//val sampleCardItems = listOf(
//    HomeViewModel.Event(
//        id = "1",
//        summary = "Event 1",
//        description = "This is event 1",
//        location = "Location 1",
//        start = HomeViewModel.EventDateTime(
//            dateTime = "2024-02-16T10:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        end = HomeViewModel.EventDateTime(
//            dateTime = "2024-02-16T11:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        colorResId = Color.White,
//        eventType = HomeViewModel.EventType.GBM
//    ),
//    HomeViewModel.Event(
//        id = "2",
//        summary = "Event 2",
//        description = "This is event 2",
//        location = "Location 2",
//        start = HomeViewModel.EventDateTime(
//            dateTime = "2024-02-16T10:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        end = HomeViewModel.EventDateTime(
//            dateTime = "2024-02-16T11:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        colorResId = Color.White,
//        eventType = HomeViewModel.EventType.GBM
//    ),
//    HomeViewModel.Event(
//        id = "3",
//        summary = "Event 3",
//        description = "This is event 3",
//        location = "Location 3",
//        start = HomeViewModel.EventDateTime(
//            dateTime = "2024-02-17T10:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        end = HomeViewModel.EventDateTime(
//            dateTime = "2024-02-17T11:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        colorResId = Color.White,
//        eventType = HomeViewModel.EventType.GBM
//    ),
//    HomeViewModel.Event(
//        id = "4",
//        summary = "Event 4",
//        description = "This is event 4",
//        location = "Location 4",
//        start = HomeViewModel.EventDateTime(
//            dateTime = "2024-02-17T10:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        end = HomeViewModel.EventDateTime(
//            dateTime = "2024-02-17T12:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        colorResId = Color.White,
//        eventType = HomeViewModel.EventType.GBM
//    ),
//    HomeViewModel.Event(
//        id = "5",
//        summary = "Event 5",
//        description = "This is event 5",
//        location = "Location 5",
//        start = HomeViewModel.EventDateTime(
//            dateTime = "2023-12-19T10:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        end = HomeViewModel.EventDateTime(
//            dateTime = "2023-12-19T11:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        colorResId = Color.White,
//        eventType = HomeViewModel.EventType.GBM,
//    ),
//HomeViewModel.Event(
//        id = "6",
//        summary = "Event 6",
//        description = "This is event 6",
//        location = "Location 6",
//        start = HomeViewModel.EventDateTime(
//            dateTime = "2023-12-19T10:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        end = HomeViewModel.EventDateTime(
//            dateTime = "2023-12-19T11:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//    colorResId = Color.White,
//    eventType = HomeViewModel.EventType.GBM
//    ),
//    HomeViewModel.Event(
//        id = "7",
//        summary = "Event 7",
//        description = "This is event 7",
//        location = "Location 7",
//        start = HomeViewModel.EventDateTime(
//            dateTime = "2023-12-19T10:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        end = HomeViewModel.EventDateTime(
//            dateTime = "2023-12-19T11:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        colorResId = Color.White,
//        eventType = HomeViewModel.EventType.GBM
//    ),
//    HomeViewModel.Event(
//        id = "8",
//        summary = "Event 8",
//        description = "This is event 8",
//        location = "Location 8",
//        start = HomeViewModel.EventDateTime(
//            dateTime = "2023-12-19T10:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        end = HomeViewModel.EventDateTime(
//            dateTime = "2023-12-19T11:00:00-04:00",
//            timeZone = "America/New_York"
//        ),
//        colorResId = Color.White,
//        eventType = HomeViewModel.EventType.GBM
//    )
//)

// New Method to render calendar
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

// New HomeScreen Things
@Composable
fun TopHeader(
    viewModel: HomeViewModel = viewModel()
) {
    val homeState by viewModel.homeState.collectAsState()
    val currentDate = homeState.currentDate

    // Take the date from the current viewModel date and display the month
    // then in the same row we will display a search icon and notification icon
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(93.dp)
            .background(color = Variables.orange)
            .padding(10.dp),

    ) {
        // display month together "Month Year"

        Text(
            // I think we should add the year as well
            text = "${currentDate.month}",
            style = androidx.compose.ui.text.TextStyle(
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.Bottom)
                .width(107.dp)
                .height(31.dp)
        )

        // display search icon using the icon pack Image is for custom icon
//        Image(
//            painter = painterResource(id = R.drawable.ic_search),
//            contentDescription = "Search Icon",
//            modifier = Modifier
//                .size(24.dp)
//                .align(Alignment.CenterVertically)
//        )

        // This is for a temp one to test layout
        // Icon for bell
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications",
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.Bottom)
                .width(33.dp)
                .height(32.dp)
                .clickable { viewModel.openNotificationWindow() },
            tint = Color.White
        )

        Icon(
            imageVector = Icons.Default.Flag,
            contentDescription = "Notifications",
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.Bottom)
                .width(33.dp)
                .height(32.dp)
                .clickable { viewModel.loadEvents() },
            tint = Color.White
        )

    }
}

object Variables {
    val orange = Color(0xFFD25917)
    val blue = Color(0xFF011F35)
}

@Composable
fun SlidingEventWindow(viewModel: HomeViewModel) {
    val homeState = viewModel.homeState.collectAsState()
    val isVisible = homeState.value.isEventDetailsVisible
    val event = homeState.value.selectedEvent

    // Dynamically calculate screen width
    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }

    if (isVisible) {
        BackHandler {
            viewModel.hideEventDetails()
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(initialOffsetX = { screenWidth.toInt() }),
        exit = slideOutHorizontally(targetOffsetX = { screenWidth.toInt() })
    ) {
        EventDetails(event, viewModel)
    }
}

@Composable
fun EventDetails (event: HomeViewModel.Event?, viewModel: HomeViewModel = viewModel()) {
    // Event Details
    Surface (
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(),
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
        ) {

            // image and close button container, could be made into own composable to be used later
            Box(contentAlignment = Alignment.TopStart) {
                Image(
                    painter = painterResource(id = R.drawable.shpe_logo_full_color),
                    contentDescription = "Event Image",
                    modifier = Modifier
                        .fillMaxWidth()
                )
                IconButton(onClick = { viewModel.hideEventDetails() },
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.1f), shape = CircleShape)
                ) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Dismiss",
                        tint = Color.White
                    )
                }
            }

            // Event details card to have the rounded corner style be there
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(size = 25.dp),
                colors = CardDefaults.cardColors(containerColor = Variables.blue),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // This is the date, time and location
                    Column {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(0.9f),
                                text = event!!.summary,
                                style = androidx.compose.ui.text.TextStyle(
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD25917),
                                )
                            )
                            // current placeholder for the icon, can replace with the actual image we need,
                            Icon(
                                imageVector = Icons.Default.People,
                                contentDescription = "People",
                                tint = Color(0xFFD25917),
                                modifier = Modifier
                                    .size(45.dp)
                                    .weight(0.1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(82.dp))

                        Row {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "Date",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = formatDate(event!!.start),
                                style = androidx.compose.ui.text.TextStyle (
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFFFFFF),
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        Row {
                            Icon(imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = formatEventTime(event!!),
                                style = androidx.compose.ui.text.TextStyle (
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFFFFFF),
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        Row {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = event!!.location ?: ("TBD"),
                                style = androidx.compose.ui.text.TextStyle (
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFFFFFF),
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(82.dp))

                        Text(text = "Description",
                            style = androidx.compose.ui.text.TextStyle (
                                fontSize = 18.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = event!!.description
                                ?: ("This is basic placeholder of data. In order for this to work" +
                                        "properly, we need to make sure that in the google calendar these events are updated." +
                                        "Otherwise we would need a specific functions to update these on later."),
                            style = androidx.compose.ui.text.TextStyle (
                                fontSize = 18.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                            )
                        )
                    }
                    // maybe have the save event be here:

                }
            }
        }
    }
}

//@Preview (showBackground = true)
//@Composable
//fun EventDetailsPreview() {
//    EventDetails(
//        event = HomeViewModel.Event(
//            id = "1",
//            summary = "SHPE GBM #1",
//            description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
//            location = "https://ufl.zoom.us/j/95895737986",
//            start = HomeViewModel.EventDateTime(
//                dateTime = "2023-12-19T18:00:00-04:00",
//                timeZone = "America/New_York"
//            ),
//            end = HomeViewModel.EventDateTime(
//                dateTime = "2023-12-19T19:00:00-04:00",
//                timeZone = "America/New_York"
//            ),
//            colorResId = Color.White,
//            eventType = HomeViewModel.EventType.GBM
//        )
//    )
//}

@Composable
fun SlidingNotificationWindow(viewModel: HomeViewModel) {
    val homeState = viewModel.homeState.collectAsState()
    val isVisible = homeState.value.isNotificationWindowVisible

    // Dynamically calculate screen width
    val screenWidth =
        with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }

    if (isVisible) {
        BackHandler {
            viewModel.hideNotificationWindow()
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(initialOffsetX = { screenWidth.toInt() }),
        exit = slideOutHorizontally(targetOffsetX = { screenWidth.toInt() })
    ) {
        NotificationSettingsContent(viewModel = viewModel)
    }
}

@Composable
fun NotificationSettingsContent(viewModel: HomeViewModel) {
    val context = LocalContext.current
    val homeState by viewModel.homeState.collectAsState()


    // Notification Details
    Surface (
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(),
        color = Variables.blue
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // image and close button container, could be made into own composable to be used later
            Box(contentAlignment = Alignment.TopStart) {

                // Header for notification window
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(93.dp)
                        .background(color = Variables.orange)
                        .padding(10.dp),

                    ) {
                    // Used to Exit the notification window
                    IconButton(
                        onClick = { viewModel.hideNotificationWindow() },
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .height(35.dp)
                            .width(35.dp)
                        ,
                    ) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Dismiss",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Notifications Settings",
                        style = androidx.compose.ui.text.TextStyle(
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.Bottom)
                            .width(107.dp)
                            .height(31.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(120.dp))

            Text(
                text = "Tap which type of event you want" +
                        "\nnotifications for",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),

                    textAlign = TextAlign.Center,
                ),
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(120.dp))

            // Rest of tHE Content in the screen
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Column (horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                    ) {

                        if (homeState.notificationSettings.gbmNotification) {
                            Image(
                                painter = painterResource(id = R.drawable.gbmselected),
                                contentDescription = "GBM Notifications ON", modifier = Modifier
                                    .width(92.dp)
                                    .height(90.dp)
                                    .clickable {
                                        viewModel.toggleNotificationSettings(
                                            context,
                                            HomeViewModel.EventType.GBM,
                                            !viewModel.homeState.value.notificationSettings.gbmNotification
                                        )
                                    }
                            )
                        }
                        else {
                            Image(
                                painter = painterResource(id = R.drawable.gbmdefault),
                                contentDescription = "GBM Notifications ON", modifier = Modifier
                                    .width(92.dp)
                                    .height(90.dp)
                                    .clickable {
                                        viewModel.toggleNotificationSettings(
                                            context,
                                            HomeViewModel.EventType.GBM,
                                            !viewModel.homeState.value.notificationSettings.gbmNotification
                                        )
                                    }
                            )
                        }

                        Text(
                            text = "GBMs",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),

                                textAlign = TextAlign.Center,
                            )
                        )
                    }

                    Column (horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        if (homeState.notificationSettings.infoSessionNotification) {
                            Image(
                                painter = painterResource(id = R.drawable.infoselected),
                                contentDescription = "Info Session Notifications ON", modifier = Modifier
                                    .width(92.dp)
                                    .height(90.dp)
                                    .clickable {
                                        viewModel.toggleNotificationSettings(
                                            context,
                                            HomeViewModel.EventType.InfoSession,
                                            !viewModel.homeState.value.notificationSettings.infoSessionNotification
                                        )
                                    }
                            )
                        }
                        else {
                            Image(
                                painter = painterResource(id = R.drawable.infodefault),
                                contentDescription = "Info Session Notifications OFF", modifier = Modifier
                                    .width(92.dp)
                                    .height(90.dp)
                                    .clickable {
                                        viewModel.toggleNotificationSettings(
                                            context,
                                            HomeViewModel.EventType.InfoSession,
                                            !viewModel.homeState.value.notificationSettings.infoSessionNotification
                                        )
                                    }
                            )
                        }

                        Text(
                            text = "Info\nSessions",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),

                                textAlign = TextAlign.Center,
                            )

                        )
                    }

                    Column (horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                    ){
                        if (homeState.notificationSettings.workshopNotification) {
                            Image(
                                painter = painterResource(id = R.drawable.workshopselected),
                                contentDescription = "Info Session Notifications ON", modifier = Modifier
                                    .width(92.dp)
                                    .height(90.dp)
                                    .clickable {
                                        viewModel.toggleNotificationSettings(
                                            context,
                                            HomeViewModel.EventType.Workshop,
                                            !viewModel.homeState.value.notificationSettings.workshopNotification
                                        )
                                    }
                            )
                        }
                        else {
                            Image(
                                painter = painterResource(id = R.drawable.workshopdefault),
                                contentDescription = "Workshop Notifications OFF", modifier = Modifier
                                    .width(92.dp)
                                    .height(90.dp)
                                    .clickable {
                                        viewModel.toggleNotificationSettings(
                                            context,
                                            HomeViewModel.EventType.Workshop,
                                            !viewModel.homeState.value.notificationSettings.workshopNotification
                                        )
                                    }
                            )
                        }
                        Text(
                            text = "Workshops",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),

                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(60.dp))


                Row {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        if (homeState.notificationSettings.volunteeringNotification) {
                            Image(
                                painter = painterResource(id = R.drawable.volunteerselected),
                                contentDescription = "Volunteer Notifications ON", modifier = Modifier
                                    .width(92.dp)
                                    .height(90.dp)
                                    .clickable {
                                        viewModel.toggleNotificationSettings(
                                            context,
                                            HomeViewModel.EventType.Volunteering,
                                            !viewModel.homeState.value.notificationSettings.volunteeringNotification
                                        )
                                    }
                            )
                        }
                        else {
                            Image(
                                painter = painterResource(id = R.drawable.volunteerdefault),
                                contentDescription = "Volunteer Notifications OFF", modifier = Modifier
                                    .width(92.dp)
                                    .height(90.dp)
                                    .clickable {
                                        viewModel.toggleNotificationSettings(
                                            context,
                                            HomeViewModel.EventType.Volunteering,
                                            !viewModel.homeState.value.notificationSettings.volunteeringNotification
                                        )
                                    }
                            )
                        }
                        Text(
                            text = "Volunteering",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),

                                textAlign = TextAlign.Center,
                            )
                        )
                    }

                    Column (horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                    ){
                        if (homeState.notificationSettings.socialNotification) {
                            Image(
                                painter = painterResource(id = R.drawable.socialselected),
                                contentDescription = "Social Notifications ON", modifier = Modifier
                                    .width(92.dp)
                                    .height(90.dp)
                                    .clickable {
                                        viewModel.toggleNotificationSettings(
                                            context,
                                            HomeViewModel.EventType.Social,
                                            !viewModel.homeState.value.notificationSettings.socialNotification
                                        )
                                    }
                            )
                        }
                        else {
                            Image(
                                painter = painterResource(id = R.drawable.socialdefault),
                                contentDescription = "Social Notifications OFF", modifier = Modifier
                                    .width(92.dp)
                                    .height(90.dp)
                                    .clickable {
                                        viewModel.toggleNotificationSettings(
                                            context,
                                            HomeViewModel.EventType.Social,
                                            !viewModel.homeState.value.notificationSettings.socialNotification
                                        )
                                    }
                            )
                        }
                        Text(
                            text = "Socials",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),

                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun NotificationSettingsPreview() {
    NotificationSettingsContent(
        viewModel = HomeViewModel(
            notificationRepo = NotificationRepository(
                context = LocalContext.current
            ),
            eventRepo = EventRepository(
                context = LocalContext.current
            ),
        )
    )
}

@Preview (showBackground = true)
@Composable
fun TopHeaderPreview() {
    TopHeader(
        viewModel = HomeViewModel(
            notificationRepo = NotificationRepository(
                context = LocalContext.current
            ),
            eventRepo = EventRepository(
                context = LocalContext.current
            ),
        )
    )
}

// This is to display events under calendar
@Composable
fun EventCard(event: HomeViewModel.Event, viewModel: HomeViewModel = viewModel()) {
    // not sure about padding for now
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, end = 5.dp, bottom = 10.dp)
            .clickable { viewModel.selectEvent(event) },
        colors = CardDefaults.cardColors(containerColor = event.colorResId),
        shape = RoundedCornerShape(size = 25.dp),
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row {
                Text(text = event.summary, style = MaterialTheme.typography.titleLarge)
                // Insert people together icon
                // current placeholder for the icon, can replace with the actual image we need,
                Icon(imageVector = Icons.Default.People, contentDescription = "People")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(imageVector = Icons.Default.People, contentDescription = "People")
                    Text(
                        text = formatDate(event.start),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Icon(imageVector = Icons.Default.Timer, contentDescription = null)
                    Text(text = formatEventTime(event), style = MaterialTheme.typography.bodyMedium)
                }
            }

//            IconButton(onClick = onSaveClicked) {
//                Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
//            }
        }
    }

//    if (showPopUp) {
//        EventPopUp(event, showPopUp, onDismissRequest = { showPopUp = false })
//    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SlidingSheet() {
    val scaffoldSheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
    ) { innerPadding ->
        // 40.dp for the drag handle
        val bottomPadding = innerPadding.calculateBottomPadding() + 40.dp
        BottomSheetScaffold(
            scaffoldState = scaffoldSheetState,
            sheetPeekHeight = bottomPadding,
            modifier = Modifier.padding(innerPadding),
            sheetContent = {
                Column(
                    Modifier
                        .padding(bottom = bottomPadding)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Sheet content")
                    Spacer(Modifier.height(20.dp))
                    Button(onClick = {
                        scope.launch { scaffoldSheetState.bottomSheetState.collapse()}
                    }) {
                        Text("Hide bottom sheet")
                    }
                    Button(onClick = { }) {
                        Text("Some button")
                    }
                }
            },
        ) {

            // this is where your screen could go
            NewHomeScreen(viewModel = viewModel())

        }

    }
}


// Not being used for good for reference
@Composable
fun EventPopUp(event: HomeViewModel.Event, showPopup: Boolean, onDismissRequest: () -> Unit ) {
// Pop up for the event
    Dialog(onDismissRequest = onDismissRequest,
        DialogProperties(dismissOnBackPress = true,
        dismissOnClickOutside = true,
        usePlatformDefaultWidth = false
        )
    ) {
        // Customize the layout of the dialog
        Surface(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(),
            ) {

            Column(modifier = Modifier
                .fillMaxWidth()
            ) {

                // image and close button container, could be made into own composable to be used later
                Box(contentAlignment = Alignment.TopStart) {
                    Image(
                        painter = painterResource(id = R.drawable.shpe_logo_full_color),
                        contentDescription = "Event Image",
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    IconButton(onClick = onDismissRequest,
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.1f), shape = CircleShape)
                    ) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Dismiss",
                            tint = Color.White
                        )
                    }
                }

                // Event details card to have the rounded corner style be there
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(size = 25.dp),
                    colors = CardDefaults.cardColors(containerColor = Variables.blue),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Placeholder for the picture at the top
                        // This is the date, time and location
                        Column {
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text (
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .weight(0.9f),
                                    textAlign = TextAlign.Center,

                                    text = event.summary,
                                    style = androidx.compose.ui.text.TextStyle(
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFD25917),
                                    )
                                )
                                // current placeholder for the icon, can replace with the actual image we need,
                                Icon(
                                    imageVector = Icons.Default.People,
                                    contentDescription = "People",
                                    tint = Color(0xFFD25917),
                                    modifier = Modifier
                                        .size(45.dp)
                                        .weight(0.1f)
                                )
                            }
                            Spacer(modifier = Modifier.height(82.dp))

                            Row {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = "Date",
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = formatDate(event.start),
                                    style = androidx.compose.ui.text.TextStyle (
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                        )
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            Row {
                                Icon(imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = formatEventTime(event),
                                    style = androidx.compose.ui.text.TextStyle (
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFFFFFFF),
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            Row {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Location",
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = event.location ?: ("TBD"),
                                    style = androidx.compose.ui.text.TextStyle (
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFFFFFFF),
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(82.dp))

                            Text(text = "Description",
                                style = androidx.compose.ui.text.TextStyle (
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFFFFFF),
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = event.description
                                    ?: ("This is basic placeholder of data. In order for this to work" +
                                            "properly, we need to make sure that in the google calendar these events are updated." +
                                            "Otherwise we would need a specific functions to update these on later."),
                                style = androidx.compose.ui.text.TextStyle (
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFFFFFF),
                                )
                            )
                        }
                        // maybe have the save event be here:

                    }
                }
            }
        }
    }
}

// preview for popup
//@Preview (showBackground = true)
//@Composable
//fun EventPopUpPreview() {
//    EventPopUp(
//        event = HomeViewModel.Event(
//            id = "1",
//            summary = "SHPE GBM #1",
//            description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
//            location = "https://ufl.zoom.us/j/95895737986",
//            start = HomeViewModel.EventDateTime(
//                dateTime = "2023-12-19T18:00:00-04:00",
//                timeZone = "America/New_York"
//            ),
//            end = HomeViewModel.EventDateTime(
//                dateTime = "2023-12-19T19:00:00-04:00",
//                timeZone = "America/New_York"
//            ),
//            colorResId = Color.White,
//            eventType = HomeViewModel.EventType.GBM
//        ),
//        showPopup = true,
//        onDismissRequest = { }
//    )
//}

@Composable
fun EventCardFeed(events: List<HomeViewModel.Event>) {
    val daysInMonth = getDaysInMonthArray(YearMonth.now())

    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier
            .background(Variables.blue)
            .fillMaxSize()

    ) {
        items(daysInMonth) { day ->
            if (day != null) {
                val dayEvents = events.filter { it.occursOnDate(day) }
                if (dayEvents.isNotEmpty()) {
                    DayContainer(date = day, events = dayEvents)
                }
            }
        }
    }
}

@Composable
fun EventCardFeedSimple(viewModel: HomeViewModel) {
    val state by viewModel.homeState.collectAsState()
    val events = state.events
    // Dynamically calculate days based on loaded months
    val daysInMonth = viewModel.getDaysInMonthArray(state.loadedMonths)

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier
            .background(Variables.blue)
            .fillMaxSize()
    ) {
        items(daysInMonth) { day ->
            day.let {
                val dayEvents = events.filter { it.occursOnDate(day) }
                if (dayEvents.isNotEmpty()) {
                    DayContainer(date = day, events = dayEvents)
                }
            }
        }
    }

    // Detecting scroll to the bottom and load the next month
    LaunchedEffect(listState) {
        snapshotFlow { listState.isNearTheEnd() }
            .distinctUntilChanged() // Only react to changes in the scroll position state.
            .filter { it } // Only proceed if it's true that we're near the end.
            .collect {
                val lastLoadedMonth = state.loadedMonths.maxOrNull() ?: YearMonth.now()
                val nextMonth = lastLoadedMonth.plusMonths(1)
                coroutineScope.launch {
                    viewModel.fetchEventsForMonth(nextMonth)
                }
            }
    }
}

fun LazyListState.isNearTheEnd(bufferItems: Int = 3): Boolean {
    // Check if the last visible item's index is within `bufferItems` of the total item count
    val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return false
    val totalItemCount = layoutInfo.totalItemsCount
    return lastVisibleItemIndex >= totalItemCount - bufferItems - 1
}

@Composable
fun DayContainer(
    date: LocalDate,
    events: List<HomeViewModel.Event>,
) {

    val dayOfWeek = DateTimeFormatter.ofPattern("EEE").format(date)

    // Display the day of the month on the left and then all the events of that day on the right in a column
    Row {
        // This is used to show the date
        Column (
            modifier = Modifier
                .weight(0.1f)
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = dayOfWeek,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFB7B7B7),
                    textAlign = TextAlign.Center,
                ),
            )
            Text(
                text = date.dayOfMonth.toString(),
                style  = androidx.compose.ui.text.TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFB7B7B7),
                    textAlign = TextAlign.Center,
                ),
            )
        }

        // This is used to show the events
        Column (
            modifier = Modifier
                .weight(1f)
        ) {
            events.forEach { event ->
                EventCard(event)
            }
            // dotted spacer
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
            )
        }
    }
}

//@Preview (showBackground = true)
//@Composable
//fun DayContainerPreview() {
//    DayContainer(
//        // pass february 16, 2024
//        date = LocalDate.of(2024, 2, 16),
//        events = sampleCardItems
//    )
//}
//
//@Preview (showBackground = true)
//@Composable
//fun EventCardFeedPreview() {
//    SHPEUFMobileKotlinTheme {
//        EventCardFeed(events = sampleCardItems)
//    }
//}

// used to make the time formatted properly
fun formatEventTime(event: HomeViewModel.Event): String {
    val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("h:mm a")
    val zoneId = ZoneId.of("America/New_York")

    return try {
        val startZonedDateTime = ZonedDateTime.parse(event.start.dateTime, inputFormatter).withZoneSameInstant(zoneId)
        val endZonedDateTime = ZonedDateTime.parse(event.end.dateTime, inputFormatter).withZoneSameInstant(zoneId)
        val startTime = startZonedDateTime.format(outputFormatter)
        val endTime = endZonedDateTime.format(outputFormatter)
        "$startTime - $endTime"
    } catch (e: Exception) {
        if (event.eventType == HomeViewModel.EventType.Default) {
            "All Day"
        } else {
            "TBD"
        }
    }
}

fun formatDate(eventDateTime: HomeViewModel.EventDateTime): String {
    val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
    val zoneId = ZoneId.systemDefault()

    return try {
        val zonedDateTime =
            ZonedDateTime.parse(eventDateTime.dateTime, inputFormatter).withZoneSameInstant(zoneId)
        val dayOfMonth = zonedDateTime.dayOfMonth
        val dayOfMonthWithOrdinal = "$dayOfMonth${getOrdinalIndicator(dayOfMonth)}"
        zonedDateTime.format(outputFormatter).replaceFirst(Regex("\\d+"), dayOfMonthWithOrdinal)
    } catch (e: Exception) {
        // if all day, say all day, if no date time, it basically means all day.
        "All Day"
    }
}

fun getOrdinalIndicator(dayOfMonth: Int): String {
        return when {
            dayOfMonth in 11..13 -> "th"
            dayOfMonth % 10 == 1 -> "st"
            dayOfMonth % 10 == 2 -> "nd"
            dayOfMonth % 10 == 3 -> "rd"
            else -> "th"
        }
    }

//@Preview(showBackground = true)
//@Composable
//fun EventCardPreview() {
//    SHPEUFMobileKotlinTheme {
//        EventCard(
//            event = HomeViewModel.Event(
//                id = "1",
//                summary = "SHPE UF General Body Meeting",
//                description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
//                location = "https://ufl.zoom.us/j/95895737986",
//                start = HomeViewModel.EventDateTime(
//                    dateTime = "2023-12-19T18:00:00-04:00",
//                    timeZone = "America/New_York"
//                ),
//                end = HomeViewModel.EventDateTime(
//                    dateTime = "2023-12-19T19:00:00-04:00",
//                    timeZone = "America/New_York"
//                ),
//                colorResId = Color.White,
//                eventType = HomeViewModel.EventType.GBM
//            )
//        )
//    }
//}

// sliding page

@Composable
fun NewHomeScreen(viewModel: HomeViewModel) {
    val homeState = viewModel.homeState.collectAsState()
    val currentDate = homeState.value.currentDate
    val events = homeState.value.events

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }


    
    // surround with surface
    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Variables.blue
    ) {
        LaunchedEffect(currentDate) {
            viewModel.fetchEventsForMonth(YearMonth.from(currentDate))
        }

        Column {
            TopHeader(viewModel = viewModel)
           // EventCardFeed(events = events)
            EventCardFeedSimple(viewModel = viewModel)
                // Place other components here if needed
            }

        SlidingEventWindow(viewModel = viewModel)
        SlidingNotificationWindow(viewModel = viewModel)

        val onDaySelected: (LocalDate) -> Unit = { date ->
            selectedDate = date
        }
    }


}

@Preview
@Composable
fun NewHomeScreenPreview() {
    NewHomeScreen(
        viewModel = HomeViewModel(
            notificationRepo = NotificationRepository(
                context = LocalContext.current
            ),
            eventRepo = EventRepository(
                context = LocalContext.current
            ),
        )
    )
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
