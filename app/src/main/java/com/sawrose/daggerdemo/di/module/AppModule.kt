package com.sawrose.daggerdemo.di.module


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = arrayOf(ViewModelModule::class))
class AppModule{

    @Singleton
    @Provides
    fun provideAuth():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideDatabase():FirebaseDatabase{
        val database = FirebaseDatabase.getInstance()
        database.setPersistenceEnabled(true)
        return database
    }

}