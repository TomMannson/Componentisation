package com.jmoraes.componentizationsample.di

import com.jmoraes.componentizationsample.App
import com.tommannson.apps.componentisation.di.ActivityBinding
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [AndroidSupportInjectionModule::class, ActivityBinding::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Builder : AndroidInjector.Factory<App>
}
