package com.tommannson.apps.componentisation.model.usecase.login

import android.os.SystemClock
import com.tommannson.apps.componentisation.arch.bus.BusFactory
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginUsecase @Inject constructor() {

    @Inject
    lateinit var appBus: BusFactory

    fun start() {
        appBus.getSafeManagedObservableFiltered<LoginBusinessEvents>()
            .observeOn(Schedulers.computation())
            .subscribe {
                resolveMessage(it)
            }
            .toString()
    }

    private fun resolveMessage(it: LoginBusinessEvents) {
        if (it is LoginBusinessEvents.PerformLogin) {
            SystemClock.sleep(3000)
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
