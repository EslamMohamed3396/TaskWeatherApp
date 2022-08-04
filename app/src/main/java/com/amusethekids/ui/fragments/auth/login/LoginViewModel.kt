package com.amusethekids.ui.fragments.auth.login

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

    private fun validPassword(textInputLayout: TextInputLayout): Boolean {
        return EditTextValidiation.validUserName(textInputLayout)
    }

    fun watcherName(textInputLayout: TextInputLayout) {
        textInputLayout.editText?.doOnTextChanged { text, start, before, count ->
            validName(textInputLayout)
        }
    }

    fun watcherPassword(textInputLayout: TextInputLayout) {
        textInputLayout.editText?.doOnTextChanged { text, start, before, count ->
            validPassword(textInputLayout)
        }
    }

    fun checkValidity(name: TextInputLayout, password: TextInputLayout) {
        if (!validName(name) || !validName(password)) return
        _validity.postValue(Unit)
    }

}