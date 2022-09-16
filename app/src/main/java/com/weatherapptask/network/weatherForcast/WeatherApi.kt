package com.weatherapptask.network.weatherForcast


import com.weatherapptask.BuildConfig
import com.weatherapptask.network.weatherForcast.model.response.ResponseWeatherForeCast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast.json")
    suspend fun weatherForecast(
        @Query("key") key: String = BuildConfig.KEY,
        @Query("q") query: String?,
        @Query("days") days: Int = 3,
    ): Response<ResponseWeatherForeCast>

}