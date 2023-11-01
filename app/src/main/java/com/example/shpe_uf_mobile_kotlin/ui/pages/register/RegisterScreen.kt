package com.example.shpe_uf_mobile_kotlin.ui.pages.register



import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField



import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RegistrationPage() {
    SHPEUFMobileKotlinTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 30.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Image(
                        painter = painterResource(id = R.drawable.shpe_logo_full_color),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxSize()
                    )

                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        text = "Create SHPE-UF Account",
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center
                    )
                }

                Column {

                    //TODO Add registration input fields

                    GradientRegistrationHeaderLabel(
                        nameButton = "First Name",
                        RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp)
                    )


                    var firstName by remember { mutableStateOf("") }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedTextField(
                            modifier = Modifier,
                            value = firstName,
                            onValueChange = { firstName = it },
                            label = {
                                Text(
                                    text = "Enter your First Name...",
                                    fontSize = 11.sp,
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    GradientRegistrationHeaderLabel(
                        nameButton = "Last Name",
                        RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp)
                    )


                    var lastName by remember { mutableStateOf("") }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedTextField(
                            modifier = Modifier,
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = {
                                Text(
                                    text = "Enter your Last Name...",
                                    fontSize = 11.sp,
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    GradientRegistrationHeaderLabel(
                        nameButton = "Email",
                        RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp)
                    )

                    var email by remember { mutableStateOf("") }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedTextField(
                            modifier = Modifier,
                            value = email,
                            onValueChange = { email = it },
                            label = {
                                Text(
                                    text = "Enter your Email...",
                                    fontSize = 11.sp,
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    GradientRegistrationHeaderLabel(
                        nameButton = "Password",
                        RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp)
                    )

                    var password by remember { mutableStateOf("") }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedTextField(
                            modifier = Modifier,
                            value = password,
                            onValueChange = { password = it },
                            label = {
                                Text(
                                    text = "Enter your Password...",
                                    fontSize = 11.sp,
                                )
                            }
                        )
                    }

                }


                Column (
                    modifier = Modifier
                        .fillMaxWidth(),
                ){

                    GradientButton(
                        nameButton = "Create Account",
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Already have an account?",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.labelLarge
                        )
                        TextButton(onClick = {

                            //TODO Navigate to the sign-in page for users that already have an account

                        }) {
                            Text(
                                text = "Sign In",
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GradientButton(
    nameButton: String,
) {
    val gradientColors = listOf(Color(0xFFD23C20), Color(0xFFF1652F))


    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            //TODO Handle user registration
        },

        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nameButton,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}


@Composable
private fun GradientRegistrationHeaderLabel(
    nameButton: String,
    roundedCornerShape: RoundedCornerShape
){
    val gradientColors = listOf(Color(0xFFD23C20), Color(0xFFF1652F))
    val cornerRadius = 10.dp


    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = {
            // TODO leave empty as the button is for appearances and to hold first name
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ), shape = RoundedCornerShape(cornerRadius)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = roundedCornerShape
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = nameButton,
                fontSize = 17.sp,
                color = Color.White
            )
        }
    }
}