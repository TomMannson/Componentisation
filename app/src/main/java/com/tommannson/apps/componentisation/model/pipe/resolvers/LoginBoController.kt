package com.tommannson.apps.componentisation.model.pipe.resolvers

import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.components.login.LoginFormEvent
import com.tommannson.apps.componentisation.components.login.LoginState
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
                    .doOnNext {
                        screenScoped.emit(LoginState("", "", true))
                    }
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .map(::validateForm)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        when(it){
                            is LoginFormEvent.LoginDataInvalid -> LoginState("", "", error = false)
                            is LoginFormEvent.LoginSuccess -> LoginState("", "", error = false, progress = false)
                        }
                    }
            }
        }
    }

    fun validateForm(event: LoginFormEvent.SubmitLogin): LoginFormEvent {
        if (event.login == "") {
            return LoginFormEvent.LoginDataInvalid("sdfsdf", "asdasd")
        }
        return LoginFormEvent.LoginSuccess
    }
}