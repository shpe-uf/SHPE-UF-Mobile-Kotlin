package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

data class ProfileUiState(
    val fullName:String? = null,
    val userName: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val ethnicity: String? = null,
    val country: String? = null,
    val year: String? = null,
    val gradYear: String? = null,
    val classes: Array<String>? = null,
    val internships: Array<String>? = null,
    val socialMedia: Array<String>? = null,
    val photo: String? = null
)