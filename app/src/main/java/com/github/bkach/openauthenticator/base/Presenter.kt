package com.github.bkach.openauthenticator.base

import io.reactivex.disposables.CompositeDisposable
import android.support.annotation.CallSuper
import android.support.annotation.NonNull
import io.reactivex.disposables.Disposable

/**
 * {Description}
 *
 * @author boris@ustwo.com
 */
abstract class Presenter<T> {

    private val attachedDisposables = CompositeDisposable()
    var view : T? = null

    @CallSuper open fun onAttach(view : T) {
        this.view = view
    }

    @CallSuper fun onDetach() {
        if (!isViewAttached()) {
            throw IllegalStateException("View is already detached")
        }
        view = null

        attachedDisposables.clear()
    }

    @CallSuper protected fun disposeOnViewDetach(@NonNull disposable: Disposable) {
        attachedDisposables.add(disposable)
    }


    fun isViewAttached(): Boolean {
        return view != null
    }

}