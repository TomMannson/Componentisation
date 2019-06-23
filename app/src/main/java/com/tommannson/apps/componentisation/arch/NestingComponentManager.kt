package com.tommannson.apps.componentisation.arch

class NestingComponentManager() {

    val list = mutableListOf<UINewComponent<*, *>>()

    fun add(component: UINewComponent<*, *>) {
        list += component
    }

    internal fun renderChildren() {
        for (component in list) {
            component.commitState();
        }
    }

    internal fun createChildren() {
        for (component in list) {
            component.create();
        }
    }

    fun clearChildren() {
        for (component in list) {
            component.dispose();
        }
    }
}
