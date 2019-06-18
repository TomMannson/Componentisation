package com.tommannson.apps.componentisation.model.pipe

import com.netflix.arch.ComponentEvent
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

abstract class BaseResolver<T : ComponentEvent> : ObservableTransformer<T, T> {

    abstract fun resolve(event: T);

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.filter { validType(it) }
            .map {
                resolve(it);
                return@map it
            }
    }

    abstract fun validType(input: Any): Boolean

    inline fun <reified T> type(input: Any) = input is T

}
