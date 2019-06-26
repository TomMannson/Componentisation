package com.tommannson.apps.componentisation.components.progress

import com.tommannson.apps.componentisation.arch.bus.BusFactory
import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import com.tommannson.apps.componentisation.components.login.form.LoginFormEvent
import com.tommannson.apps.componentisation.model.pipe.BaseResolver
import com.tommannson.apps.componentisation.model.usecase.login.LoginBusinessEvents
import javax.inject.Inject

class ProgressController @Inject constructor() : BaseResolver<LoginFormEvent, LoginBusinessEvents>() {


    override fun getInternalBus() = null
    override fun getExternalBus() = appScoped.getSafeManagedObservableFiltered<LoginBusinessEvents>()

    @Inject
    lateinit var screenScoped: ScopedEventBusFactory
    @Inject
    lateinit var appScoped: BusFactory

    override fun resolveIn(event: LoginFormEvent) {
    }

    override fun resolveExternalIn(event: LoginBusinessEvents) {
        if (event is LoginBusinessEvents.PerformLogin) {
            screenScoped.emit(ProgressState(true))
        } else {
            screenScoped.emit(ProgressState(false))
        }
    }
}