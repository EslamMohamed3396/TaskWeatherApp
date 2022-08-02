package com.amusethekids.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amusethekids.R
import com.amusethekids.databinding.FragmentLoginBinding
import com.amusethekids.ui.base.BaseFragment


class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun initClicks() {

    }

    override fun initViewModel() {

    }

    override fun onCreateInit() {

    }

}