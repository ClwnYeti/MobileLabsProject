package itmo.isit.clwnyeti.mobilelabsproject.logic.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.ChannelRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.MessageCacheRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeaderViewModel @Inject constructor(
    private val tokenStore: TokenRepository,
    private val messageRepository: MessageCacheRepository,
    private val channelRepository: ChannelRepository,
): ViewModel(){
    private val _title = MutableStateFlow<String?>(null)

    val ui: StateFlow<UiState> = combine(
        tokenStore.userNameFlow,
        tokenStore.tokenFlow,
        _title
    ) { name, token, title ->
        UiState(
            title = title,
            userName = name,
            tokenPresent = !token.isNullOrBlank()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState())

    fun setTitle(newTitle: String?) { _title.value = newTitle }

    fun logout() {
        viewModelScope.launch {
            tokenStore.logout()
            messageRepository.clear()
            channelRepository.clear()
        }
        _title.value = null
    }

    data class UiState(
        val title: String? = null,
        val userName: String? = null,
        val tokenPresent: Boolean = false
    )
}