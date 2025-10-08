package com.example.shpe_uf_mobile_kotlin.ui.pages.wrapped

data class WrappedState(
    val isLoading: Boolean = true,
    val topMonth: String = "loading ...",
    val topCategory: String = "loading ...",
    val memberSince: String = "loading ...",
    val semester: String = "loading ...",
    val points: Int = 0,
    val percentile: Int = 0,
    val error: String? = null
)
