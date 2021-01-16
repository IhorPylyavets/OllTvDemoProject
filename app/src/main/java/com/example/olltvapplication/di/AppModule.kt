package com.example.olltvapplication.di

import com.example.olltvapplication.ui.channelList.ChannelListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ChannelListViewModel(get()) }
}
