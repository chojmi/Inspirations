package com.github.chojmi.inspirations.presentation.model.gallery;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Person implements Parcelable {
    public static Person create(String username, String iconUrl) {
        return new AutoValue_Person(username, iconUrl);
    }

    public abstract String getUsername();

    public abstract String getIconUrl();
}
