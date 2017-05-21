package com.github.chojmi.inspirations.presentation.model.gallery;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class PhotoFavs implements Parcelable {
    public static PhotoFavs create(List<Person> people, int page, String photoId, int total) {
        return new AutoValue_PhotoFavs(people, page, photoId, total);
    }

    public abstract List<Person> getPeople();

    public abstract int getPage();

    public abstract String getPhotoId();

    public abstract int getTotal();
}
