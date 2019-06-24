package com.tommannson.apps.componentisation.components.progress

import com.tommannson.apps.componentisation.arch.RxAction
import com.tommannson.apps.componentisation.arch.bus.BusFactory
import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import com.tommannson.apps.componentisation.components.login.LoginFormEvent
import com.tommannson.apps.componentisation.model.pipe.BaseResolver
import com.tommannson.apps.componentisation.model.usecase.login.LoginBusinessEvents
import javax.inject.Inject

class ProgressController @Inject constructor() : BaseResolver<LoginFormEvent, RxAction>() {


    override fun getInternalBus() = null
    override fun getExternalBus() = appScoped.getSafeManagedObservable()

    @Inject
    lateinit var screenScoped: ScopedEventBusFactory
    @Inject
    lateinit var appScoped: BusFactory

    override fun resolveIn(event: LoginFormEvent) {
    }

    override fun resolveExternalIn(event: RxAction) {
        if (event is LoginBusinessEvents.PerformLogin) {
            screenScoped.emit(ProgressState(true))
        } else if (event is LoginBusinessEvents) {
            screenScoped.emit(ProgressState(false))
        }
    }
}