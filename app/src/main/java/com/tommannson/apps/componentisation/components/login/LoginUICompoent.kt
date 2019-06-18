package com.tommannson.apps.componentisation.components.login

import android.view.ViewGroup
import com.jmoraes.componentizationsample.components.login.LoginModel
import com.jmoraes.componentizationsample.components.login.LoginUIView
import com.netflix.arch.*
import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.arch.UINewView
import com.tommannson.apps.componentisation.arch.UIParent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class LoginUICompoent(containerId: Int, private val bus: ScopedEventBusFactory, state: LoginState) : UINewComponent<LoginFormEvent, LoginState>(containerId, state) {

    lateinit var uiView: LoginUIView


    override fun createView(component: ViewGroup): UINewView<LoginFormEvent, LoginState> = LoginUIView(component, bus)

    override fun create(uiParent: UIParent) {
        super.create(uiParent)
        uiView = view as LoginUIView
    }

    init {
        bus.getSafeManagedObservableFiltered<LoginModel.State>()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when (it) {
                        is LoginModel.State -> {
                            uiView.navigate(it);
                        }
                    }
                }
                .track()
    }

    override fun getContainerId() = 0;

    override fun getUserInteractionEvents(): Observable<LoginFormEvent> = Observable.empty()
}

sealed class LoginFormEvent : ComponentEvent() {
    class SubmitLogin(val login: String, val password: String) : LoginFormEvent()
    class LoginDataInvalid(val login: String, val password: String) : LoginFormEvent()
    object LoginSuccess : LoginFormEvent()
}

data class LoginState(val login: String, val password: String)
