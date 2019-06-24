package com.tommannson.apps.componentisation.di.screens;

import android.app.Activity;


import com.tommannson.apps.componentisation.screens.login.LoginActivity;
import dagger.Binds;
import dagger.Module;

@Module
public interface LoginModule {

    @Binds
    Activity loginActivity(LoginActivity login);

}
