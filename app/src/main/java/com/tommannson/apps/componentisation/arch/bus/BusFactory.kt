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

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.tommannson.apps.componentisation.arch.RxAction
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * It implements a Factory pattern generating Rx Subjects based on Event Types.
 * It maintain a map of Rx Subjects, one per type per instance of EventBusFactory.
 *
 * @param owner is a LifecycleOwner used to auto disposeIfNeeded based on destroy observable
 */
class BusFactory : ViewModel() {

    companion object {

        private var bus: BusFactory? = null

        /**
         * Return the [EventBusFactory] associated to the [LifecycleOwner]. It there is no bus it will create one.
         * If the [LifecycleOwner] used is a fragment it use [Fragment#getViewLifecycleOwner()]
         */
        @JvmStatic
        @Synchronized
        fun get(): BusFactory {
            if (bus == null) {
                bus = BusFactory()
            }
            return bus as BusFactory;
        }

    }


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val bussSubject = PublishSubject.create<RxAction>().toSerialized()


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

    override fun onCleared() {
        super.onCleared()
        bussSubject.onComplete()
    }

}


