package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.admin.stats
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
// This import name depends on your package structure in the .graphql file
import com.shpeuf.shpe_uf_mobile_kotlin.GetAllAdminStatsQuery

class StatsViewModel(private val apolloClient: ApolloClient) : ViewModel() {

    // Internal state that holds the calculated data
    private val _uiState = MutableStateFlow(AdminStatsState())
    val uiState: StateFlow<AdminStatsState> = _uiState.asStateFlow()
    init {
        fetchAdminStats()
    }
    fun fetchAdminStats() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // This call uses the queries from your screenshot
                val response = apolloClient.query(GetAllAdminStatsQuery()).execute()

                response.data?.let { data ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            stats = Stat(
                                years = processRawData(rD= data.getYearStat),
                                majors = processRawData(rD = data.getMajorStat),
                                countries = processRawData(rD = data.getCountryStat),
                                genders = processRawData(rD = data.getSexStat),
                                ethnicities = processRawData(rD = data.getEthnicityStat)
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    /**
     * Helper to calculate percentages as required by the task.
     * Formula: (Value / Total) * 100
     */
    private fun processRawData(rawData: List<GetAllAdminStatsQuery.StatNode>?): MutableMap<String, Pair<Int, Float>> {
        val result = mutableMapOf<String, Pair<Int, Float>>()
        if (rawData.isNullOrEmpty()) return result

        val total = rawData.sumOf { it.value ?: 0 }

        rawData.forEach { node ->
            val label = node._id ?: "Unknown"
            val count = node.value ?: 0
            val percentage = if (total > 0) (count.toFloat() / total) * 100f else 0f

            result[label] = Pair(count, percentage)
        }
        return result
    }
}