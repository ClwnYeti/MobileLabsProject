package itmo.isit.clwnyeti.mobilelabsproject.logic.repositories

import itmo.isit.clwnyeti.mobilelabsproject.logic.db.dao.ChannelDao
import itmo.isit.clwnyeti.mobilelabsproject.logic.db.entities.ChannelEntity
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.services.ChannelApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChannelCacheRepository @Inject constructor(
    private val api: ChannelApiService,
    private val channelDao: ChannelDao
): ChannelRepository {
    override fun observeChannels(): Flow<List<ChannelEntity>> = channelDao.observeChannels()

    override suspend fun refreshChannels() {
        val names = api.getChannelNames()

        val mapped = names.map { name ->
            ChannelEntity(name = name)
        }

        channelDao.insertAll(mapped)
    }

    override suspend fun clear() = channelDao.clear()
}