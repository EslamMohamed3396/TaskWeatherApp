package com.amusethekids.ui.activity

import android.view.LayoutInflater
import com.amusethekids.databinding.ActivityMainBinding
import com.amusethekids.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)

    override fun initClicks() {

    }

    override fun onCreateInit() {

    }

}