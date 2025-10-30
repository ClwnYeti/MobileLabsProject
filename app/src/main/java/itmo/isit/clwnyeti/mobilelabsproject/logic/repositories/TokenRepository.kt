package itmo.isit.clwnyeti.mobilelabsproject.logic.repositories

interface TokenRepository: TokenBaseRepository {
    suspend fun login(userName: String, password: String)
    suspend fun logout()
}