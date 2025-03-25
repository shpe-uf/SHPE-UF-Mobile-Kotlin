package com.example.shpe_uf_mobile_kotlin.ui.pages.profile
import android.graphics.Bitmap

data class ProfileUiState(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val fullName: String = "$firstName $lastName",
    val userName: String = "",
    val email: String = "",
    val gender: String = "",
    val ethnicity: String = "",
    val country: String = "",
    val year: String = "",
    val gradYear: String = "",
    val major: String = "",
    val classes: List<String?>? = null,
    val internships: List<String?>? = null,
    val socialMedia: List<String?>? = null,
    val photoBitmap: Bitmap? = null, //May change approach if too laggy
    val photo: String = "",
    val editable: List<Boolean> = listOf(false, true), // used for editing the text fields in the profile page,
    val isGenderExpanded: Boolean = false,
    val isEthnicityExpanded: Boolean = false,
    val isCountryOriginExpanded: Boolean = false,
    val isMajorExpanded: Boolean = false,
    val isYearExpanded: Boolean = false,
    val isGraduationExpanded: Boolean = false,

    // Error messages
    val errorMessages: Map<String, String?> = emptyMap()
)