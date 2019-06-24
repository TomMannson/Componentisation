package com.tommannson.apps.componentisation.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory

import dagger.Provides

@dagger.Module
object GenericViewModule {

    @Provides
    @JvmStatic
    fun getBus(activity: Activity): ScopedEventBusFactory {
        return ScopedEventBusFactory.get(activity as AppCompatActivity)
    }
}
