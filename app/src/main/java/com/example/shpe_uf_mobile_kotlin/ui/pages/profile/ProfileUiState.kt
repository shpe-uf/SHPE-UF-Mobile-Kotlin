package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

data class ProfileUiState(
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val fullName: String? = "$firstName $lastName",
    val userName: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val ethnicity: String? = null,
    val country: String? = null,
    val year: String? = null,
    val gradYear: String? = null,
    val major: String? = null,
    val classes: List<String?>? = null,
    val internships: List<String?>? = null,
    val socialMedia: List<String?>? = null,
    val photo: String? = null
)