package com.tommannson.apps.componentisation.arch

sealed class BindingState {
    object INITIALISED:BindingState()
    object BOUND:BindingState()
    object NEED_REBIND:BindingState()
}
