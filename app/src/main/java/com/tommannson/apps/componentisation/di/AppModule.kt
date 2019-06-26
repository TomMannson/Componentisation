package com.tommannson.apps.componentisation.di

import androidx.lifecycle.ViewModelProvider
import com.tommannson.apps.componentisation.arch.bus.BusFactory
import com.tommannson.apps.componentisation.arch.viewmodel.ViewModelProviderImpl
import dagger.Provides

@dagger.Module
object AppModule {

    @Provides
    @JvmStatic
    fun getBus(): BusFactory {
        return BusFactory.get()
    }
}
