package com.weatherapptask.ui.fragments.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.weatherapptask.R
import com.weatherapptask.databinding.FragmentSplashBinding
import com.weatherapptask.ui.base.BaseFragment


class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSplashBinding.inflate(inflater, container, false)

    override fun initClicks() {

    }

    override fun initViewModel() {
        listnerGoToHome()
        initViewModelGoToHome()
    }

    override fun onCreateInit() {
    }


    private fun listnerGoToHome() {
        viewModel.goToHome.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }


    private fun initViewModelGoToHome() {
        viewModel.goToHome()
    }

}