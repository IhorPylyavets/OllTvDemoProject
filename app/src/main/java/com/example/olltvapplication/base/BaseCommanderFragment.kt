package com.example.olltvapplication.base

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import androidx.annotation.CallSuper

abstract class BaseCommanderFragment : BaseFragment() {

    private var dialog: AlertDialog? = null

    /**
     * Experimental flag -
     * if true - set adjust resize to activity during fragment life
     */

    open val autoResize = false

    override fun onStart() {
        super.onStart()
        if (autoResize) {
            Handler(Looper.getMainLooper()).post {
                enableLayoutAutoresize(true)
            }
        }
    }

    protected fun enableLayoutAutoresize(autoresize: Boolean) {
        activity?.window?.setSoftInputMode(
            if (autoresize) {
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
            } else {
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
            }
        )
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (autoResize) {
            enableLayoutAutoresize(true)
        }
    }

    override fun onDestroyView() {
        if (autoResize) {
            enableLayoutAutoresize(false)
        }

        dialog?.dismiss()
        super.onDestroyView()
    }

    override fun onStop() {
        super.onStop()
        if (autoResize) {
            enableLayoutAutoresize(false)
        }
    }

    fun BaseCommanderFragment.dpToPx(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp, resources.displayMetrics
        )
    }

    fun BaseCommanderFragment.dpToPx(dp: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(), resources.displayMetrics
        )
    }
}