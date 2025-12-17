package com.cloudpos.demo.paywizard.utils

import android.util.Log
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException


/**
 * Network connectivity detection utility class
 * Determine network availability via Socket connection
 */
object NetworkConnectivityChecker {

    // Socket connection timeout (milliseconds)
    private const val SOCKET_TIMEOUT = 3000
    private val TAG = "NetworkConnectivityChecker";

    // List of hosts and ports for Socket testing
    private val TEST_SOCKETS = arrayOf(
        SocketTestTarget("portal.paywizard.biz", 443),   // HTTPS port1
        SocketTestTarget("www.google.com", 443),         // HTTPS port2
        SocketTestTarget("www.bing.com", 443),           // HTTPS port3
        SocketTestTarget("8.8.8.8", 53),                 // DNS port4
    )

    /**
     * Socket test target data class
     */
    private data class SocketTestTarget(
        val host: String,
        val port: Int
    )

    /**
     * Detecting network connectivity
     * @param callback
     */
    fun checkConnectivity(callback: ConnectivityCallback?) {
        AppThreadExecutors.instance?.io()?.execute {
            val isConnected = performConnectivityTest()
            Log.d(TAG,"Network connectivity check result: $isConnected")
            HandlerUtils.postRunnable { callback?.onResult(isConnected) }
        }
    }

    /**
     * Perform connectivity tests
     * @return true This indicates that at least one socket connection was successful.
     */
    private fun performConnectivityTest(): Boolean {
        Log.d(TAG,"Starting network connectivity test")

        if (socketConnectivityTest()) {
            Log.d(TAG,"Socket test passed")
            return true
        }

        Log.d(TAG,"All socket tests failed")
        return false
    }

    /**
     * Socket Perform connectivity tests
     * @return true indicates that at least one socket connection was successful.
     */
    private fun socketConnectivityTest(): Boolean {
        TEST_SOCKETS.forEach { target ->
            try {
                Log.d(TAG,"Attempting Socket connection: ${target.host}:${target.port}")
                if (testSocketConnection(target.host, target.port)) {
                    Log.d(TAG,"Socket test successful: ${target.host}:${target.port}")
                    return true
                }
                Log.w(TAG,"Socket test failed: ${target.host}:${target.port}")
            } catch (e: Exception) {
                Log.e(TAG,"Socket test exception: ${target.host}:${target.port}, error: ${e.message}")
            }
        }
        return false
    }

    /**
     * test Socket connection
     * @param host
     * @param port
     * @return true
     */
    private fun testSocketConnection(host: String, port: Int): Boolean {
        var socket: Socket? = null
        try {
            socket = Socket()
            val socketAddress = InetSocketAddress(host, port)

            // Set connection timeout
            socket.connect(socketAddress, SOCKET_TIMEOUT)

            // Check if the connection is successful.
            val isConnected = socket.isConnected && !socket.isClosed
            Log.d(TAG,"Socket connection result: $host:$port, connected: $isConnected")

            return isConnected
        } catch (e: SocketTimeoutException) {
            Log.e(TAG,"Socket connection timeout: $host:$port")
            return false
        } catch (e: IOException) {
            Log.e(TAG,"Socket connection IOException: $host:$port, error: ${e.message}")
            return false
        } catch (e: Exception) {
            Log.e(TAG,"Socket connection exception: $host:$port, error: ${e.message}")
            return false
        } finally {
            try {
                socket?.close()
            } catch (e: Exception) {
                Log.e(TAG,"Exception closing socket: ${e.message}")
            }
        }
    }

    /**
     * Network connectivity detection callback interface
     */
    fun interface ConnectivityCallback {
        /**
         * Detection result callback
         * @param isConnected true indicates that the network is available, and false indicates that the network is unavailable.
         */
        fun onResult(isConnected: Boolean)
    }

}