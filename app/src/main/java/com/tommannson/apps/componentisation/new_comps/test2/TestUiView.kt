package com.tommannson.apps.componentisation.new_comps.test2

import android.view.LayoutInflater
import android.view.ViewGroup

import com.jmoraes.componentizationsample.new_comps.test1.TestState
import com.jmoraes.componentizationsample.new_comps.test1.TestUiComponent
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.UINewView

class Test2UiView(val parent: ViewGroup) : UINewView<Any, Any>(parent) {

    lateinit var wraper: ViewGroup

    override fun build() {
        wraper = LayoutInflater.from(parent.context).inflate(R.layout.component_test2, parent, false) as ViewGroup
        parent.addView(wraper)
        add(TestUiComponent(1, TestState("aaaaaaaaaaaaaaaa")))
    }

    override fun render(localState: Any) {
    }

    override val containerId: Int
        get() = 0


}