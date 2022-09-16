package com.weatherapptask.network.search


import com.weatherapptask.BuildConfig
import com.weatherapptask.network.search.model.response.ResponseSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("v1/search.json")
    suspend fun search(
        @Query("key") key: String = BuildConfig.KEY,
        @Query("q") query: String?
    ): Response<ResponseSearch>

}