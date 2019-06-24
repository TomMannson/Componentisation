package com.tommannson.apps.componentisation.di

import com.tommannson.apps.componentisation.arch.BusFactory
import dagger.Provides

@dagger.Module
object AppModule {

    @Provides
    @JvmStatic
    fun getBus(): BusFactory {
        return BusFactory.get()
    }
}
