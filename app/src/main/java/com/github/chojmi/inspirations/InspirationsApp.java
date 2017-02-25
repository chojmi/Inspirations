package com.github.chojmi.inspirations;

import android.app.Application;

import com.github.chojmi.inspirations.data.source.DaggerGalleryRepositoryComponent;
import com.github.chojmi.inspirations.data.source.GalleryRepository;
import com.github.chojmi.inspirations.data.source.GalleryRepositoryComponent;
import com.github.chojmi.inspirations.data.source.remote.DaggerRemoteGalleryComponent;
import com.github.chojmi.inspirations.data.source.remote.RemoteGalleryComponent;

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
