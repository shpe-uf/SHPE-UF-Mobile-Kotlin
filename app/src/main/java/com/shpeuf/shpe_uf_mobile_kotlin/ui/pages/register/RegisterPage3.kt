package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.shpeuf.shpe_uf_mobile_kotlin.R
import com.shpeuf.shpe_uf_mobile_kotlin.ui.custom.SuperiorDropdownMenu
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.NavRoute


//@Preview
@Composable
fun RegistrationPage3Preview(navController: NavHostController, registerPage1ViewModel: RegisterPage1ViewModel){


    RegistrationPage3(registerPage1ViewModel = registerPage1ViewModel, navController = navController)

}

@Composable
fun RegistrationPage3(registerPage1ViewModel: RegisterPage1ViewModel, navController: NavHostController){

    val uiState by registerPage1ViewModel.uiState.collectAsState()

    RegisterPage3Background(navController)

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

            MajorDropDownMenu(
                value = uiState.major ?: "",
                isError = uiState.majorErrorMessage != null,
                errorMessage = uiState.majorErrorMessage ?: "",
                onValueChange = { registerPage1ViewModel.onMajorChanged(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            YearDropDownMenu(
                value = uiState.year ?: "",
                isError = uiState.yearErrorMessage != null,
                errorMessage = uiState.yearErrorMessage ?: "",
                onValueChange = { registerPage1ViewModel.onYearChanged(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            GraduationYearDropDownMenu(
                value = uiState.graduationYear ?: "",
                isError = uiState.graduationYearErrorMessage != null,
                errorMessage = uiState.graduationYearErrorMessage ?: "",
                onValueChange = { registerPage1ViewModel.onGraduationYearChanged(it) }
            )

            Spacer(modifier = Modifier.height(104.dp))

            CompleteRegistrationButton(
                onClick = {
                    if (registerPage1ViewModel.validateRegisterPage3Fields() == true) {
                        registerPage1ViewModel.validateAndRegisterUser()
                        navController.navigate(NavRoute.LOGIN)
                    }
                    else {
                        Log.d("Error", "The input validation for the page works, only Major for now")
                        // Do nothing don't go to next page
                    }
                }
            )
        }
    }
}




@Composable
fun RegisterPage3Background(navController: NavController) {

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
            BackButtonPage3(
                onClick = { navController.navigate(NavRoute.REGISTER_2) }
            )
        }
    }
}

@Composable
private fun MajorDropDownMenu(
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit

){
    val options = listOf(
        "Aerospace Engineering",
        "Agricultural & Biological Engineering",
        "Biomedical Engineering",
        "Chemical Engineering",
        "Civil Engineering",
        "Coastal & Oceanographic Engineering",
        "Computer Engineering",
        "Computer Science",
        "Digital Arts & Sciences",
        "Electrical Engineering",
        "Environmental Engineering Sciences",
        "Human-Centered Computing",
        "Industrial & Systems Engineering",
        "Materials Science & Engineering",
        "Mechanical Engineering",
        "Nuclear Engineering",
        "Other"
    )

    SuperiorDropdownMenu(
        label = "Major",
        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
        selectedOption = value,
        options = options,
        onOptionSelected = onValueChange,
        leadingIcon = R.drawable.majoricon,
        leadingIconModifier = Modifier.size(32.dp),
        isError = isError,
        errorMessage = errorMessage
    )
}

@Composable
private fun YearDropDownMenu(
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit

){
    val options = listOf(
        "1st Year",
        "2nd Year",
        "3rd Year",
        "4th Year",
        "5th Year or Higher",
        "Graduate",
        "Ph.D."
    )

    SuperiorDropdownMenu(
        label = "Class Year",
        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
        selectedOption = value,
        options = options,
        onOptionSelected = onValueChange,
        leadingIcon = R.drawable.yearicon,
        leadingIconModifier = Modifier.size(32.dp),
        isError = isError,
        errorMessage = errorMessage
    )
}


@Composable
private fun GraduationYearDropDownMenu(
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit

){
    val options = listOf(
        "2025",
        "2026",
        "2027",
        "2028",
        "2029"
    )

    SuperiorDropdownMenu(
        label = "Graduation Year",
        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
        selectedOption = value,
        options = options,
        onOptionSelected = onValueChange,
        leadingIcon = R.drawable.graduationcapicon,
        leadingIconModifier = Modifier.size(32.dp),
        isError = isError,
        errorMessage = errorMessage
    )
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


@Composable
private fun BackButtonPage3(onClick: () -> Unit) {

    Image(
        painter = painterResource(id = R.drawable.backbuttonicon),
        contentDescription = "BackButtonIcon",
        modifier = Modifier
            .size(43.dp)
            .clickable(onClick = onClick)
    )
}
