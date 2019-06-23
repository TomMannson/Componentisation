package com.tommannson.apps.componentisation.arch

class LazyState(var lazy: Boolean = false) {

    fun initialiseLazy() {
        lazy = true;
    }

    companion object {
        fun lazy() = LazyState(true)

        fun initialised() = LazyState(false)
    }
}
