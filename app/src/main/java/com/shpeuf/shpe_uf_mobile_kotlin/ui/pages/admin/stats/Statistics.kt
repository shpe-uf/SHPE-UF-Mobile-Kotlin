package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.admin.stats
data class Stat(
    val majors: MutableMap<String, Pair<Int, Float>> = mutableMapOf(),
    val years: MutableMap<String, Pair<Int, Float>> = mutableMapOf(),
    val countries: MutableMap<String, Pair<Int, Float>> = mutableMapOf(),
    val genders: MutableMap<String, Pair<Int, Float>> = mutableMapOf(),
    val ethnicities: MutableMap<String, Pair<Int, Float>> = mutableMapOf()
)
//start
//data class
//viewmodel
//defined functions outside drawables
//queries

