package itmo.isit.clwnyeti.mobilelabsproject.logic.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.MessageData

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val serverId: Long,
    val to: String,
    val from: String,
    val data: MessageData,
    val time: Long
)