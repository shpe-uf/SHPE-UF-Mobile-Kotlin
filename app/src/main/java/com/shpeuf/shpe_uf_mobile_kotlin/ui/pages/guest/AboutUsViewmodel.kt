package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.guest

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
}

