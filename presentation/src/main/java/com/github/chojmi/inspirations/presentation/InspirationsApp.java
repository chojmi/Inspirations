package com.github.chojmi.inspirations.presentation;

import android.app.Application;

import com.github.chojmi.inspirations.presentation.gallery.GalleryComponent;
import com.github.chojmi.inspirations.presentation.gallery.grid.GridModule;
import com.github.chojmi.inspirations.presentation.gallery.photo.PhotoViewModule;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

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
        return createGalleryComponent(null);
    }

    public GalleryComponent createGalleryComponent(Photo photo) {
        galleryComponent = applicationComponent.plus(new GridModule(), new PhotoViewModule(photo));
        return galleryComponent;
    }

    public void releaseGalleryComponent() {
        galleryComponent = null;
    }
}
