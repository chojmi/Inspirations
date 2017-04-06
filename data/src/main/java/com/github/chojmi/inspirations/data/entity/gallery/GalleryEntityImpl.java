package com.github.chojmi.inspirations.data.entity.gallery;

import android.os.Parcelable;

import com.github.chojmi.inspirations.data.entity.PhotoEntityImpl;
import com.github.chojmi.inspirations.domain.entity.gallery.GalleryEntity;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class GalleryEntityImpl implements Parcelable, GalleryEntity {
    public static TypeAdapter<GalleryEntityImpl> typeAdapter(Gson gson) {
        return new AutoValue_GalleryEntityImpl.GsonTypeAdapter(gson);
    }

    public abstract int getPage();

    public abstract int getPages();

    public abstract int getPerpage();

    public abstract int getTotal();

    public abstract List<PhotoEntityImpl> getPhoto();
}
