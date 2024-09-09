package com.example.shpe_uf_mobile_kotlin.ui.pages.signIn

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.navigation.Routes
import com.example.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE
import com.example.shpe_uf_mobile_kotlin.ui.theme.dark_bg


@Preview(showBackground = true)
@Composable
fun LoadingScreen() {

    // Dark mode support
    val gator = if (isSystemInDarkTheme()) {
        painterResource(R.drawable.gator)
    } else {
        painterResource(R.drawable.light_gator)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(OrangeSHPE)
            .fillMaxSize()
            .padding(top = 204.dp)
    ) {
        Image(
            painter = gator,
            contentDescription = "Gator",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(312.dp)
                .height(210.dp)
        )
        Text(
            modifier = Modifier.padding(top = 30.dp),
            text = "SHPE UF",
            style = TextStyle(
                fontSize = 64.sp,
                //fontFamily = FontFamily(Font(R.font.viga)),
                fontWeight = FontWeight(400),
                color = Color.White
            )
        )
        Spacer(Modifier.padding(top = 100.dp))
        ButtonSHPE { TODO("Something") }
//        ButtonSHPE { navController.navigate(Routes.login) }

    }
}

@Composable
fun ButtonSHPE(onClick: () -> Unit) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) dark_bg else Color.White

    Button(
        onClick = onClick,
        interactionSource = interactionSource,
        modifier = Modifier
            .width(129.dp)
            .height(126.dp)
            .clip(CircleShape),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
        ),
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.shpe_logo_full_color),
            contentDescription = "SHPE Logo",
            modifier = Modifier
                .width(104.dp)
                .height(112.dp)
        )
    }
}
