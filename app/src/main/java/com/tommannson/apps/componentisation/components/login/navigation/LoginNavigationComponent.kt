package com.tommannson.apps.componentisation.components.login.navigation

import android.view.ViewGroup
import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import com.tommannson.apps.componentisation.arch.component.UIComponent
import com.tommannson.apps.componentisation.screens.main.githubclient.GithubPreviewActivity

class LoginNavigationComponent(
    container: ViewGroup,
    private val bus: ScopedEventBusFactory
) : UIComponent<LoginNavigationEvent, LoginNavigationState>(container, LoginNavigationState(false)) {

    init {
        bus.getSafeManagedObservableFiltered<LoginNavigationState>()
            .track()
    }

    override fun build() {

    }

    override fun render(localState: LoginNavigationState) {
        if (localState.needNavigation) {
            GithubPreviewActivity.start(container.context)
            bus.emit(LoginNavigationEvent::class.java, LoginNavigationEvent.NavigationPerformed)
        }
    }

    companion object {

        @Synchronized
        fun get(
            container: ViewGroup,
            bus: ScopedEventBusFactory
        ) = LoginNavigationComponent(container, bus)
    }
}
