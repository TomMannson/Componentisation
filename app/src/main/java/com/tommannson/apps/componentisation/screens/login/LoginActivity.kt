package com.tommannson.apps.componentisation.screens.login

import android.os.Bundle
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import com.tommannson.apps.componentisation.components.login.LoginFormController
import com.tommannson.apps.componentisation.components.login.LoginUIComponent
import com.tommannson.apps.componentisation.components.progress.ProgressController
import com.tommannson.apps.componentisation.components.progress.ProgressUIComponent
import com.tommannson.apps.componentisation.screens.base.BaseActivity
import javax.inject.Inject


class LoginActivity : BaseActivity() {

    @Inject
    lateinit var screenBus: ScopedEventBusFactory
    @Inject
    lateinit var progressController: ProgressController
    @Inject
    lateinit var loginController: LoginFormController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginController.start()
        progressController.start()

        host.composition({
            add(LoginUIComponent(findViewById(R.id.login_form), screenBus))
            add(ProgressUIComponent(findViewById(R.id.progress_holder), screenBus))
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        loginController.clean()
        progressController.clean()

    }
}




