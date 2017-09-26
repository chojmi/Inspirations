package com.github.chojmi.inspirations.presentation.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;

public final class ImageViewUtils {
    private ImageViewUtils() {
        throw new AssertionError();
    }

    public static void loadImage(ImageView imageView, String url) {
        loadImage(imageView, url, null);
    }

    public static void loadImage(ImageView imageView, String url, RequestListener<Drawable> listener) {
        Glide.with(imageView.getContext()).load(url).listener(listener).into(imageView);
    }

    public static void clearImageCache(ImageView imageView) {
        Glide.with(imageView.getContext().getApplicationContext()).clear(imageView);
    }
}
