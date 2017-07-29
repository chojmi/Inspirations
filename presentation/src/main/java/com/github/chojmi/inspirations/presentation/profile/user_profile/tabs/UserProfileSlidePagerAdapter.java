package com.github.chojmi.inspirations.presentation.profile.user_profile.tabs;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chojmi.inspirations.presentation.profile.user_profile.UserProfileComponent;
import com.github.chojmi.inspirations.presentation.profile.user_profile.public_photos.UserPublicPhotosView;

public class UserProfileSlidePagerAdapter extends PagerAdapter {

    private final Context mContext;
    private final UserProfileComponent userProfileComponent;

    public UserProfileSlidePagerAdapter(Context context, UserProfileComponent userProfileComponent) {
        this.mContext = context;
        this.userProfileComponent = userProfileComponent;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        UserProfileTab customPagerEnum = UserProfileTab.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);
        if (layout instanceof UserPublicPhotosView) {
            userProfileComponent.inject((UserPublicPhotosView) layout);
        }
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return UserProfileTab.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        UserProfileTab customPagerEnum = UserProfileTab.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }
}
