package com.example.olltvapplication.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T, V: View>(itemView: V) : RecyclerView.ViewHolder(itemView) {

    protected val context: Context
        get() = itemView.context

    interface Factory<T, V: View> {
        fun create(context: Context): BaseViewHolder<T, V>
    }

    abstract fun bind(view: V?, item: T, position: Int)

    open fun bindSelected(view: V?, item: T, position: Int, selectedPosition: Int) {
    }
}
