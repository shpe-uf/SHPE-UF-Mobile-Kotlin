package com.example.shpe_uf_mobile_kotlin.ui.pages.points

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.apolloClient
import com.example.shpe_uf_mobile_kotlin.EventsQuery
import com.example.shpe_uf_mobile_kotlin.ExampleQuery
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.shpe_uf_mobile_kotlin.EventsQuery.Event
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.example.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import com.example.shpe_uf_mobile_kotlin.ui.theme.dark_bg
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


/*
******************************************************
FUNCTION: PointsView()
* Displays the main points view, including the top bar,
* points percentile, and points calendar. Interacts with
* SHPEUFAppViewModel to get user-specific data.
******************************************************
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PointsView(shpeufAppViewModel: SHPEUFAppViewModel) {

    val pointsPageViewModel = remember { PointsPageViewModel() }

    val UserState by shpeufAppViewModel.uiState.collectAsState()

    val id = UserState.id

    val UsernameState by shpeufAppViewModel.userState.collectAsState()

    val username = UsernameState.username

    SHPEUFMobileKotlinTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopSection()

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item {
                    PointsPercentile(pointsPageViewModel, id, username)
                }
                item {
                    PointsCalendar(id)
                }
            }
        }
    }
}

/*
******************************************************
FUNCTION: TopSection()
* Design for the top bar of the points page, featuring
* a title and the SHPE logo.
******************************************************
 */
