package com.francescsoftware.weathersample.core.connectivity.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityMonitor
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

private class NetworkCallback(
    private val send: (ConnectivityStatus) -> Unit,
) : ConnectivityManager.NetworkCallback() {

    private val connectedNetworks = mutableSetOf<Network>()

    override fun onAvailable(network: Network) {
        connectedNetworks.add(network)
        send(ConnectivityStatus.Connected)
    }

    override fun onLost(network: Network) {
        connectedNetworks.remove(network)
        send(
            if (connectedNetworks.isEmpty()) {
                ConnectivityStatus.Disconnected
            } else {
                ConnectivityStatus.Connected
            },
        )
    }
}

internal class ConnectivityMonitorImpl @Inject constructor(
    @ApplicationContext context: Context,
) : ConnectivityMonitor {

    override val connectedStatus = callbackFlow {
        val connectivityManager = context.getSystemService<ConnectivityManager>()
        if (connectivityManager != null) {
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            val callback = NetworkCallback { connectivityStatus ->
                trySend(connectivityStatus)
            }

            connectivityManager.registerNetworkCallback(
                networkRequest,
                callback,
            )

            val isConnected = connectivityManager
                .getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
            trySend(if (isConnected) ConnectivityStatus.Connected else ConnectivityStatus.Disconnected)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        } else {
            trySend(ConnectivityStatus.Disconnected)
            close()
        }
    }
        .conflate()
        .distinctUntilChanged()
}
