package com.weatherapptask.network.weatherForcast


import com.weatherapptask.network.weatherForcast.model.response.ResponseWeatherForeCast
import com.weatherapptask.utilits.Response

interface WeatherForecastRepository {
    suspend fun weatherForecast(
        query: String?
    ): Response<ResponseWeatherForeCast>

    private class Implementation(
        private val weatherApi: WeatherApi,
    ) : WeatherForecastRepository {
        override suspend fun weatherForecast(query: String?): Response<ResponseWeatherForeCast> {
            return Response.handle(
                getResponse = {
                    weatherApi.weatherForecast(query = query)
                },
                onFailure = {
                    Response.error(it.throwable, it.message, it.responseCode)
                })
        }
    }

    companion object {
        @JvmStatic
        fun create(
            weatherApi: WeatherApi
        ): WeatherForecastRepository = Implementation(weatherApi)
    }
}