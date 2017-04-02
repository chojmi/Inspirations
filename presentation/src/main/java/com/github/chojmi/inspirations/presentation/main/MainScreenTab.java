package com.github.chojmi.inspirations.presentation.main;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.camera.CameraFragment;
import com.github.chojmi.inspirations.presentation.gallery.GalleryFragment;
import com.github.chojmi.inspirations.presentation.my_profile.MyProfileFragment;

enum MainScreenTab {
    GALLERY(GalleryFragment.newInstance(), R.string.gallery),
    MY_PROFILE(MyProfileFragment.newInstance(), R.string.my_profile),
    CAMERA(CameraFragment.newInstance(), R.string.camera);

    private Fragment fragment;
    @StringRes private int titleRes;

    MainScreenTab(Fragment fragment, @StringRes int titleRes) {
        this.fragment = fragment;
        this.titleRes = titleRes;
    }

    static MainScreenTab valueOf(int position) {
        return values()[position];
    }

    public static int size() {
        return values().length;
    }

    Fragment getFragment() {
        return fragment;
    }

    String getTitle(Context context) {
        return context.getString(titleRes);
    }
}
