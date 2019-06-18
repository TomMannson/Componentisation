package com.jmoraes.componentizationsample.screens.login

import com.tommannson.apps.componentisation.components.login.LoginFormEvent
import com.tommannson.apps.componentisation.arch.RxAction
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class LoginState() : BiFunction<LoginState.LoginStateData, RxAction, LoginState.LoginStateData> {


    fun <T> registerForEvent(source: Iterable<Observable<RxAction>>) {
        Observable.merge(source)
                .scan(LoginStateData(), this)
                .subscribe()
    }


    override fun apply(state: LoginStateData, event: RxAction): LoginStateData {
        when (event) {
            is LoginFormEvent.SubmitLogin -> {
                if (state.loginInProgress) {
                    return state
                }
                return state.copy(loginInProgress = true, loginButtonEnabled = false)
            }
        }

        return state
    }

    data class LoginStateData(
            val loginValidationMessage: String = "",
            val passwordValidationMessage: String = "",
            val loginButtonEnabled: Boolean = true,
            val loginInProgress: Boolean = false
    )

}
