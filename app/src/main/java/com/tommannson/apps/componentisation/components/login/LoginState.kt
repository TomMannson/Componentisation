package com.tommannson.apps.componentisation.components.login

import com.tommannson.apps.componentisation.arch.RxAction

data class LoginState(
    val login: String,
    val password: String,
    val progress: Boolean = false,
    val error: Boolean = false,
    val success: Boolean = false
) : RxAction()
