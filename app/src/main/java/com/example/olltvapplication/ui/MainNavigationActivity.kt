package com.example.olltvapplication.ui

import com.example.olltvapplication.R
import com.example.olltvapplication.base.BaseNavigationActivity
import com.example.olltvapplication.data.ChannelItem
import com.example.olltvapplication.ui.channelDetails.ChannelDetailsFragment
import com.example.olltvapplication.ui.channelList.ChannelListFragmentNavigator
import com.example.olltvapplication.ui.channelDetails.ChannelDetailsFragmentNavigator

abstract class MainNavigationActivity:
    BaseNavigationActivity(),
    ChannelListFragmentNavigator,
    ChannelDetailsFragmentNavigator {

    override fun openChannelDetailsFragment(item: ChannelItem) =
        openFragment(
            R.id.channelDetailsFragment,
            ChannelDetailsFragment.getBundle(item)
        )
}
