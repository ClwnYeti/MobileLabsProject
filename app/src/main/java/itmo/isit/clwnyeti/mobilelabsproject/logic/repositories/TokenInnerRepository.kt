package itmo.isit.clwnyeti.mobilelabsproject.logic.repositories

interface TokenInnerRepository: TokenBaseRepository {
    fun save(userName: String, token: String)
    fun clear()
}