package com.github.chojmi.inspirations.presentation.profile.login;

import com.github.chojmi.inspirations.presentation.profile.ProfileScope;

import dagger.Subcomponent;

@ProfileScope
@Subcomponent(modules = LoginWebViewModule.class)
public interface LoginWebViewComponent {
    void inject(LoginWebViewActivity target);
}