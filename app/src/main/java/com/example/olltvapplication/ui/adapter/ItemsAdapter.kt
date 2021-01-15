package com.example.olltvapplication.ui.adapter

import android.content.Context
import com.example.olltvapplication.base.BaseRecyclerAdapter
import com.example.olltvapplication.base.BaseViewHolder
import com.example.olltvapplication.data.ResponseItem
import com.example.olltvapplication.ui.view.ResponseItemView

class ItemsAdapter(context: Context) :
    BaseRecyclerAdapter<ResponseItem, ResponseItemView>(
        context, emptyList(), ItemViewHolder()
    )

class ItemViewHolder :
    BaseViewHolder.Factory<ResponseItem, ResponseItemView> {

    override fun create(context: Context): BaseViewHolder<ResponseItem, ResponseItemView> =
        ItemVH(ResponseItemView(context))

    inner class ItemVH(view: ResponseItemView) :
        BaseViewHolder<ResponseItem, ResponseItemView>(view) {

        override fun bind(view: ResponseItemView?, item: ResponseItem, position: Int) {
            view?.setData(item)
        }
    }
}
