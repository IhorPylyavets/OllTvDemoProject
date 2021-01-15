package com.example.olltvapplication.di

import com.example.olltvapplication.repository.OllTvRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { OllTvRepository(get()) }
}
