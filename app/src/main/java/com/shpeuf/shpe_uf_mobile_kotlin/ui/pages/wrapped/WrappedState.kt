package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.wrapped

data class WrappedState(
    val isLoading: Boolean = true,
    val topMonth: String = "loading ...",
    val secondMonth: String = "loading ...",
    val thirdMonth: String = "loading ...",
    val topCategory: String = "loading ...",
    val memberSince: String = "loading ...",
    val semester: String = "loading ...",
    val points: Int = 0,
    val percentile: Int = 100,
    val error: String? = null,

    val steps: List<PageTiming> = emptyList(),
    val index: Int = 0,
    val progress: Float = 0f,
    val isPlaying: Boolean = true,
    val isUserScrubbing: Boolean = false,
    val lastAdvanceAtMs: Long = 0L,
    val finished: Boolean = false
)

data class PageTiming(
    val index: Int,
    val startMs: Long,
    val endMs: Long
)

data class WrappedPage(
    val id: String,
    val title: String,
    val durationMs: Long
)