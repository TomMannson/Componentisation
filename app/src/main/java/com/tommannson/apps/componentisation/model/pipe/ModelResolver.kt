package com.tommannson.apps.componentisation.model.pipe


import com.tommannson.apps.componentisation.model.pipe.resolvers.LoginBoController
import com.tommannson.apps.componentisation.arch.RxAction
import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.components.login.LoginFormEvent
import io.reactivex.disposables.Disposable

class ModelResolver {

    var disposable: Disposable? = null;

    fun resolve(event: RxAction, factory: ScopedEventBusFactory) {
        when (event) {
//            is GithubIntaractionEvent -> {
//                DataResolver().resolve(event = event, busFactory = factory);
//            }
            is LoginFormEvent -> {
                LoginBoController().resolve(event = event, busFactory = factory);
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