package com.tommannson.apps.componentisation.arch.component

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.tommannson.apps.componentisation.arch.LazyComponentManager
import com.tommannson.apps.componentisation.arch.LazyState
import com.tommannson.apps.componentisation.arch.NestingComponentManager
import com.tommannson.apps.componentisation.arch.identity.ComponentId
import com.tommannson.apps.componentisation.arch.identity.IdGenerator
import com.tommannson.apps.componentisation.arch.lifecycle.RxLifecycleCreator
import com.tommannson.apps.componentisation.arch.plusAssign
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

abstract class UIComponent<T, State>(
    val container: ViewGroup,
    initialState: State,
    lazyState: LazyState = LazyState.initialised(),
    val owner: LifecycleOwner? = container.context as LifecycleOwner?
) {

    val idGenerator = IdGenerator.get()
    var localState = initialState
    internal var disposable = CompositeDisposable()
    internal var id = 0;
    internal var nestingManager = NestingComponentManager()
    internal var lazyInitialisation =
        LazyComponentManager(lazyState, this, nestingManager)

    open fun create() {
        build()
        createChildren()
    }

    protected fun add(component: UIComponent<*, *>, id: ComponentId = idGenerator.getNextNumberId()) {
        nestingManager.add(id, component);
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

    open fun getContainerId() = 0
    open fun filterState() = true

    open fun dispose() {
        disposable.dispose()
        nestingManager.clearChildren()
    }

    fun Observable<State>.track() {
        disposable += this.observeOn(AndroidSchedulers.mainThread())
            .filter { filterState() }
            .compose(if (owner != null) RxLifecycleCreator.LifeCycleComposer<State>(owner) else RxLifecycleCreator.EmptyComposer())
            .subscribe {
                if (it !== localState) {
                    this@UIComponent.render(it)
                    localState = it
                }
            }

    }
}