package itmo.isit.clwnyeti.mobilelabsproject.containers.modules

import android.content.Context
import android.net.ConnectivityManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.utils.AuthInterceptor
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.services.AuthApiService
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.services.ChannelApiService
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.services.MessageApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    companion object {
        const val BASE_URL = "https://faerytea.name/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        explicitNulls = false
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideChannelsApi(retrofit: Retrofit): ChannelApiService =
        retrofit.create(ChannelApiService::class.java)

    @Provides
    @Singleton
    fun provideMessagesApi(retrofit: Retrofit): MessageApiService =
        retrofit.create(MessageApiService::class.java)

    @Provides
    @Singleton
    fun provideConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}