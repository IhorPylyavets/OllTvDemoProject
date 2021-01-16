package com.example.olltvapplication.navigation

import com.example.olltvapplication.base.BaseCommanderFragment

abstract class BaseToolbarWrapperFragment<out TW : BaseToolbarWrapper> : BaseCommanderFragment() {

    fun toolbarWrapper(reinit: Boolean = true, toolbarWrapper: TW.() -> Unit) {
        (activity as? TW)?.let {
            if (reinit) {
                it.hideAll()
            }
            toolbarWrapper.invoke(it)
        }
    }
}

interface BaseToolbarWrapper {
    fun hideAll()
    fun showCloseBtn()
    fun showBackBtn()
    fun setToolbarTitle(text: String)
}
