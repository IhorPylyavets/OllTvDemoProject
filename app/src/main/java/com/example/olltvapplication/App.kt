package com.example.olltvapplication

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.example.olltvapplication.di.appModule
import com.example.olltvapplication.di.networkModule
import com.example.olltvapplication.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(appModule, networkModule, repositoryModule)
            )
        }
    }
}
