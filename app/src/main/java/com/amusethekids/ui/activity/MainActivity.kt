package com.amusethekids.ui.activity

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.amusethekids.R
import com.amusethekids.databinding.ActivityMainBinding
import com.amusethekids.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)

    override fun initClicks() {

    }

    override fun onCreateInit() {
        initNavigationBottom()
    }

    private fun initNavigationBottom() {
        binding.bottomNavigation.setupWithNavController(getNavController())
        binding.bottomNavigation.itemIconTintList = null
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            return@setOnItemSelectedListener onNavItemDestinationSelected(
                item,
                getNavController()
            )
        }
    }

    private fun onNavItemDestinationSelected(
        item: MenuItem,
        navController: NavController
    ): Boolean {

        val builder = NavOptions.Builder()
            .setLaunchSingleTop(true)
        if (item.order and Menu.CATEGORY_SECONDARY == 0) {
            builder.setPopUpTo(
                R.id.newsFragment,
                false
            )
        }
        val options = builder.build()
        return try {
            navController.navigate(item.itemId, null, options)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }


    private fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}