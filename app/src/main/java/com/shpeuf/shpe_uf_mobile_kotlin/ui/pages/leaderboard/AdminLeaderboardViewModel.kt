package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpeuf.shpe_uf_mobile_kotlin.GetUsersFallPointsQuery
import com.shpeuf.shpe_uf_mobile_kotlin.GetUsersSpringPointsQuery
import com.shpeuf.shpe_uf_mobile_kotlin.GetUsersSummerPointsQuery
import com.shpeuf.shpe_uf_mobile_kotlin.apolloClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class Semester { FALL, SPRING, SUMMER }

data class LeaderboardRow(
    val rank: Int,
    val name: String,
    val email: String,
    val points: Int
)

data class AdminLeaderboardUiState(
    val selectedSemester: Semester = Semester.FALL,
    val isLoading: Boolean = false,
    val error: String? = null,
    val rows: List<LeaderboardRow> = emptyList()
)

class AdminLeaderboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdminLeaderboardUiState())
    val uiState: StateFlow<AdminLeaderboardUiState> = _uiState.asStateFlow()

    fun selectSemester(semester: Semester) {
        _uiState.update { it.copy(selectedSemester = semester) }
        load()
    }

    fun load() {
        val semester = _uiState.value.selectedSemester

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            runCatching { fetchLeaderboard(semester) }
                .onSuccess { rows ->
                    _uiState.update { it.copy(isLoading = false, rows = rows) }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Failed to load leaderboard"
                        )
                    }
                }
        }
    }

    private suspend fun fetchLeaderboard(semester: Semester): List<LeaderboardRow> {
        val users: List<Triple<String, String, Int>> = when (semester) {
            Semester.FALL -> {
                val res = apolloClient.query(GetUsersFallPointsQuery()).execute()
                if (res.hasErrors()) throw IllegalStateException(res.errors?.first()?.message ?: "GraphQL error")
                res.data?.getUsers.orEmpty().filterNotNull()
                    .map { Triple("${it.firstName} ${it.lastName}", it.email, it.fallPoints) }
            }
            Semester.SPRING -> {
                val res = apolloClient.query(GetUsersSpringPointsQuery()).execute()
                if (res.hasErrors()) throw IllegalStateException(res.errors?.first()?.message ?: "GraphQL error")
                res.data?.getUsers.orEmpty().filterNotNull()
                    .map { Triple("${it.firstName} ${it.lastName}", it.email, it.springPoints) }
            }
            Semester.SUMMER -> {
                val res = apolloClient.query(GetUsersSummerPointsQuery()).execute()
                if (res.hasErrors()) throw IllegalStateException(res.errors?.first()?.message ?: "GraphQL error")
                res.data?.getUsers.orEmpty().filterNotNull()
                    .map { Triple("${it.firstName} ${it.lastName}", it.email, it.summerPoints) }
            }
        }

        return users
            .sortedWith(compareByDescending<Triple<String, String, Int>> { it.third }.thenBy { it.first })
            .mapIndexed { idx, (name, email, points) ->
                LeaderboardRow(rank = idx + 1, name = name, email = email, points = points)
            }
    }
}