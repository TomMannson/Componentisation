package com.jmoraes.componentizationsample.new_comps.test2

import android.view.ViewGroup
import com.netflix.arch.UINewComponent
import com.tommannson.apps.componentisation.arch.UINewView
import com.tommannson.apps.componentisation.new_comps.test2.Test2UiView
import io.reactivex.Observable

class Test2UiComponent(private val parent: ViewGroup) : UINewComponent<Any, Any>(1, Any()) {

    override fun createView(viewGroup: ViewGroup): UINewView<Any, Any> {
        return Test2UiView(parent)
    }

    override fun getContainerId() = 0

    override fun getUserInteractionEvents(): Observable<Any> = Observable.just(Any())
}


data class Test2State(val test: String)