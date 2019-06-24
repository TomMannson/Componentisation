package com.tommannson.apps.componentisation.arch

import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

//inline fun <reified T, reified OUTPUT> listen(observable: Observable<T>): Observable<OUTPUT> {
//    return observable.cast<OUTPUT>(OUTPUT::class.java)
//}
//
inline fun <reified T : RxAction, reified T2 : RxAction, reified OUTPUT> listenMainStram2(bus: ScopedEventBusFactory): Observable<OUTPUT> {

    return Observable.merge(
        bus.getSafeManagedObservableFiltered<T>(),
        bus.getSafeManagedObservableFiltered<T2>()
    ).cast<OUTPUT>(OUTPUT::class.java)
}


//
//inline fun <reified T, reified OUTPUT> listen(observable: Observable<T>): Observable<OUTPUT> {
//    return observable.cast<OUTPUT>(OUTPUT::class.java)
//}
//
//inline fun <reified T, reified OUTPUT> listen(observable: Observable<T>): Observable<OUTPUT> {
//    return observable.cast<OUTPUT>(OUTPUT::class.java)
//}

operator fun CompositeDisposable.plusAssign(disposable: Disposable?) {
    if(disposable != null) {
        this.add(disposable)
    }
}