package com.tommannson.apps.componentisation.arch

import android.view.ViewGroup
import com.tommannson.apps.componentisation.arch.BindingState.NEED_REBIND
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

abstract class UINewComponent<T, State>(
    val container: ViewGroup,
    initialState: State,
    lazyState: LazyState = LazyState.initialised()
) {

    private var localState = initialState
    val disposable = CompositeDisposable()
    var id = 0;
    val nestingManager = NestingComponentManager()
    val lazyInitialisation = LazyComponentManager(lazyState, this, nestingManager)

    abstract fun getContainerId(): Int
    abstract fun getUserInteractionEvents(): Observable<T>

    open fun create() {
        build()
        createChildren()
    }

    protected fun add(component: UINewComponent<*, *>) {
        nestingManager.add(component);
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

    open fun dispose() {
        nestingManager.clearChildren()
    }

    fun Observable<State>.track() {
        disposable.add(
            this.observeOn(AndroidSchedulers.mainThread())
                .subscribe { this@UINewComponent.render(it) }
        )
    }
}