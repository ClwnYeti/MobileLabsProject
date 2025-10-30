package itmo.isit.clwnyeti.mobilelabsproject.containers.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import itmo.isit.clwnyeti.mobilelabsproject.logic.db.ChatAppDatabase
import itmo.isit.clwnyeti.mobilelabsproject.logic.db.dao.ChannelDao
import itmo.isit.clwnyeti.mobilelabsproject.logic.db.dao.MessageDao
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.services.AuthApiService
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.services.ChannelApiService
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.services.MessageApiService
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.ChannelCacheRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.ChannelRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.MessageCacheRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.MessageRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.TokenInnerRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.TokenMainRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.TokenPreferencesRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.TokenRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {
    @Provides
    @Singleton
    fun provideTokenInnerRepository(@ApplicationContext context: Context): TokenInnerRepository =
        TokenPreferencesRepository(context)

    @Provides
    @Singleton
    fun provideTokenRepository(apiService: AuthApiService, innerRepository: TokenInnerRepository): TokenRepository =
        TokenMainRepository(apiService, innerRepository)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ChatAppDatabase =
        Room.databaseBuilder(
            context,
            ChatAppDatabase::class.java,
            "chat.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMessageDao(db: ChatAppDatabase): MessageDao = db.messageDao()

    @Provides
    fun provideChannelDao(db: ChatAppDatabase): ChannelDao = db.channelDao()

    @Provides
    fun provideMessageRepository(dao: MessageDao, service: MessageApiService): MessageRepository =
        MessageCacheRepository(service, dao)

    @Provides
    fun provideChannelRepository(dao: ChannelDao, service: ChannelApiService): ChannelRepository =
        ChannelCacheRepository(service, dao)
}