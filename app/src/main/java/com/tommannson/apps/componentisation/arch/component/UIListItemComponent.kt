package com.tommannson.apps.componentisation.arch.component

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class UIListItemComponent<Event, State>(
    val parent: UIListComponent<Event, List<State>>,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    abstract fun render(state: State)

}