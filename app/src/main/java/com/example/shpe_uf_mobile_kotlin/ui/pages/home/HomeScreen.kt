package com.example.shpe_uf_mobile_kotlin.ui.pages.home
import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.ui.theme.TextColor
import com.example.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import com.example.shpe_uf_mobile_kotlin.ui.theme.WhiteSHPE
import com.example.shpe_uf_mobile_kotlin.util.*
import kotlinx.coroutines.delay

// Sample Card Items that are used for previews
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

/**
 * @description Top header is used to display current month and notification settings
 *
 * @author Josue Vicente & resources
 * @date 2024
 *
 * @param modifier the modifier passed in by the parent composable
 * @param viewModel this obtains the state to get the current month
 **/
@Composable
fun TopHeader(modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel()) {
    val homeState by viewModel.homeState.collectAsState()

    // Take the date from the current viewModel date and display the month
    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(83.dp)
            .background(color = headerOrange)
            .padding(
                top = WindowInsets.navigationBars
                    .asPaddingValues(LocalDensity.current)
                    .calculateTopPadding()
            ),
        verticalAlignment = Alignment.CenterVertically

    ) {
        // display month together "Month Year"
        Text(
            // I think we should add the year as well
            text = homeState.monthDisplayedName.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            style = TextStyle(
                color = Color.White,
                fontFamily = Viga,
                fontSize = 24.sp,
                fontWeight = FontWeight(400)
            ),
            color = Color.White,
            modifier = modifier
                .weight(1f)
                .align(Alignment.Bottom)
                .padding(vertical = 15.dp, horizontal = 32.dp)
        )

        // Icon for bell
        Icon(
            painter = painterResource(id = R.drawable.notifications_icon),
            contentDescription = "Notifications",

            modifier = modifier
                .size(33.dp)
                .align(Alignment.Bottom)
                .offset(y = (-14).dp, x = (-28).dp)
                .clickable { viewModel.openNotificationWindow() },
            tint = Color.White
        )
    }
}

/**
 * @description SlidingEventWindow is used in the calendar screen to display the event a user selects
 * to display event details
 *
 * @author Josue Vicente & resources
 * @date 2024
 *
 * @param modifier the modifier passed in by the parent composable
 * @param viewModel gets state if window open and the event it would display
 * @param isDarkMode updates color of background
 **/
@Composable
fun SlidingEventWindow(modifier: Modifier = Modifier, viewModel: HomeViewModel, isDarkMode: Boolean) {
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
        EventDetails(modifier, event, viewModel, isDarkMode)
    }
}

/**
 * @author Josue Vicente & resources
 * @date 2024
 *
 * @description EventDetails is used by the Sliding Event Window and is the actual content of the window
 *
 * @param modifier the modifier passed in by the parent composable
 * @param event this is the event we are to display the details for
 * @param viewModel this viewModer is used get the event types and to update the state by using some functions
 * @param isDarkMode updates the color of the background
 **/
