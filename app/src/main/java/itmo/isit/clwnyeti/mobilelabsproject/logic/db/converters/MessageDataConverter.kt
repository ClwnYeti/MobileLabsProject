package itmo.isit.clwnyeti.mobilelabsproject.logic.db.converters

import androidx.room.TypeConverter
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.MessageData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MessageDataConverter {
    @TypeConverter
    fun fromMessageData(data: MessageData?): String? {
        return data?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toMessageData(json: String?): MessageData? {
        return json?.let { Json.decodeFromString<MessageData>(it) }
    }
}