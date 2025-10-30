package itmo.isit.clwnyeti.mobilelabsproject.logic.network.utils

import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.TokenInnerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenRepo: TokenInnerRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenRepo.getToken()
        val request = if (!token.isNullOrBlank()) {
            chain.request().newBuilder()
                .addHeader("X-Auth-Token", token)
                .build()
        } else {
            chain.request()
        }

        val response = chain.proceed(request)

        if (response.isSuccessful) {
            return response
        }

        if (response.code == 401) {
            CoroutineScope(Dispatchers.Default).launch {
                tokenRepo.clear()
                AppEventBus.emit(AppEvent.Unauthorized)
            }
        } else {
            CoroutineScope(Dispatchers.Default).launch {
                tokenRepo.clear()
                AppEventBus.emit(AppEvent.Error(response.message))
            }
        }
        return response
    }
}

object AppEventBus {
    private val _events = MutableSharedFlow<AppEvent>()
    val events = _events.asSharedFlow()

    suspend fun emit(event: AppEvent) = _events.emit(event)
}

sealed class AppEvent {
    object Unauthorized : AppEvent()
    data class Error(val message: String) : AppEvent()
}