package com.example.shpe_uf_mobile_kotlin.ui.pages.guest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class InstagramPost(
    val id: String,
    val caption: String?,
    val mediaUrl: String,
    val permalink: String,
    val localImageName: String? = null
)

class GuestAboutViewModel : ViewModel() {

    private val _posts = MutableStateFlow<List<InstagramPost>>(emptyList())
    val posts: StateFlow<List<InstagramPost>> = _posts

    fun loadMockPosts() {
        _posts.value = mockInstagramPosts
    }

    //For da future
    fun fetchInstagramPostsFromAPI() {
        viewModelScope.launch {
            try {
                // TODO: Add real URL and network logic
                val response = emptyList<InstagramPost>() // Placeholder
                _posts.value = response
            } catch (e: Exception) {
                Log.e("GuestAboutViewModel", "Error fetching posts", e)
            }
        }
    }
}

