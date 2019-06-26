package com.tommannson.apps.componentisation.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import com.tommannson.apps.componentisation.arch.viewmodel.ViewModelProviderImpl

import dagger.Provides

@dagger.Module
object GenericViewModule {

    @Provides
    @JvmStatic
    fun getBus(activity: Activity): ScopedEventBusFactory {
        return ScopedEventBusFactory.get(activity as AppCompatActivity)
    }


    @Provides
    @JvmStatic
    fun viewModelProvider(impl: ViewModelProviderImpl): ViewModelProvider.Factory {
        return impl
    }
}
