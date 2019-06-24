package com.tommannson.apps.componentisation.di

import com.tommannson.apps.componentisation.App
import com.tommannson.apps.componentisation.di.ActivityBinding
import com.tommannson.apps.componentisation.di.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [AndroidSupportInjectionModule::class, ActivityBinding::class, AppModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Builder : AndroidInjector.Factory<App>
}