@Composable
fun EventDetails (modifier: Modifier, event: HomeViewModel.Event?, viewModel: HomeViewModel = viewModel(), isDarkMode: Boolean) {
    if (event == null) {
        return
    }

    // Event Details
    Surface (
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight()
            .background(if (isDarkMode) blueDarkModeBackground else WhiteSHPE),
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ){
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

                IconButton(
                    onClick = { viewModel.hideEventDetails() },
                    modifier = Modifier
                        .offset(y = 10.dp, x = 10.dp)
                        .background(Color.Black.copy(alpha = 0.3f), shape = CircleShape)
                ) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Dismiss",
                        tint = Color.White
                    )
                }
            }

            // Event details card to have the rounded corner style be there
            //Lazy Column houses all composable for event details
            //Saving changes bc merge error occurred
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (isDarkMode) blueDarkModeBackground else WhiteSHPE)
                    .offset(y = (-23).dp),
                shape = RoundedCornerShape(size = 25.dp),
                colors = CardDefaults.cardColors(containerColor =
                if (isDarkMode) blueDarkModeBackground else WhiteSHPE),

            ) {
                 LazyColumn (modifier = Modifier
                      .fillMaxWidth()
                      .fillMaxHeight()
                      .padding(50.dp))
                 {
                     item {
                         //Title
                         Row(
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
                                     color = Color(0xFFD25917),
                                 )
                             )
                             Image(
                                 painter = painterResource(
                                     id = when (event.eventType) {
                                         HomeViewModel.EventType.GBM -> R.drawable.gbm_icon
                                         HomeViewModel.EventType.InfoSession -> R.drawable.infosession_icon
                                         HomeViewModel.EventType.Workshop -> R.drawable.workshop_icon
                                         HomeViewModel.EventType.Social -> R.drawable.social_icon
                                         HomeViewModel.EventType.Volunteering -> R.drawable.volunteering_icon
                                         HomeViewModel.EventType.Default -> R.drawable.social_icon
                                     }
                                 ),
                                 contentDescription = "Favorite",
                                 modifier = Modifier
                                     .size(37.dp)
                             )
                         }
                     }
                     item {
                         Spacer(modifier = Modifier.height(50.dp))
                     }

                     // Event Date
                     item {
                         Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                             Image(
                                 painterResource(R.drawable.calendar_ui_icon),
                                 contentDescription = "Calendar",
                                 modifier = Modifier
                                     .size(37.dp)
                             )

                             Spacer(modifier = Modifier.width(10.dp))

                             Text(
                                 text = formatDate(event!!.start),
                                 style = TextStyle(
                                     fontSize = 18.sp,
                                     fontFamily = Universltstd,
                                     fontWeight = FontWeight(400),
                                     color = if (isDarkMode) Color.White else Color.Black,),
                                 modifier = Modifier
                                     .align(Alignment.CenterVertically)
                             )
                         }
                     }
                     item {
                         Spacer(modifier = Modifier.height(10.dp))
                     }

                     // Event Time
                     item {
                         Row (modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                         Image(
                             painter = painterResource(id = R.drawable.timer_ui_icon),
                             contentDescription = "Clock",
                             modifier = Modifier
                                 .size(37.dp)
                         )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = formatEventTime(event!!),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = Universltstd,
                                    fontWeight = FontWeight(400),
                                    color = if (isDarkMode) Color.White else Color.Black,),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                        }
                     }
                     item {
                        Spacer(modifier = Modifier.height(10.dp))
                     }

                     // Event Location
                     item{
                         Row (modifier = Modifier) {
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
                                   color = if (isDarkMode) Color.White else Color.Black,),
                               modifier = Modifier
                                   .align(Alignment.CenterVertically)
                           )
                       }
                     }
                     item {
                        Spacer(modifier = Modifier.height(50.dp))
                     }
                     item {
                         Text(text = "Description:",
                             style = TextStyle (
                             fontSize = 18.sp,
                             fontFamily = Universltstd,
                             fontWeight = FontWeight(400),
                             color = if (isDarkMode) Color.White else Color.Black,)
                         )
                     }
                     item {
                         Spacer(modifier = Modifier.height(10.dp))
                     }
                     item {
                        Text( modifier = Modifier,
                        text = event!!.description
                            ?: ("Join us!"),
                            style = TextStyle (
                            fontSize = 18.sp,
                            fontWeight = FontWeight(400),
                            color = if (isDarkMode) Color.White else Color.Black,)
                        )
                     }
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun EventDetailsPreview() {
    EventDetails(
        modifier = Modifier,
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
        ),
        isDarkMode = true
    )
}

/**
 * @description This is the window that opens up when the user clicks on the notification settings icon
 *
 * @author Josue Vicente & resources
 * @date 2024
 *
 * @param modifier the modifier passed in by the parent composable
 * @param viewModel this viewModel is used to verify if the window is open (state)
 * @param darkMode updates the color of the background
 **/
