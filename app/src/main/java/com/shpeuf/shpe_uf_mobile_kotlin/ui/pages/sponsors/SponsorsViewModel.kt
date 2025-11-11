package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.sponsors

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpeuf.shpe_uf_mobile_kotlin.apolloClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.shpeuf.shpe_uf_mobile_kotlin.GetPartnersQuery

class SponsorsViewModel : ViewModel() {
    private val _partners = MutableStateFlow<List<GetPartnersQuery.GetPartner>>(emptyList())
    val partners: StateFlow<List<GetPartnersQuery.GetPartner>> = _partners

    init {
        fetchPartners()
    }

    fun fetchPartners() {
        viewModelScope.launch {
            try {
                val response = apolloClient.query(GetPartnersQuery()).execute()
                response.data?.getPartners?.let {
                    val filtered = it.filterNotNull()
                    _partners.value = filtered
                } ?: Log.d("SponsorsViewModel", "No data received in getPartners")
            } catch (e: Exception) {
                Log.e("SponsorsViewModel", "Error fetching partners", e)
            }
        }
    }

    fun partnersByTier(tier: String): List<GetPartnersQuery.GetPartner> {
        return partners.value.filter { it.tier.equals(tier, ignoreCase = true) }
    }
}


