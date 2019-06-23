package com.tommannson.apps.componentisation.arch

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.netflix.arch.DisposingChecker
import java.util.*
import kotlin.collections.HashMap

open class UINewHost : ViewModel(), UIParent {

    override fun getFindViewGroup(id: Int) = activity.findViewById<ViewGroup>(id)

    val disposingChecker = DisposingChecker()
    val mapOfComponent = mutableMapOf<UUID, UINewComponent<*, *>>()
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

    fun add(component: UINewComponent<*, *>) {
        mapOfComponent[UUID.randomUUID()] = component
    }

    fun build(
        needRender: Boolean = true
    ) {
        for (component in mapOfComponent) {
            component.value.create();
        }
        if (needRender) {
            for (component in mapOfComponent) {
                component.value.commitState();
            }
        }
    }

    fun dispatchRendering() {
        for (component in mapOfComponent) {
            component.value.commitState();
        }
    }

    fun disposeIfNeeded(disposeTarget: Any) {
        if (disposingChecker.check(disposeTarget)) {
            for (component in mapOfComponent) {
                component.value.dispose();
            }
        }
    }

    fun composition(function: UINewHost.() -> Unit): UINewHost {
        function()
        build(!mapOfComponent.isEmpty())
        return this
    }
}
