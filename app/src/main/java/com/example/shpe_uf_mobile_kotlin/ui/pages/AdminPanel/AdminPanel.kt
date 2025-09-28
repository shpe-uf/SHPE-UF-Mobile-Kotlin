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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R

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
    val lightBlue = Color(0xBF2457C5)
    val orange = Color(0xFFD25917)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBlue)
    ) {
        // Top bar with back arrow and title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = "Admin Panel",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(4f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Empty space to balance the back button
            Spacer(modifier = Modifier.weight(1f))
        }

        // Scrollable grid of admin options
        androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                // First row: Events and Members
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    AdminCard(
                        title = "Events",
                        iconRes = R.drawable.calendar_dm_on, // placeholder
                        backgroundColor = lightBlue,
                        modifier = Modifier.weight(1f),
                        onClick = { /* Handle Events click */ }
                    )

                    AdminCard(
                        title = "Members",
                        iconRes = R.drawable.pfp_dm_on, // placeholder
                        backgroundColor = lightBlue,
                        modifier = Modifier.weight(1f),
                        onClick = { /* Handle Members click */ }
                    )
                }
            }

            item {
                // Second row: Resources and Requests
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    AdminCard(
                        title = "Resources",
                        iconRes = R.drawable.leaderboard_dm_on, // placeholder
                        backgroundColor = orange,
                        modifier = Modifier.weight(1f),
                        onClick = { /* Handle Resources click */ }
                    )

                    AdminCard(
                        title = "Requests",
                        iconRes = R.drawable.handshakeicon, // placeholder
                        backgroundColor = orange,
                        modifier = Modifier.weight(1f),
                        onClick = { /* Handle Requests click */ }
                    )
                }
            }

            item {
                // Third row: Statistics and Corporate Database
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    AdminCard(
                        title = "Statistics",
                        iconRes = R.drawable.calendar_dm_on, // placeholder
                        backgroundColor = lightBlue,
                        modifier = Modifier.weight(1f),
                        onClick = { /* Handle Statistics click */ }
                    )

                    AdminCard(
                        title = "Corporate Database",
                        iconRes = R.drawable.communityicon, // placeholder
                        backgroundColor = lightBlue,
                        modifier = Modifier.weight(1f),
                        onClick = { /* Handle Corporate Database click */ }
                    )
                }
            }

            item {
                // Fourth row: Reimburse and SHPE Rentals
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    AdminCard(
                        title = "Reimburse",
                        iconRes = R.drawable.profile_circle_orange, // placeholder
                        backgroundColor = orange,
                        modifier = Modifier.weight(1f),
                        onClick = { /* Handle Reimburse click */ }
                    )

                    AdminCard(
                        title = "SHPE Rentals",
                        iconRes = R.drawable.profile_cap, // placeholder
                        backgroundColor = orange,
                        modifier = Modifier.weight(1f),
                        onClick = { /* Handle SHPE Rentals click */ }
                    )
                }
            }
        }
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
            .height(120.dp) // Fixed height instead of square
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
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
                modifier = Modifier.size(28.dp) // Smaller icon
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp, // Smaller text
                fontWeight = FontWeight.Medium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}