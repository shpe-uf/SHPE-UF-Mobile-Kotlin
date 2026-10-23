package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.profile

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shpeuf.shpe_uf_mobile_kotlin.R
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.NavRoute
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel

//TODO: add bottom bar functionality

//@Preview(showBackground = true)
@Composable
fun StaticProfilePagePreview(
    viewModel: ProfileViewModel, navController: NavHostController, mainViewModel: SHPEUFAppViewModel
) {

    StaticProfileScreen(viewModel, navController, mainViewModel)
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StaticProfileScreen(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    mainViewModel: SHPEUFAppViewModel
) {
    val uiState by profileViewModel.uiState.collectAsState()
    val mainState by mainViewModel.uiState.collectAsState()
    val context = LocalContext.current

    // rememberSaveable caches this value so it survives theme toggles
    var isAdmin by rememberSaveable { mutableStateOf(profileViewModel.isUserAdmin()) }

    // Whenever the UI state updates (when the profile finishes loading from the network), we check the admin status. If it's true, we lock it in
    LaunchedEffect(uiState) {
        if (profileViewModel.isUserAdmin()) {
            isAdmin = true
        }
    }

    // This ensures loadProfile only runs ONCE when the screen opens or the ID changes rather than running on every single keystroke
    LaunchedEffect(mainState.id) {
        profileViewModel.loadProfile(mainState.id)
    }

    // Pass ONLY state and action callbacks to the UI
    StaticProfileContent(
        uiState = uiState,
        isDarkMode = mainState.isDarkMode,
        isAdmin = isAdmin,
        onFullNameChanged = profileViewModel::onFullNameChanged,
        onUserNameChanged = profileViewModel::onUserNameChanged,
        onEmailChanged = profileViewModel::onEmailChanged,
        onGenderChanged = profileViewModel::onGenderChanged,
        onEthnicityChanged = profileViewModel::onEthnicityChanged,
        onCountryChanged = profileViewModel::onCountryChanged,
        onMajorChanged = profileViewModel::onMajorChanged,
        onYearChanged = profileViewModel::onYearChanged,
        onGradYearChanged = profileViewModel::onGradYearChanged,
        onAddClass = profileViewModel::addClass,
        onRemoveClass = profileViewModel::removeClass,
        onAddInternship = profileViewModel::addInternship,
        onRemoveInternship = profileViewModel::removeInternship,
        onAddLink = profileViewModel::addLinks,
        onRemoveLink = profileViewModel::removeLink,
        onDropdownToggle = profileViewModel::toggleDropdownMenu,
        onSaveClick = { profileViewModel.saveProfileChanges() },
        onCancelClick = { profileViewModel.cancelProfileChanges() },
        onEditClick = { profileViewModel.editProfile() },
        onImageSelected = { uri -> profileViewModel.handleSelectedImage(context, uri) },
        onAdminPanelClick = { navController.navigate(NavRoute.ADMIN) },
        onThemeToggle = { isDark -> mainViewModel.saveDarkMode(isDark) },
        onLogoutClick = {
            mainViewModel.logoutUser()
            navController.navigate(NavRoute.LOGIN)
        },
        onDeleteAccountConfirm = {
            val deleteUnsuccessful = profileViewModel.deleteProfile(mainViewModel)
            if (!deleteUnsuccessful) {
                navController.navigate(NavRoute.OPENING)
            }
        }
    )
}

@Composable
fun StaticProfileContent(
    uiState: ProfileUiState,
    isDarkMode: Boolean,
    isAdmin: Boolean,
    onFullNameChanged: (String) -> Unit,
    onUserNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onGenderChanged: (String) -> Unit,
    onEthnicityChanged: (String) -> Unit,
    onCountryChanged: (String) -> Unit,
    onMajorChanged: (String) -> Unit,
    onYearChanged: (String) -> Unit,
    onGradYearChanged: (String) -> Unit,
    onAddClass: (String) -> Unit,
    onRemoveClass: (String) -> Unit,
    onAddInternship: (String) -> Unit,
    onRemoveInternship: (String) -> Unit,
    onAddLink: (String) -> Unit,
    onRemoveLink: (String) -> Unit,
    onDropdownToggle: (Int) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onEditClick: () -> Unit,
    onImageSelected: (Uri) -> Unit,
    onAdminPanelClick: () -> Unit,
    onThemeToggle: (Boolean) -> Unit,
    onLogoutClick: () -> Unit,
    onDeleteAccountConfirm: () -> Unit
) {
    SHPEUFMobileKotlinTheme {
        val textColor = if (isDarkMode) Color.White else Color.Black
        val containerColor = if (!isDarkMode) Color(0xFFD25917) else Color(0xFF001627)

        StaticProfilePageBackground(
            isDarkMode = isDarkMode,
            uiState = uiState,
            textColor = textColor,
            containerColor = containerColor,
            isAdmin = isAdmin,
            onSaveClick = onSaveClick,
            onCancelClick = onCancelClick,
            onEditClick = onEditClick,
            onAdminPanelClick = onAdminPanelClick,
            onImageSelected = onImageSelected
        )

        Box {
            LazyColumn(
                modifier = Modifier
                    .padding(top = if (isAdmin) 350.dp else 300.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
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

                item { Spacer(modifier = Modifier.height(27.dp)) }

                // Name
                item {
                    ProfileItem(
                        value = uiState.fullName,
                        onValueChange = onFullNameChanged,
                        textColor = textColor,
                        icon = R.drawable.profile_circle_orange,
                        isDarkMode = isDarkMode,
                        title = "NAME",
                        editable = uiState.editable,
                        onExpandedChange = { onDropdownToggle(6) },
                        errorMessage = uiState.errorMessages["fullName"]
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                }

                // Username
                item {
                    ProfileItem(
                        value = uiState.userName,
                        onValueChange = onUserNameChanged,
                        textColor = textColor,
                        isDarkMode = isDarkMode,
                        icon = R.drawable.profile_circle_orange,
                        title = "USERNAME",
                        editable = listOf(false, true),
                        onExpandedChange = { onDropdownToggle(6) }
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                }

                // Email
                item {
                    ProfileItem(
                        value = uiState.email,
                        onValueChange = onEmailChanged,
                        isDarkMode = isDarkMode,
                        textColor = textColor,
                        icon = R.drawable.profile_email,
                        title = "EMAIL",
                        editable = listOf(false, true),
                        onExpandedChange = { onDropdownToggle(6) },
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                }

                // Gender
                item {
                    ProfileItem(
                        value = uiState.gender,
                        onValueChange = onGenderChanged,
                        isDarkMode = isDarkMode,
                        textColor = textColor,
                        icon = R.drawable.profile_gender_equality,
                        title = "GENDER",
                        editable = uiState.editable,
                        newValue = listOf("Male", "Female", "Non-Binary", "Other"),
                        onExpandedChange = { onDropdownToggle(0) },
                        dropdown = true
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                }

                // Ethnicity
                item {
                    ProfileItem(
                        value = uiState.ethnicity,
                        onValueChange = onEthnicityChanged,
                        isDarkMode = isDarkMode,
                        textColor = textColor,
                        icon = R.drawable.profile_globe,
                        title = "ETHNICITY",
                        editable = uiState.editable,
                        newValue = listOf("American Indian or Alaska Native", "Asian", "Black or African American", "Hispanic/Latino", "Native Hawaiian or Other Pacific Islander", "White", "Two or more ethnicities", "Prefer not to answer"),
                        onExpandedChange = { onDropdownToggle(1) },
                        dropdown = true
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                }

                // Country
                item {
                    ProfileItem(
                        value = uiState.country,
                        onValueChange = onCountryChanged,
                        isDarkMode = isDarkMode,
                        textColor = textColor,
                        icon = R.drawable.profile_globe,
                        title = "COUNTRY OF ORIGIN",
                        editable = uiState.editable,
                        newValue = listOf(
                            "Afghanistan",
                            "Albania",
                            "Algeria",
                            "American Samoa",
                            "Andorra",
                            "Angola",
                            "Anguilla",
                            "Antarctica",
                            "Antigua & Barbuda",
                            "Argentina",
                            "Armenia",
                            "Aruba",
                            "Ascension Island",
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
                            "Bosnia & Herzegovina",
                            "Botswana",
                            "Bouvet Island",
                            "Brazil",
                            "British Virgin Islands",
                            "Brunei",
                            "Bulgaria",
                            "Burkina Faso",
                            "Burundi",
                            "Cambodia",
                            "Cameroon",
                            "Canada",
                            "Canary Islands",
                            "Cape Verde",
                            "Caribbean Netherlands",
                            "Cayman Islands",
                            "Central African Republic",
                            "Ceuta & Melilla",
                            "Chad",
                            "Chagos Archipelago",
                            "Chile",
                            "Christmas Island",
                            "Clipperton Island",
                            "Cocos [Keeling] Islands",
                            "Colombia",
                            "Comoros",
                            "Congo - Brazzaville",
                            "Congo - Kinshasa",
                            "Cook Islands",
                            "Costa Rica",
                            "Croatia",
                            "Cuba",
                            "Curaçao",
                            "Cyprus",
                            "Czechia",
                            "Côte d'Ivoire",
                            "Denmark",
                            "Diego Garcia",
                            "Djibouti",
                            "Dominica",
                            "Dominican Republic",
                            "Ecuador",
                            "Egypt",
                            "El Salvador",
                            "Equatorial Guinea",
                            "Eritrea",
                            "Estonia",
                            "Eswatini",
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
                            "Heard & McDonald Island",
                            "Honduras",
                            "Hungary",
                            "Iceland",
                            "India",
                            "Indonesia",
                            "Iran",
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
                            "kiribati",
                            "Kosovo",
                            "Kuwait",
                            "Kyrgyzstan",
                            "Laos",
                            "Latvia",
                            "Lebanon",
                            "Lesotho",
                            "Liberia",
                            "Libya",
                            "Liechtenstein",
                            "Lithuania",
                            "Luxembourg",
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
                            "Micronesia",
                            "Moldova",
                            "Monaco",
                            "Mongolia",
                            "Montenegro",
                            "Montserrat",
                            "Morocco",
                            "Mozambique",
                            "Myanmar [Burma]",
                            "Namibia",
                            "Nauru",
                            "Nepal",
                            "Netherlands",
                            "New Caledonia",
                            "New Zealand",
                            "Nicaragua",
                            "Niger",
                            "Nigeria",
                            "Niue",
                            "Norfolk Island",
                            "North Korea",
                            "North Macedonia",
                            "Northern Mariana Islands",
                            "Norway",
                            "Oman",
                            "Pakistan",
                            "Palau",
                            "Palestinian Territories",
                            "Panama",
                            "Papua New Guinea",
                            "Paraguay",
                            "Peru",
                            "Philippines",
                            "Pitcairn Islands",
                            "Poland",
                            "Portugal",
                            "Puerto Rico",
                            "Qatar",
                            "Romania",
                            "Root (China mainland)",
                            "Root (Hong Kong)",
                            "Root (Macao)",
                            "Root (Taiwan)",
                            "Russia",
                            "Rwanda",
                            "Réunion",
                            "Samoa",
                            "San Marino",
                            "Sark",
                            "Saudi Arabia",
                            "Senegal",
                            "Serbia",
                            "Seychelles",
                            "Sierra Leone",
                            "Singapore",
                            "Sint Maarten",
                            "Slovakia",
                            "Slovenia",
                            "So. Georgia & So. Sandwich Isl.",
                            "Solomon Islands",
                            "Somalia",
                            "South Africa",
                            "South Korea",
                            "South Sudan",
                            "Spain",
                            "Sri Lanka",
                            "St Barthélemy",
                            "St Helena",
                            "St Kitts & Nevis",
                            "St Lucia",
                            "St Martin",
                            "St Pierre & Miquelon",
                            "St Vincent & the Grenadines",
                            "Sudan",
                            "Suriname",
                            "Svalbard & Jan Mayen",
                            "Sweden",
                            "Switzerland",
                            "Syria",
                            "São Tomé & Príncipe",
                            "Tajikistan",
                            "Tanzania",
                            "Thailand",
                            "Timor-Leste",
                            "Togo",
                            "Tokelau",
                            "Tonga",
                            "Trinidad & Tobago",
                            "Tristan da Cunha",
                            "Tunisia",
                            "Turkmenistan",
                            "Turks & Caicos Islands",
                            "Tuvalu",
                            "Türkiye",
                            "US Outlying Islands",
                            "US Virgin Islands",
                            "Uganda",
                            "Ukraine",
                            "United Arab Emirates",
                            "United Kingdom",
                            "United States",
                            "Uruguay",
                            "Uzbekistan",
                            "Vanuatu",
                            "Vatican City",
                            "Venezuela",
                            "Vietnam",
                            "Wallis & Futuna",
                            "Western Sahara",
                            "Yemen",
                            "Zambia",
                            "Zimbabwe",
                            "Åland Islands"
                        ),
                        onExpandedChange = { onDropdownToggle(2) },
                        dropdown = true
                    )
                }

                item { Spacer(modifier = Modifier.height(27.dp)) }

                item {
                    Text(
                        text = "EDUCATION INFO",
                        modifier = Modifier.fillMaxWidth().padding(start = 36.dp),
                        color = textColor, fontSize = 20.sp, fontWeight = FontWeight.Bold
                    )
                }

                item { Spacer(modifier = Modifier.height(27.dp)) }

                // Major
                item {
                    ProfileItem(
                        value = uiState.major,
                        onValueChange = onMajorChanged,
                        isDarkMode = isDarkMode,
                        textColor = textColor,
                        icon = R.drawable.profile_cap,
                        title = "MAJOR",
                        editable = uiState.editable,
                        newValue = listOf(
                            "Aerospace Engineering",
                            "Agricultural & Biological Engineering",
                            "Biomedical Engineering",
                            "Chemical Engineering",
                            "Civil Engineering",
                            "Coastal & Oceanographic Engineering",
                            "Computer Engineering",
                            "Computer Science",
                            "Digital Arts & Science",
                            "Electrical Engineering",
                            "Environmental Engineering Sciences",
                            "Human-Centered Computing",
                            "Industrial & Systems Engineering",
                            "Materials Science & Engineering",
                            "Mechanical Engineering",
                            "Nuclear Engineering",
                            "Other"
                        ),
                        onExpandedChange = { onDropdownToggle(3) },
                        dropdown = true
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                }

                // Current Year
                item {
                    ProfileItem(
                        value = uiState.year,
                        onValueChange = onYearChanged,
                        isDarkMode = isDarkMode,
                        textColor = textColor,
                        icon = R.drawable.profile_year,
                        title = "YEAR",
                        editable = uiState.editable,
                        newValue = listOf("1st Year", "2nd Year", "3rd Year", "4th Year", "5th Year or Higher", "Graduate", "Ph.D."),
                        onExpandedChange = { onDropdownToggle(4) },
                        dropdown = true
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                }

                // Graduation Year
                item {
                    ProfileItem(
                        value = uiState.gradYear,
                        onValueChange = onGradYearChanged,
                        isDarkMode = isDarkMode,
                        textColor = textColor,
                        icon = R.drawable.profile_cap,
                        title = "GRADUATION YEAR",
                        editable = uiState.editable,
                        newValue = listOf("2025", "2026", "2027", "2028"),
                        onExpandedChange = { onDropdownToggle(5) },
                        dropdown = true
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                }

                // Classes
                item {
                    ProfileLists(
                        value = uiState.classes ?: listOf(),
                        textColor = textColor,
                        icon = R.drawable.university_campus,
                        title = "CLASSES",
                        editable = uiState.editable,
                        isDarkMode = isDarkMode,
                        onAddValue = onAddClass,
                        onRemoveValue = onRemoveClass,
                        errorMessage = uiState.errorMessages["classes"]
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                }

                // Internships
                item {
                    ProfileLists(
                        value = uiState.internships ?: listOf(),
                        onAddValue = onAddInternship,
                        onRemoveValue = onRemoveInternship,
                        textColor = textColor,
                        icon = R.drawable.office,
                        title = "INTERNSHIPS",
                        editable = uiState.editable,
                        isDarkMode = isDarkMode,
                        errorMessage = uiState.errorMessages["internships"]
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                }

                // Links
                item {
                    ProfileLists(
                        value = uiState.socialMedia ?: listOf(),
                        onAddValue = onAddLink,
                        onRemoveValue = onRemoveLink,
                        textColor = textColor,
                        isDarkMode = isDarkMode,
                        icon = R.drawable.internet,
                        title = "LINKS",
                        editable = uiState.editable,
                        listType = 'l',
                        errorMessage = uiState.errorMessages["socialMedia"]
                    )
                }

                item { Spacer(modifier = Modifier.height(25.dp)) }

                item {
                    if (!uiState.editable[0] && uiState.editable[1]) {
                        // AppearanceToggle is stateless except for the MainViewModel
                        // For now just wrap the callbacks
                        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                            Text(
                                text = "APPEARANCE",
                                modifier = Modifier.fillMaxWidth().padding(start = 36.dp),
                                color = if (isDarkMode) Color.White else Color.Black,
                                fontSize = 20.sp, fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(27.dp))
                            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                                ModeButton(label = "Light Mode", iconRes = R.drawable.ic_light_mode, selected = !isDarkMode, onClick = { onThemeToggle(false) })
                                ModeButton(label = "Dark Mode", iconRes = R.drawable.ic_dark_mode, selected = isDarkMode, onClick = { onThemeToggle(true) })
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }

                item {
                    if (!uiState.editable[0] && uiState.editable[1]) {
                        LogoutButton(onClick = onLogoutClick)
                    }
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }

                item {
                    if (!uiState.editable[0] && uiState.editable[1]) {
                        DeleteAccountButton(onDeleteConfirm = onDeleteAccountConfirm)
                    }
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
        }
    }
}



@Composable
fun ProfileImage(
    isDarkMode: Boolean,
    editable: List<Boolean>,
    photoBitmap: android.graphics.Bitmap?,
    onEditClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(116.dp)
    ) {
        if (photoBitmap != null) {
            Image(
                bitmap = photoBitmap.asImageBitmap(),
                contentDescription = "USER PROFILE PIC",
                modifier = Modifier.fillMaxSize().clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(
                    id = if (isDarkMode) R.drawable.empty_profile_picture_dark
                    else R.drawable.empty_profile_picture_light
                ),
                contentDescription = "PROFILE PIC CIRCLE",
                modifier = Modifier.fillMaxSize()
            )
        }

        if (editable[0] && !editable[1]) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .align(Alignment.TopEnd)
                    .clickable { onEditClick() }
                    .background(Color(0xFFD25917), CircleShape)
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.LightGray, CircleShape).padding(5.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.fileicon),
                        contentDescription = "Edit Profile Picture",
                        modifier = Modifier.fillMaxSize().padding(1.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StaticProfilePageBackground(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    uiState: ProfileUiState,
    textColor: Color,
    containerColor: Color,
    isAdmin: Boolean,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onEditClick: () -> Unit,
    onAdminPanelClick: () -> Unit,
    onImageSelected: (Uri) -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val topPadding = screenWidth * 0.15f
    val orangeHeight = screenHeight * (1.01f / 4f)
    val blueHeight = screenHeight * (3.4f / 5f)

    // Create image picker launcher using SAF
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }

    val launchImagePicker = { imagePickerLauncher.launch("image/*") }

    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth().height(orangeHeight).background(Color(0xFFD25917))
        )
        Box(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopStart).padding(top = 102.dp)
        ) {
            Image(
                painter = painterResource(id = if (isDarkMode) R.drawable.gator_dark_mode else R.drawable.gator_light_mode),
                contentDescription = "SHPE GATOR",
                modifier = Modifier.align(Alignment.TopStart).width(90.dp).height(90.dp)
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth().height(blueHeight).align(Alignment.BottomCenter)
                .background(if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background)
        )
        Box(
            modifier = Modifier.fillMaxWidth().padding(top = 86.dp)
        ) {
            Image(
                painter = painterResource(id = if (isDarkMode) R.drawable.background_blue_circle else R.drawable.background_white_circle),
                contentDescription = "PROFILE CURVE",
                modifier = Modifier.align(Alignment.Center).width(450.dp).height(450.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth().padding(top = topPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                ProfileImage(
                    isDarkMode = isDarkMode,
                    editable = uiState.editable,
                    photoBitmap = uiState.photoBitmap,
                    onEditClick = { launchImagePicker() }
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = uiState.fullName, color = textColor, fontSize = 24.sp, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (uiState.editable[0] && !uiState.editable[1]) {
                    Row(
                        modifier = Modifier.width(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ProfileChangesButton(
                            onClick = onSaveClick,
                            containerColor = containerColor,
                            title = "Save",
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        ProfileChangesButton(
                            onClick = onCancelClick,
                            containerColor = containerColor,
                            title = "Cancel",
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else if (!uiState.editable[0] && uiState.editable[1]) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Might need to change EditProfileButton to take just `onClick` rather than ViewModels
                        EditProfileButton(onClick = onEditClick, profileViewModel = viewModel())

                        if (isAdmin){
                            AdminPanelButton(onClick = onAdminPanelClick)
                        }
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileLists(
    value: List<String?>,
    listType: Char = 'c',
    onAddValue: (String) -> Unit,
    onRemoveValue: (String) -> Unit,
    textColor: Color,
    isDarkMode: Boolean,
    icon: Int,
    title: String,
    editable: List<Boolean>,
    errorMessage: String?
) {

    val bg_color = if (isDarkMode) Color(0xFF002139) else Color(0xFFF5F5F5)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bg_color)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
        ) {
            // Row for Icon and Field Name
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .padding(horizontal = 40.dp)
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "Icon",
                    modifier = Modifier.size(26.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    color = Color(0xFFD25917),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Not editable

            if (!editable[0] && editable[1]) {

                // Now loop through the value and display each class
                value.forEach { classItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp)
                    ) {

                        if (listType != 'c') {
                            val ctx = LocalContext.current

                            // Simple fix if you put https://
                            if (classItem != null) {
                                if (classItem.startsWith("https://")){
                                    classItem.removePrefix("https://")
                                }
                            }

                            TextButton(onClick = {
                                // When in doubt, try catching the error.
                                try {
                                    val urlIntent = Intent(
                                        Intent.ACTION_VIEW, Uri.parse("https://$classItem")
                                    )
                                    ctx.startActivity(urlIntent)
                                } catch (e: Exception) {
                                    Toast.makeText(ctx, "Could not open link.", Toast.LENGTH_SHORT).show()
                                }

                            }) {
                                if (classItem != null) { // null checking again
                                    Text(
                                        text = classItem.removePrefix("https://"),
                                        modifier = Modifier.fillMaxWidth(),
                                        color = textColor,
                                        fontSize = 15.sp
                                    )
                                }
                            }

                        } else {
                            if (classItem != null) {
                                Text(
                                    text = classItem,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = textColor,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            } else { // Editable
                // TextField that can take in multiple attributes and add them to a list, for now show the elements in a box.

                var text by remember { mutableStateOf("") }

                val underlineColor = if (isDarkMode) Color.White else Color.Black

                Column (
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        TextField(
                            value = text,
                            modifier = Modifier
                                .weight(1f),
//                                .padding(horizontal = 32.dp),
                            placeholder = { Text(text = "Add your ${title.lowercase()} here") },
                            onValueChange = { text = it },
                            enabled = true,
                            readOnly = false,
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontSize = 15.sp, color = textColor),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.White,
                                focusedPlaceholderColor = Color.LightGray,
                                unfocusedPlaceholderColor = Color.LightGray,
                                focusedBorderColor = underlineColor,
                                unfocusedBorderColor = underlineColor,
                                cursorColor = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(
                            onClick = {
                                onAddValue(text)
                                text = ""
                                      },
                        ) {
                            Icon(
                                Icons.Filled.AddCircle,
                                contentDescription = "Add",
                                modifier = Modifier.size(30.dp),
                                tint = Color.Blue
                            )
                        }
                    }

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (listType != 'c') {

                        val containerColor = if (isDarkMode) Color(0xFF003935) else Color.Gray


                        value.forEach { linkItem ->
                            Column(modifier = Modifier.padding(horizontal = 32.dp)) {
                                Button(
                                    onClick = { onRemoveValue(linkItem ?: "") },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = containerColor, contentColor = textColor
                                    ),
                                    modifier = Modifier.wrapContentSize(Alignment.Center)
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (linkItem != null) {
                                            Text(
                                                text = linkItem.removePrefix("https://"),
                                                color = Color.White,
                                                maxLines = 1,
                                                overflow = TextOverflow.Visible
                                            )
                                        }
                                        Icon(
                                            Icons.Filled.RemoveCircle,
                                            contentDescription = "Remove",
                                            modifier = Modifier.size(15.dp),
                                            tint = Color.White
                                        )
                                    }
                                }
                            }

                        }
                    } else {

                        val containerColor = if (isDarkMode) Color(0xFF003935) else Color.Gray

                        ContextualFlowRow(
                            modifier = Modifier.padding(horizontal = 32.dp),
                            itemCount = value.size,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            val safeValue = value.getOrNull(it) ?: ""

                            Button(
                                onClick = { onRemoveValue(safeValue) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = containerColor, contentColor = textColor
                                ),
                                modifier = Modifier.wrapContentSize(Alignment.Center)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = safeValue,
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Visible
                                    )
                                    Icon(
                                        Icons.Filled.RemoveCircle,
                                        contentDescription = "Remove",
                                        modifier = Modifier.size(15.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }

                    }

                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileItem(
    value: String,
    onValueChange: (String) -> Unit,
    textColor: Color,
    icon: Int,
    title: String,
    editable: List<Boolean> = listOf(false, true),
    dropdown: Boolean = false,
    newValue: List<String> = listOf(""),
    isDarkMode: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    errorMessage: String? = null,
) {

    val bgColor = if (isDarkMode) Color(0xFF002139) else Color(0xFFF5F5F5)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            // Row for Icon and Field Name
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .padding(horizontal = 40.dp)
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "Icon",
                    modifier = Modifier.size(26.dp) // Set size for the icon
                )
                Spacer(modifier = Modifier.width(10.dp)) // Add some space between the icon and the text
                Text(
                    text = title, color = Color(0xFFD25917), // Orange color
                    fontSize = 20.sp, fontWeight = FontWeight.Bold
                )
            }

            if (!dropdown) {

                if (!editable[0] && editable[1]) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp, vertical = 12.dp),
                        text = value,
                        color = textColor,
                        fontSize = 15.sp,
                    )
                } else {
                    // Outlined Text Field for Input

                    val underlineColor = if (isDarkMode) Color.White else Color.Black

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 40.dp)
                    ) {
                        TextField(
                            modifier = Modifier.padding(vertical = 12.dp),
                            value = value,
                            onValueChange = { newValue -> onValueChange(newValue) },
                            enabled = true,
                            readOnly = false,
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontSize = 15.sp, color = textColor),

                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.White, focusedPlaceholderColor = Color.Gray,
                                unfocusedPlaceholderColor = Color.Gray,
                                focusedBorderColor = underlineColor,
                                unfocusedBorderColor = underlineColor
                            )
                        )
                        Log.d("error", "$errorMessage")
                        if (errorMessage!= null) {
                            Text(
                                text = errorMessage,
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                }


            } else {

                if (!editable[0] && editable[1]) {
                    Text(
                        text = value,
                        color = textColor,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp, vertical = 12.dp),
                    )
                } else {
                    var expanded by remember { mutableStateOf(false) }

                    val dropdownColor = if (isDarkMode) Color.White else Color.Black

                    Box(
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 40.dp)
                        ) {
                            Text(
                                text = value,
                                color = textColor,
                                fontSize = 15.sp,
                            )

                            IconButton(onClick = { expanded = !expanded },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = dropdownColor,
                                    containerColor = Color.Transparent
                                )
                            ) {
                                Icon(
                                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                    contentDescription = if (expanded) "Dropdown on" else "Dropdown off"
                                )
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },

                                ) {
                                newValue.forEach { item ->
                                    DropdownMenuItem(text = { Text(item) }, onClick = {
                                        onValueChange(item)
                                        expanded = false
                                    })
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EditProfileButton(
    onClick: () -> Unit, profileViewModel: ProfileViewModel
) {
    Box(
        modifier = Modifier.fillMaxWidth() // Ensure the Box takes up the full width of the screen
    ) {
        Button(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .align(Alignment.Center),
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF001627), contentColor = Color.White
            ),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

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
private fun AdminPanelButton(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .align(Alignment.Center),
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD25917),
                contentColor = Color.White
            ),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Admin Panel",
                    fontSize = 19.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(11.dp))

                Icon(
                    painter = painterResource(id = R.drawable.admin),
                    contentDescription = "AdminPanelIcon",
                    modifier = Modifier.size(20.dp)
                )
            }

        }
    }
}

@Composable
private fun ProfileChangesButton(
    onClick: () -> Unit, containerColor: Color, title: String, modifier: Modifier
) {
    Button(
        modifier = modifier.wrapContentSize(Alignment.Center),
        onClick = onClick,
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor, contentColor = Color.White
        ),
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun LogoutButton(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth() // Ensure the Box takes up the full width of the screen
    ) {
        Button(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .align(Alignment.Center),
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF001627), contentColor = Color.White
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
fun DeleteAccountButton(
    onDeleteConfirm: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = Modifier.wrapContentSize(Alignment.Center).align(Alignment.Center),
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
            title = { Text(text = "Delete Account?", textAlign = TextAlign.Center) },
            text = { Text("Deleting your account will remove all your personal data forever. This cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onDeleteConfirm()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
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
    Column(modifier = modifier
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

