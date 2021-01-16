package com.example.olltvapplication.ui.channelDetails

import android.os.Bundle
import android.view.View
import com.example.olltvapplication.R
import com.example.olltvapplication.data.ChannelItem
import com.example.olltvapplication.databinding.FragmentChannelDetailsBinding
import com.example.olltvapplication.navigation.BaseFragmentNavigationCommander
import com.example.olltvapplication.navigation.BaseFragmentNavigator
import com.example.olltvapplication.navigation.MainActivityToolbarWrapper
import com.example.olltvapplication.viewBinding.viewBinding
import com.squareup.picasso.Picasso

interface ChannelDetailsFragmentNavigator : BaseFragmentNavigationCommander

class ChannelDetailsFragment:
    BaseFragmentNavigator<ChannelDetailsFragmentNavigator, MainActivityToolbarWrapper>() {

    override fun getLayoutRes() = R.layout.fragment_channel_details

    private val bindingView by viewBinding(FragmentChannelDetailsBinding::bind)

    companion object {
        private const val KEY_CHANNEL_ITEM = "channel_item"
        fun getBundle(item: ChannelItem?) = Bundle().apply {
            item?.let { putParcelable(KEY_CHANNEL_ITEM, it) }
        }
    }

    private lateinit var channelItem: ChannelItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            channelItem = requireArguments().getParcelable(KEY_CHANNEL_ITEM)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarWrapper {
            hideAll()
            showBackBtn()
            setToolbarTitle(channelItem.channelName)
        }

        bindingView.tvDescription.text = channelItem.description

        Picasso
            .get()
            .load(channelItem.icon)
            .placeholder(R.drawable.ic_empty)
            .error(R.drawable.ic_empty)
            .into(bindingView.channelIcon)
    }
}
