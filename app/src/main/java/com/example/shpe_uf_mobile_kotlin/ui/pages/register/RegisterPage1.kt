package com.example.shpe_uf_mobile_kotlin.ui.pages.register

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R

@Preview
@Composable
fun ResgistrationPage1Ui(){
    RegistrationPageBaseLayer()

    RegistrationPage1ProgressionBar()

    RegistrationPage1WelcomeText()
}


@Preview
@Composable
fun RegistrationPageBaseLayer(modifier: Modifier = Modifier) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val orangeHeight = screenHeight * (1.01f/11f)

    val blueHeight = screenHeight * (10f/11f)


    Box(modifier = modifier
        .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(orangeHeight)
                .background(Color(0xFFD25917))
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(blueHeight)
                .align(Alignment.BottomCenter)
                .background(Color(0xFF011F35))
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.gatordarkcropped),
            contentDescription = "gator"
        )

    }
}


@Preview
@Composable
fun RegistrationPage1ProgressionBar(modifier: Modifier = Modifier){

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ){

        Spacer(modifier = Modifier.height(132.dp))

        Row{
            Row{
                Box(
                    modifier = Modifier
                        .size(width = 106.dp, height = 5.dp)
                        .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
                )

            }

            Spacer(modifier = Modifier.width(8.dp))

            Row{
                Box(
                    modifier = Modifier
                        .size(width = 106.dp, height = 5.dp)
                        .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
                )

            }

            Spacer(modifier = Modifier.width(8.dp))

            Row{
                Box(
                    modifier = Modifier
                        .size(width = 106.dp, height = 5.dp)
                        .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
                )
            }
        }
    }
}


@Preview
@Composable
fun RegistrationPage1WelcomeText(modifier: Modifier = Modifier){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp)

    ){

        Spacer(modifier = Modifier.height(174.dp))

        Text(
            text = "Welcome to SHPE!",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF)
            )
        )

        Row{
            Row{
                Text(
                    text = "REGISTER",
                    style = TextStyle(
                        fontSize = 46.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFD25917)
                    )
                )
            }

            Spacer(modifier = Modifier.width(76.dp))

            Row{
                Image(
                    painter = painterResource(id = R.drawable.shpe_logo_full_color),
                    contentDescription = "shpeLogo",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun RegistrationPage1TextFields(modifier: Modifier = Modifier){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp)

    ){

    }

    
}

