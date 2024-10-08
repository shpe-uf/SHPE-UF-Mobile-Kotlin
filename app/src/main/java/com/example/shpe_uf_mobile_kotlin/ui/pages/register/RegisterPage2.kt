package com.example.shpe_uf_mobile_kotlin.ui.pages.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.navigation.NavRoute


//@Preview
@Composable
fun RegistrationPage2Preview(navController: NavHostController, registerPage1ViewModel: RegisterPage1ViewModel){


    RegistrationPage2(registerPage1ViewModel = registerPage1ViewModel, navController = navController)

}


/*
Function is used for housing all the different separate components that
ultimately make up the final UI
 */
@Composable
fun RegistrationPage2(registerPage1ViewModel: RegisterPage1ViewModel, navController: NavHostController){

    val uiState by registerPage1ViewModel.uiState.collectAsState()

    RegisterPage2Background(navController)


    Box(
        modifier = Modifier
            .padding(top = 83.dp)
    ) {
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF011F35)),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            item{
                Spacer(modifier = Modifier.height(42.dp))
            }


            item{
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

            }

            item{

                // This is the start of the personal details text with the user profile image

                Spacer(modifier = Modifier.height(35.dp))

            }

            item{

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

            }

            item{

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

            }

            item{

                Spacer(modifier = Modifier.height(20.dp))
                // Ideal OG Figma padding made it less so that the bottom continue button would be visible.
//            Spacer(modifier = Modifier.height(64.dp))

            }

            item{
                Text(
                    text = "First Name",
                    fontSize = 16.sp,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 72.dp)
                        .fillMaxWidth()
                )

            }
            item{
                Spacer(modifier = Modifier.height(4.dp))
            }

            item{
                RegisterFirstName(
                    value = uiState.firstName ?: "",
                    isError = uiState.firstNameErrorMessage != null,
                    errorMessage = uiState.firstNameErrorMessage ?: "",
                    onValueChange = { registerPage1ViewModel.onFirstNameChanged(it) })

            }

            item{


//            Spacer(modifier = Modifier.height(12.dp))
                Spacer(modifier = Modifier.height(6.dp))

            }

            item{

                Text(
                    text = "Last Name",
                    fontSize = 16.sp,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 72.dp)
                        .fillMaxWidth()
                )

            }

            item{
                Spacer(modifier = Modifier.height(4.dp))
            }

            item{

                RegisterLastName(
                    value = uiState.lastName ?: "",
                    isError = uiState.lastNameErrorMessage != null,
                    errorMessage = uiState.lastNameErrorMessage ?: "",
                    onValueChange = { registerPage1ViewModel.onLastNameChanged(it) }
                )

            }

            item{

                //            Spacer(modifier = Modifier.height(12.dp))
                Spacer(modifier = Modifier.height(3.dp))

            }

            item{

                Text(
                    text = "Gender",
                    fontSize = 16.sp,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 72.dp)
                        .fillMaxWidth()
                )

            }

            item{

                Spacer(modifier = Modifier.height(4.dp))

            }


            item{

                GenderDropDownMenu(
                    value = uiState.gender ?: "",
                    isError = uiState.genderErrorMessage != null,
                    errorMessage = uiState.genderErrorMessage ?: "",
                    isGenderMenuExpanded = uiState.isGenderMenuExpanded,
                    onExpandedChange = {registerPage1ViewModel.toggleGenderMenuExpansion()},
                    onValueChange = { registerPage1ViewModel.onGenderChanged(it) }
                )

            }

            item{


//            Spacer(modifier = Modifier.height(12.dp))
                Spacer(modifier = Modifier.height(3.dp))

            }

            item{
                Text(
                    text = "Ethnicity",
                    fontSize = 16.sp,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 72.dp)
                        .fillMaxWidth()
                )

            }

            item{

                Spacer(modifier = Modifier.height(4.dp))

            }

            item{

                EthnicityDropDownMenu(
                    value = uiState.ethnicity ?: "",
                    isError = uiState.ethnicityErrorMessage != null,
                    errorMessage = uiState.ethnicityErrorMessage ?: "",
                    isEthnicityMenuExpanded = uiState.isEthnicityMenuExpanded,
                    onExpandedChange = {registerPage1ViewModel.toggleEthnicityMenuExpansion()},
                    onValueChange = { registerPage1ViewModel.onEthnicityChanged(it) }
                )

            }

            item{

                //            Spacer(modifier = Modifier.height(12.dp))
                Spacer(modifier = Modifier.height(3.dp))

            }

            item{
                Text(
                    text = "Country of Origin",
                    fontSize = 16.sp,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 72.dp)
                        .fillMaxWidth()
                )

            }

            item{

                Spacer(modifier = Modifier.height(4.dp))

            }

            item{

                CountryOriginDropDownMenu(
                    value = uiState.countryOrigin ?: "",
                    isError = uiState.countryOriginErrorMessage != null,
                    errorMessage = uiState.countryOriginErrorMessage ?: "",
                    isCountryOriginMenuExpanded = uiState.isCountryOriginMenuExpanded,
                    onExpandedChange = {registerPage1ViewModel.toggleCountryOriginMenuExpansion()},
                    onValueChange = { registerPage1ViewModel.onCountryOriginChanged(it) }
                )

            }

            item{

                //            Spacer(modifier = Modifier.height(40.dp))
                Spacer(modifier = Modifier.height(20.dp))

            }

            item{

                ContinueButton(
                    onClick = {
                        if (registerPage1ViewModel.validateRegisterPage2Fields() == true) {
                            navController.navigate(NavRoute.REGISTER_3)
                        }
                        else {
                            // Do nothing don't go to next page
                        }
                    }
                )

            }

            item{
                Spacer(modifier = Modifier.height(20.dp))
            }



        }


    }
}


