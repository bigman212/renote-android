package ru.bill.renote.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    val events: EventsQueue by lazy { EventsQueue() }

    protected fun sendErrorEvent(error: Throwable) {
        events.append(ErrorEvent(error.message ?: error.toString()))
    }

    fun Disposable.disposeOnCleared() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
