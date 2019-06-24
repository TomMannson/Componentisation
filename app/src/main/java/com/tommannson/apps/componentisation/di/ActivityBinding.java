package com.tommannson.apps.componentisation.di;

import com.tommannson.apps.componentisation.di.login.LoginModule;
import com.tommannson.apps.componentisation.screens.login.LoginActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityBinding {

    @ContributesAndroidInjector(modules = {LoginModule.class, AssistModuleModule.class, GenericViewModule.class})
    public LoginActivity loginBinding();
}
