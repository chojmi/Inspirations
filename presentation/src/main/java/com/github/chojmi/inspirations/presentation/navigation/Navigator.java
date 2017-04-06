package com.github.chojmi.inspirations.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.github.chojmi.inspirations.presentation.gallery.photo.PhotoActivity;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {
    }

    public void navigateToPhoto(Context context, Photo photo) {
        if (context != null) {
            Intent intentToLaunch = PhotoActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}