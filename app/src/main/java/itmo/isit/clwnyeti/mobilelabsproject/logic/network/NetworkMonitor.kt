package itmo.isit.clwnyeti.mobilelabsproject.logic.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkMonitor @Inject constructor(
    private val cm: ConnectivityManager
) {
    val isOnline = callbackFlow {
        val cb = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) { trySend(true) }
            override fun onLost(network: Network) { trySend(isCurrentlyOnline()) }
        }
        val req = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        trySend(isCurrentlyOnline())
        cm.registerNetworkCallback(req, cb)
        awaitClose { cm.unregisterNetworkCallback(cb) }
    }.distinctUntilChanged()

    fun isCurrentlyOnline(): Boolean =
        cm.activeNetwork?.let { n ->
            cm.getNetworkCapabilities(n)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } == true
}