package com.github.chojmi.inspirations.presentation.profile.user_profile.tabs;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.github.chojmi.inspirations.presentation.R;

enum UserProfileTab {
    PUBLIC_PHOTOS(R.string.public_photos, R.layout.view_user_profile_public_photos),
    ALBUMS(R.string.albums, R.layout.view_user_profile_albums);
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
