package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme

//TODO: add bottom bar functionality

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    val viewModel = ProfileViewModel()
    ProfileScreen(viewModel)
}


@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel){
    SHPEUFMobileKotlinTheme{
        val uiState by profileViewModel.uiState.collectAsState()
        //TODO: fetch name to display under profile picture
        ProfilePageBackground()
        Column(
            modifier = Modifier
                //TODO: fix disappearing profile fields
                .padding(horizontal = 15.dp, vertical = 210.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
            ){
                SaveCancelButtons(
                    onSaveClick = { profileViewModel.saveProfileChanges() },
                    onCancelClick = { profileViewModel.cancelProfileChanges() }
                )
            }
            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                //TODO: remove borders from text fields
                ProfileName(
                    value = uiState.fullName ?: "",
                    onValueChange = profileViewModel::onFullNameChanged
                )
                ProfileUserName(
                    value = uiState.userName ?: "",
                    onValueChange = profileViewModel::onUserNameChanged
                )
                ProfileEmail(
                    value = uiState.email ?: "",
                    onValueChange = profileViewModel::onEmailChanged
                )
//                ProfileGender(
//                    selectedGender = uiState.gender ?: "",
//                    onGenderSelected = profileViewModel::onGenderChanged,
//                    genders = listOf("Male", "Female", "Other")
//                )
//                ProfileEthnicity(
//                    selectedEthnicity = uiState.ethnicity ?: "",
//                    onEthnicitySelected = profileViewModel::onEthnicityChanged,
//                    ethnicities = listOf("Hispanic", "Black/African American", "Asian", "White", "Native American", "Native Hawaiian", "Two or More Races", "Other")
//                )
//                ProfileCountry(
//                    selectedCountry = uiState.country ?: "",
//                    onCountrySelected = profileViewModel::onCountryChanged,
//                    countries = listOf(
//                        "Argentina",
//                        "Australia",
//                        "Austria",
//                        "Belgium",
//                        "Brazil",
//                        "Canada",
//                        "Chile",
//                        "China",
//                        "Czech Republic",
//                        "Denmark",
//                        "Egypt",
//                        "Finland",
//                        "France",
//                        "Germany",
//                        "Greece",
//                        "Hungary",
//                        "India",
//                        "Indonesia",
//                        "Ireland",
//                        "Israel",
//                        "Italy",
//                        "Japan",
//                        "Kenya",
//                        "Malaysia",
//                        "Mexico",
//                        "Netherlands",
//                        "New Zealand",
//                        "Nigeria",
//                        "Norway",
//                        "Pakistan",
//                        "Philippines",
//                        "Poland",
//                        "Portugal",
//                        "Romania",
//                        "Russia",
//                        "Saudi Arabia",
//                        "Singapore",
//                        "South Africa",
//                        "South Korea",
//                        "Spain",
//                        "Sweden",
//                        "Switzerland",
//                        "Thailand",
//                        "Turkey",
//                        "Ukraine",
//                        "United Arab Emirates",
//                        "United Kingdom",
//                        "United States",
//                        "Vietnam"
//                    )
//                )
//                ProfileYear(
//                    selectedYear = uiState.year ?: "",
//                    onYearSelected = profileViewModel::onYearChanged,
//                    years = listOf("First", "Second", "Third", "Fourth", "Fifth", "Grad")
//                )
//                ProfileGradYear(
//                    selectedYear = uiState.gradYear ?: "",
//                    onYearSelected = profileViewModel::onGradYearChanged,
//                    years = listOf("2024", "2025", "2026", "2027")
//                )
            }
        }
    }
}

@Composable
fun ProfilePageBackground(modifier: Modifier = Modifier) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val orangeHeight = screenHeight * (1.01f / 5f) // Orange covers about a fourth of the screen
    val blueHeight = screenHeight * (4f / 5f) // Blue covers the remaining three-fourths
    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(orangeHeight)
                .background(Color(0xFFD25917))
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart) // Align centrally
                .padding(top = 102.dp) // Adjust the padding to move the image
        ) {
            Image(
                painter = painterResource(id = R.drawable.gator_dark_mode),
                contentDescription = "SHPE GATOR DARK",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .width(90.dp) // Fixed width
                    .height(90.dp) // Fixed height
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(blueHeight)
                .align(Alignment.BottomCenter)
                .background(Color(0xFF011F35))
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 86.dp) // Adjust the padding to move the image

        ){
            Image(
                painter = painterResource(id = R.drawable.background_blue_circle),
                contentDescription = "BLUE CURVE",
                modifier = Modifier.align(Alignment.Center)
                    .width(450.dp) // Fixed width
                    .height(450.dp) // Fixed height
            )
        }
        //TODO: display user's profile picture when they upload it
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 93.dp) // Adjust the padding to move the image

        ){
            Image(
                painter = painterResource(id = R.drawable.empty_profile_picture),
                contentDescription = "BLUE CURVE",
                modifier = Modifier.align(Alignment.Center)
                    .width(95.dp) // Fixed width
                    .height(95.dp) // Fixed height
            )
        }
    }
}

