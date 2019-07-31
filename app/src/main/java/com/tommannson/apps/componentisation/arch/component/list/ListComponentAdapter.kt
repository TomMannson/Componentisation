package com.tommannson.apps.componentisation.arch.component.list

import androidx.recyclerview.widget.RecyclerView

abstract class ListComponentAdapter<T> : RecyclerView.Adapter<UIListItemComponent<*, out T>>() {

    var list: List<T> = listOf()

    override fun getItemCount(): Int = list.size
}