@Composable
fun SlidingNotificationWindow(modifier: Modifier, viewModel: HomeViewModel, darkMode: Boolean) {
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
        NotificationSettingsContent(modifier = Modifier, viewModel = viewModel, darkMode)
    }
}

/**
 * @description NotificationSettingsContent is part of the sliding notification window and is the actual content
 *
 * @author Josue Vicente & Resources
 * @date 2024
 *
 * @param modifier the modifier passed in by the parent composable
 * @param viewModel this viewModer is used get the event types and to update the state by using some functions
 * @param darkMode updates the color of the background
 **/
@Composable
fun NotificationSettingsContent(modifier: Modifier, viewModel: HomeViewModel, darkMode: Boolean) {
    val context = LocalContext.current
    val homeState by viewModel.homeState.collectAsState()

    // Notification Details
    Surface (
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(),
        color = if(darkMode) ThemeColors.Night.background else ThemeColors.Day.background
    ) {
        // Permissions and Dialogs
        PermissionsAndDialogs(viewModel, context)

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
                            .width(35.dp),
                    ) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Dismiss",
                            tint = Color.White
                        )
                    }
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

            // Box for instructions text
            Box(
                modifier = Modifier
                    .weight(0.3f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = "Tap which type of event you want" +
                            "\nnotifications for",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = Viga,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFB7B7B7),
                        textAlign = TextAlign.Center,
                    ),
                    color = if (darkMode) Color(0xFFB7B7B7) else Color(0xFF011F35),
                )
            }

            // Box the options for notifications
            Box(
                modifier = Modifier
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row{
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            if (homeState.notificationSettings.gbmNotification) {
                                Image(
                                    painter = painterResource(id = R.drawable.gbmselected),
                                    contentDescription = "GBM Notifications ON",
                                    modifier = Modifier
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
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.gbmdefault),
                                    contentDescription = "GBM Notifications ON",
                                    modifier = Modifier
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
                                style = getTextStyle(darkMode),
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            if (homeState.notificationSettings.infoSessionNotification) {
                                Image(
                                    painter = painterResource(id = R.drawable.infoselected),
                                    contentDescription = "Info Session Notifications ON",
                                    modifier = Modifier
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
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.infodefault),
                                    contentDescription = "Info Session Notifications OFF",
                                    modifier = Modifier
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
                                style =  getTextStyle(darkMode),
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            if (homeState.notificationSettings.workshopNotification) {
                                Image(
                                    painter = painterResource(id = R.drawable.workshopselected),
                                    contentDescription = "Info Session Notifications ON",
                                    modifier = Modifier
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
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.workshopdefault),
                                    contentDescription = "Workshop Notifications OFF",
                                    modifier = Modifier
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
                                style =  getTextStyle(darkMode),
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                    }
                    Row {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            if (homeState.notificationSettings.volunteeringNotification) {
                                Image(
                                    painter = painterResource(id = R.drawable.volunteerselected),
                                    contentDescription = "Volunteer Notifications ON",
                                    modifier = Modifier
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
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.volunteerdefault),
                                    contentDescription = "Volunteer Notifications OFF",
                                    modifier = Modifier
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
                                style =  getTextStyle(darkMode),
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            if (homeState.notificationSettings.socialNotification) {
                                Image(
                                    painter = painterResource(id = R.drawable.socialselected),
                                    contentDescription = "Social Notifications ON",
                                    modifier = Modifier
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
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.socialdefault),
                                    contentDescription = "Social Notifications OFF",
                                    modifier = Modifier
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
                                style =  getTextStyle(darkMode),
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                    }
                }
            }

            // This is now the button for all notifications
            Box(
                modifier = Modifier
                    .weight(0.2f),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier
                        .width(254.dp)
                        .height(41.dp),
                    onClick = { viewModel.toggleAllNotifications(context) },
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

/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **
 * @author Josue Vicente & Resources
 * @date 2025
 *
 * @description This allows for easier formatting of text used in the notification settings content
 *
 * @param darkMode updates the color of the text based on dark mode for the notification settings
 ** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
@Composable
fun getTextStyle(darkMode: Boolean): TextStyle {
    return TextStyle(
        fontSize = 16.sp,
        fontFamily = Universltstd,
        fontWeight = FontWeight(400),
        color = if (darkMode) ThemeColors.Night.text else ThemeColors.Day.text,
        textAlign = TextAlign.Center,
        )
}

/**
 * @description PermissionsAndDialogs is used to request permissions for the notification settings
 *
 * @author Josue Vicente & Resources
 * @date 2024
 *
 * @param viewModel used to maintain the state of the dialog queue
 * @param context used to be able to open up the app settings based on the application information
 **/
@Composable
fun PermissionsAndDialogs(viewModel: HomeViewModel, context: Context) {
    val dialogQueue = viewModel.visiblePermissionDialogQueue

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissions.forEach { (permission, isGranted) ->
                if (isGranted) {
                    Log.d("HomeViewModel", "Permission Granted: $permission")
                    if (dialogQueue.contains(permission)) {
                        viewModel.dismissDialog()
                    }
                }
                else {
                    viewModel.dismissDialog()
                    viewModel.onPermissionResult(permission, isGranted)
                    Log.d("HomeViewModel", "Permission Denied: $permission")
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            multiplePermissionResultLauncher.launch(permissionsToRequest)
        }
    }

    val activity = context as Activity
    dialogQueue
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.POST_NOTIFICATIONS -> NotificationsPermissionProvider()
                    Manifest.permission.USE_EXACT_ALARM -> NotificationsPermissionProvider()
                    else -> return@forEach
                },
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(activity, permission),
                onDismiss = { viewModel.dismissDialog() },
                onOkayClick = {
                    viewModel.dismissDialog()
                    Log.d("HomeViewModel", "Restarting to ask again: $permission")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        multiplePermissionResultLauncher.launch(permissionsToRequest)
                    }
                },
                onGoToAppSettingsClick = {
                    viewModel.dismissDialog()
                    openAppSettings(context)
                }
            )
        }
}

/**
 * @description Permission Dialog is used to let users open app settings and remind them we need
 * notification permissions
 *
 * @author Josue Vicente & Resources
 * @date 2024
 *
 * @param viewModel used to maintain the state of the dialog queue
 * @param context used to be able to open up the app settings based on the application information
 **/
@Composable
fun PermissionDialog (
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkayClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Permission Required")
        },
        text = {
            Column {
                Text(
                    text = permissionTextProvider.getDesiredPermissionText(isPermanentlyDeclined,
                        Manifest.permission.CAMERA),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        },
        confirmButton = {
            if (!isPermanentlyDeclined) {
                Button(onClick = onOkayClick) {
                    Text("Okay")
                }
            }
            else {
                Button(onClick = onGoToAppSettingsClick) {
                    Text("Go to App Settings")
                }
            }
        },
        dismissButton = {
            if (!isPermanentlyDeclined) {
                Button(onClick = onDismiss) {
                    Text("Dismiss")
                }
            }
        },
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private val permissionsToRequest = arrayOf(
    Manifest.permission.USE_EXACT_ALARM,
    Manifest.permission.POST_NOTIFICATIONS,
    Manifest.permission.WAKE_LOCK
)

interface PermissionTextProvider{
    fun getDesiredPermissionText(isPermanentlyDeclined: Boolean, permission: String): String
}

class NotificationsPermissionProvider : PermissionTextProvider {
    override fun getDesiredPermissionText(isPermanentlyDeclined: Boolean, permission: String): String {
        return if (isPermanentlyDeclined) {
            "You have permanently denied notifications permission. Please go to app settings and enable it."
        } else {
            "This app requires the notifications permission to function properly."
        }
    }
}

@Preview (showBackground = true)
@Composable
fun NotificationSettingsPreview() {
    NotificationSettingsContent(
        modifier = Modifier,
        viewModel = HomeViewModel(
            notificationRepo = NotificationRepository(
                context = LocalContext.current
            ),
            eventRepo = EventRepository(
                context = LocalContext.current
            ),
        ),
        darkMode = true
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

/**
 * @description EventCard is used to display event details on the main calendar
 *
 * @author Josue Vicente & Resources
 * @date 2024
 *
 * @param modifier used to maintain the state of the dialog queue
 * @param event is the event we obtain the information to display
 * @param viewModel used to compare what the current event type is to determine color and icon. It
 * helps update the home screen state when to update the current event selected
 **/
@Composable
fun EventCard(modifier: Modifier, event: HomeViewModel.Event, viewModel: HomeViewModel = viewModel()) {
    // Have to have a mutable state of for recomposition, otherwise when the event started, there would
    // be no highlight unless changing page or updating the viewModel
    val currentTime = remember { mutableStateOf(ZonedDateTime.now(ZoneId.of("America/New_York"))) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime.value = ZonedDateTime.now(ZoneId.of("America/New_York"))
        }
    }

    val eventStartTime = event.start.dateTime?.let { ZonedDateTime.parse(it) }
        ?: event.start.date?.let { LocalDate.parse(it).atStartOfDay(ZoneId.of("America/New_York")) }
    val eventEndTime = event.end.dateTime?.let { ZonedDateTime.parse(it) }
        ?: event.end.date?.let { LocalDate.parse(it).atStartOfDay(ZoneId.of("America/New_York")) }

    // checking to see if within the right window, rn 15 minutes
    val isOngoing = currentTime.value.isAfter(eventStartTime!!.minusMinutes(15)) &&
            (eventEndTime != null && currentTime.value.isBefore(eventEndTime))

    // starting animation and a new thread to run it.
    val animatedBorderWidth = remember { Animatable(0f) }
    LaunchedEffect(isOngoing) {
        if (isOngoing) {
            while (true) {
                animatedBorderWidth.animateTo(4f, animationSpec = tween(durationMillis = 1000, easing = LinearEasing))
                animatedBorderWidth.animateTo(0f, animationSpec = tween(durationMillis = 1000, easing = LinearEasing))
            }
        }
    }

    // a borderw width of -1 used as with 0 it would still be faintly around the event
    Card(
        modifier = Modifier
            .fillMaxWidth()

            .padding(start = 10.dp, top = 5.dp, end = 5.dp, bottom = 5.dp)
            .clickable { viewModel.selectEvent(event) }
            .border(
                width = if (isOngoing) animatedBorderWidth.value.dp else (-1).dp,
                brush = SolidColor(Color(0xFFFD9837)),
                shape = RoundedCornerShape(size = 25.dp)
            ),
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
                        .weight(0.90f)
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
                    modifier = Modifier
                        .size(20.dp)
                        .weight(0.1f),
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

/**
 * @description EventCardFeed displays DayContainer cards in a LazyColumn for the calendar page
 *
 * @author Josue Vicente & Resources
 * @date 2024
 *
 * @param modifier used to maintain the state of the dialog queue
 * @param viewModel is used to get all of the events being stored
 * @param isDarkMode Used to determine color of the background when in dark mode
 **/
@Composable
fun EventCardFeed(modifier: Modifier, viewModel: HomeViewModel, isDarkMode : Boolean) {
    val state by viewModel.homeState.collectAsState()
    val events = state.events
    val listState = rememberLazyListState()
    val today = LocalDate.now()
    val isRefreshing = state.isRefreshing
    val scope = rememberCoroutineScope()

    // Updated to handle events spanning multiple days
    val groupedEvents = remember(events) {
        mutableMapOf<LocalDate, MutableList<HomeViewModel.Event>>().apply {
            events.forEach { event ->
                var startDate = LocalDate.now();
                var endDate = LocalDate.now();

                // not all have date time, some have date
                if (event.start.dateTime == null) {
                    startDate = LocalDate.parse(event.start.date)
                    endDate = LocalDate.parse(event.end.date)
                }
                else {
                    startDate = LocalDate.parse(event.start.dateTime.substring(0, 10))
                    endDate = LocalDate.parse(event.end.dateTime?.substring(0, 10))
                }
                var currentDate = startDate
                while (!currentDate.isAfter(endDate)) {
                    // Only consider dates from today onwards
                    if (!currentDate.isBefore(today)) {
                        this.getOrPut(currentDate) { mutableListOf() }.add(event)
                    }
                    currentDate = currentDate.plusDays(1)
                }
            }
        }.toSortedMap()
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(if(isDarkMode) blueDarkModeBackground else WhiteSHPE)
            .padding(top = 83.dp)
    ) {
        PullToRefreshLazyColumn(
            items = groupedEvents.keys.toList(),
            content = { date ->
                DayContainer(modifier = Modifier, date = date, events = groupedEvents[date]!!, viewModel = viewModel,
                    isDarkMode = isDarkMode)

                Spacer(modifier = Modifier.height(10.dp))

                if (date != groupedEvents.keys.last()) {
                    Row {
                        Spacer(modifier = Modifier.fillMaxWidth(0.15f))

                        Image(
                            painter = painterResource(id =
                            if(isDarkMode) R.drawable.dashed_line_spacer_white
                            else R.drawable.dashed_line_spacer_black),
                            contentDescription = "Dotted Line",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Transparent)
                        )
                    }
                }
            },
            isRefreshing = isRefreshing,
            onRefresh = {
                scope.launch {
                    viewModel.pullToRefresh()
                }
            },
            state = listState
        )

        LaunchedEffect(listState, groupedEvents, state) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo }
                .collect { visibleItems ->
                    if (visibleItems.isNotEmpty()) {
                        val firstVisibleIndex = visibleItems.first().index
                        val lastVisibleIndex = visibleItems.last().index

                        val firstVisibleDate = groupedEvents.keys.toList()[firstVisibleIndex]
                        val lastVisibleDate = groupedEvents.keys.toList()[lastVisibleIndex]

                        if (firstVisibleDate != null && lastVisibleDate != null) {
                            if (firstVisibleDate.month != lastVisibleDate.month) {
                                viewModel.updateMonthName(lastVisibleDate.month.name)
                            }
                        }
                    }
                }
        }
    }
}

/**
 * @description PullToRefreshLazyColumn displays events in a LazyColumn with a pull to refresh feature
 *
 * @author Josue Vicente & Resources
 * @date 2024
 *
 * @param modifier used to maintain the state of the dialog queue
 * @param content The things that are placed inside the lazy column
 * @param isRefreshing used for state on whether or not the screen is refreshing
 * @param onRefresh a function passed in that will do something when the user refreshes
 * @param state the current state of the lazy column, essentially at what spot are the current items at
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PullToRefreshLazyColumn(
    items: List<T>,
    content: @Composable (T) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    val pullToRefreshState = rememberPullToRefreshState()
    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        LazyColumn(
            state = state,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) {
                content(it)
            }
        }

        if(pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                Log.d("PullToRefresh", "Refreshing")
                onRefresh()
            }
        }

        LaunchedEffect(isRefreshing) {
            if(isRefreshing) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter),
            containerColor = if(isSystemInDarkTheme()) ThemeColors.Night.background else ThemeColors.Day.background,
        )
    }
}

/**
 * @description DayContainer uses events passed in to create a list of event cards for that date passed in
 *
 * @author Josue Vicente & Resources
 * @date 2024
 *
 * @param modifier used to maintain the state of the dialog queue
 * @param date The date events are displayed for
 * @param events a list of events that occur at the date passed in
 * @param viewModel used to compare what the current event type is to determine color and icon. It
 * helps update the home screen state when to update the current event selected
 * @param isDarkMode changes the color of the DayContainers based on dark mode
 **/
@Composable
fun DayContainer(
    modifier: Modifier,
    date: LocalDate,
    events: List<HomeViewModel.Event>,
    viewModel: HomeViewModel,
    isDarkMode: Boolean
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
                    color = if(isDarkMode) TextColor else Color.Black,
                    textAlign = TextAlign.Center,
                ),
            )
            Text(
                text = date.dayOfMonth.toString(),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = Universltstd,
                    fontWeight = FontWeight(400),
                    color = if(isDarkMode) Color.White else Color.Black,
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
                EventCard(modifier = Modifier, event, viewModel = viewModel)
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
                modifier = Modifier,
                date = LocalDate.now(),
                events = sampleCardItems,
                viewModel = HomeViewModel(
                    notificationRepo = NotificationRepository(
                        context = LocalContext.current
                    ),
                    eventRepo = EventRepository(
                        context = LocalContext.current
                    ),
                ),
                isDarkMode = true
            )
        }
    }
}

