package com.sawrose.daggerdemo.di.component

import android.app.Application
import com.sawrose.daggerdemo.DemoApp
import com.sawrose.daggerdemo.di.module.ActivitiesModule
import com.sawrose.daggerdemo.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, AppModule::class, ActivitiesModule::class))
interface AppComponent {

    fun inject(application:DemoApp)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application) : Builder
        fun build() : AppComponent
    }
}