package com.github.chojmi.inspirations.presentation;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.github.chojmi.inspirations.data.source.remote.module.RestClientModule;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.GridComponent;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.GridModule;
import com.github.chojmi.inspirations.presentation.gallery.ui.photo.PhotoViewComponent;
import com.github.chojmi.inspirations.presentation.gallery.ui.photo.PhotoViewModule;
import com.github.chojmi.inspirations.presentation.my_profile.login.LoginWebViewComponent;
import com.github.chojmi.inspirations.presentation.my_profile.login.LoginWebViewModule;

import timber.log.Timber;

public class InspirationsApp extends Application {

    private ApplicationComponent applicationComponent;
    private GridComponent gridComponent;
    private PhotoViewComponent photoViewComponent;
    private LoginWebViewComponent loginWebViewComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
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

    public LoginWebViewComponent createLoginWebViewViewComponent() {
        loginWebViewComponent = applicationComponent.plus(new LoginWebViewModule());
        return loginWebViewComponent;
    }

    public void releaseLoginWebViewViewComponent() {
        loginWebViewComponent = null;
    }
}
