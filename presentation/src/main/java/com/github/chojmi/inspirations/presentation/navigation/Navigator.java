package com.github.chojmi.inspirations.presentation.navigation;

import android.support.v4.app.Fragment;

import com.github.chojmi.inspirations.presentation.gallery.GalleryFragment;

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

    public Fragment getGalleryFragment() {
        return GalleryFragment.newInstance();
    }
}
