package itmo.isit.clwnyeti.mobilelabsproject.logic.dto

import android.net.Uri
import itmo.isit.clwnyeti.mobilelabsproject.containers.modules.NetworkModule
import kotlinx.serialization.Serializable

@Serializable
data class MessageImageData(
    val link: String
) {
    fun toFullImage(): String {
        val safePath = link.split('/')
            .joinToString("/") { Uri.encode(it) }
        return "${NetworkModule.BASE_URL}img/$safePath"
    }

    fun toThumbImage(): String {
        val safePath = link.split('/')
            .joinToString("/") { Uri.encode(it) }
        return "${NetworkModule.BASE_URL}thumb/$safePath"
    }
}
