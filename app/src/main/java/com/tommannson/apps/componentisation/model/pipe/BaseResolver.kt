package com.tommannson.apps.componentisation.model.pipe

import com.tommannson.apps.componentisation.arch.ComponentEvent
import com.tommannson.apps.componentisation.arch.RxAction
import com.tommannson.apps.componentisation.arch.plusAssign
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BaseResolver<INTERNAL : ComponentEvent, EXTERNAL : RxAction> {

    abstract fun resolveIn(event: INTERNAL);

    abstract fun resolveExternalIn(event: EXTERNAL);

    val compositeDisposable = CompositeDisposable()

    fun start() {
        compositeDisposable += getInternalBus()?.subscribe { resolveIn(it) }
        compositeDisposable += getExternalBus()?.subscribe { resolveExternalIn(it) }
    }

    fun clean() {
        compositeDisposable.dispose()
    }

    abstract fun getInternalBus(): Observable<INTERNAL>?
    abstract fun getExternalBus(): Observable<EXTERNAL>?

}
