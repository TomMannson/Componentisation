package com.tommannson.apps.componentisation.screens.login

import android.os.Bundle
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.model.ModelHolder
import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import com.tommannson.apps.componentisation.components.login.form.LoginUIComponent
import com.tommannson.apps.componentisation.components.login.navigation.LoginNavigationComponent
import com.tommannson.apps.componentisation.components.progress.ProgressUIComponent
import com.tommannson.apps.componentisation.screens.base.BaseActivity
import javax.inject.Inject


class LoginActivity : BaseActivity() {

    @Inject
    lateinit var screenBus: ScopedEventBusFactory

    val model by viewmodel<ModelHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        model.init()

        host.composition({
            add(
                LoginUIComponent(
                    findViewById(R.id.login_form),
                    screenBus
                )
            )
            add(ProgressUIComponent(findViewById(R.id.progress_holder), screenBus))
            LoginNavigationComponent.get(findViewById(R.id.progress_holder), screenBus)
        })
    }
}




