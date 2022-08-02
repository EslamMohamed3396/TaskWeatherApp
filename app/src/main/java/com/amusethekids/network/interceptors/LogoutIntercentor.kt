/*
package com.amusethekids.network.interceptors

import com.docformative.docformative.network.auth.UserPreferences
import okhttp3.Interceptor
import okhttp3.Response

class LogoutInterceptor(
    private val client: AuthenticationAPIClient,
    private val userPreferences: UserPreferences,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())
        if (response.code == 401) {
            if (refreshToken()) {
                response.close()
                response = chain.proceed(chain.request())
            }

            if (response.code == 401) {

                logout()
            }
        }
        return response
    }

    private fun logout() {
        try {
            client.revokeToken(userPreferences.currentUser?.refreshToken ?: "")
        } catch (ex: java.lang.Exception) {
            return
        }
    }

    private fun refreshToken(): Boolean {

        try {
            val response =
                client.renewAuth(userPreferences.currentUser?.refreshToken ?: "").execute()
            userPreferences.currentUser!!.authToken = response.accessToken
            userPreferences.currentUser!!.refreshToken = response.refreshToken
        } catch (ex: Exception) {
            return false
        }

        return true
    }
}*/
