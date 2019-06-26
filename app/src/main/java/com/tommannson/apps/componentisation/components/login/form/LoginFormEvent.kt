package com.tommannson.apps.componentisation.components.login.form

import com.tommannson.apps.componentisation.arch.ComponentEvent

sealed class LoginFormEvent : ComponentEvent() {
    data class SubmitLogin(val login: String, val password: String) : LoginFormEvent()
    data class LoginDataInvalid(val login: String, val password: String) : LoginFormEvent()
    object LoginSuccess : LoginFormEvent()
}