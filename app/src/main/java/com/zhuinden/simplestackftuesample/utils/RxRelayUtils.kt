package com.zhuinden.simplestackftuesample.utils

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

fun <T> BehaviorRelay<T>.get(): T = value!!

fun <T> BehaviorRelay<T>.getOrNull(): T? = value

fun <T : Any> BehaviorRelay<T>.set(value: T) {
    this.accept(value)
}

fun <T : Any> Observable<T>.bindToRelay(
    compositeDisposable: CompositeDisposable,
    relay: BehaviorRelay<T>
) {
    subscribeBy {
        relay.accept(it)
    }.addTo(compositeDisposable)
}

fun <T : Any> Observable<T>.observe(
    compositeDisposable: CompositeDisposable,
    onError: (Throwable) -> Unit = {},
    onNext: (T) -> Unit
) {
    subscribeBy(
        onError = onError,
        onNext = onNext
    ).addTo(compositeDisposable)
}