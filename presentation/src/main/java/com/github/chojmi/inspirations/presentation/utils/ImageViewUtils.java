package com.github.chojmi.inspirations.presentation.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chojmi.inspirations.presentation.R;

public final class ImageViewUtils {
    private ImageViewUtils() {
        throw new AssertionError();
    }

    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).apply(RequestOptions.placeholderOf(R.drawable.ic_placeholder)).into(imageView);
    }

    public static void clearImageCache(ImageView imageView) {
        Glide.with(imageView.getContext().getApplicationContext()).clear(imageView);
    }
}
