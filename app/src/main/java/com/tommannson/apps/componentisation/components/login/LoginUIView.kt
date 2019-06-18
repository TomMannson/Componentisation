package com.jmoraes.componentizationsample.components.login

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.arch.UINewView
import com.tommannson.apps.componentisation.components.login.LoginFormEvent
import com.tommannson.apps.componentisation.components.login.LoginState

class LoginUIView(private val containter: ViewGroup, private val bus: ScopedEventBusFactory) : UINewView<LoginFormEvent, LoginState>(containter) {

    lateinit var view: View
    lateinit var etLogin: EditText
    lateinit var etPassword: EditText
    lateinit var btnSubmit: Button

    override fun build() {
        if (containter.childCount == 0) {
            view = LayoutInflater.from(containter.context).inflate(R.layout.component_login, containter, true)
        } else {
            view = containter.getChildAt(0)
        }
        etLogin = view.findViewById(R.id.et_login)
        etPassword = view.findViewById(R.id.et_password)
        btnSubmit = view.findViewById(R.id.btn_submit)

        btnSubmit.setOnClickListener {
            val loginText = etLogin.text.toString();
            val passwordText = etPassword.text.toString()
            bus.emit(LoginFormEvent::class.java, LoginFormEvent.SubmitLogin(loginText, passwordText))
        }
    }

    override fun render(localState: LoginState) {
        etLogin.setText(localState.login)
        etPassword.setText(localState.password)
    }

    override val containerId: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    fun navigate(it: Any) {
        val activity = view.context as Activity
//        activity.startActivity(Intent(activity, GithubPreviewActivity::class.java))
    }
}
