package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

import com.apollographql.apollo3.api.Optional

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
    val classes: Optional<List<String?>?>? = null,
    val internships: Optional<List<String?>?>? = null,
    val socialMedia: Optional<List<String?>?>? = null,
    val photo: String? = null,
    val editable: List<Boolean> = listOf(false, true) // used for editing the text fields in the profile page,
)