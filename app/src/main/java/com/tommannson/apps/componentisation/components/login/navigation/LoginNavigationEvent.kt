package com.tommannson.apps.componentisation.components.login.navigation

import com.tommannson.apps.componentisation.arch.ComponentEvent

sealed class LoginNavigationEvent : ComponentEvent() {
    object NavigationPerformed : LoginNavigationEvent()
}