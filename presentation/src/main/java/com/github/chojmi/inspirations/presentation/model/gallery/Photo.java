package com.github.chojmi.inspirations.presentation.model.gallery;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Photo {
    public static Photo create(String url) {
        return new AutoValue_Photo(url);
    }

    public abstract String getUrl();
}
