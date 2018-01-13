package com.sawrose.daggerdemo.di.module

import com.sawrose.daggerdemo.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    internal abstract fun provideLoginActivity(): LoginActivity

}