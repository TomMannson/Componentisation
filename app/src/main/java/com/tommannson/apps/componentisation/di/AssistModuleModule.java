package com.tommannson.apps.componentisation.di;

import com.squareup.inject.assisted.dagger2.AssistedModule;

import dagger.Module;

@AssistedModule
@Module(includes = AssistedInject_AssistModuleModule.class)
abstract class AssistModuleModule {}
