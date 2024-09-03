package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
        ProfilePageBackground()
        Column(
            modifier = Modifier
                //TODO: fix disappearing profile text fields
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
                //TODO: implement functionality for Save and Cancel buttons once user decides to edit profile
                EditProfileButton(
                    onClick = {profileViewModel.editProfile()}
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
                ProfileGender(
                    value = uiState.gender ?: "",
                    onValueChange = profileViewModel::onGenderChanged
                )
                ProfileEthnicity(
                    value = uiState.ethnicity ?: "",
                    onValueChange = profileViewModel::onEthnicityChanged
                )
                ProfileCountry(
                    value = uiState.country ?: "",
                    onValueChange = profileViewModel::onCountryChanged
                )
                ProfileYear(
                    value = uiState.year ?: "",
                    onValueChange = profileViewModel::onYearChanged
                )
                ProfileGradYear(
                    value = uiState.gradYear ?: "",
                    onValueChange = profileViewModel::onGradYearChanged
                )
            }
        }
    }
}




//TODO: reformat necessary variables into dropdowns

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileGradYear(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.profile_cap),
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
                text = "GRADUATION YEAR",
                fontSize = 15.sp
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileYear(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.profile_year),
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
                text = "YEAR",
                fontSize = 15.sp
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCountry(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.profile_globe),
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
                text = "COUNTRY OF ORIGIN",
                fontSize = 15.sp
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEthnicity(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.profile_globe),
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
                text = "ETHNICITY",
                fontSize = 15.sp
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileGender(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.7f),
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.profile_gender_equality),
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
                text = "GENDER",
                fontSize = 15.sp
            )
        }
    )
}

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


@Composable
private fun EditProfileButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .wrapContentWidth() // Adjust the width of the button to wrap its content
            .height(35.dp), // Adjust the height of the button
        onClick = onClick,
        shape = RoundedCornerShape(20.dp), // Rounded corners
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF001627),
            contentColor = Color.White
        ),
    ){
        Row(
            //TODO: fix alignment
            horizontalArrangement = Arrangement.Center, // Center content horizontally
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Edit Profile",
                fontSize = 14.sp, // Adjust the font size
                color = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp)) // Add some space between icon and text
            Icon(
                painter = painterResource(id = R.drawable.edit_profile_pencil), // Replace with your pencil icon
                contentDescription = "Edit Profile Icon",
                modifier = Modifier.size(16.dp), // Adjust the size of the icon
                tint = Color.White // Set the color of the icon
            )
        }
    }
}