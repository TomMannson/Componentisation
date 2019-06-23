package com.tommannson.apps.componentisation.screens.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.arch.UINewHost
import com.tommannson.apps.componentisation.components.login.LoginState
import com.tommannson.apps.componentisation.components.login.LoginUIComponent
import com.tommannson.apps.componentisation.model.pipe.LoginModelResolver


class LoginActivity : AppCompatActivity() {

    val model = LoginModelResolver()
    lateinit var host: UINewHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        model.subscribeWithBus(ScopedEventBusFactory.get(this))
        host = UINewHost.create(this)
            .composition({
                add(
                    LoginUIComponent(
                        findViewById(R.id.login_form),
                        ScopedEventBusFactory.get(this@LoginActivity),
                        LoginState("test", "")
                    )
                )
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




