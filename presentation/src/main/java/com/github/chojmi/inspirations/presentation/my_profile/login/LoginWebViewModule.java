package com.github.chojmi.inspirations.presentation.my_profile.login;

import com.github.chojmi.inspirations.presentation.my_profile.MyProfileScope;

import dagger.Module;
import dagger.Provides;

@MyProfileScope
@Module
public class LoginWebViewModule {
    @Provides
    LoginWebViewContract.Presenter providePhotoViewPresenter() {
        return new LoginWebViewPresenter();
    }
}