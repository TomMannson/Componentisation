package com.tommannson.apps.componentisation.components.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.arch.UINewComponent
import com.tommannson.apps.componentisation.arch.bindView
import io.reactivex.Observable

class LoginUIComponent(private val containter: ViewGroup, private val bus: ScopedEventBusFactory, state: LoginState) :
    UINewComponent<LoginFormEvent, LoginState>(containter, state) {

    val etLogin: EditText by bindView(R.id.et_login)
    val etPassword: EditText by bindView(R.id.et_password)
    val btnSubmit: Button by bindView(R.id.btn_submit)

    override fun build() {
        inflateIfNeed()

        btnSubmit.setOnClickListener {
            val loginText = etLogin.text.toString();
            val passwordText = etPassword.text.toString()
            bus.emit(LoginFormEvent::class.java, LoginFormEvent.SubmitLogin(loginText, passwordText))
        }
    }

    private fun inflateIfNeed() {
        if (containter.findViewById<View>(R.id.lbl_login) == null) {
            LayoutInflater.from(containter.context).inflate(R.layout.component_login, containter, true)
        }
    }

    override fun render(localState: LoginState) {
        if (localState.error) {
            etLogin.setError("invalid")
            etPassword.setError("invalid")
        } else if (localState.progress) {
            etLogin.isEnabled = false
            etPassword.isEnabled = false
            btnSubmit.isEnabled = false
        }
    }

    init {
        bus.getSafeManagedObservableFiltered<LoginState>()
            .track();
    }

    override fun getContainerId() = 0;

    override fun getUserInteractionEvents(): Observable<LoginFormEvent> = Observable.empty()
}

