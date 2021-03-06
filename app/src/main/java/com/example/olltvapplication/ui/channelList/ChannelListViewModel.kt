package com.example.olltvapplication.ui.channelList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.olltvapplication.base.BaseViewModel
import com.example.olltvapplication.data.ChannelItem
import com.example.olltvapplication.repository.OllTvRepository

class ChannelListViewModel(
        private val repository: OllTvRepository
): BaseViewModel() {

    val itemList = MutableLiveData<List<ChannelItem>>()

    var borderId: Long = 0
    var direction = 0
    var androidId = ""

    var loadingMore: Boolean = true

    var totalProducts = 0

    fun loadData() {
        repository.loadData(
                serialNumber = androidId,
                borderId = borderId,
                direction = direction
        ).fetchData(
                success = {
                    itemList.value = it.items
                    totalProducts = it.total

                    if (!it.items.isNullOrEmpty()) {
                        borderId = it.items!!.last().id
                    }
                },
                error = {
                    Log.e(ChannelListViewModel::class.java.name, it.message.toString())
                }
        )
    }

    fun getMoreProducts() {
        if (loadingMore) {
            loadingMore = false
            loadData()
        }
    }

}