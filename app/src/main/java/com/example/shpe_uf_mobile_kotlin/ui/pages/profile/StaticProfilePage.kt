package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.ui.navigation.NavRoute
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme

import androidx.compose.foundation.clickable
import com.example.shpe_uf_mobile_kotlin.ui.theme.ThemeColors

//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExposedDropdownMenuBox
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.modifier.modifierLocalConsumer

//TODO: add bottom bar functionality

//@Preview(showBackground = true)
@Composable
fun StaticProfilePagePreview(
    viewModel: ProfileViewModel,
    navController: NavHostController,
    mainViewModel: SHPEUFAppViewModel
) {

    StaticProfileScreen(viewModel, navController, mainViewModel)
}


@Composable
fun StaticProfileScreen(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    mainViewModel: SHPEUFAppViewModel
){
    SHPEUFMobileKotlinTheme{
        val uiState by profileViewModel.uiState.collectAsState()
        val mainState by mainViewModel.uiState.collectAsState()

        val isDarkMode = mainState.isDarkMode

        val textColor = if(isDarkMode){
            Color.White

        } else {
            Color.Black
        }

        profileViewModel.loadProfile(mainState.id)

        StaticProfilePageBackground(isDarkMode = isDarkMode)


        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(top = 206.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item{
                Text(
                    text = uiState.fullName ?: "",
                    color = textColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            item {
                EditProfileButton(
                    onClick = {profileViewModel.tempEditProfile()},
                    profileViewModel
                )
//                navController.navigate(NavRoute.EDITPROFILE)
            }

            item{
                Spacer(modifier = Modifier.height(39.dp))
            }

            item {
                Text(
                    text = "ACCOUNT INFO",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp),
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item{
                Spacer(modifier = Modifier.height(27.dp))
            }

            item{
                StaticProfileName(
                    value = uiState.fullName ?: "",
                    onValueChange = profileViewModel::onFullNameChanged,
                    textColor = textColor
                )
            }

            item{
                StaticProfileUserName(
                    value = uiState.userName ?: "",
                    onValueChange = profileViewModel::onUserNameChanged,
                    textColor = textColor
                )
            }

            item{
                StaticProfileEmail(
                    value = uiState.email ?: "",
                    onValueChange = profileViewModel::onEmailChanged,
                    textColor = textColor
                )
            }

            item{
                StaticProfileGender(
                    value = uiState.gender ?: "",
                    onValueChange = profileViewModel::onGenderChanged,
                    textColor = textColor
                )
            }

            item{
                StaticProfileEthnicity(
                    value = uiState.ethnicity ?: "",
                    onValueChange = profileViewModel::onEthnicityChanged,
                    textColor = textColor
                )
            }

            item{
                StaticProfileCountry(
                    value = uiState.country ?: "",
                    onValueChange = profileViewModel::onCountryChanged,
                    textColor = textColor
                )
            }

            item{
                StaticProfileYear(
                    value = uiState.year ?: "",
                    onValueChange = profileViewModel::onYearChanged,
                    textColor = textColor
                )
            }

            item{
                StaticProfileGradYear(
                    value = uiState.gradYear ?: "",
                    onValueChange = profileViewModel::onGradYearChanged,
                    textColor = textColor
                )
            }

            item{
                Spacer(modifier = Modifier.height(25.dp))
            }

            item {
                AppearanceToggle(mainViewModel, isDarkMode = isDarkMode)
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            item{
                LogoutButton (

                    onClick = {
                        mainViewModel.logoutUser()

                        navController.navigate(NavRoute.LOGIN)

                    }
                )
            }

            item{
                Spacer(modifier = Modifier.height(20.dp))
            }

            item{
                DeleteAccountButton (profileViewModel, mainViewModel, navController)
            }

            item{
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun StaticProfilePageBackground(modifier: Modifier = Modifier, isDarkMode: Boolean) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val orangeHeight = screenHeight * (1.01f / 5f) // Orange covers about a fourth of the screen
    val blueHeight = screenHeight * (3.4f / 5f)  // Blue covers the remaining three-fourths
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
                painter = painterResource(id =
                if (isDarkMode) R.drawable.gator_dark_mode
                else R.drawable.gator_light_mode),
                contentDescription = "SHPE GATOR",
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
                .background(if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 86.dp) // Adjust the padding to move the image

        ){
            Image(
                painter = painterResource(id =
                if (isDarkMode) R.drawable.background_blue_circle
                else R.drawable.background_white_circle),
                contentDescription = "PROFILE CURVE",
                modifier = Modifier
                    .align(Alignment.Center)
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
                painter = painterResource(id =
                if (isDarkMode) R.drawable.empty_profile_picture_dark
                else R.drawable.empty_profile_picture_light),
                contentDescription = "PROFILE PIC CIRCLE",
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(116.dp) // Fixed width
                    .height(110.dp) // Fixed height
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticProfileGradYear(value: String, onValueChange: (String) -> Unit, textColor: Color) {
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
                painter = painterResource(id = R.drawable.profile_cap),
                contentDescription = "GradCapIcon",
                modifier = Modifier.size(26.dp) // Set size for the icon
            )
            Spacer(modifier = Modifier.width(10.dp)) // Add some space between the icon and the text
            Text(
                text = "GRADUATION YEAR",
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
            enabled = false,
            readOnly = true,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(fontSize = 15.sp, color = textColor),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.White,
                focusedPlaceholderColor = Color.Gray
            )
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticProfileYear(value: String, onValueChange: (String) -> Unit, textColor: Color) {
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
                contentDescription = "CalenderIcon",
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
        // Outlined Text Field for Input
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            enabled = false,
            readOnly = true,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(fontSize = 15.sp, color = textColor),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.White,
                focusedPlaceholderColor = Color.Gray
            )
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticProfileCountry(value: String, onValueChange: (String) -> Unit, textColor: Color) {
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
        // Outlined Text Field for Input
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            enabled = false,
            readOnly = true,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(fontSize = 15.sp, color = textColor),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.White,
                focusedPlaceholderColor = Color.Gray
            )
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticProfileEthnicity(value: String, onValueChange: (String) -> Unit, textColor: Color) {
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
        // Outlined Text Field for Input
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            enabled = false,
            readOnly = true,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(fontSize = 15.sp, color = textColor),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.White,
                focusedPlaceholderColor = Color.Gray
            )
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticProfileGender(value: String, onValueChange: (String) -> Unit, textColor: Color) {
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
        // Outlined Text Field for Input
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            enabled = false,
            readOnly = true,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(fontSize = 15.sp, color = textColor),


            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.White,
                focusedPlaceholderColor = Color.Gray
            )
        )
    }
}








@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticProfileEmail(value: String, onValueChange: (String) -> Unit, textColor: Color) {
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
            enabled = false,
            readOnly = true,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textStyle = TextStyle(fontSize = 15.sp, color = textColor),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                focusedPlaceholderColor = Color.Gray
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticProfileUserName(value: String, onValueChange: (String) -> Unit, textColor: Color) {
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
            enabled = false,
            readOnly = true,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(fontSize = 15.sp, color = textColor),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                focusedPlaceholderColor = Color.Gray
            )
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticProfileName(
    value: String,
    onValueChange: (String) -> Unit,
    textColor: Color
) {
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
            enabled = false,
            readOnly = true,
            onValueChange = { newValue -> onValueChange(newValue) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(fontSize = 15.sp, color = textColor),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Blue,
                focusedTextColor = Color.Yellow,
                focusedPlaceholderColor = Color.Magenta
            )
        )
    }
}




@Composable
private fun EditProfileButton(
    onClick: () -> Unit,
    profileViewModel: ProfileViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // Ensure the Box takes up the full width of the screen
            .padding(top = 16.dp) // Add some padding at the top to prevent cutting off
    ) {
        Button(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .align(Alignment.Center),
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF001627),
                contentColor = Color.White
            ),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically){

                Text(
                    text = "Edit Profile",
                    fontSize = 19.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(11.dp))

                Image(
                    painter = painterResource(id = R.drawable.edit_profile_pencil),
                    contentDescription = "EditPencilIcon",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}


@Composable
private fun LogoutButton(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // Ensure the Box takes up the full width of the screen
    ) {
        Button(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .align(Alignment.Center),
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF001627),
                contentColor = Color.White
            ),
        ) {
            Text(
                text = "Logout",
                fontSize = 19.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun DeleteAccountButton(profileViewModel: ProfileViewModel,
                                shpeUFAppViewModel: SHPEUFAppViewModel,
                                navController: NavHostController
) {

    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth() // Ensure the Box takes up the full width of the screen
    ) {
        Button(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .align(Alignment.Center),
            onClick = { showDialog = true },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
        ) {
            Text(
                text = "Delete Account",
                fontSize = 19.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Delete Account?",
                    textAlign = TextAlign.Center
            ) },
            text = { Text("Deleting your account will remove all your personal data forever. This cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        val deleteUnsuccessful = profileViewModel.deleteProfile(shpeUFAppViewModel)
                        showDialog = false
                        if (!deleteUnsuccessful) {
                            navController.navigate(NavRoute.OPENING)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun AppearanceToggle(mainViewModel: SHPEUFAppViewModel, isDarkMode: Boolean) {
    // Column container for the appearance section
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Section title
        Text(
            text = "APPEARANCE",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp),
            color = if (isDarkMode) Color.White else Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(27.dp))

        // Column containing the mode buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            // Light Mode button
            ModeButton(
                label = "Light Mode",
                iconRes = R.drawable.ic_light_mode,
                selected = !isDarkMode,
                onClick = { mainViewModel.saveDarkMode(false) },
            )

            // Dark Mode button
            ModeButton(
                label = "Dark Mode",
                iconRes = R.drawable.ic_dark_mode,
                selected = isDarkMode,
                onClick = { mainViewModel.saveDarkMode(true) },
            )
        }
    }
}

@Composable
fun ModeButton(
    label: String,
    iconRes: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Column to stack the button content and divider (if any)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() } // Make the entire area clickable
    ) {
        // Row containing the mode icon, label, and selection indicator
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                // Change background color when selected
                .background(
//                    color = if (selected) Color(0xFF001627) else Color.Transparent,
                    color = Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            // Mode icon (light or dark)
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "$label Icon",
                modifier = Modifier.size(26.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Mode label text
            Text(
                text = label,
                color = Color(0xFFD25917), // Orange color
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // Spacer to push the selection indicator to the end
            Spacer(modifier = Modifier.weight(1f))

            // Selection indicator icon (checked or unchecked)
            Image(
                painter = painterResource(
                    id = if (selected) R.drawable.ic_checked else R.drawable.ic_unchecked
                ),
                contentDescription = if (selected) "Selected" else "Not Selected",
                // Adjust size based on selection state
                modifier = Modifier.size(if (selected) 35.dp else 31.dp)
            )
        }
    }
}

