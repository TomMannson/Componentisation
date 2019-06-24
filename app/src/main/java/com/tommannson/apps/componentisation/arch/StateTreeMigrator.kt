package com.tommannson.apps.componentisation.arch

import com.tommannson.apps.componentisation.arch.component.UIComponent
import com.tommannson.apps.componentisation.arch.identity.ComponentId

class StateTreeMigrator {

    fun migrateState(
        lastTree: MutableMap<ComponentId, UIComponent<*, *>>,
        mapOfComponent: MutableMap<ComponentId, UIComponent<*, *>>
    ) {
        for ((key, value) in lastTree) {
            val target = mapOfComponent[key]
            if (target != null) {
                migrateComponentState(target, value)
            } else {
                mapOfComponent[key] = value
            }
        }
    }

    fun migrateComponentState(old: UIComponent<*, *>, new: UIComponent<*, *>) {
        MigrateStateJava.setState(new, old)
        new.lazyInitialisation = old.lazyInitialisation.copy(component = new, nestingManager = new.nestingManager)
    }

    fun migrateComponentChildren(old: NestingComponentManager, new: NestingComponentManager) {
        migrateState(old.nestingMap, new.nestingMap)
    }

}
