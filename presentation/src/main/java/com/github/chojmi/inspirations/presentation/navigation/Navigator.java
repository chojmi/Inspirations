package com.github.chojmi.inspirations.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.github.chojmi.inspirations.presentation.gallery.GalleryActivity;

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

    public void navigateToGallery(Context context) {
        if (context != null) {
            Intent intentToLaunch = GalleryActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}
