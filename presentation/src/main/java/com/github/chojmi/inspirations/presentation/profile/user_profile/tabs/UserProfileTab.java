package com.github.chojmi.inspirations.presentation.profile.user_profile.tabs;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.github.chojmi.inspirations.presentation.R;

enum UserProfileTab {
    PUBLIC_PHOTOS(R.string.public_photos, R.layout.profile_user_public_photos_view),
    ALBUMS(R.string.albums, R.layout.profile_user_albums_view);
    private Fragment fragment;
    @StringRes private int titleRes;

    private int mTitleResId;
    private int mLayoutResId;

    UserProfileTab(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
