package com.tommannson.apps.componentisation.arch

import com.tommannson.apps.componentisation.arch.component.UIComponent

data class LazyComponentManager(
    private var lazyState: LazyState,
    private val component: UIComponent<*, *>,
    private val nestingManager: NestingComponentManager
) {

    fun performLazyLoadingIfNeed() {
        if (lazyState.lazy) {
            component.build()
            nestingManager.createChildren()
        }
    }

    fun initialize() {
        lazyState.initialiseLazy()
    }
}
