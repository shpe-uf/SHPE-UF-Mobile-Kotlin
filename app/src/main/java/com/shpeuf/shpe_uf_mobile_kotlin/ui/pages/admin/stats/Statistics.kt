package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.admin.stats
data class StatEntry(
    val label: String,
    val count: Int,
    val percentage: Float
)

data class Stat(
    val years: Map<String, Pair<Int, Float>> = emptyMap(),
    val majors: Map<String, Pair<Int, Float>> = emptyMap(),
    val countries: Map<String, Pair<Int, Float>> = emptyMap(),
    val genders: Map<String, Pair<Int, Float>> = emptyMap(),
    val ethnicities: Map<String, Pair<Int, Float>> = emptyMap()
)

data class AdminStatsState(
    val stats: Stat = Stat(),
    val isLoading: Boolean = false,
    val error: String? = null
)


