package com.tommannson.apps.componentisation

import com.facebook.stetho.Stetho
import com.tommannson.apps.componentisation.di.AppComponent
import com.tommannson.apps.componentisation.di.DaggerAppComponent
import com.tommannson.apps.componentisation.model.usecase.login.LoginUsecase
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {

    val component by lazy { DaggerAppComponent.factory().create(this) as AppComponent }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = component

    @Inject
    lateinit var loginUsecase: LoginUsecase

    override fun onCreate() {
        super.onCreate()
        loginUsecase.start();
        Stetho.initializeWithDefaults(this);
    }

}
