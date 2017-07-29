package com.github.chojmi.inspirations.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.ui.photo.PhotoViewActivity;
import com.github.chojmi.inspirations.presentation.profile.login.LoginWebViewActivity;
import com.github.chojmi.inspirations.presentation.profile.user_profile.UserProfileActivity;

import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    public Navigator() {
    }

    public void navigateToPhoto(Context context, Photo photo) {
        if (context != null) {
            Intent intentToLaunch = PhotoViewActivity.getCallingIntent(context, photo);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToLoginWebView(Fragment fragment, int requestCode) {
        if (fragment != null) {
            Intent intentToLaunch = LoginWebViewActivity.getCallingIntent(fragment.getContext());
            fragment.startActivityForResult(intentToLaunch, requestCode);
        }
    }

    public void navigateToUserProfile(Context context, String userId) {
        if (context != null) {
            Intent intentToLaunch = UserProfileActivity.getCallingIntent(context, userId);
            context.startActivity(intentToLaunch);
        }
    }
}