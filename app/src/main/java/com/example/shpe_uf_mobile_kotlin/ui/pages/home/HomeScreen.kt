package com.example.shpe_uf_mobile_kotlin.ui.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.shpe_uf_mobile_kotlin.ui.theme.SHPEUFMobileKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SHPEUFMobileKotlinTheme  {
                // Replace this with your card data
                CardView(title = "Card Title", content = "Card Content")
            }
        }
    }
}


@Composable
fun CardView(title: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(text = content)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardViewPreview() {
    SHPEUFMobileKotlinTheme {
        CardView(title = "Sample Card", content = "This is a sample card content.")
    }
}