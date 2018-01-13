package com.sawrose.daggerdemo.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.sawrose.daggerdemo.DemoApp
import com.sawrose.daggerdemo.di.component.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector


object AppInjector {

    fun init(application:DemoApp){
        DaggerAppComponent.builder()
                .application(application)
                .build()
                .inject(application)


        application
                .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks{
                    override fun onActivityPaused(activity: Activity) {
                    }

                    override fun onActivityResumed(activity: Activity) {
                    }

                    override fun onActivityStarted(activity: Activity) {
                    }

                    override fun onActivityDestroyed(activity: Activity) {
                    }

                    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle?) {

                    }

                    override fun onActivityStopped(activity: Activity) {
                    }

                    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                        handleActivity(activity)
                    }
                })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector){
            AndroidInjection.inject(activity)
        }

        if (activity is FragmentActivity){
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(
                            object : FragmentManager.FragmentLifecycleCallbacks(){
                                override fun onFragmentPreAttached(fm: FragmentManager?, f: Fragment?, context: Context?) {
                                    if (f is Injectable){
                                        AndroidSupportInjection.inject(f)
                                    }
                                    super.onFragmentPreAttached(fm, f, context)
                                }
                            },true
                    )
        }
    }
}