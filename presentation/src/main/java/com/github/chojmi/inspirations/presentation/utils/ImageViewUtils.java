package com.github.chojmi.inspirations.presentation.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public final class ImageViewUtils {
    private ImageViewUtils() {
        throw new AssertionError();
    }

    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public static void loadImageWithoutAnim(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontTransform()
                .into(imageView);
    }

    public static void clearImageCache(ImageView imageView) {
        Glide.clear(imageView);
    }
}
