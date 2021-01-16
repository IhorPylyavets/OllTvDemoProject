package com.example.olltvapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.olltvapplication.extension.removeParentFirst

abstract class BaseFragment : Fragment() {

    /**
     * Use this field for check fragment restoring
     * */
    private var isCreatedNew = false

    private var rootView: View? = null

    @LayoutRes
    open fun getLayoutRes(): Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = if (getLayoutRes() == -1) super.onCreateView(
                inflater,
                container,
                savedInstanceState
            )
            else inflater.inflate(getLayoutRes(), container, false)
        } else {
            rootView?.removeParentFirst()
        }
        return rootView
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isCreatedNew) fragmentReady() else openFromBackFragment()

        isCreatedNew = true
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToLivesData()
    }

    /**
     * Will invoke when fragment open first time.
     * After come back from another fragment, fragmentReady() does not call again.
     * */
    open fun fragmentReady() {}

    /**
     * Will invoke every time to back from another fragment
     * */
    open fun openFromBackFragment() {}

    /**
     * Will invoke every time for fragment on onActivityCreated
     * */
    open fun subscribeToLivesData() {}

}
