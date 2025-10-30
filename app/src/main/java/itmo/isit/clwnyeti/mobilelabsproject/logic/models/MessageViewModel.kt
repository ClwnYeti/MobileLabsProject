package itmo.isit.clwnyeti.mobilelabsproject.logic.models

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import itmo.isit.clwnyeti.mobilelabsproject.logic.db.entities.MessageEntity
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.ChannelInfo
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.UIState
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.NetworkMonitor
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.MessageRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val repo: MessageRepository,
    private val tokenStore: TokenRepository,
    savedStateHandle: SavedStateHandle,
    networkMonitor: NetworkMonitor
): ViewModel() {
    val channel: ChannelInfo = savedStateHandle
        .get<String>("channel")
        ?.let { json ->
            Json.decodeFromString<ChannelInfo>(Uri.decode(json))
        }
        ?: error("Missing 'channel' argument")

    val online: StateFlow<Boolean> = networkMonitor.isOnline
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    private val _ui = MutableStateFlow(UIState())
    val ui: StateFlow<UIState> = _ui.asStateFlow()
    private var loadingMore = false


    val messages: StateFlow<List<MessageEntity>> = repo.observeMessages(channel.name)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())


    init {
        viewModelScope.launch { refresh() }
    }

    fun sendText(text: String) = viewModelScope.launch {
        if (text.isBlank()) return@launch
        if (!online.value) {
            return@launch
        }
        val token = tokenStore.getToken()
        val user = tokenStore.getUserName()
        if (token.isNullOrBlank() || user.isNullOrBlank()) {
            return@launch
        }

        runCatching {
            repo.sendText(
                userName = user,
                channel = channel.name,
                text = text.trim()
            )
            repo.refreshChannel(channel.name, limit = 20, lastKnownId = null)
        }
    }

    fun refresh() = viewModelScope.launch {
        val isOnline = online.value
        if (!isOnline) return@launch
        val token = tokenStore.getToken()
        if (token.isNullOrBlank()) {
            return@launch
        }
        _ui.update { it.copy(loading = true, error = null) }
        if (online.value) {
            runCatching {
                repo.refreshChannel(
                    channel = channel.name,
                    limit = 20,
                    lastKnownId = null
                )
            }
        }
        _ui.update { it.copy(loading = false) }
    }

    fun loadMore() = viewModelScope.launch {
        if (loadingMore) return@launch
        val isOnline = online.value
        if (!isOnline) return@launch
        val current = messages.value
        val oldest = current.minByOrNull { it.serverId }
        loadingMore = true
        if (oldest != null) {
            val token = tokenStore.getToken()
            if (token.isNullOrBlank()) {
                return@launch
            }
            if (online.value) {
                runCatching {
                    repo.refreshChannel(
                        channel = channel.name,
                        limit = 20,
                        lastKnownId = oldest.serverId,
                    )
                }
            }
        }
        loadingMore = false
    }
}