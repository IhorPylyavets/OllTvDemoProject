package com.example.olltvapplication.navigation

import com.example.olltvapplication.ui.MainNavigationActivity
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf

abstract class BaseFragmentNavigator<out FNC : BaseFragmentNavigationCommander, out TW: BaseToolbarWrapper> :
    BaseToolbarWrapperFragment<TW>() {

    val fragmentNavigator: FNC by lazy {
        return@lazy when (activity) {
            is MainNavigationActivity -> requireActivity().currentScope.get<MainNavigationActivity> {
                parametersOf(activity)
            } as FNC
            else -> requireActivity().currentScope.get<MainNavigationActivity> {
                parametersOf(activity)
            } as FNC
        }
    }
}

interface MainActivityToolbarWrapper : BaseToolbarWrapper {
}
