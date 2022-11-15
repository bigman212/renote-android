package com.bill.renote.base

import androidx.lifecycle.ViewModel
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

abstract class BaseViewModel : ViewModel(), AppViewModel, CoroutineScope {

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + Dispatchers.Main.immediate + CoroutineExceptionHandler(::handleCoroutineException)

    override val events: EventsQueue by lazy { EventsQueue() }

    private fun handleCoroutineException(coroutineContext: CoroutineContext, throwable: Throwable) {
        if (throwable is CancellationException) {
            Timber.i(throwable)
        } else {
            Timber.tag(this::class.java.simpleName).e(throwable, coroutineContext.toString())
        }
    }

    override fun onCleared() {
        coroutineContext.cancelChildren()
        super.onCleared()
    }
}
