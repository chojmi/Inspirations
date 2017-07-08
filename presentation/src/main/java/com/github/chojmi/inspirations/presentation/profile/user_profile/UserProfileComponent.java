package com.github.chojmi.inspirations.presentation.profile.user_profile;

import com.github.chojmi.inspirations.presentation.profile.ProfileScope;

import dagger.Subcomponent;

@ProfileScope
@Subcomponent(modules = UserProfileModule.class)
public interface UserProfileComponent {
    void inject(UserProfileView target);
}
