package com.tommannson.apps.componentisation.components.login.form

import com.tommannson.apps.componentisation.arch.bus.BusFactory
import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import com.tommannson.apps.componentisation.model.pipe.BaseResolver
import com.tommannson.apps.componentisation.model.usecase.login.LoginBusinessEvents
import javax.inject.Inject

class LoginFormController @Inject constructor() : BaseResolver<LoginFormEvent, LoginBusinessEvents>() {


    override fun getInternalBus() = screenScoped.getSafeManagedObservableFiltered<LoginFormEvent>()
    override fun getExternalBus() = appScoped.getSafeManagedObservableFiltered<LoginBusinessEvents>()

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
        state = localState.copy(progress = true, error = false, success = false)
        screenScoped.emit(state as LoginState)

        appScoped.emit(LoginBusinessEvents::class.java, LoginBusinessEvents.PerformLogin(event.login, event.password))
    }

    override fun resolveExternalIn(event: LoginBusinessEvents) {
        val newState: LoginState? = when (event) {
            is LoginBusinessEvents.LoginDataInvalid -> LoginState(
                "",
                "",
                error = true,
                progress = false
            )
            is LoginBusinessEvents.LoginSuccess -> LoginState(
                "",
                "",
                success = true,
                progress = false
            )
            else -> null
        }
        if (newState != null) {
            state = newState;
            screenScoped.emit(newState)
        }
    }
}