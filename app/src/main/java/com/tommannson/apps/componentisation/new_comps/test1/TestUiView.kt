package com.tommannson.apps.componentisation.new_comps.test1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.jmoraes.componentizationsample.new_comps.test1.TestState
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.UINewView

class TestUiView(val parent: ViewGroup) : UINewView<Any, TestState>(parent) {

    lateinit var text: TextView

    override fun build() {
        text = LayoutInflater.from(parent.context).inflate(R.layout.component_test, parent, false) as TextView
        parent.addView(text)
    }

    override fun render(localState: TestState) {
        text.text = localState.test
    }

    override val containerId: Int
        get() = 0


}