package com.github.chojmi.inspirations.presentation.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

class MainScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private Context context;

    MainScreenSlidePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = checkNotNull(context);
    }

    @Override
    public Fragment getItem(int position) {
        return MainScreenTab.valueOf(position).getFragment();
    }

    @Override
    public int getCount() {
        return MainScreenTab.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MainScreenTab.valueOf(position).getTitle(context);
    }
}
