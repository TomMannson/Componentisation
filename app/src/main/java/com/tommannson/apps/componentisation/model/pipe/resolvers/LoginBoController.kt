package com.tommannson.apps.componentisation.model.pipe.resolvers

import android.util.Log
import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.components.login.LoginFormEvent
import com.tommannson.apps.componentisation.model.pipe.BaseResolver
import com.tommannson.apps.componentisation.model.ws.MainInteractorImpl
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class LoginBoController : BaseResolver<LoginFormEvent>() {
    override fun validType(input: Any) = type<LoginFormEvent>(input)

    val impl = MainInteractorImpl()
    lateinit var screenScoped: ScopedEventBusFactory
    lateinit var appScoped: ScopedEventBusFactory

    override fun resolve(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.SubmitLogin -> {
                Observable.just(event)
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .map(::validateForm)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        screenScoped.emit(it)
                    }
            }
        }
    }

    fun validateForm(event: LoginFormEvent.SubmitLogin): LoginFormEvent {
        if (event.login == "") {
            Log.d("asdasdasd", "asdasdasd")
        }
        return LoginFormEvent.SubmitLogin("", "")
    }
}