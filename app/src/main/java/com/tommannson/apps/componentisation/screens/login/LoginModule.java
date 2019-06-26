package com.tommannson.apps.componentisation.screens.login;

import android.app.Activity;
import androidx.lifecycle.ViewModel;
import com.tommannson.apps.componentisation.di.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface LoginModule {

    @Binds
    Activity loginActivity(LoginActivity login);

    @Binds
    @IntoMap
    @ViewModelKey(LoginScreenModelHolder.class)
    abstract ViewModel loginController(LoginScreenModelHolder impl);

}
