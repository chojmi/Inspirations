package com.github.chojmi.inspirations.data.entity.gallery;

import android.os.Parcelable;

import com.github.chojmi.inspirations.data.entity.PhotoEntity;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class GalleryEntity implements Parcelable {
    public static TypeAdapter<GalleryEntity> typeAdapter(Gson gson) {
        return new AutoValue_GalleryEntity.GsonTypeAdapter(gson);
    }

    public abstract int getPage();

    public abstract int getPages();

    public abstract int getPerpage();

    public abstract int getTotal();

    public abstract List<PhotoEntity> getPhoto();
}
