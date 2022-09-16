package com.weatherapptask.ui.fragments.home

import androidx.lifecycle.LiveData
import com.weatherapptask.network.search.SearchRepository
import com.weatherapptask.network.search.model.response.ResponseSearch
import com.weatherapptask.network.weatherForcast.WeatherForecastRepository
import com.weatherapptask.network.weatherForcast.model.response.ResponseWeatherForeCast
import com.weatherapptask.ui.base.BaseViewModel
import com.weatherapptask.utilits.SingleLiveData

class HomeViewModel(
    val weatherForecastRepository: WeatherForecastRepository,
    val searchRepository: SearchRepository
) : BaseViewModel() {

    private val _weatherForeCast = SingleLiveData<ResponseWeatherForeCast>()
    val weatherForeCast: LiveData<ResponseWeatherForeCast> get() = _weatherForeCast

    private val _search = SingleLiveData<ResponseSearch>()
    val search: LiveData<ResponseSearch> get() = _search

    fun weatherForeCast(
        query: String
    ) {
        call({
            return@call weatherForecastRepository.weatherForecast(
                query
            )
        }) {
            if (it.isSuccess && it.data != null) {
                _weatherForeCast.value = it.data!!
            } else {
                _generalError.postValue(it.message)
            }
        }
    }

    fun search(
        query: String
    ) {
        call({
            return@call searchRepository.search(
                query
            )
        }) {
            if (it.isSuccess && it.data != null) {
                _search.value = it.data!!
            } else {
                _generalError.postValue(it.message)
            }
        }
    }

}