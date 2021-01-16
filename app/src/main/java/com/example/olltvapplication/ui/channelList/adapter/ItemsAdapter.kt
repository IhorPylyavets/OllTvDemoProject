package com.example.olltvapplication.ui.channelList.adapter

import android.content.Context
import com.example.olltvapplication.base.BaseRecyclerAdapter
import com.example.olltvapplication.base.BaseViewHolder
import com.example.olltvapplication.data.ChannelItem
import com.example.olltvapplication.ui.channelList.view.ChannelItemView

class ItemsAdapter(context: Context) :
    BaseRecyclerAdapter<ChannelItem, ChannelItemView>(
        context, emptyList(), ItemViewHolder()
    )

class ItemViewHolder :
    BaseViewHolder.Factory<ChannelItem, ChannelItemView> {

    override fun create(context: Context): BaseViewHolder<ChannelItem, ChannelItemView> =
        ItemVH(ChannelItemView(context))

    inner class ItemVH(view: ChannelItemView) :
        BaseViewHolder<ChannelItem, ChannelItemView>(view) {

        override fun bind(view: ChannelItemView?, item: ChannelItem, position: Int) {
            view?.setData(item)
        }
    }
}
