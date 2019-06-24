package com.tommannson.apps.componentisation.components.progress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.arch.component.UIComponent
import com.tommannson.apps.componentisation.arch.bindView


class ProgressUIComponent
@AssistedInject constructor(
    @Assisted private val containter: ViewGroup,
    private val bus: ScopedEventBusFactory
) :
    UIComponent<Any, ProgressState>(containter, ProgressState(false)) {

    val progress: View by bindView(R.id.progress_root)

    override fun build() {
        inflateIfNeed()
    }

    private fun inflateIfNeed() {
        if (containter.findViewById<View>(R.id.progress_root) == null) {
            LayoutInflater.from(containter.context).inflate(R.layout.component_progress, containter, true)
        }
    }

    override fun render(localState: ProgressState) {
        progress.visibility = if (localState.enabled) View.VISIBLE else View.GONE
    }

    init {
        bus.getSafeManagedObservableFiltered<ProgressState>()
            .track();
    }

    @AssistedInject.Factory
    internal interface Factory {
        fun create(containter: ViewGroup): ProgressUIComponent
    }
}

