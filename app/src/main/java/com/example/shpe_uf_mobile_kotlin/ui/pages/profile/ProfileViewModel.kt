package com.example.shpe_uf_mobile_kotlin.ui.pages.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shpe_uf_mobile_kotlin.DeleteUserMutation
import com.example.shpe_uf_mobile_kotlin.GetUserQuery
import com.example.shpe_uf_mobile_kotlin.apolloClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProfileViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()


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

    fun saveProfileChanges() {
        TODO("Not yet implemented")
    }

    fun cancelProfileChanges() {
        TODO("Not yet implemented")
    }

    fun deleteProfile(){

    }

    // Function to update the user profile with values from the database given the user's ID.
    private fun getUserInfo(id: String){
        viewModelScope.launch{
            val userInfo = getUserInfoCoroutine(id)

            if(userInfo != null){
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

    // Functions for delete the user from the SHPE server.
    private fun deleteUserProfile(email: String){
        viewModelScope.launch {
            val output = deleteUserProfileCoroutine(email)

            // TODO: Add navigation to opening page after user account is deleted.
        }
    }

    private suspend fun deleteUserProfileCoroutine(email: String): Boolean{
        val response = apolloClient.mutation(DeleteUserMutation(email)).execute()

        return response.hasErrors()
    }
}