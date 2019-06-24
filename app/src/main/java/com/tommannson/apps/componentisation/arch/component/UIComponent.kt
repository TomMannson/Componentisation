package com.tommannson.apps.componentisation.arch.component

import android.view.ViewGroup
import com.tommannson.apps.componentisation.arch.LazyComponentManager
import com.tommannson.apps.componentisation.arch.LazyState
import com.tommannson.apps.componentisation.arch.NestingComponentManager
import com.tommannson.apps.componentisation.arch.identity.IdGenerator
import com.tommannson.apps.componentisation.arch.plusAssign
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

abstract class UIComponent<T, State>(
    val container: ViewGroup,
    initialState: State,
    lazyState: LazyState = LazyState.initialised()
) {

    internal var localState = initialState
    internal var disposable = CompositeDisposable()
    internal var id = 0;
    internal var nestingManager = NestingComponentManager()
    internal var lazyInitialisation =
        LazyComponentManager(lazyState, this, nestingManager)

    open fun create() {
        build()
        createChildren()
    }

    protected fun add(component: UIComponent<*, *>) {
        nestingManager.add(IdGenerator.getNextNumberId(), component);
    }

    private fun createChildren() {
        nestingManager.createChildren()
    }

    fun commitState() {
        render(localState)
        nestingManager.renderChildren()
    }

    fun show() {
        lazyInitialisation.initialize();
        lazyInitialisation.performLazyLoadingIfNeed()
    }

    abstract fun build()

    abstract fun render(localState: State)

    fun getContainerId() = 0
    fun getUserInteractionEvents() = Observable.empty<T>()

    open fun dispose() {
        nestingManager.clearChildren()
    }

    fun Observable<State>.track() {
        disposable += this.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                this@UIComponent.render(it)
                localState = it
            }

    }
}