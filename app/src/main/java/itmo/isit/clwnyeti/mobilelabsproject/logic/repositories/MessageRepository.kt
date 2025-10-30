package itmo.isit.clwnyeti.mobilelabsproject.logic.repositories

import itmo.isit.clwnyeti.mobilelabsproject.logic.db.entities.MessageEntity
import kotlinx.coroutines.flow.Flow


interface MessageRepository {
    fun observeMessages(channel: String): Flow<List<MessageEntity>>
    suspend fun refreshChannel(
        channel: String,
        limit: Int,
        lastKnownId: Long?
    )

    suspend fun sendText(
        userName: String,
        channel: String,
        text: String
    ): Long

    suspend fun clear()
}