package com.tommannson.apps.componentisation.model.usecase.login

import com.tommannson.apps.componentisation.arch.RxAction

sealed class LoginBusinessEvents : RxAction() {

    class PerformLogin(val login: String, val password: String): LoginBusinessEvents()
    class LoginDataInvalid(val login: String, val password: String) : LoginBusinessEvents()
    object LoginSuccess: LoginBusinessEvents()
    object LoginError: LoginBusinessEvents()


}
