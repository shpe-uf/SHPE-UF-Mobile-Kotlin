package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
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

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    val viewModel = ProfileViewModel()
    ProfileScreen(viewModel)
}

//TODO: set background to be Figma gator
//TODO: add dark mode functionality
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel){
    SHPEUFMobileKotlinTheme{
        val uiState by profileViewModel.uiState.collectAsState()
        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 30.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            Column {
                //TODO: display user's profile picture when they upload it
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .height(95.dp)
                        .fillMaxSize()
                )
            }
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
            ){
                //TODO: implement functionality for Save and Cancel buttons once user decides to edit profile
                GradientButton(
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




//TODO: change necessary icons for each profile variable
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
                text = "Graduation Year",
                fontSize = 18.sp
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
                text = "Year",
                fontSize = 18.sp
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
                text = "Country of Origin",
                fontSize = 18.sp
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
                text = "Ethnicity",
                fontSize = 18.sp
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileGender(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
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
                text = "Gender",
                fontSize = 18.sp
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
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        label = {
            Text(
                text = "Email",
                fontSize = 18.sp
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
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        label = {
            Text(
                text = "Username",
                fontSize = 18.sp
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
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        label = {
            Text(
                text = "Name",
                fontSize = 18.sp
            )
        }
    )
}

@Composable
private fun GradientButton(
    onClick: () -> Unit
) {
    val gradientColors = listOf(Color(0xFFD23C20), Color(0xFFF1652F))
    Button(
        modifier = Modifier
            .fillMaxWidth(),
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
//                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                )
                .padding(horizontal = 20.dp, vertical = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Edit Profile",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}
