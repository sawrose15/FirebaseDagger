package com.sawrose.daggerdemo.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sawrose.daggerdemo.di.ViewModelFactory
import com.sawrose.daggerdemo.di.module.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule{

//    @Binds
//    @IntoMap
//    @ViewModelKey(ICOViewModel::class)
//    internal abstract fun bindUserViewModel(icoViewModel: ICOViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
