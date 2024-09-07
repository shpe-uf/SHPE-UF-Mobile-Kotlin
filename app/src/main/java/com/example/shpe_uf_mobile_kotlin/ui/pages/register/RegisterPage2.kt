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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shpe_uf_mobile_kotlin.R


//@Preview
@Composable
fun RegistrationPage2Preview(navController: NavController, registerPage1ViewModel: RegisterPage1ViewModel){

//    val viewModel = RegisterPage1ViewModel()

    RegistrationPage2(registerPage1ViewModel = registerPage1ViewModel, navController = navController)

}


/*
Function is used for housing all the different separate components that
ultimately make up the final UI
 */
@Composable
fun RegistrationPage2(registerPage1ViewModel: RegisterPage1ViewModel, navController: NavController){

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

            // This is the start of progression bar 2

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
                            .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
                    )
                }
            }

            // This is the end of progression bar 2

            // This is the start of the personal details text with the user profile image

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "Enter your info to finalize your profile",
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
                        text = "Personal Details",
                        style = TextStyle(
                            fontSize = 36.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFD25917)
                        )
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))

                Row{
                    Image(
                        painter = painterResource(id = R.drawable.personaldetailsicon),
                        contentDescription = "PersonalDetailsIcon",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(64.dp))


            Text(
                text = "First Name",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            RegisterFirstName(
                value = uiState.firstName ?: "",
                isError = uiState.firstNameErrorMessage != null,
                errorMessage = uiState.firstNameErrorMessage ?: "",
                onValueChange = { registerPage1ViewModel.onFirstNameChanged(it) })

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Last Name",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            RegisterLastName(
                value = uiState.lastName ?: "",
                isError = uiState.lastName != null,
                errorMessage = uiState.lastNameErrorMessage ?: "",
                onValueChange = { registerPage1ViewModel.onLastNameChanged(it) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Gender",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            GenderDropDownMenu(
                value = uiState.gender ?: "",
                isError = uiState.gender != null,
                errorMessage = uiState.genderErrorMessage ?: "",
                isGenderMenuExpanded = uiState.isGenderMenuExpanded,
                onExpandedChange = {registerPage1ViewModel.toggleGenderMenuExpansion()},
                onValueChange = { registerPage1ViewModel.onGenderChanged(it) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Ethnicity",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            EthnicityDropDownMenu(
                value = uiState.ethnicity ?: "",
                isError = uiState.ethnicity != null,
                errorMessage = uiState.ethnicityErrorMessage ?: "",
                isEthnicityMenuExpanded = uiState.isEthnicityMenuExpanded,
                onExpandedChange = {registerPage1ViewModel.toggleEthnicityMenuExpansion()},
                onValueChange = { registerPage1ViewModel.onEthnicityChanged(it) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Country of Origin",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            CountryOriginDropDownMenu(
                value = uiState.countryOrigin ?: "",
                isError = uiState.countryOrigin != null,
                errorMessage = uiState.countryOriginErrorMessage ?: "",
                isCountryOriginMenuExpanded = uiState.isCountryOriginMenuExpanded,
                onExpandedChange = {registerPage1ViewModel.toggleCountryOriginMenuExpansion()},
                onValueChange = { registerPage1ViewModel.onCountryOriginChanged(it) }
            )


            Spacer(modifier = Modifier.height(40.dp))


            ContinueButton(
//            onClick = { // TODO Navigate to final registration page
                // This on click should be used for very final view when slicking complete registration button
                onClick = { navController.navigate(RegisterRoutes.registerPage3) }
//                onClick = { registerPage1ViewModel.validateAndRegisterUser() }
            )
        }


    }



}


//@Preview
//@Composable
//fun RegistrationPage2ProgressionBar(modifier: Modifier = Modifier){
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
//                        .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
//                )
//            }
//        }
//    }
//}


//@Preview
//@Composable
//fun RegistrationPage2PersonalDetailsText(modifier: Modifier = Modifier){
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
//            text = "Enter your info to finalize your profile",
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
//                    text = "Personal Details",
//                    style = TextStyle(
//                        fontSize = 36.sp,
//                        fontWeight = FontWeight(400),
//                        color = Color(0xFFD25917)
//                    )
//                )
//            }
//
//            Spacer(modifier = Modifier.width(32.dp))
//
//            Row{
//                Image(
//                    painter = painterResource(id = R.drawable.personaldetailsicon),
//                    contentDescription = "PersonalDetailsIcon",
//                    modifier = Modifier.size(50.dp)
//                )
//            }
//        }
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterFirstName(

    /* Below are parameters for the function RegisterFirstName, which is responsible for rendering
    a text field to allow users to register their first name for an account. value is used for the
    current value of th email field, isError is used as a bool to indicated whether a user has made
    an error while filling out the first name field, errorMessage is used to store the error
    message to be displayed if there is an error, and onValueChange is a callback function invoked
    when the value of the first name field changes
     */

    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit
){


    TextField(modifier = Modifier
        .height(50.dp)
        .fillMaxWidth(0.7f),
        value = value,
        onValueChange = {onValueChange(it)},
        isError = isError,

        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.usericon),
                contentDescription = "UserIcon",
                modifier = Modifier.size(24.dp)
            )
        },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (isError)
                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
        }

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterLastName(
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(0.7f),
        value = value,
        onValueChange = {onValueChange(it)},
        isError = isError,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.usericon),
                contentDescription = "UserIcon",
                modifier = Modifier.size(24.dp)
            )
        },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (isError)
                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GenderDropDownMenu(
    value: String,
    isError: Boolean,
    errorMessage: String,
    isGenderMenuExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
){
    ExposedDropdownMenuBox(
        expanded = isGenderMenuExpanded,
        onExpandedChange = {onExpandedChange(it)}
    ) {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.gender_equality),
                    contentDescription = "GenderEqualityIcon",
                    modifier = Modifier.size(28.dp)
                )
            },
            shape = RoundedCornerShape(10.dp),

            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGenderMenuExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(0.7f)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isGenderMenuExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                text = {
                       Text(text = "Male")
                },
                onClick = {
                    onValueChange("Male")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Female")
                },
                onClick = {
                    onValueChange("Female")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Other")
                },
                onClick = {
                    onValueChange("Other")
                    onExpandedChange(false)
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EthnicityDropDownMenu(
    value: String,
    isError: Boolean,
    errorMessage: String,
    isEthnicityMenuExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
){
    ExposedDropdownMenuBox(
        expanded = isEthnicityMenuExpanded,
        onExpandedChange = {onExpandedChange(it)}
    ) {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.globeicon),
                    contentDescription = "GlobeIcon",
                    modifier = Modifier.size(24.dp)
                )
            },
            shape = RoundedCornerShape(10.dp),

            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isEthnicityMenuExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(0.7f)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isEthnicityMenuExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = "American Indian or Alaska Native")
                },
                onClick = {
                    onValueChange("American Indian or Alaska Native")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Asian")
                },
                onClick = {
                    onValueChange("Asian")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Black or African American")
                },
                onClick = {
                    onValueChange("Other")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Hispanic/Latino")
                },
                onClick = {
                    onValueChange("Hispanic/Latino")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Native Hawaiian or Other Pacific Islander")
                },
                onClick = {
                    onValueChange("Native Hawaiian or Other Pacific Islander")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "White")
                },
                onClick = {
                    onValueChange("White")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Two or more ethnicities")
                },
                onClick = {
                    onValueChange("Two or more ethnicities")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Prefer not to answer")
                },
                onClick = {
                    onValueChange("Prefer not to answer")
                    onExpandedChange(false)
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountryOriginDropDownMenu(
    value: String,
    isError: Boolean,
    errorMessage: String,
    isCountryOriginMenuExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
){
    ExposedDropdownMenuBox(
        expanded = isCountryOriginMenuExpanded,
        onExpandedChange = {onExpandedChange(it)}
    ) {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.globeicon),
                    contentDescription = "GlobeIcon",
                    modifier = Modifier.size(24.dp)
                )
            },
            shape = RoundedCornerShape(10.dp),

            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCountryOriginMenuExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(0.7f)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isCountryOriginMenuExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = "United States")
                },
                onClick = {
                    onValueChange("United States")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "India")
                },
                onClick = {
                    onValueChange("India")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Canada")
                },
                onClick = {
                    onValueChange("Canada")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Australia")
                },
                onClick = {
                    onValueChange("Australia")
                    onExpandedChange(false)
                }
            )

        }
    }
}





@Composable
private fun ContinueButton(
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
                text = "Continue",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}