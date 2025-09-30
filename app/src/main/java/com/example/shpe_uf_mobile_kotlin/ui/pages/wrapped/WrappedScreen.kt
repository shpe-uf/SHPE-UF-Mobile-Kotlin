package com.example.shpe_uf_mobile_kotlin.ui.pages.wrapped

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.error
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shpe_uf_mobile_kotlin.EventsQuery
import com.example.shpe_uf_mobile_kotlin.EventsQuery.Event
import com.example.shpe_uf_mobile_kotlin.apolloClient
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.points.PointsPageViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.points.formatDate

@Composable
fun WrappedPage(shpeufAppViewModel: SHPEUFAppViewModel, wrappedViewModel: WrappedViewModel) {
    val UserState by shpeufAppViewModel.uiState.collectAsState()
    val id = UserState.id
    val isDarkMode = UserState.isDarkMode
    val UsernameState by shpeufAppViewModel.userState.collectAsState()
    val username = UsernameState.username
}