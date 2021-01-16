package com.example.olltvapplication.ui.channelList

import android.os.Bundle
import android.view.View
import android.provider.Settings
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olltvapplication.R
import com.example.olltvapplication.data.ChannelItem
import com.example.olltvapplication.databinding.FragmentChannelListBinding
import com.example.olltvapplication.extension.visibleOrGone
import com.example.olltvapplication.navigation.BaseFragmentNavigationCommander
import com.example.olltvapplication.navigation.BaseFragmentNavigator
import com.example.olltvapplication.navigation.MainActivityToolbarWrapper
import com.example.olltvapplication.ui.channelList.adapter.ItemsAdapter
import com.example.olltvapplication.viewBinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

interface ChannelListFragmentNavigator : BaseFragmentNavigationCommander {
    fun openChannelDetailsFragment(item: ChannelItem)
}

class ChannelListFragment:
    BaseFragmentNavigator<ChannelListFragmentNavigator, MainActivityToolbarWrapper>() {

    companion object {
        private const val PRE_FETCH_DISTANCE = 5
    }

    private val viewModel: ChannelListViewModel by viewModel()

    override fun getLayoutRes() = R.layout.fragment_channel_list

    private val bindingView by viewBinding(FragmentChannelListBinding::bind)

    private lateinit var adapter: ItemsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarWrapper {
            hideAll()
            setToolbarTitle("OllTv Demo Channels")
        }

        context?.let {
            adapter = ItemsAdapter(it)
            val linearLayoutManager = LinearLayoutManager(it)
            bindingView.recyclerView.layoutManager = linearLayoutManager
            bindingView.recyclerView.adapter = adapter
            adapter.setOnItemClickListener { _, item, _, _ ->
                fragmentNavigator.openChannelDetailsFragment(item)
            }

            adapter.setOnBindListener { _, _, position ->
                adapter.itemCount.let {
                    if (position == it - PRE_FETCH_DISTANCE) loadMore()
                }
            }
        }

        context?.let {
            viewModel.androidId = Settings.Secure.getString(it.contentResolver, Settings.Secure.ANDROID_ID)
        }
        viewModel.loadData()

        viewModel.itemList.observe(viewLifecycleOwner, Observer {
            adapter.addItems(it)
        })

        viewModel.loadingEvent.observe(viewLifecycleOwner, Observer { loading ->
            bindingView.progressBar.visibleOrGone(loading)
        })
    }

    private fun loadMore() {
        viewModel.loadingMore =
            viewModel.totalProducts > 0 && adapter.items.size < viewModel.totalProducts

        viewModel.direction = 1
        viewModel.getMoreProducts()
    }
}
