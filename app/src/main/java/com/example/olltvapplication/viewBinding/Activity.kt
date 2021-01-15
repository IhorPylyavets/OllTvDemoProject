package com.example.olltvapplication.viewBinding

import android.view.View
import androidx.annotation.IdRes
import androidx.core.app.ComponentActivity
import androidx.viewbinding.ViewBinding

private class ActivityViewBindingProperty<A : ComponentActivity, T : ViewBinding>(
    viewBinder: (A) -> T
) : ViewBindingProperty<A, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: A) = thisRef
}

@JvmName("viewBindingActivity")
public fun <A : ComponentActivity, T : ViewBinding> ComponentActivity.viewBinding(
    viewBinder: (A) -> T
): ViewBindingProperty<A, T> {
    return ActivityViewBindingProperty(
        viewBinder
    )
}

@JvmName("viewBindingActivity")
public inline fun <A : ComponentActivity, T : ViewBinding> ComponentActivity.viewBinding(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (A) -> View
): ViewBindingProperty<A, T> {
    return viewBinding { activity: A -> vbFactory(viewProvider(activity)) }
}

@Suppress("unused")
@JvmName("viewBindingActivity")
public inline fun <T : ViewBinding> ComponentActivity.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding { activity: ComponentActivity ->
        vbFactory(activity.findViewById(viewBindingRootId))
    }
}