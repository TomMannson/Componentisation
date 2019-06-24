package com.tommannson.apps.componentisation.arch

import com.tommannson.apps.componentisation.arch.component.UIComponent
import com.tommannson.apps.componentisation.arch.identity.ComponentId

class NestingComponentManager() {

    val nestingMap = mutableMapOf<ComponentId, UIComponent<*, *>>()

    fun add(id: ComponentId, component: UIComponent<*, *>) {
        nestingMap += Pair(id, component)
    }

    internal fun renderChildren() {
        for ((key, component) in nestingMap) {
            component.commitState();
        }
    }

    internal fun createChildren() {
        for ((key, component) in nestingMap) {
            component.create();
        }
    }

    fun clearChildren() {
        for ((key, component) in nestingMap) {
            component.dispose();
        }
    }
}
