/*
 * Copyright (C) 2018 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by Juliano Moraes, Rohan Dhruva, Emmanuel Boudrant.
 */
package com.tommannson.apps.componentisation.arch.bus

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.tommannson.apps.componentisation.arch.EventBusFactory
import com.tommannson.apps.componentisation.arch.RxAction
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * It implements a Factory pattern generating Rx Subjects based on Event Types.
 * It maintain a map of Rx Subjects, one per type per instance of EventBusFactory.
 *
 * @param owner is a LifecycleOwner used to auto disposeIfNeeded based on destroy observable
 */
class ScopedEventBusFactory : ViewModel() {

    companion object {

        /**
         * Return the [EventBusFactory] associated to the [LifecycleOwner]. It there is no bus it will create one.
         * If the [LifecycleOwner] used is a fragment it use [Fragment#getViewLifecycleOwner()]
         */
        @JvmStatic
        fun get(lifecycleOwner: AppCompatActivity): ScopedEventBusFactory {
            return with(lifecycleOwner) {
                ViewModelProviders.of(this).get(ScopedEventBusFactory::class.java)
            }
        }
    }


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val map = HashMap<Class<*>, Subject<*>>()
    val mapPipe = HashMap<Class<*>, Disposable>()


    override fun onCleared() {
        super.onCleared()
        for ((key, bus) in map) {
            mapPipe[key]?.dispose()
            bus.onComplete()
        }
    }


    private fun <T> create(clazz: Class<T>): Subject<T> {
        val subject = PublishSubject.create<T>().toSerialized()
        map[clazz] = subject;
        mapPipe[clazz] = subject
            .map {
                Log.d("BUS_LOG", it.toString())
                return@map it
            }
            .subscribe()
        return subject
    }


    inline fun <reified T : RxAction> emit(event: T) {
        emit(T::class.java, event)
    }

    /**
     * emit will create (if needed) or use the existing Rx Subject to send events.
     *
     * @param clazz is the Event Class
     * @param event is the instance of the Event to be sent
     */
    fun <T : RxAction> emit(clazz: Class<T>, event: T) {
        val subject = if (map[clazz] != null) map[clazz] else create(clazz)
        (subject as Subject<T>).onNext(event)
    }

    /**x
     * getSafeManagedObservable returns an Rx Observable which is
     *  *Safe* against reentrant events as it is serialized and
     *  *Managed* since it disposes itself based on the lifecycle
     *
     *  @param clazz is the class of the event type used by this observable
     */
    fun <T : RxAction> getSafeManagedObservable(clazz: Class<T>): Observable<T> {
        return if (map[clazz] != null) map[clazz] as Observable<T> else create(clazz)
    }

    inline fun <reified T : RxAction> getSafeManagedObservableFiltered(): Observable<T> {
        return getSafeManagedObservable(T::class.java)
    }

}


/**
 * This method returns a destroy observable that can be passed to [com.netflix.arch.UIPresenter]s and
 * [com.netflix.arch.UIView]s as needed. This is deliberately scoped to the attached
 * [LifecycleOwner]'s [Lifecycle.Event.ON_DESTROY] because a viewholder can be reused across
 * adapter destroys.
 */
inline fun AppCompatActivity?.createDestroyObservable(): Observable<Unit> {
    return Observable.create { emitter ->
        if (this == null || this.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            emitter.onNext(Unit)
            emitter.onComplete()
            return@create
        }
        this.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun emitDestroy() {
                if (emitter.isDisposed) {
                    emitter.onNext(Unit)
                    emitter.onComplete()
                }
            }
        })
    }
}


