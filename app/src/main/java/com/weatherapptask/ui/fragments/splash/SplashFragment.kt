package com.weatherapptask.ui.fragments.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.weatherapptask.databinding.FragmentSplashBinding
import com.weatherapptask.ui.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSplashBinding.inflate(inflater, container, false)

    override fun initClicks() {

    }

    override fun initViewModel() {
        delayTwoSecondThenCallViewModel()
        listenToSignIn()
        listenToSignOut()
    }

    override fun onCreateInit() {

    }

    private fun delayTwoSecondThenCallViewModel() {
        lifecycleScope.launch {
            delay(30000)
            viewModel.isUserSignIn()
            viewModel.isUserNotLogin()
        }
    }

    private fun listenToSignIn() {
        viewModel.isUserSignIn.observe(viewLifecycleOwner) {

        }
    }

    private fun listenToSignOut() {
        viewModel.isUserNotLogin.observe(viewLifecycleOwner) {

        }
    }

}