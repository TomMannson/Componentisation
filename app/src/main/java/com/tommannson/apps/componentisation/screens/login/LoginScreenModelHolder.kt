package com.tommannson.apps.componentisation.screens.login

import com.tommannson.apps.componentisation.arch.model.ModelHolder
import com.tommannson.apps.componentisation.components.login.form.LoginFormController
import com.tommannson.apps.componentisation.components.login.navigation.LoginNavigationController
import com.tommannson.apps.componentisation.components.progress.ProgressController
import com.tommannson.apps.componentisation.model.pipe.BaseResolver
import javax.inject.Inject

class LoginScreenModelHolder @Inject constructor(
    val loginCtrl: LoginFormController,
    val progressController: ProgressController,
val loginNavigationController: LoginNavigationController
) : ModelHolder() {


    override fun getList(): List<BaseResolver<*, *>> = listOf(progressController,
        loginCtrl,
        loginNavigationController)
}
