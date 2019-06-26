package com.tommannson.apps.componentisation.screens.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tommannson.apps.componentisation.arch.UINewHost
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class BaseActivity : DaggerAppCompatActivity() {

    lateinit var host: UINewHost

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        host = UINewHost.create(this)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        host.dispatchRendering()
    }

    override fun onDestroy() {
        super.onDestroy()
        host.dispose()
    }

    inline fun <reified T : ViewModel> viewmodel(): ReadOnlyProperty<AppCompatActivity, T> {
        return VmLoader(T::class.java)
    }

    inner class VmLoader<T : ViewModel>(val target: Class<T>) : ReadOnlyProperty<AppCompatActivity, T> {

        override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
            return ViewModelProviders.of(thisRef, vmFactory).get(target)
        }
    }
}
