package com.github.chojmi.inspirations.data.model.gallery;

import android.os.Parcelable;

import com.github.chojmi.inspirations.data.model.Photo;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class Gallery implements Parcelable {
    public static TypeAdapter<Gallery> typeAdapter(Gson gson) {
        return new AutoValue_Gallery.GsonTypeAdapter(gson);
    }

    public static Gallery createEmpty() {
        return new AutoValue_Gallery(0, 0, 0, 0, Collections.emptyList());
    }

    public abstract int getPage();

    public abstract int getPages();

    public abstract int getPerpage();

    public abstract int getTotal();

    public abstract List<Photo> getPhoto();
}
