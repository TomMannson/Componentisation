package com.tommannson.apps.componentisation.screens.login

import android.os.Bundle
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.UINewHost
import com.tommannson.apps.componentisation.components.login.LoginUIComponent
import com.tommannson.apps.componentisation.components.progress.ProgressUIComponent
import com.tommannson.apps.componentisation.model.pipe.LoginModelResolver
import com.tommannson.apps.componentisation.model.pipe.resolvers.LoginBoController
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class LoginActivity : DaggerAppCompatActivity() {

    val model = LoginModelResolver()
    lateinit var host: UINewHost

    @Inject
    internal lateinit var loginUICompoentFactory: LoginUIComponent.Factory
    @Inject
    internal lateinit var progressUICompoentFactory: ProgressUIComponent.Factory
    @Inject
    lateinit var controller: LoginBoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        controller.start()
        host = UINewHost.create(this)
            .composition({
                add(loginUICompoentFactory.create(findViewById(R.id.login_form)))
                add(progressUICompoentFactory.create(findViewById(R.id.progress_holder)))
            })

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
        model.clean()
    }
}




