package com.weatherapptask.ui.fragments.auth.login

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.weatherapptask.databinding.FragmentLoginBinding
import com.weatherapptask.ui.base.BaseFragment
import org.koin.android.ext.android.inject


class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val viewModel: LoginViewModel by inject()
    private val TAG = "LoginFragment"

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.button.setOnClickListener {
            viewModel.checkValidity(binding.textInputLayout, binding.textInputLayout2)
        }
    }

    override fun initViewModel() {
        initWatcher()
        initApi()
    }

    override fun onCreateInit() {

    }

    private fun initWatcher() {
        viewModel.watcherName(binding.textInputLayout)
        viewModel.watcherPassword(binding.textInputLayout2)
    }

    private fun initApi() {
        viewModel.validity.observe(viewLifecycleOwner) {
            Log.d(TAG, "initApi: ")
        }
    }

}