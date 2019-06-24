package com.tommannson.apps.componentisation.components.events

import com.tommannson.apps.componentisation.arch.ComponentEvent

sealed class GithubIntaractionEvent : ComponentEvent() {

    object LoadRequest : GithubIntaractionEvent()
}