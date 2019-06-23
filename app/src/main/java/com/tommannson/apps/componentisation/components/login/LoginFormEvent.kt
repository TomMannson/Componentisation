package com.tommannson.apps.componentisation.components.login

import com.netflix.arch.ComponentEvent

sealed class LoginFormEvent : ComponentEvent() {
    class SubmitLogin(val login: String, val password: String) : LoginFormEvent()
    class LoginDataInvalid(val login: String, val password: String) : LoginFormEvent()
    object LoginSuccess : LoginFormEvent()
}