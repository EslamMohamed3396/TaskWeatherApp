package com.weatherapptask.ui.activity

import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import com.weatherapptask.R
import com.weatherapptask.databinding.ActivityMainBinding
import com.weatherapptask.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)

    override fun initClicks() {

    }

    override fun onCreateInit() {
        navController()
    }

    private fun navController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
    }
}