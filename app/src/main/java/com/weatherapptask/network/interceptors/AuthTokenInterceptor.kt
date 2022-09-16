package com.weatherapptask.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(
    private val securePrefs: UserPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        return if (securePrefs.currentUser?.authToken != null) {
//            val authenticatedRequest = chain.request().newBuilder()
//                .addHeader("Authorization", " Bearer ${securePrefs.currentUser!!.authToken}")
//                .build()
//            chain.proceed(authenticatedRequest)
//        } else {
//            chain.proceed(chain.request())
//        }
        return chain.proceed(chain.request())
    }
}