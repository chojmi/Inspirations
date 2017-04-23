package com.github.chojmi.inspirations.presentation.model.gallery;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Person implements Parcelable {
    public static Person create(String username) {
        return new AutoValue_Person(username);
    }

    public abstract String getUsername();
}
