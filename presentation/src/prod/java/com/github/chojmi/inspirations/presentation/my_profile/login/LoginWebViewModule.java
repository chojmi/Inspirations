package com.github.chojmi.inspirations.presentation.my_profile.login;

import com.github.chojmi.inspirations.domain.usecase.auth.GetFrob;
import com.github.chojmi.inspirations.presentation.my_profile.MyProfileScope;

import dagger.Module;
import dagger.Provides;

@MyProfileScope
@Module
public class LoginWebViewModule {
    @Provides
    LoginWebViewContract.Presenter providePhotoViewPresenter(GetFrob getFrob) {
        return new LoginWebViewPresenter(getFrob);
    }
}