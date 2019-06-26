package com.tommannson.apps.componentisation.components.login.navigation

import com.tommannson.apps.componentisation.arch.RxAction

data class LoginNavigationState(
    val needNavigation: Boolean
) : RxAction()
