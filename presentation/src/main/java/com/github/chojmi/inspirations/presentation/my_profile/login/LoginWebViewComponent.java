package com.github.chojmi.inspirations.presentation.my_profile.login;

import com.github.chojmi.inspirations.presentation.my_profile.MyProfileScope;

import dagger.Subcomponent;

@MyProfileScope
@Subcomponent(modules = LoginWebViewModule.class)
public interface LoginWebViewComponent {
    void inject(LoginWebViewActivity target);
}