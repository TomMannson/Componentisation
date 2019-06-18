package com.jmoraes.componentizationsample.new_comps.test1

import android.view.ViewGroup
import com.netflix.arch.UINewComponent
import com.tommannson.apps.componentisation.arch.UINewView
import com.tommannson.apps.componentisation.new_comps.test1.TestUiView
import io.reactivex.Observable

class TestUiComponent(private val parentId: Int, private var state: TestState) : UINewComponent<Any, TestState>(parentId, state) {

    override fun createView(viewGroup: ViewGroup): UINewView<Any, TestState> {
        return TestUiView(viewGroup)
    }

    override fun getContainerId() = 0

    override fun getUserInteractionEvents(): Observable<Any> = Observable.just(Any())
}


data class TestState(val test: String)