package com.example.olltvapplication.base

import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.component.KoinComponent

open class BaseViewModel: ViewModel(), LifecycleObserver, KoinComponent {

    private val mDisposables = CompositeDisposable()

    val loadingEvent = MutableLiveData<Boolean>()

    protected fun showLoading() {
        loadingEvent.postValue(true)
    }

    protected fun hideLoading() {
        loadingEvent.postValue(false)
    }

    private fun Disposable.addToCompositeDisposable() = mDisposables.add(this)

    protected fun <T> Observable<T>.fetchData(
        success: (data: T) -> Unit = {},
        error: (error: Throwable) -> Unit = {},
        loading: Boolean = true
    ) {
        if (loading) {
            showLoading()
        }
        this.subscribe({
            if (loading) {
                hideLoading()
            }
            success.invoke(it)
        }, {
            error.invoke(it)
            if (loading) {
                hideLoading()
            }
        }).addToCompositeDisposable()
    }

    @CallSuper
    override fun onCleared() {
        mDisposables.clear()
        super.onCleared()
    }

    protected fun <T> Observable<T>.fetchData(
        success: (data: T) -> Unit = {},
        error: (error: Throwable) -> Unit = {}
    ) {
        showLoading()
        this.subscribe({
            hideLoading()
            success.invoke(it)
        }, {
            hideLoading()
            error.invoke(it)
        }).addToCompositeDisposable()
    }
}
