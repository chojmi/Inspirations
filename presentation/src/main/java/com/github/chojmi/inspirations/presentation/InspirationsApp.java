package com.github.chojmi.inspirations.presentation;

import android.app.Application;

import com.github.chojmi.inspirations.data.source.remote.module.RestClientModule;
import com.github.chojmi.inspirations.presentation.gallery.grid.GridComponent;
import com.github.chojmi.inspirations.presentation.gallery.grid.GridModule;
import com.github.chojmi.inspirations.presentation.gallery.photo.PhotoViewComponent;
import com.github.chojmi.inspirations.presentation.gallery.photo.PhotoViewModule;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

import timber.log.Timber;

public class InspirationsApp extends Application {

    private ApplicationComponent applicationComponent;
    private GridComponent gridComponent;
    private PhotoViewComponent photoViewComponent;

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
                .restClientModule(new RestClientModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    public GridComponent createGridComponent() {
        gridComponent = applicationComponent.plus(new GridModule());
        return gridComponent;
    }

    public void releaseGridComponent() {
        gridComponent = null;
    }

    public PhotoViewComponent createPhotoViewComponent(Photo photo) {
        photoViewComponent = applicationComponent.plus(new PhotoViewModule(photo));
        return photoViewComponent;
    }

    public void releasePhotoViewComponent() {
        photoViewComponent = null;
    }
}
