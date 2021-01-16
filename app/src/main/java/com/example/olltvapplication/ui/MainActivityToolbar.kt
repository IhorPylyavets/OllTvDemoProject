package com.example.olltvapplication.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.example.olltvapplication.R
import com.example.olltvapplication.extension.gone
import com.example.olltvapplication.extension.setSafeOnClickListener
import com.example.olltvapplication.extension.visible
import kotlinx.android.synthetic.main.view_navigation_main_toolbar.view.*

interface NavigationMainToolbarActionListener {
    fun onBackClick()
}

class MainActivityToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    enum class CloseState(@DrawableRes val resource: Int) {
        BACK(R.drawable.ic_arrow_back),
        CLOSE(R.drawable.ic_close_white)
    }

    var navigationMainToolbarActionListener: NavigationMainToolbarActionListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_navigation_main_toolbar, this)
        initClickListeners()
    }

    private fun initClickListeners() {
        back_btn.setSafeOnClickListener {
            navigationMainToolbarActionListener?.onBackClick()
        }
    }

    fun showClose(closeState: CloseState) {
        back_btn.setImageResource(closeState.resource)
        back_btn.visible()
    }

    fun setTitle(title: String) {
        tv_title.visible()
        tv_title.text = title
    }

    fun hideAll() {
        back_btn.gone()
        tv_title.gone()
    }
}
