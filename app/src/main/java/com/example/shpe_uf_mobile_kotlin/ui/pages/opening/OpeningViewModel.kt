package com.example.shpe_uf_mobile_kotlin.ui.pages.opening;

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class OpeningViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OpeningPageState())
    val uiState: StateFlow<OpeningPageState> = _uiState.asStateFlow()
}