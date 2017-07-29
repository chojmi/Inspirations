package com.github.chojmi.inspirations.presentation;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.github.chojmi.inspirations.data.source.module.RestClientModule;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.GridComponent;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.GridModule;
import com.github.chojmi.inspirations.presentation.gallery.ui.photo.PhotoViewComponent;
import com.github.chojmi.inspirations.presentation.gallery.ui.photo.PhotoViewModule;
import com.github.chojmi.inspirations.presentation.profile.login.LoginWebViewComponent;
import com.github.chojmi.inspirations.presentation.profile.login.LoginWebViewModule;
import com.github.chojmi.inspirations.presentation.profile.my_profile.MyProfileComponent;
import com.github.chojmi.inspirations.presentation.profile.my_profile.MyProfileModule;
import com.github.chojmi.inspirations.presentation.profile.user_profile.UserProfileComponent;
import com.github.chojmi.inspirations.presentation.profile.user_profile.UserProfileModule;
import com.github.chojmi.inspirations.presentation.search.SearchComponent;
import com.github.chojmi.inspirations.presentation.search.SearchModule;

import io.reactivex.annotations.NonNull;
import timber.log.Timber;

public class InspirationsApp extends Application {

    private ApplicationComponent applicationComponent;
    private GridComponent gridComponent;
    private PhotoViewComponent photoViewComponent;
    private LoginWebViewComponent loginWebViewComponent;
    private MyProfileComponent myProfileComponent;
    private UserProfileComponent userProfileComponent;
    private SearchComponent searchComponent;

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

    public MyProfileComponent createMyProfileComponent() {
        myProfileComponent = applicationComponent.plus(new MyProfileModule());
        return myProfileComponent;
    }

    public void releaseMyProfileComponent() {
        myProfileComponent = null;
    }

    public UserProfileComponent createUserProfileComponent(@NonNull String userId) {
        userProfileComponent = applicationComponent.plus(new UserProfileModule(userId));
        return userProfileComponent;
    }

    public void releaseUserProfileComponent() {
        userProfileComponent = null;
    }

    public SearchComponent createSearchComponent() {
        searchComponent = applicationComponent.plus(new SearchModule());
        return searchComponent;
    }

    public void releaseSearchComponent() {
        searchComponent = null;
    }
}
