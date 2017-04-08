package com.github.chojmi.inspirations.presentation;

import android.app.Application;

import com.github.chojmi.inspirations.presentation.gallery.GalleryComponent;
import com.github.chojmi.inspirations.presentation.gallery.grid.GalleryModule;

import timber.log.Timber;

public class InspirationsApp extends Application {

    private ApplicationComponent applicationComponent;
    private GalleryComponent galleryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    public GalleryComponent createGalleryComponent() {
        galleryComponent = applicationComponent.plus(new GalleryModule());
        return galleryComponent;
    }

    public void releasePGalleryComponent() {
        galleryComponent = null;
    }
}
