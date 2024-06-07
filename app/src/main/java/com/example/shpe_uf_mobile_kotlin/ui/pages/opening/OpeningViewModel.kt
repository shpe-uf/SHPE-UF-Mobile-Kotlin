package com.example.shpe_uf_mobile_kotlin.ui.pages.opening;

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

// Represents the state of the opening page.
class OpeningViewModel : ViewModel() {

    // Holds the state of the opening page.
    private val _uiState = MutableStateFlow(OpeningPageState())
    val uiState: StateFlow<OpeningPageState> = _uiState.asStateFlow()

    // Get the page given the current index.
    fun getPage(index: Int): Pair<Int, String> {
        val currentState = uiState.value
        return currentState.pages[index]
    }

    // Get the current page key.
    private fun getPageKey(): Int {
        return uiState.value.pageKey
    }

    // Updates the current page depending on if it's scrolling or automatic.
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun updatePage(): PagerState {
        val currentState = uiState.collectAsState().value
        val pages = currentState.pages

        val pagerState = rememberPagerState(pageCount = { pages.size })
        val effectFlow = rememberFlowWithLifecycle(
            pagerState.interactionSource.interactions, LocalLifecycleOwner.current
        )

        // Used for manually moving the images using a drag interaction.
        LaunchedEffect(effectFlow) {
            effectFlow.collectLatest {
                if (it is DragInteraction.Stop) {

                    val velocity = it.velocity
                    if (pagerState.currentPage == pages.size - 1) { // Reached last page, scroll to first page.
                        pagerState.scrollToPage(0)
                    }
                    else {
                        updatePageKey()
                    }
                }
            }
        }

        // Used for automatically moving the carousel.
        LaunchedEffect(getPageKey()) {
            delay(3250)
            val newPage = (pagerState.currentPage + 1) % pages.size
            pagerState.animateScrollToPage(newPage)
            updatePageKey()
        }

        return pagerState
    }

    // Increment the page key by 1.
    private fun updatePageKey() {
        val currentState = uiState.value

        _uiState.value = currentState.copy(
            pageKey = getPageKey() + 1
        )
    }


    /**
     * Remembers the result of [flowWithLifecycle]. Updates the value if the [flow]
     * or [lifecycleOwner] changes. Cancels collection in onStop() and start it in onStart()
     *
     * @param flow The [Flow] that is going to be collected.
     * @param lifecycleOwner The [LifecycleOwner] to validate the [Lifecycle.State] from
     *
     * @return [Flow] with the remembered value of type [T]
     */
    @Composable
    private fun <T> rememberFlowWithLifecycle( // 1
        flow: Flow<T>, lifecycleOwner: LifecycleOwner
    ): Flow<T> {
        return remember(flow, lifecycleOwner) { // 2
            flow.flowWithLifecycle( // 3
                lifecycleOwner.lifecycle, // 4
                Lifecycle.State.STARTED // 5
            )
        }
    }
}

