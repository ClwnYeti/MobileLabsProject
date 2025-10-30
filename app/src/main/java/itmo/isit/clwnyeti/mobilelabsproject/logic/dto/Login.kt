package itmo.isit.clwnyeti.mobilelabsproject.logic.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Login(
    @SerialName("name")
    val username: String,
    @SerialName("pwd")
    val password: String
)
