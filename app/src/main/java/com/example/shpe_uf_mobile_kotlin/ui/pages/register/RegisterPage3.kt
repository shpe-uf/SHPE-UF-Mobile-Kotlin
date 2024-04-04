package com.example.shpe_uf_mobile_kotlin.ui.pages.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R






@Preview
@Composable
fun ResgistrationPage3Preview(){

    val viewModel = RegisterPage1ViewModel()

    RegistrationPage3(registerPage1ViewModel = viewModel)

}

@Composable
fun RegistrationPage3(registerPage1ViewModel: RegisterPage1ViewModel){

    val uiState by registerPage1ViewModel.uiState.collectAsState()

    RegistrationPageBaseLayer()

    RegistrationPage3ProgressionBar()

    RegistrationPage3AcademicInfoText()


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Spacer(modifier = Modifier.height(288.dp))

    }
}

@Preview
@Composable
fun RegistrationPage3ProgressionBar(modifier: Modifier = Modifier){

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
                        .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
                )

            }

            Spacer(modifier = Modifier.width(8.dp))

            Row{
                Box(
                    modifier = Modifier
                        .size(width = 106.dp, height = 5.dp)
                        .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
                )
            }
        }
    }
}

@Preview
@Composable
fun RegistrationPage3AcademicInfoText(modifier: Modifier = Modifier){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp)

    ){

        Spacer(modifier = Modifier.height(174.dp))

        Text(
            text = "Enter your current education details",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF)
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Row{
                Text(
                    text = "Academic Info",
                    style = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFD25917)
                    )
                )
            }

            Spacer(modifier = Modifier.width(54.dp))

            Row{
                Image(
                    painter = painterResource(id = R.drawable.bookicon),
                    contentDescription = "bookIcon",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}