package com.weatherapptask.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar


abstract class BaseFragment<VDB : ViewBinding> : Fragment() {
    private var _binding: VDB? = null
    protected val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return _binding!!.root
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VDB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateInit()
        initViewModel()
        initClicks()
    }

    protected abstract fun initClicks()

    protected abstract fun initViewModel()

    protected abstract fun onCreateInit()

    fun showSnackbar(message: String?) {
        val snackBar = Snackbar.make(_binding?.root!!, message!!, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}