@Composable
fun TopSection() {
    Column(
        modifier = Modifier
            .background(color = Color(0xFFD25917))
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .width(500.dp)
                .height(90.dp)
        ) {
            Row(
            ) {
                Text(
                    text = "Points Program",
                    style = TextStyle(
                        fontSize = 35.sp,
                        fontFamily = FontFamily(Font(R.font.viga)),
                        fontWeight = FontWeight.Bold,
                        color = Color.White

                    ),
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .padding(bottom = 10.dp)

                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            Row {
                Image(
                    painter = painterResource(id = R.drawable.shpe_logo_full_color),
                    contentDescription = "SHPE LOGO",
                    modifier = Modifier
                        .size(98.dp)
                )
            }
        }
    }
}

/*
******************************************************
FUNCTION: PointsPercentile()
* Displays the user's points percentile as an animated pie
* chart and for the corresponding semester that is underway.
* Provides the "Redeem Code" button that connects to the
* RedeemPoints() function. Displays total points accumulated
* and point statistics for each semester.
******************************************************
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointsPercentile(pointsPageViewModel: PointsPageViewModel, id: String, username: String) {
    var datas by remember { mutableStateOf(emptyList<ExampleQuery.GetUser>()) }



    LaunchedEffect(Unit) {
        val response = apolloClient.query(ExampleQuery(id)).execute()
        response.data?.getUser?.let { user ->
            datas += user
        } ?: run {
            datas = emptyList()
        }
    }

    var openBottomSheet by remember { mutableStateOf(false) }
    val semester = getSemester()
    val percentile: Int

    if (semester == "FALL")
        percentile = datas.sumOf { it.fallPercentile }
    else if (semester == "SPRING")
        percentile = datas.sumOf { it.springPercentile }
    else
        percentile = datas.sumOf { it.summerPercentile }

    val animatedDegree by animateFloatAsState(
        targetValue = percentile.toFloat() / 100,
        animationSpec = tween(durationMillis = 1500), label = ""
    )

    val background = if (isSystemInDarkTheme()) {
        ThemeColors.Night.background
    } else {
        ThemeColors.Day.background
    }
    val textColor = if(isSystemInDarkTheme()){
        Color.White

    } else {
        Color.Black
    }

    val sweepAngle = 360 * animatedDegree

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = background)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(1500.dp)
                .height(750.dp)
                .background(color = background)
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Box(
                modifier = Modifier
                    .size(290.dp)
                    .padding(16.dp)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val thickness = size.minDimension * 0.28f

                    drawPieChartSegment(
                        startAngle = -90f,
                        sweepAngle = sweepAngle,
                        color = Color(0xFF0A2059),
                        thickness = thickness
                    )
                    drawPieChartSegment(
                        startAngle = -90f + sweepAngle,
                        sweepAngle = 360 - sweepAngle,
                        color = Color(0xFFC5CAE9),
                        thickness = thickness
                    )
                    drawCenterCircle(color = background, thickness = thickness)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$semester:",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Text(
                        text = getOrdinal(percentile),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Text(
                        text = "Percentile",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(52.dp))
            val buttonColor = if(isSystemInDarkTheme()){
                OrangeSHPE

            } else {
                dark_bg
            }
            Button(
                onClick = { openBottomSheet = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = Color(0xFFFFFFFF)
                ),
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier
                    .width(280.dp)
                    .height(50.dp)
            )
            {
                Text(
                    text = "Redeem Code",
                    style = TextStyle(
                        fontSize = 20.61.sp,
                        fontFamily = FontFamily(Font(R.font.universltstd)),
                        color = Color(0xFFFFFFFF),
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                )
            }
            if (openBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        openBottomSheet = false
                        pointsPageViewModel.updateGuestsCount(0)
                        pointsPageViewModel.updateEventCode("")
                    },
                    dragHandle = {
                        Modifier.background(Color.Blue)
                    },
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        RedeemPoints(pointsPageViewModel, username) {
                            openBottomSheet = false
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(44.dp))

            Text(
                text = "Total Points: ${datas.sumOf { it.points }}",
                style = TextStyle(
                    fontSize = 20.61.sp,
                    fontFamily = FontFamily(Font(R.font.universltstd_bold)),
                    fontWeight = FontWeight(700),
                    color = textColor,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(320.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                PercentIndicator(
                    label = "FALL",
                    percent = "TOP ${datas.sumOf { it.fallPercentile }}%",
                    number = datas.sumOf { it.fallPoints },
                    modifier = Modifier.fillMaxWidth(),
                    firstGradient = Color(0xFF0A2059),
                    secondGradient = Color(0xFF2E619E),
                    dividerColor = Color(0xFF0A2059)

                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(320.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                PercentIndicator(
                    label = "SPRING",
                    percent = "TOP ${datas.sumOf { it.springPercentile }}%",
                    number = datas.sumOf { it.springPoints },
                    modifier = Modifier.fillMaxWidth(),
                    firstGradient = Color(0xFF981F14),
                    secondGradient = Color(0xFFDE5026),
                    dividerColor = Color(0xFF981F14)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(320.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                PercentIndicator(
                    label = "SUMMER",
                    percent = "TOP ${datas.sumOf { it.summerPercentile }}%",
                    number = datas.sumOf { it.summerPoints },
                    modifier = Modifier.fillMaxWidth(),
                    firstGradient = Color(0xFF0B70BA),
                    secondGradient = Color(0xFF84CBFF),
                    dividerColor = Color(0xFF0B70BA)
                )
            }
        }
    }
}

/*
******************************************************
FUNCTION: RedeemPoints()
* Design of slide up screen that is called when "Redeem
* Code" is pressed. Functionality includes a textbox
* for event code, adding up to 5 guests with plus or minus
* button, and a "Redeem" button to submit the request that
* connects to a GraphQL mutation that provides proper error
* statements to the textbox if necessary.
******************************************************
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedeemPoints(
    pointsPageViewModel: PointsPageViewModel,
    username: String,
    onCloseBottomSheet: () -> Unit
) {
    val uiState by pointsPageViewModel.uiState.collectAsState()
    var guests = uiState.guestsCount
    var text = uiState.eventCode
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Color(0xFF004C73))
            .fillMaxSize()

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(1500.dp)
                .height(750.dp)
                .background(
                    color = Color(0xFFD25917), shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(4.dp)
                    .background(color = Color(0xFFFFFFFF))

            ) {
            }
            Spacer(modifier = Modifier.height(50.dp))

            Row(
            ) {
                Text(
                    text = "REDEEM POINTS",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontFamily = FontFamily(Font(R.font.universltstd)),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                    pointsPageViewModel.updateEventCode(newText)
                },
                placeholder = {
                    Text(
                        "Event Code",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.universltstd)),
                            fontWeight = FontWeight(300),
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF011F35),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                singleLine = true,
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                modifier = Modifier
                    .width(340.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFFFFF),
                    unfocusedContainerColor = Color(0xFFFFFFFF)
                )
            )
            if (errorMessage == "null") {
                onCloseBottomSheet()
            } else {
                errorMessage?.let {
                    Text(
                        text = it,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Guests",
                style = TextStyle(
                    fontSize = 25.sp,
                    color = Color(0xFFEDEDED),
                    textAlign = TextAlign.Center,
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(210.dp)
                    .height(80.dp)
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp))
            ) {
                Button(
                    onClick = {
                        val newCount = uiState.guestsCount - 1
                        if (uiState.guestsCount > 0) pointsPageViewModel.updateGuestsCount(newCount)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFFFFF),
                        contentColor = Color(0xFFFFFFFF)
                    ),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(70.dp)
                        .height(100.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(45.dp)
                            .height(8.dp)
                            .background(
                                color = Color(0xFF72AAC0),
                                shape = RoundedCornerShape(size = 1.dp)
                            )
                    )
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(70.dp)
                        .background(
                            color = Color(0xFF72AAC0),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                )
                Text(
                    text = guests.toString(),
                    style = TextStyle(
                        fontSize = 35.sp,
                        color = Color(0xB5474545),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .padding(start = 25.dp)
                )
                Spacer(modifier = Modifier.width(25.dp))
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(70.dp)
                        .background(
                            color = Color(0xFF72AAC0),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                )
                Button(
                    onClick = {
                        val newCount = uiState.guestsCount + 1
                        if (uiState.guestsCount < 5) pointsPageViewModel.updateGuestsCount(newCount)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFFFFF),
                        contentColor = Color(0xFFFFFFFF)
                    ),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = "Plus",
                        modifier = Modifier
                            .size(42.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        errorMessage = pointsPageViewModel.redeemEvent(username).toString()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF011F35),
                    contentColor = Color(0xFFFFFFFF)
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(290.dp)
                    .height(60.dp)
            )
            {
                Text(
                    text = "Redeem",
                    style = TextStyle(
                        fontSize = 25.sp,
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                )
            }

        }
    }
}


/*
******************************************************
FUNCTION: PointsCalendar()
* Displays a categorized calendar view of events based on
* user ID and the events they have been to. The calendar
* includes 7 different event categories such as social,
* volunteering, workshops, etc. If the user has not gone
* to a specific type of event, such as a social, then
* that tile will not be displayed, condoning the calendar
* to only types that have been attended at least once.
******************************************************
 */
@Composable
fun PointsCalendar(id: String) {
    var cabinetMeeting by remember { mutableStateOf<List<Event>>(emptyList()) }
    var misc by remember { mutableStateOf<List<Event>>(emptyList()) }
    var social by remember { mutableStateOf<List<Event>>(emptyList()) }
    var gbm by remember { mutableStateOf<List<Event>>(emptyList()) }
    var volunteering by remember { mutableStateOf<List<Event>>(emptyList()) }
    var corporateEvent by remember { mutableStateOf<List<Event>>(emptyList()) }
    var workshop by remember { mutableStateOf<List<Event>>(emptyList()) }


    val background = if (isSystemInDarkTheme()) {
        ThemeColors.Night.background
    } else {
        ThemeColors.Day.background
    }

    LaunchedEffect(Unit) {
        val response = apolloClient.query(EventsQuery(id)).execute()


        // Extract events from the response
        val events = response.data?.getUser?.events?.filterNotNull()?.map {
            it.copy(createdAt = formatDate(it.createdAt))
        } ?: emptyList()

        // Separate events into different lists based on their category
        cabinetMeeting = events.filter { it.category == "Cabinet Meeting" }
        misc = events.filter { it.category == "Miscellaneous" }
        social = events.filter { it.category == "Social" }
        gbm = events.filter { it.category == "General Body Meeting" }
        volunteering = events.filter { it.category == "Volunteering" }
        workshop = events.filter { it.category == "Workshop" }
        corporateEvent = events.filter { it.category == "Corporate Event" }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 40.dp)
                .background(background)
        )
        {
            Spacer(modifier = Modifier.height(30.dp))

            if (gbm.isNotEmpty()) {
                Text(
                    text = "GBM",
                    style = TextStyle(
                        fontSize = 50.6.sp,
                        fontFamily = FontFamily(Font(R.font.universltstd)),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD25917)
                    ),
                )
                Box(
                    modifier = Modifier
                        .width(310.dp)
                        .height(70.dp)
                        .border(
                            width = 0.36665.dp,
                            color = Color(0xFF011F35),
                            shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .background(
                            color = Color(0xFFD9D9D9), shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "EVENT",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                        Spacer(modifier = Modifier.width(22.dp))
                        Text(
                            text = "DATE",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )
                        )
                        Spacer(modifier = Modifier.width(20.dp))

                        Text(
                            text = "POINTS",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                    }

                }

                EventTable(events = gbm)

                Spacer(modifier = Modifier.height(20.dp))
            }

            if (cabinetMeeting.isNotEmpty()) {
                Text(
                    text = "CABINET " +
                            "MEETING",
                    style = TextStyle(
                        fontSize = 50.6.sp,
                        fontFamily = FontFamily(Font(R.font.universltstd)),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD25917)
                    ),
                )
                Box(
                    modifier = Modifier
                        .width(310.dp)
                        .height(70.dp)
                        .border(
                            width = 0.36665.dp,
                            color = Color(0xFF011F35),
                            shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .background(
                            color = Color(0xFFD9D9D9), shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "EVENT",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                        Spacer(modifier = Modifier.width(22.dp))
                        Text(
                            text = "DATE",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )
                        )
                        Spacer(modifier = Modifier.width(20.dp))

                        Text(
                            text = "POINTS",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                    }


                }

                EventTable(events = cabinetMeeting)

                Spacer(modifier = Modifier.height(20.dp))
            }


            if(social.isNotEmpty()){
                Text(
                    text = "SOCIAL",
                    style = TextStyle(
                        fontSize = 50.6.sp,
                        fontFamily = FontFamily(Font(R.font.universltstd)),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD25917)
                    ),
                )
                Box(
                    modifier = Modifier
                        .width(310.dp)
                        .height(70.dp)
                        .border(
                            width = 0.36665.dp,
                            color = Color(0xFF011F35),
                            shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            )
                        )
                        .background(
                            color = Color(0xFFD9D9D9), shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "EVENT",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                        Spacer(modifier = Modifier.width(22.dp))
                        Text(
                            text = "DATE",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )
                        )
                        Spacer(modifier = Modifier.width(20.dp))

                        Text(
                            text = "POINTS",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                    }
                }

                EventTable(events = social)

                Spacer(modifier = Modifier.height(20.dp))
            }

            if(misc.isNotEmpty()){
                Text(
                    text = "MISC",
                    style = TextStyle(
                        fontSize = 50.6.sp,
                        fontFamily = FontFamily(Font(R.font.universltstd)),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD25917)
                    ),
                )
                Box(
                    modifier = Modifier
                        .width(310.dp)
                        .height(70.dp)
                        .border(
                            width = 0.36665.dp,
                            color = Color(0xFF011F35),
                            shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .background(
                            color = Color(0xFFD9D9D9), shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "EVENT",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                        Spacer(modifier = Modifier.width(22.dp))
                        Text(
                            text = "DATE",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )
                        )
                        Spacer(modifier = Modifier.width(20.dp))

                        Text(
                            text = "POINTS",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                    }
                }
                EventTable(events = misc)
            }
            Spacer(modifier = Modifier.height(30.dp))

            if (workshop.isNotEmpty()) {
                Text(
                    text = "WORKSHOP",
                    style = TextStyle(
                        fontSize = 50.6.sp,
                        fontFamily = FontFamily(Font(R.font.universltstd)),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD25917)
                    ),
                )
                Box(
                    modifier = Modifier
                        .width(310.dp)
                        .height(70.dp)
                        .border(
                            width = 0.36665.dp,
                            color = Color(0xFF011F35),
                            shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .background(
                            color = Color(0xFFD9D9D9), shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "EVENT",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                        Spacer(modifier = Modifier.width(22.dp))
                        Text(
                            text = "DATE",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )
                        )
                        Spacer(modifier = Modifier.width(20.dp))

                        Text(
                            text = "POINTS",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                    }

                }

                EventTable(events = workshop)

                Spacer(modifier = Modifier.height(20.dp))
            }
            Spacer(modifier = Modifier.height(30.dp))

            if (volunteering.isNotEmpty()) {
                Text(
                    text = "VOLUNTEERING",
                    style = TextStyle(
                        fontSize = 50.6.sp,
                        fontFamily = FontFamily(Font(R.font.universltstd)),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD25917)
                    ),
                )
                Box(
                    modifier = Modifier
                        .width(310.dp)
                        .height(70.dp)
                        .border(
                            width = 0.36665.dp,
                            color = Color(0xFF011F35),
                            shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .background(
                            color = Color(0xFFD9D9D9), shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "EVENT",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                        Spacer(modifier = Modifier.width(22.dp))
                        Text(
                            text = "DATE",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )
                        )
                        Spacer(modifier = Modifier.width(20.dp))

                        Text(
                            text = "POINTS",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                    }

                }

                EventTable(events = volunteering)

                Spacer(modifier = Modifier.height(20.dp))
            }
            Spacer(modifier = Modifier.height(30.dp))

            if (corporateEvent.isNotEmpty()) {
                Text(
                    text = "CORPORATE EVENT",
                    style = TextStyle(
                        fontSize = 50.6.sp,
                        fontFamily = FontFamily(Font(R.font.universltstd)),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD25917)
                    ),
                )
                Box(
                    modifier = Modifier
                        .width(310.dp)
                        .height(70.dp)
                        .border(
                            width = 0.36665.dp,
                            color = Color(0xFF011F35),
                            shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .background(
                            color = Color(0xFFD9D9D9), shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "EVENT",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                        Spacer(modifier = Modifier.width(22.dp))
                        Text(
                            text = "DATE",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )
                        )
                        Spacer(modifier = Modifier.width(20.dp))

                        Text(
                            text = "POINTS",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF002E50),
                            )

                        )
                    }

                }

                EventTable(events = corporateEvent)

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

