package com.tommannson.apps.componentisation

import com.facebook.stetho.Stetho
import com.tommannson.apps.componentisation.di.AppComponent
import com.jmoraes.componentizationsample.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    val component by lazy { DaggerAppComponent.factory().create(this) as AppComponent }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = component

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
    }

}
