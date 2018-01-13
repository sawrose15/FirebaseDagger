package com.sawrose.daggerdemo.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.sawrose.daggerdemo.di.Injectable
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by sawrose on 1/13/18.
 */
open class BaseFragment:Fragment() {

    protected var disposable = CompositeDisposable()

    override fun onPause() {
        disposable.clear()
        disposable.dispose()
        super.onPause()
    }
}