/*
******************************************************
FUNCTION: PercentIndicator()
* Displays a percentage indicator bar with a semester label,
* point percentile, and total points accumulated, alongside
* color gradient based on semester.
******************************************************
 */
@Composable
fun PercentIndicator(
    label: String,
    percent: String,
    number: Int,
    modifier: Modifier = Modifier,
    firstGradient: Color = Color(0xFF3F51B5),
    secondGradient: Color = Color(0xFF3F51B5),
    dividerColor: Color,

    ) {
    Row(
        modifier = modifier
            .background(
                Brush.linearGradient(
                    .05f to firstGradient,
                    1f to secondGradient,
                    start = Offset(0.0f, 0.0f),
                    end = Offset(0.0f, 135.0f)
                )
            ),

        verticalAlignment = Alignment.CenterVertically
    ) {
        // Label
        Text(
            text = label,
            fontWeight = FontWeight(700),
            style = TextStyle(
                fontSize = 18.73.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily(Font(R.font.universltstd_bold)),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .weight(1.4f)
        )

        // Divider
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(dividerColor)
                .padding(horizontal = 8.dp)
        )

        // Percent
        Text(
            text = percent,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = TextStyle(
                fontSize = 20.61.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily(Font(R.font.universltstd_bold)),
                color = Color(0xFF004C73),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .weight(1.8f)
        )

        // Divider
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(dividerColor)
                .padding(horizontal = 8.dp)
        )

        // Number
        Text(
            text = number.toString(),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = TextStyle(
                fontSize = 20.61.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily(Font(R.font.universltstd_bold)),
                color = Color(0xFF004C73),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

/*
******************************************************
FUNCTION: EventTable()
* Displays a list of passed in events with alternating row
* colors. Uses EventRow() to display each event.
******************************************************
 */
@Composable
fun EventTable(events: List<Event>) {
    if (events.isNotEmpty()) {
        Column {
            events.forEachIndexed { index, event ->
                val backgroundColor = if (index % 2 == 0) Color.White else Color.LightGray
                EventRow(event, backgroundColor)
            }
        }
    }
}

/*
******************************************************
FUNCTION: EventRow()
* Displays a row containing details of a single event.
* Includes event name, date, and points, with the specified
* background color.
******************************************************
 */
@Composable
fun EventRow(event: Event, backgroundColor: Color) {
    Row(
        modifier = Modifier
            .width(310.dp)
//            .height(80.dp)
            .background(color = backgroundColor)
            .border(
                width = 0.36665.dp,
                color = Color(0xFF011F35),
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = event.name,
            style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF002E50),

                ),
            modifier = Modifier
                .weight(0.8f)
                .fillMaxSize()
                .wrapContentWidth(Alignment.Start)
//            maxLines = 2,
//            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = event.createdAt,
            style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF002E50),

                ),
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.CenterHorizontally),
            maxLines = 1
        )
        Text(
            text = event.points.toString(),
            style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF002E50),

                ),
            modifier = Modifier
                .weight(0.5f)
                .wrapContentWidth(Alignment.End),
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(50.dp))
    }
}


