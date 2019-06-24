package com.tommannson.apps.componentisation.di;

import com.tommannson.apps.componentisation.di.screens.GithubModule;
import com.tommannson.apps.componentisation.di.screens.LoginModule;
import com.tommannson.apps.componentisation.screens.login.LoginActivity;
import com.tommannson.apps.componentisation.screens.main.githubclient.GithubPreviewActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityBinding {

    @ContributesAndroidInjector(modules = {LoginModule.class, AssistModuleModule.class, GenericViewModule.class})
    public LoginActivity loginBinding();

    @ContributesAndroidInjector(modules = {GithubModule.class, AssistModuleModule.class, GenericViewModule.class})
    public GithubPreviewActivity githubPreviewActivity();
}
