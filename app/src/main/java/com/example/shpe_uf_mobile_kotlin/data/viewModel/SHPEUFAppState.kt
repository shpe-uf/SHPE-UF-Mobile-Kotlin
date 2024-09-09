package com.example.shpe_uf_mobile_kotlin.data.viewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

data class SHPEUFAppState(
    val SHPEID: String? = null,
    val isLoggedIn: Boolean = false,
)