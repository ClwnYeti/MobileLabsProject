package itmo.isit.clwnyeti.mobilelabsproject.logic.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    @SerialName("id")
    val id: Long,
    @SerialName("from")
    val sender: String,
    @SerialName("to")
    val channel: String,
    @SerialName("data")
    val data: MessageData,
    @SerialName("time")
    val time: Long?
)
