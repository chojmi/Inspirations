package com.github.chojmi.inspirations.presentation;

import android.app.Application;

import timber.log.Timber;

public class InspirationsApp extends Application {

    private GalleryRepositoryComponent galleryRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        galleryRepositoryComponent = DaggerGalleryRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }

    public GalleryRepositoryComponent getGalleryRepositoryComponent() {
        return galleryRepositoryComponent;
    }
}
