package ru.bill.renote.common.extensions

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.bill.renote.common.rx.SchedulersProvider

fun <T> Observable<T>.ioSubscribe(): Observable<T> {
  return subscribeOn(Schedulers.io())
}

fun <T> Observable<T>.uiObserve(): Observable<T> {
  return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.uiObserve(): Flowable<T> {
  return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.ioSubscribe(): Flowable<T> {
  return subscribeOn(Schedulers.io())
}

fun Completable.ioSubscribe(): Completable {
  return subscribeOn(Schedulers.io())
}

fun Completable.uiObserve(): Completable {
  return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.uiObserve(): Maybe<T> {
  return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.ioSubscribe(): Maybe<T> {
  return subscribeOn(Schedulers.io())
}

fun <T> Single<T>.scheduleIoToUi(schedulers: SchedulersProvider): Single<T> {
  return subscribeOn(schedulers.io()).observeOn(schedulers.ui())
}

fun <T> Single<T>.scheduleComputationToUi(schedulers: SchedulersProvider): Single<T> {
  return subscribeOn(schedulers.computation()).observeOn(schedulers.ui())
}

fun Completable.scheduleIoToUi(schedulers: SchedulersProvider): Completable {
  return subscribeOn(schedulers.io()).observeOn(schedulers.ui())
}
