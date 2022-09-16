package com.weatherapptask.utilits

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window

import com.weatherapptask.R


object DialogUtil {
    private var dialog: Dialog? = null

    fun dialog(context: Context?, show: Boolean) {
        if (show) {
            if (dialog != null && dialog?.isShowing == true) {
                return
            }
            dialog = context?.let { Dialog(it) }

            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog?.setContentView(R.layout.dialog_loading)
            dialog?.setCancelable(false)
            dialog?.show()
        } else {
            dismissDialog()
        }

    }

    private fun dismissDialog() {
        if (dialog != null && dialog?.isShowing == true) {
            dialog?.dismiss()
        }
        dialog = null
    }

}