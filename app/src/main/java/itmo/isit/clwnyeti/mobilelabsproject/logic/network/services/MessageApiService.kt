package itmo.isit.clwnyeti.mobilelabsproject.logic.network.services

import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.Message
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MessageApiService {
    @GET("/channel/{name}")
    suspend fun getChannelMessages(
        @Path("name") name: String,
        @Query("limit") limit: Int = 20,
        @Query("lastKnownId") lastKnownId: Long? = null,
        @Query("reverse") reverse: Boolean = false
    ): List<Message>


    @POST("/messages")
    suspend fun sendMessage(@Body message: Message): Long
}