package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.sponsors

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.shpeuf.shpe_uf_mobile_kotlin.R
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.navigation.NavRoute
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.headerOrange
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.Viga

@Composable
fun GuestTopHeader(modifier: Modifier = Modifier, navController: NavHostController) {
    val currentDate = java.time.LocalDate.now()
    val monthName = currentDate.month.name.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

    Row(
        modifier = modifier.fillMaxWidth().height(83.dp).background(color = headerOrange).padding(
            top = WindowInsets.navigationBars.asPaddingValues(LocalDensity.current).calculateTopPadding()
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = monthName,
            style = TextStyle(color = Color.White, fontFamily = Viga, fontSize = 24.sp, fontWeight = FontWeight(400)),
            modifier = modifier.weight(1f).align(Alignment.Bottom).padding(vertical = 15.dp, horizontal = 32.dp)
        )
        TextButton(
            onClick = { navController.navigate(NavRoute.LOGIN) },
            modifier = modifier.align(Alignment.Bottom).offset(y = (-10).dp, x = (-18).dp)
        ) {
            Text("Login", color = Color.White)
            Icon(
                painter = painterResource(id = R.drawable.guestloginicon),
                contentDescription = "Login Icon",
                tint = Color.White,
                modifier = Modifier.size(24.dp).padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun SectionHeader(text: String, color: Color) {
    Text(
        text = text,
        color = color,
        fontSize = 20.sp,
        fontFamily = Viga,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    )
}

@Composable
fun PartnerBox(imageUrl: String, borderColor: Color) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .border(2.dp, borderColor, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .shadow(4.dp, RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().padding(4.dp)
        )
    }
}

@Composable
fun SponsorsPage(
    navController: NavHostController,
    mainViewModel: SHPEUFAppViewModel
) {
    val viewModel: SponsorsViewModel = viewModel()
    val partners by viewModel.partners.collectAsState()
    val isDarkMode = mainViewModel.uiState.collectAsState().value.isDarkMode

    val goldPartners = partners.filter { it.tier.equals("gold", ignoreCase = true) }
    val silverPartners = partners.filter { it.tier.equals("silver", ignoreCase = true) }
    val bronzePartners = partners.filter { it.tier.equals("bronze", ignoreCase = true) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            GuestTopHeader(navController = navController)

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(span = { GridItemSpan(3) }) {
                    SectionHeader("GOLD PARTNERS", Color(0xFFD4AF37))
                }
                items(goldPartners) {
                    PartnerBox(it.photo, Color(0xFFD4AF37))
                }
                item(span = { GridItemSpan(3) }) {
                    SectionHeader("SILVER PARTNERS", Color.Gray)
                }
                items(silverPartners) {
                    PartnerBox(it.photo, Color.Gray)
                }
                item(span = { GridItemSpan(3) }) {
                    SectionHeader("BRONZE PARTNERS", Color(0xFFCD7F32))
                }
                items(bronzePartners) {
                    PartnerBox(it.photo, Color(0xFFCD7F32))
                }
                item(span = { GridItemSpan(3) }) {
                    val context = LocalContext.current
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val textColor = if (isDarkMode) Color(0xFFFFFFFF) else Color(0xFF011F35)
                        Text(
                            "Interested in becoming\na partner?",
                            fontSize = 20.sp,
                            fontFamily = Viga,
                            textAlign = TextAlign.Center,
                            color = textColor
                        )
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:vpcorporate.shpeuf@gmail.com")
                                }
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = headerOrange)
                        ) {
                            Text("Contact us", fontSize = 20.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
