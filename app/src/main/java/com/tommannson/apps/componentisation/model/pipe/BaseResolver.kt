package com.tommannson.apps.componentisation.model.pipe

import com.tommannson.apps.componentisation.arch.ComponentEvent
import com.tommannson.apps.componentisation.arch.RxAction
import com.tommannson.apps.componentisation.arch.plusAssign
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseResolver<INTERNAL : ComponentEvent, EXTERNAL : RxAction> {

    abstract fun resolveIn(event: INTERNAL);

    abstract fun resolveExternalIn(event: EXTERNAL);

    val compositeDisposable = CompositeDisposable()

    fun start() {
        compositeDisposable += getInternalBus()?.observeOn(Schedulers.computation())
            ?.subscribe { resolveIn(it) }
        compositeDisposable += getExternalBus()
            ?.observeOn(Schedulers.computation())
            ?.subscribe { resolveExternalIn(it) }
    }

    fun clean() {
        compositeDisposable.dispose()
    }

    open fun getInternalBus(): Observable<INTERNAL>? = Observable.empty<INTERNAL>()
    open fun getExternalBus(): Observable<EXTERNAL>? = Observable.empty<EXTERNAL>()

}
