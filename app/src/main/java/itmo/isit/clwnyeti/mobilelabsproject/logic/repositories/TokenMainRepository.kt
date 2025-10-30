package itmo.isit.clwnyeti.mobilelabsproject.logic.repositories

import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.Login
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.services.AuthApiService
import kotlinx.coroutines.flow.Flow
import okio.IOException

class TokenMainRepository(
    private val api: AuthApiService,
    private val innerRepository: TokenInnerRepository
): TokenRepository {
    override suspend fun login(userName: String, password: String) {
        try {
            val token = api.login(Login(userName, password))
            if (token == null) {
                throw IOException()
            }
            innerRepository.save(userName, token)
        } catch (e: Exception) {
            innerRepository.clear()
            throw e
        }
    }

    override fun getToken(): String? = innerRepository.getToken()
    override fun getUserName(): String? = innerRepository.getUserName()

    override val userNameFlow: Flow<String?> = innerRepository.userNameFlow
    override val tokenFlow: Flow<String?> = innerRepository.tokenFlow

    override suspend fun logout() {
        try {
            api.logout()
        } catch (_: Exception) {
        } finally {
            innerRepository.clear()
        }
    }

    override fun isAuthorized(): Boolean = innerRepository.isAuthorized()
}