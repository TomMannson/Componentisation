package com.tommannson.apps.componentisation.arch.identity

class ComponentEquality {

    fun checkEquality(componentOne: ComponentId, componentTwo: ComponentId): Boolean {
        if (componentOne::javaClass == componentTwo::javaClass) {
            return componentOne.equals(componentTwo)
        }
        return false;
    }

}