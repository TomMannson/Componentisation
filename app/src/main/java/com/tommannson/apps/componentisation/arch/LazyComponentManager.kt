package com.tommannson.apps.componentisation.arch

class LazyComponentManager(
    private var lazyState: LazyState,
    private val component: UINewComponent<*, *>,
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
