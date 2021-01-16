package com.example.olltvapplication.di

import com.example.olltvapplication.ui.MainActivity
import com.example.olltvapplication.ui.MainNavigationActivity
import org.koin.core.qualifier.named
import org.koin.dsl.module

val navigationModule = module {
    scope(named<MainActivity>()) {
        scoped { (navigationActivity: MainNavigationActivity?) -> navigationActivity }
    }
}
