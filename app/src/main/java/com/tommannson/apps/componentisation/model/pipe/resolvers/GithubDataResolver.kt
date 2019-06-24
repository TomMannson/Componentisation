package com.tommannson.apps.componentisation.model.pipe.resolvers

import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.components.events.GithubIntaractionEvent
import com.tommannson.apps.componentisation.components.github_components.search_list.GithubEvents
import com.tommannson.apps.componentisation.model.pipe.BaseResolver
import com.tommannson.apps.componentisation.model.ws.MainInteractorImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class GithubDataResolver @Inject constructor() : BaseResolver<GithubIntaractionEvent, GithubIntaractionEvent>() {

    override fun getInternalBus() = appScoped.getSafeManagedObservableFiltered<GithubIntaractionEvent>()
    override fun getExternalBus() = appScoped.getSafeManagedObservableFiltered<GithubIntaractionEvent>()

    @Inject
    lateinit var screenScoped: ScopedEventBusFactory
    @Inject
    lateinit var appScoped: ScopedEventBusFactory
    val impl = MainInteractorImpl()

    override fun resolveIn(event: GithubIntaractionEvent) {
        when (event) {
            is GithubIntaractionEvent.LoadRequest -> {
                impl.fromWebservice()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        it?.body()?.let {
                            appScoped.emit(GithubEvents::class.java, GithubEvents.LoadRequestSuccess(it))
                        }
                    }
            }
        }
    }

    override fun resolveExternalIn(event: GithubIntaractionEvent) {
    }


}