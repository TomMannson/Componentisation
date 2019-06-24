package com.tommannson.apps.componentisation.components.login

import com.tommannson.apps.componentisation.arch.RxAction

data class LoginState(val login: String, val password: String) : RxAction() {

    var progress = false
    var error = false
    var success = false

    constructor(login: String, password: String, progress: Boolean = false, error: Boolean = false, success: Boolean = false) : this(
        login,
        login
    ) {
        this.progress = progress
        this.error = error
        this.success = success
    }

}
