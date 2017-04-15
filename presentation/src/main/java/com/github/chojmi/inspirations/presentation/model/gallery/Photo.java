package com.github.chojmi.inspirations.presentation.model.gallery;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Photo implements Parcelable {
    public static Photo create(String title, String url) {
        return new AutoValue_Photo(title, url);
    }

    public abstract String getTitle();

    public abstract String getUrl();
}
