package com.example.shpe_uf_mobile_kotlin.ui.pages.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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






//@Preview
@Composable
fun RegistrationPage3Preview(registerPage1ViewModel: RegisterPage1ViewModel){

//    val viewModel = RegisterPage1ViewModel()

    RegistrationPage3(registerPage1ViewModel = registerPage1ViewModel)

}

@Composable
fun RegistrationPage3(registerPage1ViewModel: RegisterPage1ViewModel){

    val uiState by registerPage1ViewModel.uiState.collectAsState()

    RegisterBackground()

    Box(
        modifier = Modifier
            .padding(top = 83.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF011F35)),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            Spacer(modifier = Modifier.height(42.dp))

            // This is the start of the third page progression bar
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
            // This is the end of the third page progression bar

            Spacer(modifier = Modifier.height(35.dp))


            // This is the academic info text section
            Text(
                text = "Enter your current education details",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp)
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
            // end of academic info section

            Spacer(modifier = Modifier.height(55.dp))

            Text(
                text = "Major",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            MajorDropDownMenu(
                value = uiState.major ?: "",
                isError = uiState.major != null,
                errorMessage = uiState.majorErrorMessage ?: "",
                isMajorMenuExpanded = uiState.isMajorMenuExpanded,
                onExpandedChange = {registerPage1ViewModel.toggleMajorMenuExpansion()},
                onValueChange = { registerPage1ViewModel.onMajorChanged(it) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Year",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            YearDropDownMenu(
                value = uiState.year ?: "",
                isError = uiState.year != null,
                errorMessage = uiState.yearErrorMessage ?: "",
                isYearMenuExpanded = uiState.isYearMenuExpanded,
                onExpandedChange = {registerPage1ViewModel.toggleYearMenuExpansion()},
                onValueChange = { registerPage1ViewModel.onYearChanged(it) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Graduation Year",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            GraduationYearDropDownMenu(
                value = uiState.graduationYear ?: "",
                isError = uiState.graduationYear != null,
                errorMessage = uiState.graduationYearErrorMessage ?: "",
                isGraduationMenuExpanded = uiState.isGraduationMenuExpanded,
                onExpandedChange = {registerPage1ViewModel.toggleGraduationMenuExpansion()},
                onValueChange = { registerPage1ViewModel.onGraduationYearChanged(it) }
            )

            Spacer(modifier = Modifier.height(207.dp))

            CompleteRegistrationButton(
                onClick = { registerPage1ViewModel.validateAndRegisterUser() }
            )

        }

    }





}

//@Preview
//@Composable
//fun RegistrationPage3ProgressionBar(modifier: Modifier = Modifier){
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//
//    ){
//
//        Spacer(modifier = Modifier.height(132.dp))
//
//        Row{
//            Row{
//                Box(
//                    modifier = Modifier
//                        .size(width = 106.dp, height = 5.dp)
//                        .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
//                )
//
//            }
//
//            Spacer(modifier = Modifier.width(8.dp))
//
//            Row{
//                Box(
//                    modifier = Modifier
//                        .size(width = 106.dp, height = 5.dp)
//                        .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
//                )
//
//            }
//
//            Spacer(modifier = Modifier.width(8.dp))
//
//            Row{
//                Box(
//                    modifier = Modifier
//                        .size(width = 106.dp, height = 5.dp)
//                        .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
//                )
//            }
//        }
//    }
//}


//@Preview
//@Composable
//fun RegistrationPage3AcademicInfoText(modifier: Modifier = Modifier){
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(start = 30.dp)
//
//    ){
//
//        Spacer(modifier = Modifier.height(174.dp))
//
//        Text(
//            text = "Enter your current education details",
//            style = TextStyle(
//                fontSize = 14.sp,
//                fontWeight = FontWeight(400),
//                color = Color(0xFFFFFFFF)
//            )
//        )
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ){
//            Row{
//                Text(
//                    text = "Academic Info",
//                    style = TextStyle(
//                        fontSize = 36.sp,
//                        fontWeight = FontWeight(400),
//                        color = Color(0xFFD25917)
//                    )
//                )
//            }
//
//            Spacer(modifier = Modifier.width(54.dp))
//
//            Row{
//                Image(
//                    painter = painterResource(id = R.drawable.bookicon),
//                    contentDescription = "bookIcon",
//                    modifier = Modifier.size(50.dp)
//                )
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MajorDropDownMenu(
    value: String,
    isError: Boolean,
    errorMessage: String,
    isMajorMenuExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
){
    ExposedDropdownMenuBox(
        expanded = isMajorMenuExpanded,
        onExpandedChange = {onExpandedChange(it)}
    ) {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.majoricon),
                    contentDescription = "MajorIcon",
                    modifier = Modifier.size(24.dp)
                )
            },
            shape = RoundedCornerShape(10.dp),

            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isMajorMenuExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(0.7f)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isMajorMenuExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = "Computer Science")
                },
                onClick = {
                    onValueChange("Computer Science")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Mechanical Engineering")
                },
                onClick = {
                    onValueChange("Mechanical Engineering")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Aerospace Engineering")
                },
                onClick = {
                    onValueChange("Aerospace Engineering")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Electrical Engineering")
                },
                onClick = {
                    onValueChange("Electrical Engineering")
                    onExpandedChange(false)
                }
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun YearDropDownMenu(
    value: String,
    isError: Boolean,
    errorMessage: String,
    isYearMenuExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
){
    ExposedDropdownMenuBox(
        expanded = isYearMenuExpanded,
        onExpandedChange = {onExpandedChange(it)}
    ) {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.yearicon),
                    contentDescription = "YearIcon",
                    modifier = Modifier.size(32.dp)
                )
            },
            shape = RoundedCornerShape(10.dp),

            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isYearMenuExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(0.7f)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isYearMenuExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = "Freshman")
                },
                onClick = {
                    onValueChange("Freshman")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Sophomore")
                },
                onClick = {
                    onValueChange("Sophomore")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Junior")
                },
                onClick = {
                    onValueChange("Junior")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Senior")
                },
                onClick = {
                    onValueChange("Senior")
                    onExpandedChange(false)
                }
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GraduationYearDropDownMenu(
    value: String,
    isError: Boolean,
    errorMessage: String,
    isGraduationMenuExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
){
    ExposedDropdownMenuBox(
        expanded = isGraduationMenuExpanded,
        onExpandedChange = {onExpandedChange(it)}
    ) {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.graduationcapicon),
                    contentDescription = "GraduationCapIcon",
                    modifier = Modifier.size(26.dp)
                )
            },
            shape = RoundedCornerShape(10.dp),

            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGraduationMenuExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(0.7f)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isGraduationMenuExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = "2024")
                },
                onClick = {
                    onValueChange("2024")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "2025")
                },
                onClick = {
                    onValueChange("2025")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "2026")
                },
                onClick = {
                    onValueChange("2026")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "2027")
                },
                onClick = {
                    onValueChange("2027")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "2028")
                },
                onClick = {
                    onValueChange("2028")
                    onExpandedChange(false)
                }
            )

        }
    }
}

@Composable
private fun CompleteRegistrationButton(
    onClick: () -> Unit) {

    Button(
        modifier = Modifier
            .width(351.dp),
        onClick = {
            onClick()
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
                    color = Color(0xFFD25917),

                    )
                .padding(horizontal = 20.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Complete Registration",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}