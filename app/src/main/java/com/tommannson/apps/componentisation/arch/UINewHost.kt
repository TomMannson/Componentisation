package com.tommannson.apps.componentisation.arch

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.tommannson.apps.componentisation.arch.component.UIComponent
import com.tommannson.apps.componentisation.arch.identity.ComponentId
import com.tommannson.apps.componentisation.arch.identity.IdGenerator

open class UINewHost : ViewModel(), UIParent {

    override fun getFindViewGroup(id: Int) = activity.findViewById<ViewGroup>(id)

    val idGenerator = IdGenerator.get()
    private var lastTree = mutableMapOf<ComponentId, UIComponent<*, *>>()
    private var currentTree = mutableMapOf<ComponentId, UIComponent<*, *>>()
    var currentComponentNumber = 0;
    val stateMigrator = StateTreeMigrator()
    lateinit var activity: AppCompatActivity

    companion object {
        fun create(ctx: AppCompatActivity): UINewHost {
            return ViewModelProviders.of(ctx).get(UINewHost::class.java).also {
                it.currentComponentNumber = 0;
                it.activity = ctx
            }
        }
    }

    fun add(component: UIComponent<*, *>, id: ComponentId = idGenerator.getNextNumberId()) {
        currentTree[id] = component
    }

    fun build(
        needRender: Boolean = true
    ) {
        idGenerator.reset()

        for (component in currentTree) {
            component.value.create();
        }
        stateMigrator.migrateState(lastTree, currentTree)
        lastTree = currentTree
        if (needRender) {
            for (component in currentTree) {
                component.value.commitState();
            }
        }
    }

    fun dispatchRendering() {
        for (component in currentTree) {
            component.value.commitState();
        }
    }

    fun dispose() {
        for (component in currentTree) {
            component.value.dispose();
        }
    }


    fun composition(function: UINewHost.() -> Unit): UINewHost {
        currentTree = mutableMapOf()
        function()
        build(!currentTree.isEmpty())
        return this
    }
}
