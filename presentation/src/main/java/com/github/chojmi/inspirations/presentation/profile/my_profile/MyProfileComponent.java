package com.github.chojmi.inspirations.presentation.profile.my_profile;

import com.github.chojmi.inspirations.presentation.profile.ProfileScope;

import dagger.Subcomponent;

@ProfileScope
@Subcomponent(modules = MyProfileModule.class)
public interface MyProfileComponent {
    void inject(MyProfileFragment target);
}
