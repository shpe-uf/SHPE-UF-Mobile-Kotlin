package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shpe_uf_mobile_kotlin.DeleteUserMutation
import com.example.shpe_uf_mobile_kotlin.EditUserMutation
import com.example.shpe_uf_mobile_kotlin.GetUserQuery
import com.example.shpe_uf_mobile_kotlin.apolloClient
import com.example.shpe_uf_mobile_kotlin.type.EditUserProfileInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProfileViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onFirstNameChanged(firstName: String){
        _uiState.value = _uiState.value.copy(firstName = firstName)
    }

    fun onLastNameChanged(lastName: String){
        _uiState.value = _uiState.value.copy(lastName = lastName)
    }

    //TODO: add validation for text field inputs
    fun onFullNameChanged(fullName: String) {
        _uiState.value = _uiState.value.copy(fullName = fullName)
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
    fun onYearChanged(year: String) {
        _uiState.value = _uiState.value.copy(year = year)
    }
    fun onGradYearChanged(gradYear: String) {
        _uiState.value = _uiState.value.copy(gradYear = gradYear)
    }

    fun onClassesChanged(classes: List<String?>?){
        _uiState.value = _uiState.value.copy(classes = classes)
    }

    fun onInternshipsChanged(internships: List<String?>?){
        _uiState.value = _uiState.value.copy(internships = internships)
    }

    fun onSocialMediaChanged(socialMedia: List<String?>?){
        _uiState.value = _uiState.value.copy(socialMedia = socialMedia)
    }

    fun onPhotoChanged(photo: String){
        _uiState.value = _uiState.value.copy(photo = photo)
    }

    fun getUsername(): String? {
        return _uiState.value.userName
    }

    fun saveProfileChanges() {
        TODO("Not yet implemented")
    }

    fun cancelProfileChanges() {
        TODO("Not yet implemented")
    }

    fun deleteProfile(email: String){
        deleteUserProfile(email)
    }

    // Should load the user's profile, will be called when the user logs in and the id is collected and stored in the app.
    fun loadProfile(id: String): Boolean{
        val current = uiState.value

        if(current.firstName == null){
            getUserInfo(id)
            return true
        }

        return false
    }

    // Function to update the user profile with values from the database given the user's ID.
    // Just call getUserInfo("64ea79b9f2051e00149c75b7") once, and it should populate the fields.
    private fun getUserInfo(id: String){
        viewModelScope.launch{
            val userInfo = getUserInfoCoroutine(id)

            if(userInfo != null){
                onFirstNameChanged(userInfo.firstName)
                onLastNameChanged(userInfo.lastName)
                onFullNameChanged(userInfo.firstName + " " + userInfo.lastName)
                onUserNameChanged(userInfo.username)
                onEmailChanged(userInfo.email)
                onGenderChanged(userInfo.sex)
                onEthnicityChanged(userInfo.ethnicity)
                onCountryChanged(userInfo.country)
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

        if(!response.hasErrors()){ // If no errors, return the getUser object containing user information.
            return response.data?.getUser
        } else { // If there is an error return null.
            return null
        }
    }

//    fun updateUser(option: String): EditUserProfileInput? {
//        val current = _uiState.value
//
//        if(current.firstName is String
//            && current.lastName is String
//            && current.classes is List<*>
//            && current.country is String
//            && current.email is String
//            && current.ethnicity is String
//            && current.gradYear is String
//            && current.internships is List<*>
//            && current.major is String
//            && current.photo is String
//            && current.gender is String
//            && current.socialMedia is List<*>
//            && current.year is String
//            ){
//            val input = EditUserProfileInput(
//                firstName = current.firstName,
//                lastName = current.lastName,
//                classes = current.classes,
//                country = current.country,
//                email = current.email, // used to update the user's info.
//                ethnicity = current.ethnicity,
//                graduating = current.gradYear,
//                internships = current.internships,
//                major = current.major,
//                photo = current.photo,
//                sex = current.gender,
//                socialMedia = current.socialMedia,
//                year = current.year
//            )
//
//            return input
//        }
//
//        return null
//    }

    // Function to update user profile based on attribute chosen.
//    private fun updateUserProfile(editUserProfileInput: EditUserProfileInput){
//        viewModelScope.launch {
//            updateUserProfileCoroutine(editUserProfileInput)
//        }
//    }
//
//    private suspend fun updateUserProfileCoroutine(editUserProfileInput: EditUserProfileInput): Boolean{
//        val response = apolloClient.mutation(EditUserMutation(editUserProfileInput)).execute()
//
//        return response.hasErrors()
//    }

    // Functions for delete the user from the SHPE server.
    private fun deleteUserProfile(email: String): Boolean {
        var output = false
        viewModelScope.launch {
            output = deleteUserProfileCoroutine(email)
            // TODO: Add navigation to opening page after user account is deleted.
        }
        return output
    }

    private suspend fun deleteUserProfileCoroutine(email: String): Boolean{
        val response = apolloClient.mutation(DeleteUserMutation(email)).execute()

        return response.hasErrors()
    }

    fun tempDeleteUser(): Unit? {
        // This does nothing its just so u can click delete and the app not crash
        return null
    }

    fun tempEditProfile(): Unit?{
        return null
    }
}