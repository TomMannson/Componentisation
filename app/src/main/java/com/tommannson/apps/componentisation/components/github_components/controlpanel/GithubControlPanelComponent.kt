package com.jmoraes.componentizationsample.components.github_components.controlpanel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.ComponentEvent
import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.arch.bindView
import com.tommannson.apps.componentisation.arch.component.UIComponent
import com.tommannson.apps.componentisation.components.events.GithubIntaractionEvent
import com.tommannson.apps.componentisation.components.github_components.GithubListState
import com.tommannson.apps.componentisation.components.github_components.search_list.GithubEvents

@SuppressLint("CheckResult")
open class GithubControlPanelComponent(container: ViewGroup, private val bus: ScopedEventBusFactory) :
    UIComponent<PlayerUserInteractionEvents, GithubListState>(container, GithubListState(null, false)) {

    private val clearButton: Button by bindView(R.id.button_clear)
    private val fetchButton: Button by bindView(R.id.button_fetch)

    override fun build() {
        LayoutInflater.from(container.context).inflate(R.layout.controll_panel, container, true)

        clearButton.setOnClickListener {
            bus.emit(GithubEvents.ClearList)
        }

        fetchButton.setOnClickListener {
            bus.emit(GithubIntaractionEvent.LoadRequest)
        }
    }

    override fun render(localState: GithubListState) {
    }
}

sealed class PlayerUserInteractionEvents : ComponentEvent() {
    object IntentPlayPauseClicked : PlayerUserInteractionEvents()
    object IntentRwClicked : PlayerUserInteractionEvents()
    object IntentFwClicked : PlayerUserInteractionEvents()
}