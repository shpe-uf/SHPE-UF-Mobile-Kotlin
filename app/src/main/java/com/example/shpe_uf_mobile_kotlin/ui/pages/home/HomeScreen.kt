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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.repository.EventRepository
import com.example.shpe_uf_mobile_kotlin.repository.NotificationRepository
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme
import com.example.shpe_uf_mobile_kotlin.ui.theme.Universltstd
import com.example.shpe_uf_mobile_kotlin.ui.theme.Viga
import com.example.shpe_uf_mobile_kotlin.ui.theme.blueDarkModeBackground
import com.example.shpe_uf_mobile_kotlin.ui.theme.headerOrange
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


//create sample card items
val sampleCardItems = listOf(
    HomeViewModel.Event(
        id = "1",
        summary = "Event 1",
        description = "This is event 1",
        location = "Location 1",
        start = HomeViewModel.EventDateTime(
            dateTime = "2024-06-25T10:00:00-04:00",
            date = null,
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2024-06-25T11:00:00-04:00",
            date = null,
            timeZone = "America/New_York"
        ),
        colorResId = blueDarkModeBackground,
        eventType = HomeViewModel.EventType.GBM
    ),
    HomeViewModel.Event(
        id = "2",
        summary = "Event 2",
        description = "This is event 2",
        location = "Location 2",
        start = HomeViewModel.EventDateTime(
            dateTime = null,
            date = "2024-06-26",
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = null,
            date = "2024-06-26",
            timeZone = "America/New_York"
        ),
        colorResId = Color.Red,
        eventType = HomeViewModel.EventType.GBM
    ),
    HomeViewModel.Event(
        id = "3",
        summary = "Event 3",
        description = "This is event 3",
        location = "Location 3",
        start = HomeViewModel.EventDateTime(
            dateTime = "2024-06-30T10:00:00-04:00",
            date = null,
            timeZone = "America/New_York"
        ),
        end = HomeViewModel.EventDateTime(
            dateTime = "2024-06-30T11:00:00-04:00",
            date = null,
            timeZone = "America/New_York"
        ),
        colorResId = Color.Blue,
        eventType = HomeViewModel.EventType.GBM
    )
)

