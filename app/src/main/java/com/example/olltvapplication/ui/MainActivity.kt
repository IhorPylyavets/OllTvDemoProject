package com.example.olltvapplication.ui

import android.app.Activity
import android.os.Bundle
import com.example.olltvapplication.R
import com.example.olltvapplication.databinding.ActivityMainBinding
import com.example.olltvapplication.navigation.MainActivityToolbarWrapper
import com.example.olltvapplication.viewBinding.viewBinding


class MainActivity : MainNavigationActivity(), MainActivityToolbarWrapper {

    override fun getNavControllerLayoutRes() = R.id.navHostFragment

    private val bindingView by viewBinding(ActivityMainBinding::bind, R.id.root_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNavigation()
    }

    override fun onBack() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun setNavigation() {
        bindingView.navToolbar.navigationMainToolbarActionListener =
            object : NavigationMainToolbarActionListener {
                override fun onBackClick() {
                    onBackPressed()
                }
            }
    }

    override fun onBackPressed() {
        navController?.let { nav ->
            val destinationId = nav.currentDestination?.id
            if (destinationId == R.id.channelDetailsFragment) {
                nav.popBackStack()
            }
        } ?: finish()
    }

    override fun hideAll() = bindingView.navToolbar.hideAll()

    override fun showCloseBtn() = bindingView.navToolbar.showClose(MainActivityToolbar.CloseState.CLOSE)

    override fun showBackBtn() = bindingView.navToolbar.showClose(MainActivityToolbar.CloseState.BACK)

    override fun setToolbarTitle(text: String) {
        bindingView.navToolbar.setTitle(text)
    }
}
