package itmo.isit.clwnyeti.mobilelabsproject.logic.dto

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val code: String,
    val message: String
)
