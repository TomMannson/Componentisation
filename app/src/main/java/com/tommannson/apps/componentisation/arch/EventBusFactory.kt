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
package com.tommannson.apps.componentisation.arch

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * It implements a Factory pattern generating Rx Subjects based on Event Types.
 * It maintain a map of Rx Subjects, one per type per instance of EventBusFactory.
 *
 * @param owner is a LifecycleOwner used to auto disposeIfNeeded based on destroy observable
 */
class EventBusFactory private constructor(val owner: LifecycleOwner) {

    companion object {

        private val buses = mutableMapOf<LifecycleOwner, EventBusFactory>()

        /**
         * Return the [EventBusFactory] associated to the [LifecycleOwner]. It there is no bus it will create one.
         * If the [LifecycleOwner] used is a fragment it use [Fragment#getViewLifecycleOwner()]
         */
        @JvmStatic
        fun get(lifecycleOwner: LifecycleOwner): EventBusFactory {
            return with(lifecycleOwner) {
                var bus = buses[lifecycleOwner]
                if (bus == null) {
                    bus = EventBusFactory(lifecycleOwner)
                    buses[lifecycleOwner] = bus
                    // LifecycleOwner
                    lifecycleOwner.lifecycle.addObserver(bus.observer)
                }
                bus
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val bussSubject = PublishSubject.create<RxAction>().toSerialized()

    internal val observer = object : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            bussSubject.onComplete()
            buses.remove(owner)
        }
    }

    /**
     * emit will create (if needed) or use the existing Rx Subject to send events.
     *
     * @param clazz is the Event Class
     * @param event is the instance of the Event to be sent
     */
    fun <T : RxAction> emit(event: T) {
        bussSubject.onNext(event)
    }

    fun <T : RxAction> emit(clazz: Class<*>, event: T) {
        bussSubject.onNext(event)
    }

    /**x
     * getSafeManagedObservable returns an Rx Observable which is
     *  *Safe* against reentrant events as it is serialized and
     *  *Managed* since it disposes itself based on the lifecycle
     *
     *  @param clazz is the class of the event type used by this observable
     */
    fun getSafeManagedObservable(): Observable<RxAction> = bussSubject

    inline fun <reified T : RxAction> getSafeManagedObservableFiltered() =
            bussSubject.filter { it is T }
                    .cast(T::class.java)


    /**
     * getDestroyObservable observes to Lifecycle owner and fires when
     * lifecycle.currentState == Lifecycle.State.DESTROYED
     */
    fun getDestroyObservable(): Observable<Unit> {
        return owner.createDestroyObservable()
    }
}

/**
 * Extension on [LifecycleOwner] used to emit an event.
 */
inline fun <reified T : RxAction> LifecycleOwner.emit(event: T) =
        with(EventBusFactory.get(this)) {
            getSafeManagedObservable()
            emit(event)
        }

/**
 * Extension on [LifecycleOwner] used used to get the state observable.
 */
inline fun LifecycleOwner.getSafeManagedObservable(): Observable<RxAction> =
        EventBusFactory.get(this).getSafeManagedObservable()

/**
 * This method returns a destroy observable that can be passed to [com.netflix.arch.UIPresenter]s and
 * [com.netflix.arch.UIView]s as needed. This is deliberately scoped to the attached
 * [LifecycleOwner]'s [Lifecycle.Event.ON_DESTROY] because a viewholder can be reused across
 * adapter destroys.
 */
inline fun LifecycleOwner?.createDestroyObservable(): Observable<Unit> {
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