//@Preview
@Composable
fun RegisterPage2Background(navController: NavController) {

    // Used boxes to layer things on top of each other to help arrange things as needed

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD25917))
    ) {
        // Image centered at the top
        Image(
            painter = painterResource(R.drawable.gatordark),
            contentDescription = "Gator",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .align(Alignment.TopCenter)
        )

        // BackButton positioned with padding
        Box(
            modifier = Modifier
                .padding(start = 22.dp, top = 35.dp)
                .align(Alignment.TopStart)
        ) {
            BackButtonPage2(
                onClick = { navController.navigate(NavRoute.REGISTER) }
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
//        .height(50.dp)
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
//            .height(50.dp)
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
            isError = isError,
            readOnly = true,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.gender_equality),
                    contentDescription = "GenderEqualityIcon",
                    modifier = Modifier.size(28.dp)
                )
            },
            shape = RoundedCornerShape(10.dp),
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
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGenderMenuExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
//                .height(50.dp)
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
            isError = isError,
            readOnly = true,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.globeicon),
                    contentDescription = "GlobeIcon",
                    modifier = Modifier.size(24.dp)
                )
            },
            shape = RoundedCornerShape(10.dp),

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
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isEthnicityMenuExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
//                .height(50.dp)
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
                    onValueChange("Black or African American")
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
            isError = isError,
            readOnly = true,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.globeicon),
                    contentDescription = "GlobeIcon",
                    modifier = Modifier.size(24.dp)
                )
            },
            shape = RoundedCornerShape(10.dp),

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
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCountryOriginMenuExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
//                .height(50.dp)
                .fillMaxWidth(0.7f)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isCountryOriginMenuExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = "Argentina")
                },
                onClick = {
                    onValueChange("Argentina")
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
            DropdownMenuItem(
                text = {
                    Text(text = "Austria")
                },
                onClick = {
                    onValueChange("Austria")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Belgium")
                },
                onClick = {
                    onValueChange("Belgium")
                    onExpandedChange(false)
                }
            )

            DropdownMenuItem(
                text = {
                    Text(text = "Brazil")
                },
                onClick = {
                    onValueChange("Brazil")
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
                    Text(text = "Chile")
                },
                onClick = {
                    onValueChange("Chile")
                    onExpandedChange(false)
                }
            )

            DropdownMenuItem(
                text = {
                    Text(text = "China")
                },
                onClick = {
                    onValueChange("China")
                    onExpandedChange(false)
                }
            )

            DropdownMenuItem(
                text = {
                    Text(text = "Czech Republic")
                },
                onClick = {
                    onValueChange("Czech Republic")
                    onExpandedChange(false)
                }
            )


            DropdownMenuItem(
                text = {
                    Text(text = "Denmark")
                },
                onClick = {
                    onValueChange("Denmark")
                    onExpandedChange(false)
                }
            )


            DropdownMenuItem(
                text = {
                    Text(text = "Egypt")
                },
                onClick = {
                    onValueChange("Egypt")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Finland")
                },
                onClick = {
                    onValueChange("Finland")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "France")
                },
                onClick = {
                    onValueChange("France")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Germany")
                },
                onClick = {
                    onValueChange("Germany")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Greece")
                },
                onClick = {
                    onValueChange("Greece")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Hungary")
                },
                onClick = {
                    onValueChange("Hungary")
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
                    Text(text = "Indonesia")
                },
                onClick = {
                    onValueChange("Indonesia")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Ireland")
                },
                onClick = {
                    onValueChange("Ireland")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Israel")
                },
                onClick = {
                    onValueChange("Israel")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Italy")
                },
                onClick = {
                    onValueChange("Italy")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Japan")
                },
                onClick = {
                    onValueChange("Japan")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Kenya")
                },
                onClick = {
                    onValueChange("Kenya")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Malaysia")
                },
                onClick = {
                    onValueChange("Malaysia")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Mexico")
                },
                onClick = {
                    onValueChange("Mexico")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Netherlands")
                },
                onClick = {
                    onValueChange("Netherlands")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "New Zealand")
                },
                onClick = {
                    onValueChange("New Zealand")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Nigeria")
                },
                onClick = {
                    onValueChange("Nigeria")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Norway")
                },
                onClick = {
                    onValueChange("Norway")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Pakistan")
                },
                onClick = {
                    onValueChange("Pakistan")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Philippines")
                },
                onClick = {
                    onValueChange("Philippines")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Poland")
                },
                onClick = {
                    onValueChange("Poland")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Portugal")
                },
                onClick = {
                    onValueChange("Portugal")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Romania")
                },
                onClick = {
                    onValueChange("Romania")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Russia")
                },
                onClick = {
                    onValueChange("Russia")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Saudi Arabia")
                },
                onClick = {
                    onValueChange("Saudi Arabia")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Singapore")
                },
                onClick = {
                    onValueChange("Singapore")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "South Africa")
                },
                onClick = {
                    onValueChange("South Africa")
                    onExpandedChange(false)
                }
            )

            DropdownMenuItem(
                text = {
                    Text(text = "South Korea")
                },
                onClick = {
                    onValueChange("South Korea")
                    onExpandedChange(false)
                }
            )

            DropdownMenuItem(
                text = {
                    Text(text = "Spain")
                },
                onClick = {
                    onValueChange("Spain")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Sweden")
                },
                onClick = {
                    onValueChange("Sweden")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Switzerland")
                },
                onClick = {
                    onValueChange("Switzerland")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Thailand")
                },
                onClick = {
                    onValueChange("Thailand")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Turkey")
                },
                onClick = {
                    onValueChange("Turkey")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Ukraine")
                },
                onClick = {
                    onValueChange("Ukraine")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "United Arab Emirates")
                },
                onClick = {
                    onValueChange("United Arab Emirates")
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "United Kingdom")
                },
                onClick = {
                    onValueChange("United Kingdom")
                    onExpandedChange(false)
                }
            )
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
                    Text(text = "Vietnam")
                },
                onClick = {
                    onValueChange("Vietnam")
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

@Composable
private fun BackButtonPage2(onClick: () -> Unit) {

    Image(
        painter = painterResource(id = R.drawable.backbuttonicon),
        contentDescription = "BackButtonIcon",
        modifier = Modifier
            .size(43.dp)
            .clickable(onClick = onClick)
    )
}




// SUPERIOR TEXT FIELD VERSION


//package com.example.shpe_uf_mobile_kotlin.ui.pages.register
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExposedDropdownMenuBox
//import androidx.compose.material3.ExposedDropdownMenuDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.shpe_uf_mobile_kotlin.R
//import com.example.shpe_uf_mobile_kotlin.ui.custom.SuperiorTextField
//
//
////@Preview
//@Composable
//fun RegistrationPage2Preview(navController: NavController, registerPage1ViewModel: RegisterPage1ViewModel){
//
//
//    RegistrationPage2(registerPage1ViewModel = registerPage1ViewModel, navController = navController)
//
//}
//
//
//// Used for testing. Not used in final version
//@Preview
//@Composable
//fun RegistrationPage2(){
//
//    Box(
//        modifier = Modifier
//            .padding(top = 83.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFF011F35)),
//            horizontalAlignment = Alignment.CenterHorizontally
//
//        ){
//            Spacer(modifier = Modifier.height(42.dp))
//
//            // This is the start of progression bar 2
//
//            Row{
//                Row{
//                    Box(
//                        modifier = Modifier
//                            .size(width = 106.dp, height = 5.dp)
//                            .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
//                    )
//
//                }
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                Row{
//                    Box(
//                        modifier = Modifier
//                            .size(width = 106.dp, height = 5.dp)
//                            .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
//                    )
//
//                }
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                Row{
//                    Box(
//                        modifier = Modifier
//                            .size(width = 106.dp, height = 5.dp)
//                            .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
//                    )
//                }
//            }
//
//            // This is the end of progression bar 2
//
//            // This is the start of the personal details text with the user profile image
//
//            Spacer(modifier = Modifier.height(35.dp))
//
//            Text(
//                text = "Enter your info to finalize your profile",
//                style = TextStyle(
//                    fontSize = 14.sp,
//                    fontFamily = FontFamily(Font(R.font.universltstd)),
//                    fontWeight = FontWeight(400),
//                    color = Color(0xFFFFFFFF)
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 30.dp)
//
//            )
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                Row{
//                    Text(
//                        text = "Personal Details",
//                        style = TextStyle(
//                            fontSize = 36.sp,
//                            fontFamily = FontFamily(Font(R.font.viga)),
//                            fontWeight = FontWeight(400),
//                            color = Color(0xFFD25917)
//                        )
//                    )
//                }
//
//                Spacer(modifier = Modifier.width(32.dp))
//
//                Row{
//                    Image(
//                        painter = painterResource(id = R.drawable.personaldetailsicon),
//                        contentDescription = "PersonalDetailsIcon",
//                        modifier = Modifier.size(50.dp)
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(54.dp))
//            // Ideal OG Figma padding made it less so that the bottom continue button would be visible.
////            Spacer(modifier = Modifier.height(64.dp))
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            RegisterFirstName(
//                value =  "",
//                isError = null == true,
//                errorMessage =  "",
//                onValueChange = { TODO() })
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            RegisterLastName(
//                value = "",
//                isError = null == true,
//                errorMessage =  "",
//                onValueChange = { TODO() }
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Text(
//                text = "Gender",
//                fontSize = 16.sp,
//                fontFamily = FontFamily(Font(R.font.universltstd)),
//                fontWeight = FontWeight(400),
//                color = Color(0xFFFFFFFF),
//                textAlign = TextAlign.Start,
//                modifier = Modifier
//                    .padding(start = 72.dp)
//                    .fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            GenderDropDownMenu(
//                value = "",
//                isError = null == true,
//                errorMessage = "",
//                onExpandedChange = { TODO()},
//                onValueChange = { TODO() }
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Text(
//                text = "Ethnicity",
//                fontSize = 16.sp,
//                color = Color(0xFFFFFFFF),
//                fontFamily = FontFamily(Font(R.font.universltstd)),
//                fontWeight = FontWeight(400),
//                textAlign = TextAlign.Start,
//                modifier = Modifier
//                    .padding(start = 72.dp)
//                    .fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            EthnicityDropDownMenu(
//                value =  "",
//                isError = null == true,
//                errorMessage =  "",
//                isEthnicityMenuExpanded = false,
//                onExpandedChange = {TODO()},
//                onValueChange = { TODO() }
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Text(
//                text = "Country of Origin",
//                fontSize = 16.sp,
//                color = Color(0xFFFFFFFF),
//                fontFamily = FontFamily(Font(R.font.universltstd)),
//                fontWeight = FontWeight(400),
//                textAlign = TextAlign.Start,
//                modifier = Modifier
//                    .padding(start = 72.dp)
//                    .fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            CountryOriginDropDownMenu(
//                value = "",
//                isError = null == true,
//                errorMessage =  "",
//                isCountryOriginMenuExpanded = true,
//                onExpandedChange = {TODO()},
//                onValueChange = { TODO() }
//            )
//
////            Spacer(modifier = Modifier.height(40.dp))
//            Spacer(modifier = Modifier.height(30.dp))
//
//
//            ContinueButton(
//                onClick = {
////                    if (registerPage1ViewModel.validateRegisterPage2Fields() == true) {
////                        navController.navigate(RegisterRoutes.registerPage3)
////                    }
////                    else {
////                        // Do nothing don't go to next page
////                    }
//                }
//            )
//        }
//
//
//    }
//}
//
//
//
///*
//Function is used for housing all the different separate components that
//ultimately make up the final UI
// */
//@Composable
//fun RegistrationPage2(registerPage1ViewModel: RegisterPage1ViewModel, navController: NavController){
//
//    val uiState by registerPage1ViewModel.uiState.collectAsState()
//
//    RegisterPage2Background(navController)
//
//
//    Box(
//        modifier = Modifier
//            .padding(top = 83.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFF011F35)),
//            horizontalAlignment = Alignment.CenterHorizontally
//
//        ){
//            Spacer(modifier = Modifier.height(42.dp))
//
//            // This is the start of progression bar 2
//
//            Row{
//                Row{
//                    Box(
//                        modifier = Modifier
//                            .size(width = 106.dp, height = 5.dp)
//                            .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
//                    )
//
//                }
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                Row{
//                    Box(
//                        modifier = Modifier
//                            .size(width = 106.dp, height = 5.dp)
//                            .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
//                    )
//
//                }
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                Row{
//                    Box(
//                        modifier = Modifier
//                            .size(width = 106.dp, height = 5.dp)
//                            .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
//                    )
//                }
//            }
//
//            // This is the end of progression bar 2
//
//            // This is the start of the personal details text with the user profile image
//
//            Spacer(modifier = Modifier.height(35.dp))
//
//            Text(
//                text = "Enter your info to finalize your profile",
//                style = TextStyle(
//                    fontSize = 14.sp,
//                    fontFamily = FontFamily(Font(R.font.universltstd)),
//                    fontWeight = FontWeight(400),
//                    color = Color(0xFFFFFFFF)
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 30.dp)
//
//            )
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                Row{
//                    Text(
//                        text = "Personal Details",
//                        style = TextStyle(
//                            fontSize = 36.sp,
//                            fontFamily = FontFamily(Font(R.font.viga)),
//                            fontWeight = FontWeight(400),
//                            color = Color(0xFFD25917)
//                        )
//                    )
//                }
//
//                Spacer(modifier = Modifier.width(32.dp))
//
//                Row{
//                    Image(
//                        painter = painterResource(id = R.drawable.personaldetailsicon),
//                        contentDescription = "PersonalDetailsIcon",
//                        modifier = Modifier.size(50.dp)
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(54.dp))
//            // Ideal OG Figma padding made it less so that the bottom continue button would be visible.
////            Spacer(modifier = Modifier.height(64.dp))
//
//
//            Text(
//                text = "First Name",
//                fontSize = 16.sp,
//                color = Color(0xFFFFFFFF),
//                textAlign = TextAlign.Start,
//                modifier = Modifier
//                    .padding(start = 72.dp)
//                    .fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            RegisterFirstName(
//                value = uiState.firstName ?: "",
//                isError = uiState.firstNameErrorMessage != null,
//                errorMessage = uiState.firstNameErrorMessage ?: "",
//                onValueChange = { registerPage1ViewModel.onFirstNameChanged(it) })
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = "Last Name",
//                fontSize = 16.sp,
//                color = Color(0xFFFFFFFF),
//                textAlign = TextAlign.Start,
//                modifier = Modifier
//                    .padding(start = 72.dp)
//                    .fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            RegisterLastName(
//                value = uiState.lastName ?: "",
//                isError = uiState.lastNameErrorMessage != null,
//                errorMessage = uiState.lastNameErrorMessage ?: "",
//                onValueChange = { registerPage1ViewModel.onLastNameChanged(it) }
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = "Gender",
//                fontSize = 16.sp,
//                fontFamily = FontFamily(Font(R.font.universltstd)),
//                fontWeight = FontWeight(400),
//                color = Color(0xFFFFFFFF),
//                textAlign = TextAlign.Start,
//                modifier = Modifier
//                    .padding(start = 72.dp)
//                    .fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            GenderDropDownMenu(
//                value = uiState.gender ?: "",
//                isError = uiState.genderErrorMessage != null,
//                errorMessage = uiState.genderErrorMessage ?: "",
//                isGenderMenuExpanded = uiState.isGenderMenuExpanded,
//                onExpandedChange = {registerPage1ViewModel.toggleGenderMenuExpansion()},
//                onValueChange = { registerPage1ViewModel.onGenderChanged(it) }
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = "Ethnicity",
//                fontSize = 16.sp,
//                fontFamily = FontFamily(Font(R.font.universltstd)),
//                fontWeight = FontWeight(400),
//                color = Color(0xFFFFFFFF),
//                textAlign = TextAlign.Start,
//                modifier = Modifier
//                    .padding(start = 72.dp)
//                    .fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            EthnicityDropDownMenu(
//                value = uiState.ethnicity ?: "",
//                isError = uiState.ethnicityErrorMessage != null,
//                errorMessage = uiState.ethnicityErrorMessage ?: "",
//                isEthnicityMenuExpanded = uiState.isEthnicityMenuExpanded,
//                onExpandedChange = {registerPage1ViewModel.toggleEthnicityMenuExpansion()},
//                onValueChange = { registerPage1ViewModel.onEthnicityChanged(it) }
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = "Country of Origin",
//                fontSize = 16.sp,
//                fontFamily = FontFamily(Font(R.font.universltstd)),
//                fontWeight = FontWeight(400),
//                color = Color(0xFFFFFFFF),
//                textAlign = TextAlign.Start,
//                modifier = Modifier
//                    .padding(start = 72.dp)
//                    .fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            CountryOriginDropDownMenu(
//                value = uiState.countryOrigin ?: "",
//                isError = uiState.countryOriginErrorMessage != null,
//                errorMessage = uiState.countryOriginErrorMessage ?: "",
//                isCountryOriginMenuExpanded = uiState.isCountryOriginMenuExpanded,
//                onExpandedChange = {registerPage1ViewModel.toggleCountryOriginMenuExpansion()},
//                onValueChange = { registerPage1ViewModel.onCountryOriginChanged(it) }
//            )
//
////            Spacer(modifier = Modifier.height(40.dp))
//            Spacer(modifier = Modifier.height(30.dp))
//
//
//            ContinueButton(
//                onClick = {
//                    if (registerPage1ViewModel.validateRegisterPage2Fields()) {
//                        navController.navigate(RegisterRoutes.registerPage3)
//                    }
//                    else {
//                        // Do nothing don't go to next page
//                    }
//                }
//            )
//        }
//
//
//    }
//}
//
//
////@Preview
//@Composable
//fun RegisterPage2Background(navController: NavController) {
//
//    // Used boxes to layer things on top of each other to help arrange things as needed
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFD25917))
//    ) {
//        // Image centered at the top
//        Image(
//            painter = painterResource(R.drawable.gatordark),
//            contentDescription = "Gator",
//            contentScale = ContentScale.FillWidth,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 48.dp)
//                .align(Alignment.TopCenter)
//        )
//
//        // BackButton positioned with padding
//        Box(
//            modifier = Modifier
//                .padding(start = 22.dp, top = 35.dp)
//                .align(Alignment.TopStart)
//        ) {
//            BackButtonPage2(
//                onClick = { navController.navigate(RegisterRoutes.registerPage1) }
//            )
//        }
//    }
//}
//
//
////@Preview
////@Composable
////fun RegistrationPage2ProgressionBar(modifier: Modifier = Modifier){
////
////    Column(
////        modifier = Modifier
////            .fillMaxSize(),
////        horizontalAlignment = Alignment.CenterHorizontally
////
////    ){
////
////        Spacer(modifier = Modifier.height(132.dp))
////
////        Row{
////            Row{
////                Box(
////                    modifier = Modifier
////                        .size(width = 106.dp, height = 5.dp)
////                        .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
////                )
////
////            }
////
////            Spacer(modifier = Modifier.width(8.dp))
////
////            Row{
////                Box(
////                    modifier = Modifier
////                        .size(width = 106.dp, height = 5.dp)
////                        .background(Color(0xFFD25917), shape = RoundedCornerShape(1.dp))
////                )
////
////            }
////
////            Spacer(modifier = Modifier.width(8.dp))
////
////            Row{
////                Box(
////                    modifier = Modifier
////                        .size(width = 106.dp, height = 5.dp)
////                        .background(Color(0xFF999999), shape = RoundedCornerShape(1.dp))
////                )
////            }
////        }
////    }
////}
//
//
////@Preview
////@Composable
////fun RegistrationPage2PersonalDetailsText(modifier: Modifier = Modifier){
////    Column(
////        modifier = Modifier
////            .fillMaxSize()
////            .padding(start = 30.dp)
////
////    ){
////
////        Spacer(modifier = Modifier.height(174.dp))
////
////        Text(
////            text = "Enter your info to finalize your profile",
////            style = TextStyle(
////                fontSize = 14.sp,
////                fontWeight = FontWeight(400),
////                color = Color(0xFFFFFFFF)
////            )
////        )
////
////        Row(
////            verticalAlignment = Alignment.CenterVertically
////        ){
////            Row{
////                Text(
////                    text = "Personal Details",
////                    style = TextStyle(
////                        fontSize = 36.sp,
////                        fontWeight = FontWeight(400),
////                        color = Color(0xFFD25917)
////                    )
////                )
////            }
////
////            Spacer(modifier = Modifier.width(32.dp))
////
////            Row{
////                Image(
////                    painter = painterResource(id = R.drawable.personaldetailsicon),
////                    contentDescription = "PersonalDetailsIcon",
////                    modifier = Modifier.size(50.dp)
////                )
////            }
////        }
////    }
////}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun RegisterFirstName(
//
//    /* Below are parameters for the function RegisterFirstName, which is responsible for rendering
//    a text field to allow users to register their first name for an account. value is used for the
//    current value of th email field, isError is used as a bool to indicated whether a user has made
//    an error while filling out the first name field, errorMessage is used to store the error
//    message to be displayed if there is an error, and onValueChange is a callback function invoked
//    when the value of the first name field changes
//     */
//
//    value: String,
//    isError: Boolean,
//    errorMessage: String,
//    onValueChange: (String) -> Unit
//){
//
//    SuperiorTextField(
//        label = "First Name",
//        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
//        width = 270.dp,
//        height = 37.64706.dp,
//        value = value,
//        onValueChange = onValueChange,
//        leadingIcon = R.drawable.profile_circle,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        leadingIconModifier = Modifier.size(32.dp).padding(start = 12.dp),
//        isError = isError,
//        errorMessage = errorMessage,
//    )
//
////    TextField(modifier = Modifier
//////        .height(50.dp)
////        .fillMaxWidth(0.7f),
////        value = value,
////        onValueChange = {onValueChange(it)},
////        isError = isError,
////
////        leadingIcon = {
////            Image(
////                painter = painterResource(id = R.drawable.usericon),
////                contentDescription = "UserIcon",
////                modifier = Modifier.size(24.dp)
////            )
////        },
////        shape = RoundedCornerShape(10.dp),
////        singleLine = true,
////        keyboardOptions = KeyboardOptions(
////            keyboardType = KeyboardType.Text
////        ),
////        supportingText = {
////            if (isError) {
////                Text(
////                    modifier = Modifier.fillMaxWidth(),
////                    text = errorMessage,
////                    color = MaterialTheme.colorScheme.error
////                )
////            }
////        },
////        trailingIcon = {
////            if (isError)
////                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
////        }
////
////    )
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun RegisterLastName(
//    value: String,
//    isError: Boolean,
//    errorMessage: String,
//    onValueChange: (String) -> Unit
//) {
//
//    SuperiorTextField(
//        label = "Last Name",
//        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
//        width = 270.dp,
//        height = 37.64706.dp,
//        value = value,
//        onValueChange = onValueChange,
//        leadingIcon = R.drawable.profile_circle,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        leadingIconModifier = Modifier.size(32.dp).padding(start = 12.dp),
//        isError = isError,
//        errorMessage = errorMessage,
//    )
//
////    TextField(
////        modifier = Modifier
//////            .height(50.dp)
////            .fillMaxWidth(0.7f),
////        value = value,
////        onValueChange = {onValueChange(it)},
////        isError = isError,
////        leadingIcon = {
////            Image(
////                painter = painterResource(id = R.drawable.usericon),
////                contentDescription = "UserIcon",
////                modifier = Modifier.size(24.dp)
////            )
////        },
////        shape = RoundedCornerShape(10.dp),
////        singleLine = true,
////        keyboardOptions = KeyboardOptions(
////            keyboardType = KeyboardType.Text
////        ),
////        supportingText = {
////
////            if (isError) {
////                Text(
////                    modifier = Modifier.fillMaxWidth(),
////                    text = errorMessage,
////                    color = MaterialTheme.colorScheme.error
////                )
////            }
////        },
////        trailingIcon = {
////            if (isError)
////                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
////        }
////    )
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun GenderDropDownMenu(
//    value: String,
//    isError: Boolean,
//    errorMessage: String,
//    isGenderMenuExpanded: Boolean = false,
//    onExpandedChange: (Boolean) -> Unit,
//    onValueChange: (String) -> Unit,
//){
//    ExposedDropdownMenuBox(
//        expanded = isGenderMenuExpanded,
//        onExpandedChange = {onExpandedChange(it)}
//    ) {
////        SuperiorTextField(
////            label = "Gender",
////            labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
////            onValueChange = {},
////            value = value,
////            leadingIcon = R.drawable.gender_equality,
////            readOnly = true,
////            _trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGenderMenuExpanded) },
////            )
//        TextField(
//            value = value,
//            onValueChange = {},
//            isError = isError,
//            readOnly = true,
//            leadingIcon = {
//                Image(
//                    painter = painterResource(id = R.drawable.gender_equality),
//                    contentDescription = "GenderEqualityIcon",
//                    modifier = Modifier.size(28.dp)
//                )
//            },
//            shape = RoundedCornerShape(10.dp),
//            supportingText = {
//                if (isError) {
//                    Text(
//                        modifier = Modifier.fillMaxWidth(),
//                        text = errorMessage,
//                        color = MaterialTheme.colorScheme.error
//                    )
//                }
//            },
//            trailingIcon = {
//                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGenderMenuExpanded)
//            },
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = Color.White,
//                unfocusedContainerColor = Color.White
//            ),
//            modifier = Modifier
////                .height(50.dp)
//                .height(37.64706.dp)
//                .width(270.dp)
//                .fillMaxWidth(0.7f)
//                .menuAnchor()
//        )
//        ExposedDropdownMenu(
//            expanded = isGenderMenuExpanded,
//            onDismissRequest = { onExpandedChange(false) }
//        ) {
//            DropdownMenuItem(
//                text = {
//                       Text(text = "Male")
//                },
//                onClick = {
//                    onValueChange("Male")
//                    onExpandedChange(false)
//                }
//            )
//            DropdownMenuItem(
//                text = {
//                    Text(text = "Female")
//                },
//                onClick = {
//                    onValueChange("Female")
//                    onExpandedChange(false)
//                }
//            )
//            DropdownMenuItem(
//                text = {
//                    Text(text = "Other")
//                },
//                onClick = {
//                    onValueChange("Other")
//                    onExpandedChange(false)
//                }
//            )
//        }
//    }
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun EthnicityDropDownMenu(
//    value: String,
//    isError: Boolean,
//    errorMessage: String,
//    isEthnicityMenuExpanded: Boolean,
//    onExpandedChange: (Boolean) -> Unit,
//    onValueChange: (String) -> Unit,
//){
//    ExposedDropdownMenuBox(
//        expanded = isEthnicityMenuExpanded,
//        onExpandedChange = {onExpandedChange(it)}
//    ) {
//        TextField(
//            value = value,
//            onValueChange = {},
//            isError = isError,
//            readOnly = true,
//            leadingIcon = {
//                Image(
//                    painter = painterResource(id = R.drawable.globeicon),
//                    contentDescription = "GlobeIcon",
//                    modifier = Modifier.size(24.dp)
//                )
//            },
//            shape = RoundedCornerShape(10.dp),
//
//            supportingText = {
//
//                if (isError) {
//                    Text(
//                        modifier = Modifier.fillMaxWidth(),
//                        text = errorMessage,
//                        color = MaterialTheme.colorScheme.error
//                    )
//                }
//            },
//
//            trailingIcon = {
//                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isEthnicityMenuExpanded)
//            },
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = Color.White,
//                unfocusedContainerColor = Color.White
//            ),
//            modifier = Modifier
////                .height(50.dp)
//                .height(37.64706.dp)
//                .width(270.dp)
//                .fillMaxWidth(0.7f)
//                .menuAnchor()
//        )
//        ExposedDropdownMenu(
//            expanded = isEthnicityMenuExpanded,
//            onDismissRequest = { onExpandedChange(false) }
//        ) {
//            DropdownMenuItem(
//                text = {
//                    Text(text = "American Indian or Alaska Native")
//                },
//                onClick = {
//                    onValueChange("American Indian or Alaska Native")
//                    onExpandedChange(false)
//                }
//            )
//            DropdownMenuItem(
//                text = {
//                    Text(text = "Asian")
//                },
//                onClick = {
//                    onValueChange("Asian")
//                    onExpandedChange(false)
//                }
//            )
//            DropdownMenuItem(
//                text = {
//                    Text(text = "Black or African American")
//                },
//                onClick = {
//                    onValueChange("Other")
//                    onExpandedChange(false)
//                }
//            )
//            DropdownMenuItem(
//                text = {
//                    Text(text = "Hispanic/Latino")
//                },
//                onClick = {
//                    onValueChange("Hispanic/Latino")
//                    onExpandedChange(false)
//                }
//            )
//            DropdownMenuItem(
//                text = {
//                    Text(text = "Native Hawaiian or Other Pacific Islander")
//                },
//                onClick = {
//                    onValueChange("Native Hawaiian or Other Pacific Islander")
//                    onExpandedChange(false)
//                }
//            )
//            DropdownMenuItem(
//                text = {
//                    Text(text = "White")
//                },
//                onClick = {
//                    onValueChange("White")
//                    onExpandedChange(false)
//                }
//            )
//            DropdownMenuItem(
//                text = {
//                    Text(text = "Two or more ethnicities")
//                },
//                onClick = {
//                    onValueChange("Two or more ethnicities")
//                    onExpandedChange(false)
//                }
//            )
//            DropdownMenuItem(
//                text = {
//                    Text(text = "Prefer not to answer")
//                },
//                onClick = {
//                    onValueChange("Prefer not to answer")
//                    onExpandedChange(false)
//                }
//            )
//        }
//    }
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun CountryOriginDropDownMenu(
//    value: String,
//    isError: Boolean,
//    errorMessage: String,
//    isCountryOriginMenuExpanded: Boolean,
//    onExpandedChange: (Boolean) -> Unit,
//    onValueChange: (String) -> Unit,
//){
//    ExposedDropdownMenuBox(
//        expanded = isCountryOriginMenuExpanded,
//        onExpandedChange = {onExpandedChange(it)}
//    ) {
//        TextField(
//            value = value,
//            onValueChange = {},
//            isError = isError,
//            readOnly = true,
//            leadingIcon = {
//                Image(
//                    painter = painterResource(id = R.drawable.globeicon),
//                    contentDescription = "GlobeIcon",
//                    modifier = Modifier.size(24.dp)
//                )
//            },
//            shape = RoundedCornerShape(10.dp),
//
//            supportingText = {
//
//                if (isError) {
//                    Text(
//                        modifier = Modifier.fillMaxWidth(),
//                        text = errorMessage,
//                        color = MaterialTheme.colorScheme.error
//                    )
//                }
//            },
//
//            trailingIcon = {
//                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCountryOriginMenuExpanded)
//            },
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = Color.White,
//                unfocusedContainerColor = Color.White
//            ),
//            modifier = Modifier
////                .height(50.dp)
//                .height(37.64706.dp)
//                .width(270.dp)
//                .fillMaxWidth(0.7f)
//                .menuAnchor()
//        )
//        ExposedDropdownMenu(
//            expanded = isCountryOriginMenuExpanded,
//            onDismissRequest = { onExpandedChange(false) }
//        ) {
//            DropdownMenuItem(
//                text = {
//                    Text(text = "United States")
//                },
//                onClick = {
//                    onValueChange("United States")
//                    onExpandedChange(false)
//                }
//            )
//            DropdownMenuItem(
//                text = {
//                    Text(text = "India")
//                },
//                onClick = {
//                    onValueChange("India")
//                    onExpandedChange(false)
//                }
//            )
//            DropdownMenuItem(
//                text = {
//                    Text(text = "Canada")
//                },
//                onClick = {
//                    onValueChange("Canada")
//                    onExpandedChange(false)
//                }
//            )
//            DropdownMenuItem(
//                text = {
//                    Text(text = "Australia")
//                },
//                onClick = {
//                    onValueChange("Australia")
//                    onExpandedChange(false)
//                }
//            )
//
//        }
//    }
//}
//
//
//
//
//
//@Composable
//private fun ContinueButton(
//    onClick: () -> Unit) {
//
//    Button(
//        modifier = Modifier
//            .width(351.dp),
//        onClick = {
//            onClick()
//        },
//        contentPadding = PaddingValues(),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = Color.Transparent
//        ),
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    color = Color(0xFFD25917),
//
//                    )
//                .padding(horizontal = 20.dp, vertical = 10.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Continue",
//                fontSize = 20.sp,
//                color = Color.White
//            )
//        }
//    }
//}
//
//@Composable
//private fun BackButtonPage2(onClick: () -> Unit) {
//
//    Image(
//        painter = painterResource(id = R.drawable.backbuttonicon),
//        contentDescription = "BackButtonIcon",
//        modifier = Modifier
//            .size(43.dp)
//            .clickable(onClick = onClick)
//    )
//}