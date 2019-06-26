package com.tommannson.apps.componentisation.components.login.navigation

import com.tommannson.apps.componentisation.arch.bus.BusFactory
import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import com.tommannson.apps.componentisation.model.pipe.BaseResolver
import com.tommannson.apps.componentisation.model.usecase.login.LoginBusinessEvents
import javax.inject.Inject

class LoginNavigationController @Inject constructor() : BaseResolver<LoginNavigationEvent, LoginBusinessEvents>() {


    override fun getInternalBus() = screenScoped.getSafeManagedObservableFiltered<LoginNavigationEvent>()
    override fun getExternalBus() = appScoped.getSafeManagedObservableFiltered<LoginBusinessEvents>()

    @Inject
    lateinit var screenScoped: ScopedEventBusFactory
    @Inject
    lateinit var appScoped: BusFactory

    override fun resolveIn(event: LoginNavigationEvent) {
        when (event) {
            is LoginNavigationEvent.NavigationPerformed -> doOnSubmit(event)
        }
    }

    private fun doOnSubmit(event: LoginNavigationEvent.NavigationPerformed) {
        screenScoped.emit(LoginNavigationState(false))
    }

    override fun resolveExternalIn(event: LoginBusinessEvents) {
        if (event is LoginBusinessEvents.LoginSuccess) {
            screenScoped.emit(LoginNavigationState(true))
        }
    }
}