package com.example.shpe_uf_mobile_kotlin.ui.pages.opening

import com.example.shpe_uf_mobile_kotlin.R

// Opening page state
data class OpeningPageState(

    // Storing each page as a pair of images and captions.
    val pages: List<Pair<Int, String>> = listOf(
        Pair(R.drawable.opening_1, "Familia"),
        Pair(R.drawable.opening_2, "Leadership"),
        Pair(R.drawable.opening_3, "Professionalism"),
        Pair(R.drawable.opening_4, "Resilience"),
        Pair(R.drawable.opening_5, "Mentorship"),
        Pair(R.drawable.opening_6, "Education"),
        Pair(R.drawable.opening_7, "Technology"),
        Pair(R.drawable.opening_8, "Community"),
    ),

    // Current page index
    val pageKey: Int = 0,
)