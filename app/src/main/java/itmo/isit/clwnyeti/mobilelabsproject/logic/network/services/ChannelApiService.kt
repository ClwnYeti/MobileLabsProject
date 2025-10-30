package itmo.isit.clwnyeti.mobilelabsproject.logic.network.services

import retrofit2.http.GET

interface ChannelApiService {
    @GET("/channels")
    suspend fun getChannelNames(
    ): List<String>
}