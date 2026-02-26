package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.admin.stats
data class StatEntry(
    val label: String,
    val count: Int,
    val percentage: Float
)

data class StatsViewState(
    val majors: List<StatEntry> = emptyList(),
    val years: List<StatEntry> = emptyList(),
    val countries: List<StatEntry> = emptyList(),
    // ... other categories
    val isLoading: Boolean = false
)


