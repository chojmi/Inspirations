package com.github.chojmi.inspirations.presentation;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.crashlytics.android.Crashlytics;
import com.github.chojmi.inspirations.presentation.di.DaggerAppComponent;
import com.github.chojmi.inspirations.presentation.main.Navigator;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class InspirationsApp extends Application implements HasActivityInjector {

    @Inject DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject Navigator navigator;
    private AndroidInjector<Activity> testAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Fabric.with(this, new Crashlytics());
        }
        DaggerAppComponent.builder().application(this).build().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        if (testAndroidInjector != null) {
            return testAndroidInjector;
        } else {
            return activityDispatchingAndroidInjector;
        }
    }

    public Navigator getNavigator() {
        return navigator;
    }

    @VisibleForTesting
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    @VisibleForTesting
    public void setActivityInjector(AndroidInjector<Activity> injector) {
        this.testAndroidInjector = injector;
    }
}
