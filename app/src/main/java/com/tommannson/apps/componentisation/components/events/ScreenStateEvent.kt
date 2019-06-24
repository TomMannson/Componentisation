package com.tommannson.apps.componentisation.components.events

import com.tommannson.apps.componentisation.arch.ComponentEvent


/**
 * List of all events this Screen can emit
 */
sealed class ScreenStateEvent : ComponentEvent() {
    object Loading : ScreenStateEvent()
    object Loaded : ScreenStateEvent()
    object Error : ScreenStateEvent()
}