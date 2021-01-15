package com.example.olltvapplication.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olltvapplication.R
import com.example.olltvapplication.databinding.ActivityMainBinding
import com.example.olltvapplication.extension.visibleOrGone
import com.example.olltvapplication.ui.adapter.ItemsAdapter
import com.example.olltvapplication.viewBinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    companion object {
        private const val PRE_FETCH_DISTANCE = 5
    }

    private val viewModel: MainActivityViewModel by viewModel()

    private val bindingView by viewBinding(ActivityMainBinding::bind, R.id.root_view)

    private lateinit var adapter: ItemsAdapter

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ItemsAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        bindingView.recyclerView.layoutManager = linearLayoutManager
        bindingView.recyclerView.adapter = adapter
        adapter.setOnItemClickListener { _, item, _, _ ->
            Toast.makeText(this, "Clicked: ${item.channelName}", Toast.LENGTH_SHORT).show()
        }

        adapter.setOnBindListener { _, _, position ->
            adapter.itemCount.let {
                if (position == it - PRE_FETCH_DISTANCE) loadMore()
            }
        }

        viewModel.androidId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        viewModel.loadData()

        viewModel.itemList.observe(this, Observer {
            adapter.addItems(it)
        })

        viewModel.loadingEvent.observe(this) {
            bindingView.progressBar.visibleOrGone(it)
        }
    }

    private fun loadMore() {
        viewModel.loadingMore =
            viewModel.totalProducts > 0 && adapter.items.size < viewModel.totalProducts

        viewModel.direction = 1
        viewModel.getMoreProducts()
    }
}
