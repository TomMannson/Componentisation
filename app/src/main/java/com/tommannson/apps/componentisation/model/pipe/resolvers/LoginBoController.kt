package com.tommannson.apps.componentisation.model.pipe.resolvers

import com.tommannson.apps.componentisation.arch.BusFactory
import com.tommannson.apps.componentisation.arch.RxAction
import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.arch.plusAssign
import com.tommannson.apps.componentisation.components.login.LoginFormEvent
import com.tommannson.apps.componentisation.components.login.LoginState
import com.tommannson.apps.componentisation.components.progress.ProgressState
import com.tommannson.apps.componentisation.model.pipe.BaseResolver
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginBoController @Inject constructor() : BaseResolver<LoginFormEvent, RxAction>() {


    override fun getInternalBus() = screenScoped.getSafeManagedObservableFiltered<LoginFormEvent>()
    override fun getExternalBus() = appScoped.getSafeManagedObservable()

    @Inject
    lateinit var screenScoped: ScopedEventBusFactory
    @Inject
    lateinit var appScoped: BusFactory

    override fun resolveIn(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.SubmitLogin -> doOnSubmit(event)
        }
    }

    private fun doOnSubmit(event: LoginFormEvent.SubmitLogin) {
        compositeDisposable += Observable.just(event)
            .doOnNext {
                screenScoped.emit(ProgressState(true))
                screenScoped.emit(LoginState("", "", true))
            }
            .delay(1000, TimeUnit.MILLISECONDS)
            .map(::validateForm)
            .subscribe(::resolveExternalIn)
    }

    override fun resolveExternalIn(event: RxAction) {
        if (event is LoginFormEvent) {
            screenScoped.emit(
                when (event) {
                    is LoginFormEvent.LoginDataInvalid -> LoginState("", "", error = true)
                    is LoginFormEvent.LoginSuccess -> LoginState("", "", success = true)
                    else -> LoginState("", "", progress = false, error = false, success = false)
                }
            )
            screenScoped.emit(ProgressState(false))
        }
    }

    fun validateForm(event: LoginFormEvent.SubmitLogin): LoginFormEvent {
        if (event.login == "") {
            return LoginFormEvent.LoginDataInvalid("sdfsdf", "asdasd")
        }
        return LoginFormEvent.LoginSuccess
    }
}