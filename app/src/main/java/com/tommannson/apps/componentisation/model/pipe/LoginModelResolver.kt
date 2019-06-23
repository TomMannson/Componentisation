package com.tommannson.apps.componentisation.model.pipe

import com.tommannson.apps.componentisation.arch.RxAction
import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.components.login.LoginFormEvent
import com.tommannson.apps.componentisation.model.pipe.resolvers.LoginBoController
import io.reactivex.disposables.Disposable

class LoginModelResolver {

    var disposable: Disposable? = null;

    fun resolve(event: RxAction, factory: ScopedEventBusFactory) {
        when (event) {
            is LoginFormEvent -> {
                LoginBoController()
                    .also {
                        it.screenScoped = factory
                    }
                    .resolve(event = event);
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