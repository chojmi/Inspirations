package com.github.chojmi.inspirations.presentation.gallery.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Comment implements Parcelable {
    public static Comment create(String author, String content) {
        return new AutoValue_Comment(author, content);
    }

    abstract String getAuthor();

    abstract String getContent();
}
