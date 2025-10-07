package com.example.shpe_uf_mobile_kotlin.ui.pages.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.shpe_uf_mobile_kotlin.R

//@Preview(showBackground = true)
//@Composable
//fun PreviewAdminPanel() {
//    AdminPanelScreen(isUserAdmin = true)
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelScreen(
    isUserAdmin: Boolean,
    onBack: () -> Unit = {},
    onNavigateToUserManagement: () -> Unit = {},
    onViewLogs: () -> Unit = {},
    onDeleteUser: (email: String) -> Unit = {}
) {
    // Define colors
    val darkBlue = Color(0xFF011F35)
    val lightBlue = Color(36 / 255f, 87 / 255f, 197 / 255f, 0.75f)
    val orange = Color(210 / 255f, 89 / 255f, 23 / 255f, 0.75f);

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBlue)
    ) {
        // Top bar with back arrow
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Centered title below the back arrow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Admin Panel",
                color = Color.White,
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.viga)),
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Scrollable grid of admin options
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f) // Takes up remaining space
                .padding(horizontal = 26.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // First row: Events and Members
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Equal height distribution
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                AdminCard(
                    title = "Events",
                    iconRes = R.drawable.calendar_dm_on,
                    backgroundColor = lightBlue,
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle Events click */ }
                )

                AdminCard(
                    title = "Members",
                    iconRes = R.drawable.profile,
                    backgroundColor = lightBlue,
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle Members click */ }
                )
            }

            // Second row: Resources and Requests
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                AdminCard(
                    title = "Resources",
                    iconRes = R.drawable.resources,
                    backgroundColor = orange,
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle Resources click */ }
                )

                AdminCard(
                    title = "Requests",
                    iconRes = R.drawable.requests,
                    backgroundColor = orange,
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle Requests click */ }
                )
            }

            // Third row: Statistics and Corporate Database
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                AdminCard(
                    title = "Statistics",
                    iconRes = R.drawable.statistics,
                    backgroundColor = lightBlue,
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle Statistics click */ }
                )

                AdminCard(
                    title = "Corporate Database",
                    iconRes = R.drawable.corporate_database,
                    backgroundColor = lightBlue,
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle Corporate Database click */ }
                )
            }

            // Fourth row: Reimburse and SHPE Rentals
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                AdminCard(
                    title = "Reimburse",
                    iconRes = R.drawable.reimburse,
                    backgroundColor = orange,
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle Reimburse click */ }
                )

                AdminCard(
                    title = "SHPE Rentals",
                    iconRes = R.drawable.shpe_rentals,
                    backgroundColor = orange,
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle SHPE Rentals click */ }
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun AdminCard(
    title: String,
    iconRes: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxHeight() // Fill the row's height
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.universltstd)),
                fontWeight = FontWeight.Medium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}