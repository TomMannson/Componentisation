package com.tommannson.apps.componentisation.model.pipe

import com.tommannson.apps.componentisation.arch.RxAction
import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import com.tommannson.apps.componentisation.components.login.LoginFormEvent
import io.reactivex.disposables.Disposable

class LoginModelResolver {

    var disposable: Disposable? = null;

    fun resolve(event: RxAction, factory: ScopedEventBusFactory) {
        when (event) {
            is LoginFormEvent -> {

            }
        }
    }

    fun subscribeWithBus(factory: ScopedEventBusFactory) {
        disposable = factory.getSafeManagedObservableFiltered<RxAction>()
            .subscribe {
                resolve(it, factory)
            }
    }

    fun clean() {
        disposable?.dispose()
    }


}