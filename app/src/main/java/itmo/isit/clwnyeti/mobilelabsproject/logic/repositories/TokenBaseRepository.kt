package itmo.isit.clwnyeti.mobilelabsproject.logic.repositories

import kotlinx.coroutines.flow.Flow

interface TokenBaseRepository {
    fun getToken(): String?
    fun getUserName(): String?
    val userNameFlow: Flow<String?>
    val tokenFlow: Flow<String?>
    fun isAuthorized(): Boolean
}