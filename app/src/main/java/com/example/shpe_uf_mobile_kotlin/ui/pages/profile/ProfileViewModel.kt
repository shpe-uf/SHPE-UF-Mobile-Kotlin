package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class ProfileViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    //TODO: set currentDisplayName to whatever currentName from ProfileUiState.kt is
    private lateinit var currentDisplayName: String
}