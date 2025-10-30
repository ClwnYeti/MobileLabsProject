package itmo.isit.clwnyeti.mobilelabsproject.logic.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class ChannelEntity(
    @PrimaryKey val name: String
)