package com.amusethekids.utilits.checkNetwork

import android.content.Context
import com.amusethekids.R
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(
    private val connectionMonitor: ConnectionMonitor,
    val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!connectionMonitor.isConnected()) {
            throw NoInternetConnectionException(context.getString(R.string.no_connection_message))
        } else {
            return chain.proceed(chain.request())
        }
    }
}