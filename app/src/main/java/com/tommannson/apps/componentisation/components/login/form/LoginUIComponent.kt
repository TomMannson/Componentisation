package com.tommannson.apps.componentisation.components.login.form

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.bindView
import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import com.tommannson.apps.componentisation.arch.component.UIComponent


class LoginUIComponent
@AssistedInject constructor(
    @Assisted private var containter: ViewGroup,
    private val bus: ScopedEventBusFactory,
    @Assisted state: LoginState = LoginState(
        "",
        ""
    )
) :
    UIComponent<LoginFormEvent, LoginState>(containter, state) {

    val etLogin: EditText by bindView(R.id.et_login)
    val etPassword: EditText by bindView(R.id.et_password)
    val btnSubmit: Button by bindView(R.id.btn_submit)

    init {
        bus.getSafeManagedObservableFiltered<LoginState>()
            .track();
    }

    override fun build() {
        inflateIfNeed()
        btnSubmit.setOnClickListener {
            val loginText = etLogin.text.toString();
            val passwordText = etPassword.text.toString()
            bus.emit(
                LoginFormEvent::class.java,
                LoginFormEvent.SubmitLogin(
                    loginText,
                    passwordText
                )
            )
        }
    }

    private fun inflateIfNeed() {
        if (containter.findViewById<View>(R.id.lbl_login) == null) {
            LayoutInflater.from(containter.context).inflate(R.layout.component_login, containter, true)
        }
    }

    override fun render(localState: LoginState) {

        etLogin.setError(if (localState.error) "invalid" else null)
        etPassword.setError(if (localState.error) "invalid" else null)
        etLogin.isEnabled = !localState.progress
        etPassword.isEnabled = !localState.progress
        btnSubmit.isEnabled = !localState.progress
    }

    @AssistedInject.Factory
    internal interface Factory {
        fun create(containter: ViewGroup, state: LoginState = LoginState(
            "",
            ""
        )
        ): LoginUIComponent
    }
}

