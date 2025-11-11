package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.shpeuf.shpe_uf_mobile_kotlin.R
import com.shpeuf.shpe_uf_mobile_kotlin.ui.custom.SuperiorDropdownMenu
import com.shpeuf.shpe_uf_mobile_kotlin.ui.custom.SuperiorTextField
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.NavRoute


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
            Spacer(modifier = Modifier.height(38.dp))
            }

            item{
                RegisterFirstName(
                    value = uiState.firstName ?: "",
                    isError = uiState.firstNameErrorMessage != null,
                    errorMessage = uiState.firstNameErrorMessage ?: "",
                    onValueChange = { registerPage1ViewModel.onFirstNameChanged(it) })

            }

            item{
                Spacer(modifier = Modifier.height(20.dp))
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
                Spacer(modifier = Modifier.height(20.dp))
            }

            item{
                GenderDropDownMenu(
                    value = uiState.gender ?: "",
                    isError = uiState.genderErrorMessage != null,
                    errorMessage = uiState.genderErrorMessage ?: "",
                    onValueChange = { registerPage1ViewModel.onGenderChanged(it) }
                )

            }

            item{
                Spacer(modifier = Modifier.height(20.dp))
            }

            item{

                EthnicityDropDownMenu(
                    value = uiState.ethnicity ?: "",
                    isError = uiState.ethnicityErrorMessage != null,
                    errorMessage = uiState.ethnicityErrorMessage ?: "",
                    onValueChange = { registerPage1ViewModel.onEthnicityChanged(it) }
                )

            }

            item{
                Spacer(modifier = Modifier.height(20.dp))
            }

            item{

                CountryOriginDropDownMenu(
                    value = uiState.countryOrigin ?: "",
                    isError = uiState.countryOriginErrorMessage != null,
                    errorMessage = uiState.countryOriginErrorMessage ?: "",
                    onValueChange = { registerPage1ViewModel.onCountryOriginChanged(it) }
                )

            }

            item{
                Spacer(modifier = Modifier.height(22.dp))
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

    SuperiorTextField(
        label = "First Name",
        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = R.drawable.profile_circle,
        iconTrailingIcon = {
            if (isError)
                Icon(
                    Icons.Filled.Error,
                    "error",
                    tint = Color.Red,
                    modifier = Modifier.padding(end = 8.dp)
                ) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIconModifier = Modifier.size(32.dp).padding(start = 12.dp),
        isError = isError,
        errorMessage = errorMessage,
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

    SuperiorTextField(
        label = "Last Name",
        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = R.drawable.profile_circle,
        iconTrailingIcon = {
            if (isError)
                Icon(
                    Icons.Filled.Error,
                    "error",
                    tint = Color.Red,
                    modifier = Modifier.padding(end = 8.dp)
                ) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIconModifier = Modifier.size(32.dp).padding(start = 12.dp),
        isError = isError,
        errorMessage = errorMessage,
    )
}

@Composable
private fun GenderDropDownMenu(
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit

){
    val options = listOf("Male", "Female", "Other", "Prefer not to answer")

    SuperiorDropdownMenu(
        label = "Gender",
        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
        selectedOption = value,
        options = options,
        onOptionSelected = onValueChange,
        leadingIcon = R.drawable.gender_equality,
        leadingIconModifier = Modifier.size(32.dp),
        isError = isError,
        errorMessage = errorMessage
    )
}

@Composable
private fun EthnicityDropDownMenu(
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit
){
    val options = listOf(
        "American Indian or Alaska Native",
        "Asian",
        "Black or African American",
        "Hispanic/Latino",
        "Native Hawaiian or Other Pacific Islander",
        "White",
        "Two or more ethnicities",
        "Prefer not to answer"
    )

    SuperiorDropdownMenu(
        label = "Ethnicity",
        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
        selectedOption = value,
        options = options,
        onOptionSelected = onValueChange,
        leadingIcon = R.drawable.globeicon,
        leadingIconModifier = Modifier.size(32.dp),
        isError = isError,
        errorMessage = errorMessage
    )
}

@Composable
private fun CountryOriginDropDownMenu(
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit
){
    val options = listOf(
            "Afghanistan",
            "Åland Islands",
            "Albania",
            "Algeria",
            "American Samoa",
            "Andorra",
            "Angola",
            "Anguilla",
            "Antarctica",
            "Antigua and Barbuda",
            "Argentina",
            "Armenia",
            "Aruba",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bermuda",
            "Bhutan",
            "Bolivia",
            "Bosnia and Herzegovina",
            "Botswana",
            "Bouvet Island",
            "Brazil",
            "British Indian Ocean Territory",
            "Brunei Darussalam",
            "Bulgaria",
            "Burkina Faso",
            "Burundi",
            "Cambodia",
            "Cameroon",
            "Canada",
            "Cape Verde",
            "Cayman Islands",
            "Central African Republic",
            "Chad",
            "Chile",
            "China",
            "Christmas Island",
            "Cocos (Keeling) Islands",
            "Colombia",
            "Comoros",
            "Congo",
            "Congo, The Democratic Republic of the",
            "Cook Islands",
            "Costa Rica",
            "Cote D'Ivoire",
            "Croatia",
            "Cuba",
            "Cyprus",
            "Czech Republic",
            "Denmark",
            "Djibouti",
            "Dominica",
            "Dominican Republic",
            "Ecuador",
            "Egypt",
            "El Salvador",
            "Equatorial Guinea",
            "Eritrea",
            "Estonia",
            "Ethiopia",
            "Falkland Islands",
            "Faroe Islands",
            "Fiji",
            "Finland",
            "France",
            "French Guiana",
            "French Polynesia",
            "French Southern Territories",
            "Gabon",
            "Gambia",
            "Georgia",
            "Germany",
            "Ghana",
            "Gibraltar",
            "Greece",
            "Greenland",
            "Grenada",
            "Guadeloupe",
            "Guam",
            "Guatemala",
            "Guernsey",
            "Guinea",
            "Guinea-Bissau",
            "Guyana",
            "Haiti",
            "Heard Island and Mcdonald Islands",
            "Holy See (Vatican City State)",
            "Honduras",
            "Hong Kong",
            "Hungary",
            "Iceland",
            "India",
            "Indonesia",
            "Iran, Islamic Republic Of",
            "Iraq",
            "Ireland",
            "Isle of Man",
            "Israel",
            "Italy",
            "Jamaica",
            "Japan",
            "Jersey",
            "Jordan",
            "Kazakhstan",
            "Kenya",
            "Kiribati",
            "Korea, Democratic People'S Republic of",
            "Korea, Republic of",
            "Kuwait",
            "Kyrgyzstan",
            "Lao People'S Democratic Republic",
            "Latvia",
            "Lebanon",
            "Lesotho",
            "Liberia",
            "Libyan Arab Jamahiriya",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Macao",
            "Macedonia, The Former Yugoslav Republic of",
            "Madagascar",
            "Malawi",
            "Malaysia",
            "Maldives",
            "Mali",
            "Malta",
            "Marshall Islands",
            "Martinique",
            "Mauritania",
            "Mauritius",
            "Mayotte",
            "Mexico",
            "Micronesia, Federated States of",
            "Moldova, Republic of",
            "Monaco",
            "Mongolia",
            "Montserrat",
            "Morocco",
            "Mozambique",
            "Myanmar",
            "Namibia",
            "Nauru",
            "Nepal",
            "Netherlands",
            "Netherlands Antilles",
            "New Caledonia",
            "New Zealand",
            "Nicaragua",
            "Niger",
            "Nigeria",
            "Niue",
            "Norfolk Island",
            "Northern Mariana Islands",
            "Norway",
            "Oman",
            "Pakistan",
            "Palau",
            "Palestinian Territory, Occupied",
            "Panama",
            "Papua New Guinea",
            "Paraguay",
            "Peru",
            "Philippines",
            "Pitcairn",
            "Poland",
            "Portugal",
            "Qatar",
            "Reunion",
            "Romania",
            "Russian Federation",
            "Rwanda",
            "Saint Helena",
            "Saint Kitts and Nevis",
            "Saint Lucia",
            "Saint Pierre and Miquelon",
            "Saint Vincent and the Grenadines",
            "Samoa",
            "San Marino",
            "Sao Tome and Principe",
            "Saudi Arabia",
            "Senegal",
            "Serbia and Montenegro",
            "Seychelles",
            "Sierra Leone",
            "Singapore",
            "Slovakia",
            "Slovenia",
            "Solomon Islands",
            "Somalia",
            "South Africa",
            "South Georgia and the South Sandwich Islands",
            "Spain",
            "Sri Lanka",
            "Sudan",
            "Suriname",
            "Svalbard and Jan Mayen",
            "Swaziland",
            "Sweden",
            "Switzerland",
            "Syrian Arab Republic",
            "Taiwan, Province of China",
            "Tajikistan",
            "Tanzania, United Republic of",
            "Thailand",
            "Timor-Leste",
            "Togo",
            "Tokelau",
            "Tonga",
            "Trinidad and Tobago",
            "Tunisia",
            "Turkey",
            "Turkmenistan",
            "Turks and Caicos Islands",
            "Tuvalu",
            "Uganda",
            "Ukraine",
            "United Arab Emirates",
            "United Kingdom",
            "United States",
            "United States Minor Outlying Islands",
            "Uruguay",
            "Uzbekistan",
            "Vanuatu",
            "Venezuela",
            "Vietnam",
            "Virgin Islands, British",
            "Virgin Islands, U.S.",
            "Wallis and Futuna",
            "Western Sahara",
            "Yemen", "Zambia", "Zimbabwe"
    )

    SuperiorDropdownMenu(
        label = "Country of Origin",
        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
        selectedOption = value,
        options = options,
        onOptionSelected = onValueChange,
        leadingIcon = R.drawable.globeicon,
        leadingIconModifier = Modifier.size(32.dp),
        isError = isError,
        errorMessage = errorMessage
    )
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