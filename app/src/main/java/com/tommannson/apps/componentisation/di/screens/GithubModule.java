package com.tommannson.apps.componentisation.di.screens;

import android.app.Activity;
import com.tommannson.apps.componentisation.screens.login.LoginActivity;
import com.tommannson.apps.componentisation.screens.main.githubclient.GithubPreviewActivity;
import dagger.Binds;
import dagger.Module;

@Module
public interface GithubModule {

    @Binds
    Activity loginActivity(GithubPreviewActivity login);

}
