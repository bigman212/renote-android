package com.bill.renote.data.persist

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class SchedulersProvider @Inject constructor() {
    open fun ui(): Scheduler = AndroidSchedulers.mainThread()
    open fun io(): Scheduler = Schedulers.io()
    open fun computation(): Scheduler = Schedulers.computation()
}
