package com.example.olltvapplication.extension

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.toMainThread() =
    this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
