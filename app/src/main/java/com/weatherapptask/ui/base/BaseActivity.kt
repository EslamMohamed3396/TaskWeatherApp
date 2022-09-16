package com.weatherapptask.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding(layoutInflater)
        setContentView(_binding?.root)
        onCreateInit()
        // initViewModel()
        initClicks()
    }


    protected abstract fun initClicks()

    //  protected abstract fun initViewModel()

    protected abstract fun onCreateInit()

//    fun showSnackbar(message: String?) {
//        val snackBar = Snackbar.make(_binding.root, message!!, Snackbar.LENGTH_SHORT)
//        //  snackBar.setTextColor(binding.root.context.resources.getColor(R.color.white))
//        //  snackBar.anchorView = (activity as MainActivity).binding.bottomNavigation
//        snackBar.show()
//    }

}