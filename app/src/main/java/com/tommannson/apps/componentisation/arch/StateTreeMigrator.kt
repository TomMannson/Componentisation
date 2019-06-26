package com.tommannson.apps.componentisation.arch

import com.tommannson.apps.componentisation.arch.component.UIComponent
import com.tommannson.apps.componentisation.arch.identity.ComponentId

class StateTreeMigrator {

    fun migrateState(
        lastTree: MutableMap<ComponentId, UIComponent<*, *>>,
        newTree: MutableMap<ComponentId, UIComponent<*, *>>
    ) {
        for ((key, lastComponent) in lastTree) {
            val newComponent = newTree[key]
            if (newComponent != null) {
                migrateComponentState(lastComponent, newComponent)
            }
        }
    }

    fun migrateComponentState(old: UIComponent<*, *>, new: UIComponent<*, *>) {
        MigrateStateJava.setState(old, new)
    }
}
