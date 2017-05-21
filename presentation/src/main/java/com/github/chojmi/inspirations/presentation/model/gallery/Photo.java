package com.github.chojmi.inspirations.presentation.model.gallery;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;

import io.reactivex.annotations.Nullable;

@AutoValue
public abstract class Photo implements Parcelable {
    public static Photo create(String id, String title, String url, Person person) {
        return new AutoValue_Photo(id, title, url, person, null);
    }

    public static Photo create(Photo photo, List<Person> favs) {
        return new AutoValue_Photo(photo.getId(), photo.getTitle(), photo.getUrl(), photo.getPerson(), photo.getFavs());
    }

    public abstract String getId();

    public abstract String getTitle();

    public abstract String getUrl();

    public abstract Person getPerson();

    @Nullable
    public abstract List<Person> getFavs();
}
