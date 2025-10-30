package itmo.isit.clwnyeti.mobilelabsproject.logic.repositories

import itmo.isit.clwnyeti.mobilelabsproject.logic.db.dao.MessageDao
import itmo.isit.clwnyeti.mobilelabsproject.logic.db.entities.MessageEntity
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.Message
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.MessageData
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.MessageTextData
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.services.MessageApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageCacheRepository @Inject constructor(
    private val api: MessageApiService,
    private val dao: MessageDao
) : MessageRepository {
    override fun observeMessages(channel: String): Flow<List<MessageEntity>> =
        dao.observeMessages(channel)

    override suspend fun refreshChannel(
        channel: String,
        limit: Int,
        lastKnownId: Long?
    ) {
        val remote = api.getChannelMessages(
            name = channel,
            limit = limit,
            lastKnownId = lastKnownId,
            reverse = true
        )

        val mapped = remote.map { message ->
            MessageEntity(
                serverId = message.id,
                to = message.channel,
                from = message.sender,
                data = message.data,
                time = message.time!!
            )
        }

        dao.insertAll(mapped)
    }

    override suspend fun sendText(
        userName: String,
        channel: String,
        text: String
    ): Long {
        val msg = Message(
            id = 0,
            sender = userName,
            channel = channel,
            data = MessageData(text = MessageTextData(value = text), image = null),
            time = null
        )
        val messageId = api.sendMessage(
            message = msg
        )

        return messageId
    }

    override suspend fun clear() = dao.clear()
}