/*
******************************************************
FUNCTION: formatDate()
* Converts a date string in ISO format to "MM/dd/yyyy"
* format for display purposes.
******************************************************
 */
fun formatDate(createdAt: String): String {
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    val instant = Instant.parse(createdAt)
    val dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
    return dateTime.format(formatter)
}

/*
******************************************************
FUNCTION: getOrdinal()
* Converts an integer number into its ordinal string
* representation (e.g., 1 -> "1st", 2 -> "2nd").
******************************************************
 */
fun getOrdinal(number: Int): String {
    val suffixes = arrayOf("th", "st", "nd", "rd") + Array(16) { "th" }
    val centuryRemainder = number % 100
    val tenRemainder = number % 10
    return if (centuryRemainder in 11..13) {
        "${number}th"
    } else {
        "${number}${suffixes[tenRemainder]}"
    }
}

/*
******************************************************
FUNCTION: drawPieChartSegment()
* Draws a segment of a pie chart based on the start angle,
* sweep angle, and color, using a specified thickness.
******************************************************
 */
fun DrawScope.drawPieChartSegment(
    startAngle: Float,
    sweepAngle: Float,
    color: Color,
    thickness: Float
) {
    val stroke = Stroke(width = thickness)
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = stroke,
    )
}

/*
******************************************************
FUNCTION: drawCenterCircle()
* Draws a center circle to create a doughnut-like shape
* for the pie chart, providing space for inner content.
******************************************************
 */
fun DrawScope.drawCenterCircle(color: Color, thickness: Float) {
    val innerCircleRadius = (size.minDimension / 2f) - (thickness / 2f)
    drawCircle(
        color = color,
        radius = innerCircleRadius,
    )
}

/*
******************************************************
FUNCTION: getSemester()
* Determines the current semester (SPRING, SUMMER, or FALL)
* based on the current date.
******************************************************
 */
fun getSemester(): String {
    val currentDate = LocalDate.now()
    val currentMonth = currentDate.month

    return when (currentMonth) {
        Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL -> "SPRING"
        Month.MAY, Month.JUNE, Month.JULY -> "SUMMER"
        Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER -> "FALL"
    }
}

