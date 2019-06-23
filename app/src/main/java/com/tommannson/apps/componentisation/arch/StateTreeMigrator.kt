package com.tommannson.apps.componentisation.arch

import java.util.*

class StateTreeMigrator {

    fun migrateState(
        lastTree: HashMap<UUID, UINewComponent<*, *>>,
        mapOfComponent: Map<UUID, UINewComponent<*, *>>
    ) {
        for ((key, value) in lastTree) {
            val target = mapOfComponent[key]

        }
    }

    fun migrateComponentState(old: UINewComponent<*, *>, new: UINewComponent<*, *>){
//        new.lazyInitialisation = old.lazyInitialisation
    }

}
