package com.netflix.arch

import android.view.ViewGroup
import com.tommannson.apps.componentisation.arch.UINewView
import com.tommannson.apps.componentisation.arch.UIParent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class UINewComponent<T, State>(protected val parrentId: Int, initialState: State) {

    lateinit protected var view: UINewView<T, State>
    val list = mutableListOf<UINewComponent<*, *>>()
    val disposable = CompositeDisposable()
    private var localState = initialState
    var id = 0;

    abstract fun createView(container: ViewGroup): UINewView<T, State>;
    abstract fun getContainerId(): Int
    abstract fun getUserInteractionEvents(): Observable<T>


    open fun create(uiParent: UIParent) {
        view = createView(uiParent.getFindViewGroup(parrentId))
        view.compose(this)
        buildView()
    }

    internal fun buildView() {
        view.buildView()
    }

    open fun dispose() {
        view.clearView();
    }

    fun render() {
        view.renderView(localState)
    }

    fun Disposable.track() {
        disposable.add(this)
    }
}