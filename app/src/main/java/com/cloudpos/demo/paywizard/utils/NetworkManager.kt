package com.cloudpos.demo.paywizard.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build

class NetworkManager(val context: Context) {

    companion object {
        @Volatile
        var isNetConnect = false // Real network status

        @Volatile
        var systemConnectState = false // The network status returned by the system
    }

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val onNetworkStateListenerList = mutableListOf<OnNetworkStateListener>()


    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            systemConnectState = true
            // Network connection established
            noticeAllListener(true)
            // Use NetworkConnectivityChecker for true connectivity detection
            checkConnectivity()
        }

        override fun onLost(network: Network) {
            systemConnectState = false
            // Network disconnected
            noticeAllListener(false)
        }
    }

    /**
     * Detect the actual network connection status
     */
    fun checkConnectivity() {
        if (!systemConnectState) {
            return
        }
        NetworkConnectivityChecker.checkConnectivity { realConnect ->
            if (realConnect == isNetConnect) {
                return@checkConnectivity
            }
            noticeAllListener(realConnect)
        }
    }

    fun noticeAllListener(isConnect: Boolean) {
        if (isNetConnect == isConnect) {
            return
        }
        isNetConnect = isConnect
        HandlerUtils.postRunnable {
            for (listener in onNetworkStateListenerList) {
                listener.onNetworkStateChange(isConnect)
            }
        }
    }

    fun register() {
        val networkRequest = NetworkRequest.Builder().build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        }
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    /**
     * Get network status
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            // Compatible with older versions
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    fun addListener(listener: OnNetworkStateListener) {
        onNetworkStateListenerList.add(listener)
    }

    fun removeListener(listener: OnNetworkStateListener) {
        onNetworkStateListenerList.remove(listener)
    }

    interface OnNetworkStateListener {
        fun onNetworkStateChange(isConnect: Boolean)
    }

}