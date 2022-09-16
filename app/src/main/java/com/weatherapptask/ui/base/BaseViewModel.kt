package com.weatherapptask.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.weatherapptask.utilits.Response
import com.weatherapptask.utilits.SingleLiveData
import com.weatherapptask.utilits.checkNetwork.NoInternetConnectionException
import kotlinx.coroutines.*

open class BaseViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.Main) :
    ViewModel() {
    private val _loading = SingleLiveData<Boolean>()
    private val _noNetwork = SingleLiveData<Unit>()
    protected val _generalError = SingleLiveData<String?>()
    val generalError: LiveData<String?> get() = _generalError
    val loading: SingleLiveData<Boolean> get() = _loading
    protected val noNetwork: SingleLiveData<Unit> get() = _noNetwork
    private var job: Job? = null

    fun <T> call(
        serverCall: suspend () -> Response<T>,
        onResponse: (Response<T>) -> Unit
    ) {
        job = CoroutineScope(Dispatchers.Default).launch {
            withContext(dispatcher) {
                _loading.postValue(true)
            }

            val response = serverCall()
            if (!response.isSuccess && response.throwable is NoInternetConnectionException) {
                noNetwork.postValue(Unit)
                loading.postValue(false)
            } else {
                withContext(dispatcher) {
                    _loading.postValue(false)
                    onResponse(response)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}