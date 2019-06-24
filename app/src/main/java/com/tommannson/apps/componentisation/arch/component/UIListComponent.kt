package com.tommannson.apps.componentisation.arch.component

import android.view.ViewGroup

abstract class UIListComponent<T, State>(
    container: ViewGroup,
    initialState: List<State>
) : UIComponent<T, List<State>>(container, initialState) {

    abstract fun onItemEvent(position: Int, event: T)


}