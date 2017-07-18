package com.github.bkach.openauthenticator.base

import android.app.Activity
import android.os.Bundle
import android.support.annotation.NonNull
import com.crashlytics.android.BuildConfig
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import com.crashlytics.android.core.CrashlyticsCore

/**
 * Base Activity, all activities should subclass this Activity
 *
 * @author boris@ustwo.com
 */
abstract class BaseActivity<T> : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())

        setupFabric()

        getPresenter().onAttach(getPresenterView())
    }

    fun setupFabric() {
        // Fabric Crashlytics, Disable debug
        val crashlyticsKit = Crashlytics.Builder()
                .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build()
        Fabric.with(this, crashlyticsKit)
    }

    override fun onDestroy() {
        getPresenter().onDetach()
        super.onDestroy()
    }

    abstract fun getLayoutResourceId(): Int

    @NonNull protected abstract fun getPresenter(): Presenter<T>

    @NonNull protected abstract fun getPresenterView(): T
}