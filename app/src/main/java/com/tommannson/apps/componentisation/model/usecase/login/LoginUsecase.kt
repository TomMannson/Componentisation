package com.tommannson.apps.componentisation.model.usecase.login

import android.os.SystemClock
import com.tommannson.apps.componentisation.arch.bus.BusFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.cast
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginUsecase @Inject constructor() {

    @Inject
    lateinit var appBus: BusFactory

    fun start() {
        appBus.getSafeManagedObservable()
            .filter { it is LoginBusinessEvents }
            .cast<LoginBusinessEvents>()
            .observeOn(Schedulers.computation())
            .subscribe {
                resolveMessage(it)
            }
    }

    private fun resolveMessage(it: LoginBusinessEvents) {
        if (it is LoginBusinessEvents.PerformLogin) {
            SystemClock.sleep(1000)
            appBus.emit(validateForm(it))
        }
    }

    fun validateForm(event: LoginBusinessEvents.PerformLogin): LoginBusinessEvents {
        if (event.login == "") {
            return LoginBusinessEvents.LoginDataInvalid("sdfsdf", "asdasd")
        }
        return LoginBusinessEvents.LoginSuccess
    }

}
