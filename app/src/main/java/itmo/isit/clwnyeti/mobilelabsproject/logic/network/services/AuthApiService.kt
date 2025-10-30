package itmo.isit.clwnyeti.mobilelabsproject.logic.network.services

import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.Login
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/login")
    suspend fun login(@Body loginForm: Login): String?


    @POST("/logout")
    suspend fun logout()
}