package itmo.isit.clwnyeti.mobilelabsproject.logic.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.ChannelInfo
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.NetworkMonitor
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.ChannelRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.TokenRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val repo: ChannelRepository,
    private val tokenRepository: TokenRepository,
    network: NetworkMonitor
): ViewModel() {

    data class UiState(
        val items: List<ChannelInfo> = emptyList(),
        val online: Boolean = true
    )

    private val onlineFlow = network.isOnline
    private val online: StateFlow<Boolean> = onlineFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), network.isCurrentlyOnline())
    private val channelsFlow = repo.observeChannels()

    val ui: StateFlow<UiState> = combine(
        channelsFlow, onlineFlow
    ) { list, online ->
        UiState(items = list.map { entity -> ChannelInfo(entity.name) }, online = online)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState())



    init {
        viewModelScope.launch {
            online.collectLatest { isOnline ->
                if (isOnline) {
                    refresh()
                }
            }
        }
    }

    fun refresh() = viewModelScope.launch {
        runCatching {
            val token = tokenRepository.getToken()
            if (token == null) {
                return@launch
            }
            repo.refreshChannels()
        }
    }
}