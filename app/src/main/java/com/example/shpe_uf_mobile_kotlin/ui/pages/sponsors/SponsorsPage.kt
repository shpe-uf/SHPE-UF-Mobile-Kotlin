package com.example.shpe_uf_mobile_kotlin.ui.pages.sponsors

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import com.example.shpe_uf_mobile_kotlin.GetPartnersQuery
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.ui.navigation.NavRoute
import com.example.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import com.example.shpe_uf_mobile_kotlin.ui.theme.headerOrange
import com.example.shpe_uf_mobile_kotlin.ui.theme.Viga
import coil.compose.AsyncImage

import com.example.shpe_uf_mobile_kotlin.ui.pages.sponsors.SponsorsViewModel

@Composable
fun GuestTopHeader(modifier: Modifier = Modifier, navController: NavHostController) {

    val currentDate = java.time.LocalDate.now()
    val monthName = currentDate.month.name.lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(83.dp)
            .background(color = headerOrange)
            .padding(
                top = WindowInsets.navigationBars
                    .asPaddingValues(LocalDensity.current)
                    .calculateTopPadding()
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = monthName,
            style = TextStyle(
                color = Color.White,
                fontFamily = Viga,
                fontSize = 24.sp,
                fontWeight = FontWeight(400)
            ),
            color = Color.White,
            modifier = modifier
                .weight(1f)
                .align(Alignment.Bottom)
                .padding(vertical = 15.dp, horizontal = 32.dp)
        )
        TextButton(
            onClick = { navController.navigate(NavRoute.LOGIN) },
            modifier = modifier
                .align(Alignment.Bottom)
                .offset(y = (-10).dp, x = (-18).dp)
        ) {
            Text("Login", color = Color.White)
            Icon(
                painter = painterResource(id = R.drawable.guestloginicon),
                contentDescription = "Login Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun PartnerSection(title: String, titleColor: Color, items: List<GetPartnersQuery.GetPartner>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, color = titleColor, fontSize = 20.sp, fontFamily = Viga)
        if (items.size <= 3) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                items.forEach { PartnerBox(it.photo, titleColor) }
            }
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                items(items.size) { index -> PartnerBox(items[index].photo, titleColor) }
            }
        }
    }
}

@Composable
fun PartnerBox(imageUrl: String, shadowColor: Color) {
    Box(
        modifier = Modifier.size(96.dp).shadow(4.dp, shape = RoundedCornerShape(8.dp), ambientColor = shadowColor, spotColor = shadowColor).background(Color.White, shape = RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize().padding(8.dp)
        )
    }
}

@Composable
fun SponsorsPage(viewModel: SponsorsViewModel = SponsorsViewModel(), navController: NavHostController, mainViewModel: SHPEUFAppViewModel) {
    val partners by viewModel.partners.collectAsState()
    val isDarkMode = mainViewModel.uiState.collectAsState().value.isDarkMode

    LaunchedEffect(Unit) {
        viewModel.fetchPartners()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            GuestTopHeader(navController = navController)
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { PartnerSection("GOLD PARTNERS", Color(0xFFD4AF37), viewModel.partnersByTier("gold")) }
                item { PartnerSection("SILVER PARTNERS", Color.Gray, viewModel.partnersByTier("silver")) }
                item { PartnerSection("BRONZE PARTNERS", Color(0xFFCD7F32), viewModel.partnersByTier("bronze")) }
                item {
                    val context = LocalContext.current
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Interested in becoming\na partner?", fontSize = 24.sp, fontFamily = Viga, textAlign = TextAlign.Center)
//                        Button(
//                            onClick = {
//                                val intent = Intent(Intent.ACTION_SENDTO).apply {
//                                    data = Uri.parse("mailto:vpcorporate.shpeuf@gmail.com")
//                                }
//                                context.startActivity(intent)
//                            },
//                            colors = ButtonDefaults.buttonColors(containerColor = OrangeSHPE)
//                        ) {
//                            Text("Contact us", fontSize = 20.sp, color = Color.White)
//                        }
                    }
                }
            }
        }
    }
}