// New HomeScreen Things
@Composable
fun TopHeader(
    viewModel: HomeViewModel = viewModel()
) {
    val homeState by viewModel.homeState.collectAsState()

    // Take the date from the current viewModel date and display the month
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(83.dp)
            .background(color = headerOrange)
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // display month together "Month Year"
        Text(
            // I think we should add the year as well
            text = homeState.monthDisplayedName,
            style = TextStyle(
                color = Color.White,
                fontFamily = Viga,
                fontSize = 26.sp,
                fontWeight = FontWeight(400)
            ),
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.Bottom)
        )

        // This is for a temp one to test layout
        // Icon for bell
        Icon(
            painter = painterResource(id = R.drawable.notifications_icon),
            contentDescription = "Notifications",

            modifier = Modifier
                .size(33.dp)
                .align(Alignment.Bottom)
                .clickable { viewModel.openNotificationWindow() },
            tint = Color.White
        )

        // Debug Buttons for Now
        Icon(
            imageVector = Icons.Default.Storage,
            contentDescription = "Get from local storage",
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.Bottom)
                .width(33.dp)
                .height(32.dp)
                .clickable { viewModel.loadEvents() },
            tint = Color.White
        )
        Icon(
            imageVector = Icons.Default.Cloud,
            contentDescription = "Get Events From Cloud",
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.Bottom)
                .width(33.dp)
                .height(32.dp)
                .clickable { viewModel.fetchEventsForMonth(YearMonth.now()) },
            tint = Color.White
        )
    }
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
            .fillMaxHeight()
            .background(blueDarkModeBackground),
    ) {
        Column {
            // image and close button container, could be made into own composable to be used later
            Box(contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Image(
                    painter = painterResource(id = when (event!!.eventType) {
                        HomeViewModel.EventType.GBM -> R.drawable.gbm_background
                        HomeViewModel.EventType.InfoSession -> R.drawable.info_session_background
                        HomeViewModel.EventType.Workshop -> R.drawable.workshop_background
                        HomeViewModel.EventType.Social -> R.drawable.social_background
                        HomeViewModel.EventType.Volunteering -> R.drawable.volunteering_background
                        HomeViewModel.EventType.Default -> R.drawable.social_background
                    }),
                    contentDescription = "Event Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(240.dp)
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
                    .fillMaxSize()
                    .background(blueDarkModeBackground)
                    .offset(y = (-20).dp),
                shape = RoundedCornerShape(size = 25.dp),
                colors = CardDefaults.cardColors(containerColor = blueDarkModeBackground),

            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(50.dp),
                ) {
                    //Title
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
                            style = TextStyle(
                                fontFamily = Viga,
                                fontSize = 32.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFD25917),)
                        )
                        Image (
                            painter = painterResource(id = when (event.eventType) {
                                HomeViewModel.EventType.GBM -> R.drawable.gbm_icon
                                HomeViewModel.EventType.InfoSession -> R.drawable.infosession_icon
                                HomeViewModel.EventType.Workshop -> R.drawable.workshop_icon
                                HomeViewModel.EventType.Social -> R.drawable.social_icon
                                HomeViewModel.EventType.Volunteering -> R.drawable.volunteering_icon
                                HomeViewModel.EventType.Default -> R.drawable.social_icon
                            }),
                            contentDescription = "Favorite",
                            modifier = Modifier
                                .size(37.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))

                    // Event Date
                    Row {
                        Image(
                            painterResource(R.drawable.calendar_ui_icon),
                            contentDescription = "Calendar",
                            modifier = Modifier
                                .size(37.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = formatDate(event!!.start),
                            style = TextStyle (
                                fontSize = 18.sp,
                                fontFamily = Universltstd,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    // Event Time
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Image (
                            painter = painterResource(id = R.drawable.timer_ui_icon),
                            contentDescription = "Clock",
                            modifier = Modifier
                                .size(37.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = formatEventTime(event!!),
                            style = TextStyle (
                                fontSize = 18.sp,
                                fontFamily = Universltstd,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    // Event Location
                    Row {
                        Image (
                            painter = painterResource(id = R.drawable.location_ui_icon),
                            contentDescription = "Location",
                            modifier = Modifier
                                .size(37.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = event!!.location ?: ("TBD"),
                            style = TextStyle (
                                fontSize = 18.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))

                    Text(text = "Description:",
                        style = TextStyle (
                            fontSize = 18.sp,
                            fontFamily = Universltstd,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFB7B7B7),)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = event!!.description
                            ?: ("Join us!"),
                        style = TextStyle (
                            fontSize = 18.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),)
                    )
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun EventDetailsPreview() {
    EventDetails(
        event = HomeViewModel.Event(
            id = "1",
            summary = "SHPE GBM #1",
            description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
            location = "https://ufl.zoom.us/j/95895737986",
            start = HomeViewModel.EventDateTime(
                dateTime = "2023-12-19T18:00:00-04:00",
                date = null,
                timeZone = "America/New_York"
            ),
            end = HomeViewModel.EventDateTime(
                dateTime = "2023-12-19T19:00:00-04:00",
                date = null,
                timeZone = "America/New_York"
            ),
            colorResId = Color.White,
            eventType = HomeViewModel.EventType.GBM
        ),
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
        color = blueDarkModeBackground
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
                        .height(83.dp)
                        .background(color = headerOrange)
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
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = Viga,
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
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Tap which type of event you want" +
                        "\nnotifications for",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = Viga,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFB7B7B7),
                    textAlign = TextAlign.Center,
                ),
                color = Color(0xFFB7B7B7),
            )
            Spacer(modifier = Modifier.height(80.dp))

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
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = Universltstd,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .padding(top = 10.dp)
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
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = Universltstd,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .padding(top = 10.dp)
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
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = Universltstd,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .padding(top = 10.dp)
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
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                fontFamily = Universltstd,
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .padding(top = 10.dp)
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
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = Universltstd,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .padding(top = 10.dp)
                        )
                    }
                }

                // Button for allowing all notifications
                Spacer(modifier = Modifier.height(60.dp))
                Button(
                    modifier = Modifier
                        .width(254.dp)
                        .height(41.dp),
                    onClick = { viewModel.toggleAllNotifications(context)},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = homeState.allNotificationCurrentColor
                    )
                ) {
                    Text(
                        text = "Allow for all",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = Universltstd,
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

// The Event that is displayed on the screen
@Composable
fun EventCard(event: HomeViewModel.Event, viewModel: HomeViewModel = viewModel()) {
    // not sure about padding for now
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp, end = 5.dp, bottom = 5.dp)
            .clickable { viewModel.selectEvent(event) },
        colors = CardDefaults.cardColors(containerColor = event.colorResId),
        shape = RoundedCornerShape(size = 25.dp),
    ) {
        Column(modifier = Modifier
            .padding(start = 25.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = event.summary,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = Universltstd,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                    ),
                    modifier = Modifier
                        .padding(end = 10.dp, top = 5.dp)
                )

                // we need to change this based on the event type
                Icon(
                    painter = painterResource(id = when (event.eventType) {
                        HomeViewModel.EventType.GBM -> R.drawable.gbm_icon
                        HomeViewModel.EventType.InfoSession -> R.drawable.infosession_icon
                        HomeViewModel.EventType.Workshop -> R.drawable.workshop_icon
                        HomeViewModel.EventType.Social -> R.drawable.social_icon
                        HomeViewModel.EventType.Volunteering -> R.drawable.volunteering_icon
                        HomeViewModel.EventType.Default -> R.drawable.social_icon
                    }),
                    contentDescription = "Event Type",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon (
                        painter = painterResource(id = R.drawable.calendar_ui_icon),
                        contentDescription = "Calendar",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 5.dp),
                        tint = Color.Unspecified
                    )

                    Text(
                        text = formatDate(event.start),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = Universltstd,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF)
                        ),
                        modifier = Modifier
                            .padding(top = 2.dp)
                    )
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon (
                        painter = painterResource(id = R.drawable.timer_ui_icon),
                        contentDescription = "Clock",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 5.dp),
                        tint = Color.Unspecified
                    )
                    Text(text = formatEventTime(event),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = Universltstd,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF)
                        ),
                        modifier = Modifier
                            .padding(top = 2.dp)
                    )
                }
            }
        }
    }
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
                    colors = CardDefaults.cardColors(containerColor = blueDarkModeBackground),
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
                                    style = TextStyle(
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
                                    style = TextStyle (
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
                                    style = TextStyle (
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
                                    style = TextStyle (
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFFFFFFF),
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(82.dp))

                            Text(text = "Description",
                                style = TextStyle (
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
                                style = TextStyle (
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
fun EventCardFeed(viewModel: HomeViewModel) {
    val state by viewModel.homeState.collectAsState()
    val events = state.events
    val listState = rememberLazyListState()

    // Dynamically calculate days based on loaded months
//    val daysInMonth = viewModel.getDaysInMonthArray(state.loadedMonths)
//    val coroutineScope = rememberCoroutineScope()
//    val eventInDays = mutableMapOf<LocalDate, List<HomeViewModel.Event>>()

    val today = LocalDate.now()

    // Updated to handle events spanning multiple days
    val groupedEvents = remember(events) {
        mutableMapOf<LocalDate, MutableList<HomeViewModel.Event>>().apply {
            events.forEach { event ->
                val startDate = LocalDate.parse(event.start.dateTime?.substring(0, 10))
                val endDate = LocalDate.parse(event.end.dateTime?.substring(0, 10))
                var currentDate = startDate
                while (!currentDate.isAfter(endDate)) {
                    if (!currentDate.isBefore(today)) {  // Only consider dates from today onwards
                        this.getOrPut(currentDate) { mutableListOf() }.add(event)
                    }
                    currentDate = currentDate.plusDays(1)
                }
            }
        }.toSortedMap()
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier
            .background(blueDarkModeBackground)
            .fillMaxSize()
    ) {
        groupedEvents.forEach { (date, events) ->
            item {
                if (date != null) {
                    DayContainer(date = date, events = events, viewModel = viewModel)
                    Spacer(modifier = Modifier.height(10.dp))

                    // if the last of the grouped events, no dash
                    if (date != groupedEvents.keys.last()) {
                        Row {
                            Spacer(modifier = Modifier.fillMaxWidth(0.15f))

                            Image(
                                painter = painterResource(id = R.drawable.dashed_line_spacer),
                                contentDescription = "Dotted Line",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color.Transparent)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DayContainer(
    date: LocalDate,
    events: List<HomeViewModel.Event>,
    viewModel: HomeViewModel
) {
    val dayOfWeek = DateTimeFormatter.ofPattern("EEE").format(date)

    // Display the day of the month on the left and then all the events of that day on the right in a column
    Row {
        // This is used to show the date
        Column(
            modifier = Modifier
                .weight(0.1f)
                .padding(top = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = dayOfWeek,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = Universltstd,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFB7B7B7),
                    textAlign = TextAlign.Center,
                ),
            )
            Text(
                text = date.dayOfMonth.toString(),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = Universltstd,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
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
                EventCard(event, viewModel = viewModel)
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun DayContainerPreview() {
    SHPEUFMobileKotlinTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            DayContainer(
                date = LocalDate.now(),
                events = sampleCardItems,
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
    }
}

@Preview (showBackground = true)
@Composable
fun EventCardFeedPreview() {
    SHPEUFMobileKotlinTheme {
        EventCardFeed(
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
}

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

@Preview(showBackground = true)
@Composable
fun EventCardPreview() {
    SHPEUFMobileKotlinTheme {
        EventCard(
            event = HomeViewModel.Event(
                id = "1",
                summary = "SHPE GBM #1",
                description = "Join us for our first GBM of the semester! We will be introducing our new E-Board and going over our plans for the semester. We will also be playing some games and giving away prizes!",
                location = "https://ufl.zoom.us/j/95895737986",
                start = HomeViewModel.EventDateTime(
                    dateTime = "2023-12-19T18:00:00-04:00",
                    date = null,
                    timeZone = "America/New_York"
                ),
                end = HomeViewModel.EventDateTime(
                    dateTime = "2023-12-19T19:00:00-04:00",
                    date = null,
                    timeZone = "America/New_York"
                ),
                colorResId = blueDarkModeBackground,
                eventType = HomeViewModel.EventType.GBM
            ),
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
}

@Composable
fun NewHomeScreen(viewModel: HomeViewModel) {
//    val homeState = viewModel.homeState.collectAsState()
//    val currentDate = homeState.value.currentDate
//    val launched = homeState.value.launched

    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = blueDarkModeBackground
    ) {
        LaunchedEffect(Unit) {
//            viewModel.fetchEventsForMonth(YearMonth.now())
//            viewModel.loadEvents()
        }

        Column {
            TopHeader(viewModel = viewModel)
            EventCardFeed(viewModel = viewModel)
        }

        SlidingEventWindow(viewModel = viewModel)
        SlidingNotificationWindow(viewModel = viewModel)
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
