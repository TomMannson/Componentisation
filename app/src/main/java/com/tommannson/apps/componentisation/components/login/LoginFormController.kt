package com.tommannson.apps.componentisation.components.login

import com.tommannson.apps.componentisation.arch.RxAction
import com.tommannson.apps.componentisation.arch.bus.BusFactory
import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import com.tommannson.apps.componentisation.model.pipe.BaseResolver
import com.tommannson.apps.componentisation.model.usecase.login.LoginBusinessEvents
import javax.inject.Inject

class LoginFormController @Inject constructor() : BaseResolver<LoginFormEvent, RxAction>() {


    override fun getInternalBus() = screenScoped.getSafeManagedObservableFiltered<LoginFormEvent>()
    override fun getExternalBus() = appScoped.getSafeManagedObservable()

    @Inject
    lateinit var screenScoped: ScopedEventBusFactory
    @Inject
    lateinit var appScoped: BusFactory

    var state: LoginState? = null

    override fun resolveIn(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.SubmitLogin -> doOnSubmit(event)
        }
    }

    private fun doOnSubmit(event: LoginFormEvent.SubmitLogin) {
        if (state == null) {
            state = LoginState(event.login, event.password)
        }

        val localState = state as LoginState

        if (localState.progress) {
            return
        }
        state = localState.copy(progress = true)
        screenScoped.emit(state as LoginState)

        appScoped.emit(LoginBusinessEvents::class.java, LoginBusinessEvents.PerformLogin(event.login, event.password))
    }

    override fun resolveExternalIn(event: RxAction) {
        if (event is LoginBusinessEvents) {

            val newState: LoginState? = when (event) {
                is LoginBusinessEvents.LoginDataInvalid -> LoginState("", "", error = true)
                is LoginBusinessEvents.LoginSuccess -> LoginState("", "", success = true)
                else -> null
            }
            newState?.let {
                state = it;
                screenScoped.emit(it)
            }

        }
    }
}