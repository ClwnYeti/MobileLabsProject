package itmo.isit.clwnyeti.mobilelabsproject.logic.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageData(
    @SerialName("Text")
    val text: MessageTextData? = null,
    @SerialName("Image")
    val image: MessageImageData? = null
)
