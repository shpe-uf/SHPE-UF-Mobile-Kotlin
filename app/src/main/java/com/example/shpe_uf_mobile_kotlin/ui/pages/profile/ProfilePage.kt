package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
        profileViewModel.loadProfile("64ea79b9f2051e00149c75b7")

        ProfilePageBackground()

        LazyColumn(
            modifier = Modifier
                //TODO: fix disappearing profile fields
                .padding(horizontal = 15.dp)
                .padding(top = 206.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            item{
                SaveCancelButtons(
                    onSaveClick = { profileViewModel.saveProfileChanges() },
                    onCancelClick = { profileViewModel.cancelProfileChanges() }
                )

            }

            item{
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Text(
                    text = "ACCOUNT INFO",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp),
                    color = Color(0xFFC6C6C6),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item{
                Spacer(modifier = Modifier.height(27.dp))
            }

            item{
                ProfileName(
                    value = uiState.fullName ?: "",
                    onValueChange = profileViewModel::onFullNameChanged
                )

            }

            item{
                Spacer(modifier = Modifier.height(5.dp))
            }

            item{
                ProfileUserName(
                    value = uiState.userName ?: "",
                    onValueChange = profileViewModel::onUserNameChanged
                )
            }

            item{
                Spacer(modifier = Modifier.height(5.dp))
            }

            item{
                ProfileEmail(
                    value = uiState.email ?: "",
                    onValueChange = profileViewModel::onEmailChanged
                )

            }

            item{
                Spacer(modifier = Modifier.height(5.dp))
            }

            item{
                ProfileGender(
                    selectedGender = uiState.gender ?: "",
                    onGenderSelected = profileViewModel::onGenderChanged,
                    genders = listOf("Male", "Female", "Other")
                )

            }

            item{
                Spacer(modifier = Modifier.height(5.dp))
            }

            item{
                ProfileEthnicity(
                    selectedEthnicity = uiState.ethnicity ?: "",
                    onEthnicitySelected = profileViewModel::onEthnicityChanged,
                    ethnicities = listOf("Hispanic", "Black/African American", "Asian", "White", "Native American", "Native Hawaiian", "Two or More Races", "Other")
                )

            }

            item{
                Spacer(modifier = Modifier.height(5.dp))
            }

            item{
                ProfileCountry(
                    selectedCountry = uiState.country ?: "",
                    onCountrySelected = profileViewModel::onCountryChanged,
                    countries = listOf(
                        "Argentina",
                        "Australia",
                        "Austria",
                        "Belgium",
                        "Brazil",
                        "Canada",
                        "Chile",
                        "China",
                        "Czech Republic",
                        "Denmark",
                        "Egypt",
                        "Finland",
                        "France",
                        "Germany",
                        "Greece",
                        "Hungary",
                        "India",
                        "Indonesia",
                        "Ireland",
                        "Israel",
                        "Italy",
                        "Japan",
                        "Kenya",
                        "Malaysia",
                        "Mexico",
                        "Netherlands",
                        "New Zealand",
                        "Nigeria",
                        "Norway",
                        "Pakistan",
                        "Philippines",
                        "Poland",
                        "Portugal",
                        "Romania",
                        "Russia",
                        "Saudi Arabia",
                        "Singapore",
                        "South Africa",
                        "South Korea",
                        "Spain",
                        "Sweden",
                        "Switzerland",
                        "Thailand",
                        "Turkey",
                        "Ukraine",
                        "United Arab Emirates",
                        "United Kingdom",
                        "United States",
                        "Vietnam"
                    )
                )

            }

            item{
                Spacer(modifier = Modifier.height(5.dp))
            }

            item{

                ProfileYear(
                    selectedYear = uiState.year ?: "",
                    onYearSelected = profileViewModel::onYearChanged,
                    years = listOf("First", "Second", "Third", "Fourth", "Fifth", "Grad")
                )

            }

            item{
                Spacer(modifier = Modifier.height(5.dp))
            }

            item{
                ProfileGradYear(
                    selectedYear = uiState.gradYear ?: "",
                    onYearSelected = profileViewModel::onGradYearChanged,
                    years = listOf("2024", "2025", "2026", "2027")
                )
            }

            item{
                Spacer(modifier = Modifier.height(86.dp))
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
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(450.dp) // Fixed width
                    .height(450.dp) // Fixed height
            )
        }
        //TODO: display user's profile picture when they upload it
        //TODO: dispay user's name under profile picture
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 93.dp) // Adjust the padding to move the image

        ){
            Image(
                painter = painterResource(id = R.drawable.empty_profile_picture),
                contentDescription = "BLUE CURVE",
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(116.dp) // Fixed width
                    .height(110.dp) // Fixed height
            )
        }
    }
}

