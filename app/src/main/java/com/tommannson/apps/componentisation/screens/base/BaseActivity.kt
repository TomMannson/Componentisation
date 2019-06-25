package com.tommannson.apps.componentisation.screens.base

import android.os.Bundle
import com.tommannson.apps.componentisation.arch.UINewHost
import dagger.android.support.DaggerAppCompatActivity

open class BaseActivity : DaggerAppCompatActivity() {

    lateinit var host: UINewHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        host = UINewHost.create(this)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        host.dispatchRendering()
    }

    override fun onPause() {
        super.onPause()
        host.disposeIfNeeded(this)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
