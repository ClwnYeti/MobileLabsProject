package itmo.isit.clwnyeti.mobilelabsproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChatApp : Application() {
    companion object {
        lateinit var instance: ChatApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}