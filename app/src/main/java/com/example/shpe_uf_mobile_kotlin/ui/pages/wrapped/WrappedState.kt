package com.example.shpe_uf_mobile_kotlin.ui.pages.wrapped

data class WrappedState(
    val isLoading: Boolean = true,
    val topMonth: String = "",
    val topCategory: String = "",
    val memberSince: String = "",
    val semester: String = "",
    val points: Int = 0,
    val percentile: Int = 0,
    val error: String? = null
)
