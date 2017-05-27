package com.github.chojmi.inspirations.presentation.gallery.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class PhotoComments implements Parcelable {
    public static PhotoComments create(List<Comment> comments, String photoId) {
        return new AutoValue_PhotoComments(comments, photoId);
    }

    public abstract List<Comment> getComments();

    public abstract String getPhotoId();
}
