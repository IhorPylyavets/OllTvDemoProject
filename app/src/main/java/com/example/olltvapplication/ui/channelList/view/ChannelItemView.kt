package com.example.olltvapplication.ui.channelList.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.olltvapplication.R
import com.example.olltvapplication.data.ChannelItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_channel_item.view.*

class ChannelItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_channel_item, this)
    }

    fun setData(item: ChannelItem) {
        channel_name.text = item.channelName
        tv_name.text = item.name

        Picasso
            .get()
            .load(item.icon)
            .placeholder(R.drawable.ic_empty)
            .error(R.drawable.ic_empty)
            .into(channel_icon)
    }
}
