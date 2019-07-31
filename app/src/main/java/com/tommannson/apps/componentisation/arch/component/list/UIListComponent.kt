package com.tommannson.apps.componentisation.arch.component.list

import android.view.ViewGroup
import com.tommannson.apps.componentisation.arch.component.UIComponent

abstract class UIListComponent<T, State>(
    container: ViewGroup,
    initialState: List<State>
) : UIComponent<T, List<State>>(container, initialState) {

    abstract fun onItemEvent(position: Int, event: T)
}