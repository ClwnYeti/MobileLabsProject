package itmo.isit.clwnyeti.mobilelabsproject.logic.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import itmo.isit.clwnyeti.mobilelabsproject.logic.db.converters.MessageDataConverter
import itmo.isit.clwnyeti.mobilelabsproject.logic.db.dao.ChannelDao
import itmo.isit.clwnyeti.mobilelabsproject.logic.db.dao.MessageDao
import itmo.isit.clwnyeti.mobilelabsproject.logic.db.entities.ChannelEntity
import itmo.isit.clwnyeti.mobilelabsproject.logic.db.entities.MessageEntity


@Database(
    entities = [
        MessageEntity::class,
        ChannelEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(MessageDataConverter::class)
abstract class ChatAppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun channelDao(): ChannelDao
}