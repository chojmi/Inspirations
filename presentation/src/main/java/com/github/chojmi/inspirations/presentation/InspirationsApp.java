package com.github.chojmi.inspirations.presentation;

import android.app.Activity;
import android.app.Application;

import com.github.chojmi.inspirations.presentation.di.DaggerAppComponent;
import com.github.chojmi.inspirations.presentation.main.Navigator;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class InspirationsApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    Navigator navigator;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        DaggerAppComponent.builder().application(this).build().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    public Navigator getNavigator() {
        return navigator;
    }
}
