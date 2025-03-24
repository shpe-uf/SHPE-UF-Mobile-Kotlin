package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Optional
import com.example.shpe_uf_mobile_kotlin.DeleteUserMutation
import com.example.shpe_uf_mobile_kotlin.EditUserMutation
import com.example.shpe_uf_mobile_kotlin.GetUserQuery
import com.example.shpe_uf_mobile_kotlin.apolloClient
import com.example.shpe_uf_mobile_kotlin.type.EditUserProfileInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64.Default.encodeToByteArray
import kotlin.io.encoding.ExperimentalEncodingApi


class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private var originalProfile: ProfileUiState? = null

    fun onFirstNameChanged(firstName: String) {
        _uiState.value = _uiState.value.copy(firstName = firstName)
    }

    fun onLastNameChanged(lastName: String) {
        _uiState.value = _uiState.value.copy(lastName = lastName)
    }

    //TODO: add validation for text field inputs
    fun onFullNameChanged(fullName: String) {

        val error = if (fullName.isEmpty()) "Name cannot be empty." else ""

        // splitting the name into first and last

        val parts = fullName.trim().split(" ", limit = 2)

        when (parts.size) {
            1 -> _uiState.value = _uiState.value.copy(firstName = parts[0], lastName = "")
            2 -> _uiState.value = _uiState.value.copy(firstName = parts[0], lastName = parts[1])
        }

        _uiState.value = _uiState.value.copy(
            fullName = fullName,
            errorMessages = _uiState.value.errorMessages.toMutableMap().apply {
                put("fullName", error)
            }
        )
    }

    fun onUserNameChanged(userName: String) {
        _uiState.value = _uiState.value.copy(userName = userName)
    }

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onGenderChanged(gender: String) {
        _uiState.value = _uiState.value.copy(gender = gender)
    }

    fun onEthnicityChanged(ethnicity: String) {
        _uiState.value = _uiState.value.copy(ethnicity = ethnicity)
    }

    fun onCountryChanged(country: String) {
        _uiState.value = _uiState.value.copy(country = country)
    }

    fun onMajorChanged(major: String) {
        _uiState.value = _uiState.value.copy(major = major)
    }

    fun onYearChanged(year: String) {
        _uiState.value = _uiState.value.copy(year = year)
    }

    fun onGradYearChanged(gradYear: String) {
        _uiState.value = _uiState.value.copy(gradYear = gradYear)
    }

    fun onClassesChanged(classes: List<String?>?) {
        _uiState.value = _uiState.value.copy(classes = classes)
    }

    fun onInternshipsChanged(internships: List<String?>?) {
        _uiState.value = _uiState.value.copy(internships = internships)
    }

    fun onSocialMediaChanged(socialMedia: List<String?>?) {
        _uiState.value = _uiState.value.copy(socialMedia = socialMedia)
    }

    // added photo bitmap to state when calling this fun
    fun onPhotoChanged(photo: String) {
        _uiState.value = _uiState.value.copy(photo = photo)
        _uiState.value =
            _uiState.value.copy(photoBitmap = decodeBase64ToBitmap(_uiState.value.photo.toString()))
    }

    // Add to the lists
    fun addClass(className: String) {
        val currentClasses = _uiState.value.classes?.toMutableList() ?: mutableListOf()

        val error = when {
            className.isEmpty() -> "Class cannot be empty."
            className in currentClasses -> "Class already exists."
            else -> null
        }

        if (error != null) {
            _uiState.value = _uiState.value.copy(
                errorMessages = _uiState.value.errorMessages.toMutableMap().apply {
                    put("classes", error)
                }
            )
        } else {
            currentClasses.add(className)
            _uiState.value = _uiState.value.copy(classes = currentClasses)
        }
    }

    fun addInternship(internshipName: String) {

        val currentInternships = _uiState.value.internships?.toMutableList() ?: mutableListOf()

        val error = when {
            internshipName.isEmpty() -> "Internship cannot be empty."
            internshipName in currentInternships -> "Internship already exists."
            else -> null
        }

        if (error != null) {
            _uiState.value = _uiState.value.copy(
                errorMessages = _uiState.value.errorMessages.toMutableMap().apply {
                    put("internships", error)
                }
            )
        } else {
            currentInternships.add(internshipName)
            _uiState.value = _uiState.value.copy(internships = currentInternships)
        }
    }

    fun addLinks(link: String) {
        val currentLinks = _uiState.value.socialMedia?.toMutableList() ?: mutableListOf()

        val error = when {
            link.isEmpty() -> "Links cannot be empty."
            link in currentLinks -> "Link already exists."
            else -> null
        }

        if (error != null) {
            _uiState.value = _uiState.value.copy(
                errorMessages = _uiState.value.errorMessages.toMutableMap().apply {
                    put("socialMedia", error)
                }
            )
        } else {
            currentLinks.add(link)
            _uiState.value = _uiState.value.copy(socialMedia = currentLinks)
        }
    }

    // Remove from the lists
    fun removeInternship(internshipName: String) {
        val currentInternships = _uiState.value.internships?.toMutableList() ?: mutableListOf()

        if (currentInternships.contains(internshipName)) {
            currentInternships.remove(internshipName)
        }

        _uiState.value = _uiState.value.copy(internships = currentInternships)
    }

    fun removeClass(className: String) {
        val currentClasses = _uiState.value.classes?.toMutableList() ?: mutableListOf()

        Log.d("list", "$currentClasses")

        if (currentClasses.contains(className)) {
            currentClasses.remove(className)
        }

        _uiState.value = _uiState.value.copy(classes = currentClasses)
    }

    fun removeLink(link: String) {
        val currentLinks = _uiState.value.socialMedia?.toMutableList() ?: mutableListOf()

        if (currentLinks.contains(link)) {
            currentLinks.remove(link)
        }

        _uiState.value = _uiState.value.copy(socialMedia = currentLinks)
    }

    fun cancelProfileChanges() {
        originalProfile?.let {
            _uiState.value = it.copy(editable = listOf(false, true))
        }
    }

    fun deleteProfile(email: String) {
        deleteUserProfile(email)
    }

    fun editProfile() {
        originalProfile = _uiState.value.copy()
        _uiState.value = _uiState.value.copy(editable = listOf(true, false))
    }

    // Should load the user's profile, will be called when the user logs in and the id is collected and stored in the app.
    fun loadProfile(id: String): Boolean {
        val current = uiState.value

        Log.d("Profile:", id)

        if (current.firstName == "") {
            getUserInfo(id)
            return true
        }

        return false
    }

    // Function to update the user profile with values from the database given the user's ID.
    // Just call getUserInfo("64ea79b9f2051e00149c75b7") once, and it should populate the fields.
    private fun getUserInfo(id: String) {
        viewModelScope.launch {
            val userInfo = getUserInfoCoroutine(id)

            Log.d("userinfo", "Type: ${userInfo?.javaClass?.name}, Value: $userInfo")

            if (userInfo != null) {
                onFirstNameChanged(userInfo.firstName)
                onLastNameChanged(userInfo.lastName)
                onFullNameChanged(userInfo.firstName + " " + userInfo.lastName)
                onUserNameChanged(userInfo.username)
                onEmailChanged(userInfo.email)
                onGenderChanged(userInfo.sex)
                onEthnicityChanged(userInfo.ethnicity)
                onCountryChanged(userInfo.country)
                onMajorChanged(userInfo.major)
                onYearChanged(userInfo.year)
                onGradYearChanged(userInfo.graduating)
                onClassesChanged(userInfo.classes)
                onInternshipsChanged(userInfo.internships)
                onSocialMediaChanged(userInfo.socialMedia)
                onPhotoChanged(userInfo.photo)
            }
        }
    }

    private suspend fun getUserInfoCoroutine(id: String): GetUserQuery.GetUser? { // Returns getUser object.
        val response = apolloClient.query(GetUserQuery(id)).execute() // Calling query

        return if (!response.hasErrors()) { // If no errors, return the getUser object containing user information.
            response.data?.getUser
        } else { // If there is an error return null.
            null
        }
    }


    fun saveProfileChanges() {

        val current = _uiState.value

        val input = EditUserProfileInput(
            firstName = current.firstName,
            lastName = current.lastName,
            classes = Optional.present(current.classes),
            country = current.country,
            email = current.email,
            ethnicity = current.ethnicity,
            graduating = current.gradYear,
            internships = Optional.present(current.internships),
            major = current.major,
            photo = current.photo,
            sex = current.gender,
            socialMedia = Optional.present(current.socialMedia),
            year = current.year
        )

        updateUserProfile(editUserProfileInput = Optional.present(input))
    }


    // Function to update user profile based on attribute chosen.
    private fun updateUserProfile(editUserProfileInput: Optional<EditUserProfileInput?>) {
        viewModelScope.launch {
            updateUserProfileCoroutine(editUserProfileInput)
        }
    }

    private suspend fun updateUserProfileCoroutine(editUserProfileInput: Optional<EditUserProfileInput?>): Boolean {
        val response = apolloClient.mutation(EditUserMutation(editUserProfileInput)).execute()

        if (!response.hasErrors()) {
            _uiState.value = _uiState.value.copy(editable = listOf(false, true))
            Log.d("Profile:", "${_uiState.value.editable}")
        }

        return response.hasErrors()
    }

    // Functions for delete the user from the SHPE server.
    private fun deleteUserProfile(email: String): Boolean {
        var output = false
        viewModelScope.launch {
            output = deleteUserProfileCoroutine(email)
            // TODO: Add navigation to opening page after user account is deleted.
        }
        return output
    }

    private suspend fun deleteUserProfileCoroutine(email: String): Boolean {
        val response = apolloClient.mutation(DeleteUserMutation(email)).execute()

        return response.hasErrors()
    }

    // added this helper function to decode the base64image into a bitmap
    private val PNG_BASE64_HEADER = "data:image/jpeg;base64,"

    private fun decodeBase64ToBitmap(base64: String): Bitmap? {
        val cleanBase64 = base64.replaceFirst(PNG_BASE64_HEADER, "")
        val decodedBytes = Base64.decode(cleanBase64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun tempDeleteUser(): Unit? {
        // This does nothing its just so u can click delete and the app not crash
        return null
    }

    fun toggleDropdownMenu(menu: Int) {
        when (menu) {
            0 -> _uiState.value =
                _uiState.value.copy(isGenderExpanded = !_uiState.value.isGenderExpanded)

            1 -> _uiState.value =
                _uiState.value.copy(isEthnicityExpanded = !_uiState.value.isEthnicityExpanded)

            2 -> _uiState.value =
                _uiState.value.copy(isCountryOriginExpanded = !_uiState.value.isCountryOriginExpanded)

            3 -> _uiState.value =
                _uiState.value.copy(isMajorExpanded = !_uiState.value.isMajorExpanded)

            4 -> _uiState.value =
                _uiState.value.copy(isYearExpanded = !_uiState.value.isYearExpanded)

            5 -> _uiState.value =
                _uiState.value.copy(isGraduationExpanded = !_uiState.value.isGraduationExpanded)
        }
    }
}