@Preview (showBackground = true)
@Composable
fun EventCardFeedPreview() {
    SHPEUFMobileKotlinTheme {
        EventCardFeed(
            modifier = Modifier,
            viewModel = HomeViewModel(
                notificationRepo = NotificationRepository(
                    context = LocalContext.current
                ),
                eventRepo = EventRepository(
                    context = LocalContext.current
                ),
            ),
            isDarkMode = true
        )
    }
}

/**
 * @description Helper function to format time for display of events
 *
 * @author Josue Vicente & Resources
 * @date 2024
 *
 * @param event used to get the time event occurs to return a formatted time
 **/
fun formatEventTime(event: HomeViewModel.Event): String {
    // Future Update Here: check system to see if in 24 hour time
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

/**
 * @description formatDate is a helper function to format the date an event occurs for display on calendar
 *
 * @author Josue Vicente & Resources
 * @date 2024
 *
 * @param eventDateTime holds the date and time information for the date being formatted
 **/
fun formatDate(eventDateTime: HomeViewModel.EventDateTime): String {
    val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
    val zoneId = ZoneId.systemDefault()

    return try {
        val zonedDateTime = ZonedDateTime.parse(eventDateTime.dateTime, inputFormatter).withZoneSameInstant(zoneId)
        val dayOfMonth = zonedDateTime.dayOfMonth
        val dayOfMonthWithOrdinal = "$dayOfMonth${getOrdinalIndicator(dayOfMonth)}"
        zonedDateTime.format(outputFormatter).replaceFirst(Regex("\\d+"), dayOfMonthWithOrdinal)
    } catch (e: Exception) {
        // if all day, say all day, if no date time, it basically means all day.
        "All Day"
    }
}

/**
 * @description getOrdinalIndicator is a helper function that gets whether the date should be 1st, 2nd, 3rd, 4th, etc.
 *
 * @author Josue Vicente & Resources
 * @date 2024
 *
 * @param dayOfMonth is an integer that represents the day of the month to get formatting for
 **/
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
            modifier = Modifier,
            sampleCardItems[0],
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

/**
 * @description HomeScreen displays the event calendar and allows users to change notifications settings
 * as well as being able to open up event details to explore more about the event.
 *
 * @author Josue Vicente & Resources
 * @date 2024
 *
 * @param viewModel a HomeViewModel used to pass events and state of feeds and windows to see if they are displayed
 * @param shpeufAppViewModel used to obtain the state of dark mode from the app settings
 **/
@Composable
fun HomeScreen(viewModel: HomeViewModel, shpeufAppViewModel: SHPEUFAppViewModel) {
    val userState by shpeufAppViewModel.uiState.collectAsState()
    val isDarkMode = userState.isDarkMode

    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = if(isDarkMode) Color.Black else Color.White
    ) {
        Box {
            EventCardFeed(modifier = Modifier, viewModel = viewModel, isDarkMode = isDarkMode)
            TopHeader(modifier = Modifier, viewModel = viewModel)
        }

        SlidingEventWindow(modifier = Modifier, viewModel = viewModel, isDarkMode = isDarkMode)
        SlidingNotificationWindow(modifier = Modifier, viewModel = viewModel, darkMode = isDarkMode)
    }
}