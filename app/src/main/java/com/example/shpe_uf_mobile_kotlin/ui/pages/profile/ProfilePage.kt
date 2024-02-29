package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R

//UI composables



@Composable
fun ProfileScreen(){
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = typography.titleLarge,
        )
        ProfileLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            ) {
                Text(
                    text = "Edit Profile",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { }
            ) {
                Text(
                    text = "Save",
                    fontSize = 16.sp
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { }
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 16.sp
                )
            }
        }
        UserInfo(
            name = "",
            username = "",
            email = "",
            gender = "",
            ethnicity = "",
            country = "",
            year = "",
            gradYear = "",
            modifier = Modifier.padding(20.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileLayout(modifier: Modifier = Modifier){
    Card(
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                text = "Account Info",
                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun UserInfo(name: String, username:String, email:String, gender:String, ethnicity:String, country:String, year:String, gradYear:String, modifier: Modifier = Modifier){
    Card(
        modifier = modifier
    ) {
        Text(
            text = name,
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = username,
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = email,
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = gender,
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = ethnicity,
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = country,
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = year,
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = gradYear,
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}