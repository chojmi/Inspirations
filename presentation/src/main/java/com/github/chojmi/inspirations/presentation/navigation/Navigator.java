package com.github.chojmi.inspirations.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.github.chojmi.inspirations.presentation.gallery.photo.PhotoViewActivity;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

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
}