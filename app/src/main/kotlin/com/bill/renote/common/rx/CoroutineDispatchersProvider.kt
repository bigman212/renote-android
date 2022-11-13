package com.bill.renote.common.rx

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatchersProvider {
    fun ui(): CoroutineDispatcher = Dispatchers.Main
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun computation(): CoroutineDispatcher = Dispatchers.Default
}
