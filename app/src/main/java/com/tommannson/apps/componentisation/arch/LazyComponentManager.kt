package com.tommannson.apps.componentisation.arch

import com.tommannson.apps.componentisation.arch.component.UIComponent

data class LazyComponentManager(
    private var lazyState: LazyState,
    private val component: UIComponent<*, *>
) {

    fun performLazyLoadingIfNeed() {
        if (lazyState.lazy) {
            component.create()
        }
    }

    fun initialize() {
        lazyState.initialiseLazy()
    }
}
