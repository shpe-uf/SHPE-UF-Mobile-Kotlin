package com.example.shpe_uf_mobile_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.shpe_uf_mobile_kotlin.ui.pages.opening.OpeningPage
import com.example.shpe_uf_mobile_kotlin.ui.pages.opening.OpeningPageViewModel
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SHPEUFMobileKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OpeningPage(OpeningPageViewModel())
                }
            }
        }
    }
}