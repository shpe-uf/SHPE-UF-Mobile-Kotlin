package com.example.shpe_uf_mobile_kotlin.ui.pages.sponsors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shpe_uf_mobile_kotlin.apolloClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.shpe_uf_mobile_kotlin.GetPartnersQuery

class SponsorsViewModel : ViewModel() {
    private val _partners = MutableStateFlow<List<GetPartnersQuery.GetPartner>>(emptyList())
    val partners: StateFlow<List<GetPartnersQuery.GetPartner>> = _partners

    fun fetchPartners() {
        viewModelScope.launch {
            val response = apolloClient.query(GetPartnersQuery()).execute()
            response.data?.getPartners?.let {
                _partners.value = it.filterNotNull()
            }
        }
    }

    fun partnersByTier(tier: String): List<GetPartnersQuery.GetPartner> {
        return partners.value.filter { it.tier.equals(tier, ignoreCase = true) }
    }
}


