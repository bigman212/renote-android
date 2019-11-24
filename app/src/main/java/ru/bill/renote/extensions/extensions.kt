package ru.bill.renote.extensions

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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



