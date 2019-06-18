package com.tommannson.apps.componentisation.arch

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.netflix.arch.DisposingChecker
import com.netflix.arch.UINewComponent

open class UINewHost : ViewModel(), UIParent {

    override fun getFindViewGroup(id: Int) = activity.findViewById<ViewGroup>(id)

    val disposingChecker = DisposingChecker()
    val mapOfComponent = mutableMapOf<Int, UINewComponent<*, *>>()
    var numberOfComponents = 0;
    var currentComponentNumber = 0;
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
        currentComponentNumber++;
        if (!mapOfComponent.contains(currentComponentNumber)) {
            mapOfComponent[currentComponentNumber] = component
        }
    }

    fun build(needRender: Boolean = true) {
        numberOfComponents = currentComponentNumber
        for (component in mapOfComponent) {
            component.value.create(this);
        }
        if (needRender) {
            for (component in mapOfComponent) {
                component.value.render();
            }
        }
    }

    fun dispatchRendering() {
        for (component in mapOfComponent) {
            component.value.render();
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
        if (mapOfComponent.isEmpty()) {
            function()
        }
        build(!mapOfComponent.isEmpty())
        return this
    }
}
