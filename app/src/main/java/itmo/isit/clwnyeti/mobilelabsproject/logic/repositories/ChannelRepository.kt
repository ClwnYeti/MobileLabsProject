package itmo.isit.clwnyeti.mobilelabsproject.logic.repositories

import itmo.isit.clwnyeti.mobilelabsproject.logic.db.entities.ChannelEntity
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
    fun observeChannels(): Flow<List<ChannelEntity>>
    suspend fun refreshChannels()
    suspend fun clear()
}