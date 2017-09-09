package com.github.chojmi.inspirations.presentation.main;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.GridFragment;
import com.github.chojmi.inspirations.presentation.profile.my_profile.MyProfileFragment;
import com.github.chojmi.inspirations.presentation.search.SearchFragment;

enum MainScreenTab {
    GALLERY(GridFragment.newInstance(), R.string.main_gallery),
    MY_PROFILE(MyProfileFragment.newInstance(), R.string.main_my_profile),
    SEARCH(SearchFragment.newInstance(), R.string.main_search);

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