////TODO: fix dropdown fields disappearing once an option is clicked
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileGradYear(selectedYear: String, onYearSelected: (String) -> Unit, years: List<String>) {
//    var expanded by remember { mutableStateOf(false) }
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded }
//    ) {
//        TextField(
//            value = selectedYear,
//            onValueChange = {},
//            label = { Text("GRADUATION YEAR", fontSize = 15.sp) },
//            leadingIcon = {
//                Image(
//                    painter = painterResource(id = R.drawable.profile_cap),
//                    contentDescription = null,
//                )
//            },
//            shape = RoundedCornerShape(12.dp),
//            readOnly = true,
//            modifier = Modifier
//                .fillMaxWidth(0.8f)
//                .menuAnchor()
//        )
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            years.forEach { year ->
//                DropdownMenuItem(onClick = {
//                    onYearSelected(year)
//                    expanded = false
//                }) {
//                    Text(text = year)
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileYear(selectedYear: String, onYearSelected: (String) -> Unit, years: List<String>) {
//    var expanded by remember { mutableStateOf(false) }
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded }
//    ) {
//        TextField(
//            value = selectedYear,
//            onValueChange = {},
//            label = { Text("YEAR", fontSize = 15.sp) },
//            leadingIcon = {
//                Image(
//                    painter = painterResource(id = R.drawable.profile_year),
//                    contentDescription = null,
//                )
//            },
//            shape = RoundedCornerShape(12.dp),
//            readOnly = true,
//            modifier = Modifier
//                .fillMaxWidth(0.8f)
//                .menuAnchor()
//        )
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            years.forEach { year ->
//                DropdownMenuItem(onClick = {
//                    onYearSelected(year)
//                    expanded = false
//                }) {
//                    Text(text = year)
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileCountry(selectedCountry: String, onCountrySelected: (String) -> Unit, countries: List<String>) {
//    var expanded by remember { mutableStateOf(false) }
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded }
//    ) {
//        TextField(
//            value = selectedCountry,
//            onValueChange = {},
//            label = { Text("COUNTRY OF ORIGIN", fontSize = 15.sp) },
//            leadingIcon = {
//                Image(
//                    painter = painterResource(id = R.drawable.profile_globe),
//                    contentDescription = null,
//                )
//            },
//            shape = RoundedCornerShape(12.dp),
//            readOnly = true,
//            modifier = Modifier
//                .fillMaxWidth(0.8f)
//                .menuAnchor()
//        )
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            countries.forEach { country ->
//                DropdownMenuItem(onClick = {
//                    onCountrySelected(country)
//                    expanded = false
//                }) {
//                    Text(text = country)
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileEthnicity(selectedEthnicity: String, onEthnicitySelected: (String) -> Unit, ethnicities: List<String>) {
//    var expanded by remember { mutableStateOf(false) }
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded }
//    ) {
//        TextField(
//            value = selectedEthnicity,
//            onValueChange = {},
//            label = { Text("ETHNICITY", fontSize = 15.sp) },
//            leadingIcon = {
//                Image(
//                    painter = painterResource(id = R.drawable.profile_globe),
//                    contentDescription = null,
//                )
//            },
//            shape = RoundedCornerShape(12.dp),
//            readOnly = true,
//            modifier = Modifier
//                .fillMaxWidth(0.8f)
//                .menuAnchor()
//        )
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            ethnicities.forEach { ethnicity ->
//                DropdownMenuItem(onClick = {
//                    onEthnicitySelected(ethnicity)
//                    expanded = false
//                }) {
//                    Text(text = ethnicity)
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileGender(selectedGender: String, onGenderSelected: (String) -> Unit, genders: List<String>) {
//    var expanded by remember { mutableStateOf(false) }
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded }
//    ) {
//        TextField(
//            value = selectedGender,
//            onValueChange = {},
//            label = { Text("GENDER", fontSize = 15.sp) },
//            leadingIcon = {
//                Image(
//                    painter = painterResource(id = R.drawable.profile_gender_equality),
//                    contentDescription = null,
//                )
//            },
//            shape = RoundedCornerShape(12.dp),
//            readOnly = true,
//            modifier = Modifier
//                .fillMaxWidth(0.7f)
//                .menuAnchor()
//        )
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            genders.forEach { gender ->
//                DropdownMenuItem(onClick = {
//                    onGenderSelected(gender)
//                    expanded = false
//                }) {
//                    Text(text = gender)
//                }
//            }
//        }
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEmail(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.profile_email),
                contentDescription = null,
            )
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        label = {
            Text(
                text = "EMAIL",
                fontSize = 15.sp
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUserName(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.profile_circle_orange),
                contentDescription = null,
            )
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        label = {
            Text(
                text = "USERNAME",
                fontSize = 15.sp
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileName(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.profile_circle_orange),
                contentDescription = null,
            )
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        label = {
            Text(
                text = "NAME",
                fontSize = 15.sp
            )
        }
    )
}



@Composable
private fun SaveCancelButtons(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // Ensure the Box takes up the full width of the screen
            .padding(top = 16.dp) // Add some padding at the top to prevent cutting off
            .offset(x = (LocalConfiguration.current.screenWidthDp * 0.16).dp)
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(2.dp) // Space between the buttons
        ) {
            Button(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .border(0.75.dp, Color.White, RoundedCornerShape(20.dp)),
                onClick = onSaveClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF001627),
                    contentColor = Color.White
                ),
            ) {
                Text(
                    text = "Save",
                    fontSize = 19.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Button(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(20.dp)),
                onClick = onCancelClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF001627),
                    contentColor = Color.White
                ),
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 19.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}
