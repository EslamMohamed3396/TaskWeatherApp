package com.amusethekids.ui.base

import androidx.lifecycle.ViewModel
import com.amusethekids.utilits.checkNetwork.NoInternetConnectionException
import com.amusethekids.utilits.Response
import com.amusethekids.utilits.SingleLiveData
import kotlinx.coroutines.*

open class BaseViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.Main) : ViewModel() {
    private val _loading = SingleLiveData<Boolean>()
    private val _noNetwork = SingleLiveData<Unit>()
    protected val loading: SingleLiveData<Boolean> get() = _loading
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