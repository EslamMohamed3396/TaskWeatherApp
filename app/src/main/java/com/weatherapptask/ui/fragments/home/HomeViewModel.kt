package com.weatherapptask.ui.fragments.home

import androidx.lifecycle.LiveData
import com.weatherapptask.network.weatherForcast.WeatherForecastRepository
import com.weatherapptask.network.weatherForcast.model.response.ResponseWeatherForeCast
import com.weatherapptask.ui.base.BaseViewModel
import com.weatherapptask.utilits.SingleLiveData

class HomeViewModel(
    val weatherForecastRepository: WeatherForecastRepository,
) : BaseViewModel() {

    private val _weatherForeCast = SingleLiveData<ResponseWeatherForeCast>()
    val weatherForeCast: LiveData<ResponseWeatherForeCast> get() = _weatherForeCast



    fun weatherForeCast(
        query: String
    ) {
        call({
            return@call weatherForecastRepository.weatherForecast(
                query
            )
        }) {
            if (it.isSuccess && it.data != null) {
                _weatherForeCast.value = it.data
            } else {
                _generalError.postValue(it.message)
            }
        }
    }


}