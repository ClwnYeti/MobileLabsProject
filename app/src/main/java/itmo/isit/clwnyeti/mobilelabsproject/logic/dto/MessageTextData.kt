package itmo.isit.clwnyeti.mobilelabsproject.logic.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MessageTextData(
    @SerialName("text")
    val value: String
)
