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
    val classes: List<String?>? = null,
    val internships: List<String?>? = null,
    val socialMedia: List<String?>? = null,
    val photo: String? = null
)