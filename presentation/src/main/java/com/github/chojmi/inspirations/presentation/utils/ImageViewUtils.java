package com.github.chojmi.inspirations.presentation.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public final class ImageViewUtils {
    private ImageViewUtils() {
        throw new AssertionError();
    }

    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public static void clearImageCache(ImageView imageView) {
        Glide.clear(imageView);
    }
}
