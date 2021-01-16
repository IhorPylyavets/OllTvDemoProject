package com.example.olltvapplication.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

abstract class BaseNavigationActivity: AppCompatActivity() {

    companion object {
        const val EMPTY_NAV_CONTROLLER_RES_ID = -1
    }

    private val navHostFragment: NavHostFragment? by lazy {
        if (getNavControllerLayoutRes() == EMPTY_NAV_CONTROLLER_RES_ID) null
        else supportFragmentManager.findFragmentById(getNavControllerLayoutRes()) as NavHostFragment?
    }

    protected val navController: NavController? by lazy {
        if (getNavControllerLayoutRes() == EMPTY_NAV_CONTROLLER_RES_ID) null
        else Navigation.findNavController(this, getNavControllerLayoutRes())
    }

    abstract fun getNavControllerLayoutRes(): Int

    protected fun openFragment(@IdRes nextFragmentRes: Int, data: Bundle? = null) {
        navController?.navigate(nextFragmentRes, data)
    }

    fun popBackStackFragment() {
        navController?.popBackStack()
    }

    fun getCurrentFragment(): Fragment? =
            navHostFragment?.childFragmentManager?.primaryNavigationFragment
}
