package com.amusethekids.ui.fragments.auth

import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LiveData
import com.amusethekids.ui.base.BaseViewModel
import com.amusethekids.utilits.EditTextValidiation
import com.amusethekids.utilits.SingleLiveData
import com.google.android.material.textfield.TextInputLayout

class LoginViewModel : BaseViewModel() {

    private val _validity = SingleLiveData<Unit>()
    val validity: LiveData<Unit> get() = _validity

    private fun validName(textInputLayout: TextInputLayout): Boolean {
        return EditTextValidiation.validName(textInputLayout)
    }

    fun watcher(textInputLayout: TextInputLayout) {
        textInputLayout.editText?.doOnTextChanged { text, start, before, count ->
            validName(textInputLayout)
        }
    }

    fun checkValidity(textInputLayout: TextInputLayout) {
        if (addValidityList(textInputLayout).contains(false)) return
        _validity.postValue(Unit)
    }

    private fun addValidityList(textInputLayout: TextInputLayout): ArrayList<Boolean> {
        val listChecks = ArrayList<Boolean>()
        listChecks.add(validName(textInputLayout))
        return listChecks
    }


}