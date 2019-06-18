/*
 * Copyright (C) 2018 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by Juliano Moraes, Rohan Dhruva, Emmanuel Boudrant.
 */
package com.tommannson.apps.componentisation.arch

import android.view.ViewGroup
import androidx.annotation.IdRes
import com.netflix.arch.UINewComponent

abstract class UINewView<T, State>(val container: ViewGroup, private var lazyLoad: Boolean = false) : UIParent {

    @get:IdRes
    abstract val containerId: Int
    private lateinit var parent: UINewComponent<T, State>
    val list = mutableListOf<UINewComponent<*, *>>()

    abstract fun build();

    abstract fun render(localState: State);

    fun show() {
        lazyLoad = true;
        parent.buildView()
    }

    internal fun compose(uiComponent: UINewComponent<T, State>) {
        parent = uiComponent
    }

    internal fun buildView() {
        build();
        createChildren();
    }

    protected fun add(component: UINewComponent<*, *>) {
        list += component
    }

    internal fun createChildren() {
        if (!lazyLoad) {
            for (component in parent.list) {
                component.create(this);
            }
        }
    }

    internal fun renderChildren() {
        if (!lazyLoad) {
            for (component in parent.list) {
                component.render();
            }
        }
    }

    fun renderView(localState: State) {
        render(localState)
        renderChildren()
    }

    fun clearView() {
        for (component in parent.list) {
            component.dispose();
        }
    }

    override fun getFindViewGroup(id: Int): ViewGroup {
        return container.findViewById(id)
    }
}