////TODO: fix dropdown fields disappearing once an option is clicked
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileGradYear(selectedYear: String, onYearSelected: (String) -> Unit, years: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // The text field showing the selected year
        TextField(
            value = selectedYear,
            onValueChange = {},
            label = { Text("GRADUATION YEAR", fontSize = 15.sp) },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.profile_cap),
                    contentDescription = null,
                )
            },
            shape = RoundedCornerShape(12.dp),
            readOnly = true, // Prevents manual input
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .menuAnchor()
        )

        // Dropdown menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            years.forEach { year ->
                DropdownMenuItem(
                    text = { Text(text = year) }, // Updated format
                    onClick = {
                        onYearSelected(year)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileYear(selectedYear: String, onYearSelected: (String) -> Unit, years: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 8.dp)
    ) {
        // Row for Icon and Field Name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_year),
                contentDescription = "YearIcon",
                modifier = Modifier.size(26.dp) // Set size for the icon
            )
            Spacer(modifier = Modifier.width(10.dp)) // Add some space between the icon and the text
            Text(
                text = "YEAR",
                color = Color(0xFFD25917), // Orange color
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedYear,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(12.dp),
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            years.forEach { year ->
                DropdownMenuItem(
                    text = { Text(text = year) }, // Updated to use the new API
                    onClick = {
                        onYearSelected(year)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCountry(selectedCountry: String, onCountrySelected: (String) -> Unit, countries: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 8.dp)
    ) {
        // Row for Icon and Field Name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_globe),
                contentDescription = "GlobeIcon",
                modifier = Modifier.size(26.dp) // Set size for the icon
            )
            Spacer(modifier = Modifier.width(10.dp)) // Add some space between the icon and the text
            Text(
                text = "COUNTRY OF ORIGIN",
                color = Color(0xFFD25917), // Orange color
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedCountry,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(12.dp),
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            countries.forEach { country ->
                DropdownMenuItem(
                    text = { Text(text = country) }, // Updated to use the new API
                    onClick = {
                        onCountrySelected(country)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEthnicity(selectedEthnicity: String, onEthnicitySelected: (String) -> Unit, ethnicities: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 8.dp)
    ) {
        // Row for Icon and Field Name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_globe),
                contentDescription = "GlobeIcon",
                modifier = Modifier.size(26.dp) // Set size for the icon
            )
            Spacer(modifier = Modifier.width(10.dp)) // Add some space between the icon and the text
            Text(
                text = "ETHNICITY",
                color = Color(0xFFD25917), // Orange color
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedEthnicity,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(12.dp),
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ethnicities.forEach { ethnicity ->
                DropdownMenuItem(
                    text = { Text(text = ethnicity) }, // Updated to use the new API
                    onClick = {
                        onEthnicitySelected(ethnicity)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileGender(selectedGender: String, onGenderSelected: (String) -> Unit, genders: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 8.dp)
    ) {
        // Row for Icon and Field Name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_gender_equality),
                contentDescription = "GenderIcon",
                modifier = Modifier.size(26.dp) // Set size for the icon
            )
            Spacer(modifier = Modifier.width(10.dp)) // Add some space between the icon and the text
            Text(
                text = "GENDER",
                color = Color(0xFFD25917), // Orange color
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedGender,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(12.dp),
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            genders.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(text = gender) }, // Updated to use the new API
                    onClick = {
                        onGenderSelected(gender)
                        expanded = false
                    }
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEmail(value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 8.dp)
    ) {
        // Row for Icon and Field Name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_email),
                contentDescription = null,
                modifier = Modifier.size(24.dp) // Set size for the icon
            )
            Spacer(modifier = Modifier.width(10.dp)) // Add some space between the icon and the text
            Text(
                text = "EMAIL",
                color = Color(0xFFD25917), // Orange color
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        // Outlined Text Field for Input
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            singleLine = true,
            enabled = false,
            readOnly = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textStyle = TextStyle(fontSize = 15.sp, color = Color.LightGray),
            placeholder = {
                if (value.isEmpty()) { // Show placeholder only when value is empty
                    Text(
                        text = "Enter Email", // Placeholder text
                        color = Color.Gray
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                textColor = Color.White,
                placeholderColor = Color.Gray
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUserName(value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 8.dp)
    ) {
        // Row for Icon and Field Name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_circle_orange),
                contentDescription = null,
                modifier = Modifier.size(24.dp) // Set size for the icon
            )
            Spacer(modifier = Modifier.width(10.dp)) // Add some space between the icon and the text
            Text(
                text = "USERNAME",
                color = Color(0xFFD25917), // Orange color
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        // Outlined Text Field for Input
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(fontSize = 15.sp, color = Color.White),
            placeholder = {
                if (value.isEmpty()) { // Show placeholder only when value is empty
                    Text(
                        text = "Enter Username", // Placeholder text
                        color = Color.Gray
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                textColor = Color.White,
                placeholderColor = Color.Gray
            )
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileName(value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 8.dp)
    ) {
        // Row for Icon and Field Name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_circle_orange),
                contentDescription = null,
                modifier = Modifier.size(24.dp) // Set size for the icon
            )
            Spacer(modifier = Modifier.width(10.dp)) // Add some space between the icon and the text
            Text(
                text = "NAME",
                color = Color(0xFFD25917), // Orange color
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        // Outlined Text Field for Input
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(fontSize = 15.sp, color = Color.White),
            placeholder = {
                if (value.isEmpty()) { // Show placeholder only when value is empty
                    Text(
                        text = "Enter Name", // Placeholder text
                        color = Color.Gray
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                textColor = Color.White,
                placeholderColor = Color.Gray
            )
        )
    }
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
