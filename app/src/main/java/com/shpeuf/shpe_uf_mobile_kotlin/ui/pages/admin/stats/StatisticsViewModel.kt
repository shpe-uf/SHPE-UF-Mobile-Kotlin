package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.admin.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.shpeuf.shpe_uf_mobile_kotlin.GetAllAdminStatsQuery
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StatsViewModel(private val apolloClient: ApolloClient) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminStatsState())
    val uiState: StateFlow<AdminStatsState> = _uiState.asStateFlow()

    init {
        fetchAdminStats()
    }

    fun fetchAdminStats() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = apolloClient.query(GetAllAdminStatsQuery()).execute()
                response.data?.let { data ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            stats = Stat(
                                years       = processGenericData(data.getYearStat) { it._id to it.value },
                                majors      = processGenericData(data.getMajorStat) { it._id to it.value },
                                countries   = processGenericData(data.getCountryStat) { it._id to it.value },
                                genders     = processGenericData(data.getSexStat) { it._id to it.value },
                                ethnicities = processGenericData(data.getEthnicityStat) { it._id to it.value }
                            )
                        )
                    }
                } ?: _uiState.update {
                    it.copy(isLoading = false, error = "No data returned from server.")
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
            }
        }
    }

    private fun <T> processGenericData(
        rawData: List<T?>?,
        mapper: (T) -> Pair<String?, Int?>
    ): Map<String, Pair<Int, Float>> {
        if (rawData.isNullOrEmpty()) return emptyMap()

        // 1. Filter out nulls and extract the values into a temporary list
        val items = rawData.filterNotNull().map { mapper(it) }

        // 2. Calculate the total sum of all 'value' fields for percentage calculation
        val total = items.sumOf { it.second ?: 0 }

        // 3. Create the Map<String, Pair<Int, Float>> required by Statistics.kt
        return items.associate { (id, value) ->
            val label = id ?: "Unknown"
            val count = value ?: 0
            val percentage = if (total > 0) (count.toFloat() / total) * 100f else 0f

            label to Pair(count, percentage)
        }
    }
}

// Factory so the ViewModel can receive ApolloClient via constructor
class StatsViewModelFactory(private val apolloClient: ApolloClient) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StatsViewModel(apolloClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}