package com.example.shpe_uf_mobile_kotlin.ui.pages.points

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import apolloClient
import com.example.shpe_uf_mobile_kotlin.ExampleQuery
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme

@Composable
fun TopSection(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .background(color = Color(0xFF004C73))
    ){
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .width(500.dp)
                .height(100.dp)

        ) {
            Row (
            ){
                Text(
                    text = "Points Program",
                    style = TextStyle(
                        fontSize = 35.sp,
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
                    contentDescription = "SHPE GOAT",
                    modifier = Modifier
                        .size(98.dp)
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedeemPoints(modifier: Modifier = Modifier) {
    var guests by remember { mutableStateOf(0) }
    val guestsStr: String = guests.toString()
    var text by remember { mutableStateOf("") }


    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Color(0xFFFFFFFF))
            .fillMaxSize()

    ){
        Column (
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(1500.dp)
                .height(750.dp)
                .background(color = Color(0xFF004C73), shape = RoundedCornerShape(size = 10.dp))
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

            Row (
            ){
                Text(
                    text = "REDEEM POINTS",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFF3E00),

                        textAlign = TextAlign.Center,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(

                value = text,
                onValueChange = { text = it },
                label = {
                    Text(
                        text = "Event Code ",
                        style = TextStyle(
                            fontSize = 25.sp,
                            color = Color(0xFF72AAC0),
                            textAlign = TextAlign.Center

                        ),
                        modifier = Modifier
//                                .padding(bottom = 10.dp)
//                                .fillMaxSize()
                    ) },
                maxLines = 1,

                textStyle = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(340.dp)
                    .height(50.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Guests",
                style = TextStyle(
                    fontSize = 25.sp,
                    color = Color(0xFF72AAC0),
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
            ){
                Button(onClick = {guests--},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFFFFF),
                        contentColor = Color(0xFFFFFFFF)
                    ),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(70.dp)
                        .height(100.dp)
                ){
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
                    text = guestsStr,
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
                Button(onClick = {if (guests < 5) guests += 1},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFFFFF),
                        contentColor = Color(0xFFFFFFFF)
                    ),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = "Plus",
                        modifier = Modifier
                            .size(42.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
            Button(onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD33C20),
                    contentColor = Color(0xFFFFFFFF)
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(340.dp)
                    .height(60.dp))
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

@Composable
fun PointsPercentile(modifier: Modifier = Modifier){
        var datas by remember { mutableStateOf(emptyList<ExampleQuery.GetUser>()) }
        LaunchedEffect(Unit) {
            val response = apolloClient.query(ExampleQuery("6130025242d0a000173d47fc")).execute()
            response.data?.getUser?.let { user ->
                datas += user
            } ?: run {
                datas = emptyList()
            }
        }

//        Column(
//            modifier = modifier.fillMaxSize().padding(start = 52.dp)
//        ) {
//            Text("Whatever you need: ")
//            datas.forEach { user -> Text(text = user.points.toString())}
//        }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Color(0xFF004C73))
            .fillMaxSize()
    ){
        TopSection()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(1500.dp)
                .height(750.dp)
                .background(color = Color(0xFFFFFFFF))
        ){
            Spacer(modifier = Modifier.height(60.dp))
            Box(
                modifier = Modifier
                    .size(290.dp)
                    .padding(16.dp)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val thickness = size.minDimension * 0.28f // Adjust the thickness here
                    val sweepAngle = 360 * 0.93f // Assuming 93% for the example
                    drawPieChartSegment(startAngle = -90f, sweepAngle = sweepAngle, color = Color(0xFF0A2059), thickness = thickness)
                    drawPieChartSegment(startAngle = -90f + sweepAngle, sweepAngle = 360 - sweepAngle, color = Color(0xFFC5CAE9), thickness = thickness)
                    drawCenterCircle(color = Color.White, thickness = thickness)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "FALL:",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "93rd",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Percentile",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(52.dp))
            Button(onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF004C73),
                    contentColor = Color(0xFFFFFFFF)
                ),
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier
                    .width(280.dp)
                    .height(50.dp))
            {
                Text(
                    text = "Redeem Code",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.height(44.dp))

            Text(
                text = "Total Points: ${datas.forEach { user -> Text(text = user.points.toString())}}",
//                text = datas.forEach { user -> Text(text = user.points.toString())},
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color(0xFF004C73),
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
                    percent = "TOP 95%",
                    number = 12,
                    modifier = Modifier.fillMaxWidth(),
                    firstGradient = Color(0xFF0A2059),
                    secondGradient = Color(0xFF2E619E)
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
                    percent = "TOP 4%",
                    number = 12,
                    modifier = Modifier.fillMaxWidth(),
                    firstGradient = Color(0xFF981F14),
                    secondGradient = Color(0xFFDE5026)
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
                    percent = "TOP 2%",
                    number = 4,
                    modifier = Modifier.fillMaxWidth(),
                    firstGradient = Color(0xFF0B70BA),
                    secondGradient = Color(0xFF84CBFF)
                )
            }
        }

    }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
             modifier = Modifier
                 .fillMaxWidth()
        ) {
            Button(onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFFFFF),
                    contentColor = Color(0xFFFFFFFF)
                ),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Plus",
                    modifier = Modifier
                        .size(42.dp)
                )
            }
        }
    }
}

