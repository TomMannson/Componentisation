package com.tommannson.apps.componentisation.components.events

import com.tommannson.apps.componentisation.arch.ComponentEvent

/**
 * List of all events Views can emit
 */
sealed class UserInteractionEvent : ComponentEvent() {
    object IntentTapRetry : UserInteractionEvent()
}