fun DrawScope.drawPieChartSegment(startAngle: Float, sweepAngle: Float, color: Color, thickness: Float) {
    val stroke = Stroke(width = thickness)
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = stroke
    )
}

fun DrawScope.drawCenterCircle(color: Color, thickness: Float) {
    val innerCircleRadius = (size.minDimension / 2f) - (thickness / 2f)
    drawCircle(
        color = color,
        radius = innerCircleRadius
    )
}
@Composable
fun PercentIndicator(
    label: String,
    percent: String,
    number: Int,
    modifier: Modifier = Modifier,
    firstGradient: Color = Color(0xFF3F51B5),
    secondGradient: Color = Color(0xFF3F51B5),

    ) {
    Row(
        modifier = modifier
            .background(
                Brush.linearGradient(
                .05f to firstGradient,
                1f to secondGradient,
                start = Offset(0.0f, 0.0f),
                end = Offset(0.0f, 135.0f)
            )),

        verticalAlignment = Alignment.CenterVertically
    ) {
        // Label
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                fontSize = 20.sp,
                color = Color(0xFF004C73),
                textAlign = TextAlign.Center,
            ),
            color = Color.White,
            modifier = Modifier
                .weight(1.4f) // Use weight to allow this to expand
        )

        // Divider
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(Color.White)
                .padding(horizontal = 8.dp)
        )

        // Percent
        Text(
            text = percent,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = TextStyle(
                fontSize = 20.sp,
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
                .background(Color.White)
                .padding(horizontal = 8.dp)
        )

        // Number
        Text(
            text = number.toString(),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = TextStyle(
                fontSize = 20.sp,
                color = Color(0xFF004C73),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.weight(1f) // Use weight to allow this to expand
        )
    }
}

@Composable
fun SampleUsage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, Color.Blue, RectangleShape)
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        PercentIndicator(
            label = "FALL",
            percent = "TOP",
            number = 12,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Preview(showBackground = true)
@Composable
fun TopSectionPreview() {
    SHPEUFMobileKotlinTheme {
        TopSection()
    }
}

@Preview(showBackground = true)
@Composable
fun RedeemPointsPreview() {
    SHPEUFMobileKotlinTheme {
        RedeemPoints()
    }
}
@Preview(showBackground = true)
@Composable
fun PointsPercentilPreview() {
    SHPEUFMobileKotlinTheme {
        PointsPercentile()
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    SHPEUFMobileKotlinTheme {
        BottomBar()
    }
}
