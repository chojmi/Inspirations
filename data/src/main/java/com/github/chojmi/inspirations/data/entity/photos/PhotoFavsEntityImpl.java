package com.github.chojmi.inspirations.data.entity.photos;

import android.os.Parcelable;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class PhotoFavsEntityImpl implements Parcelable, PhotoFavsEntity {
    public static TypeAdapter<PhotoFavsEntityImpl> typeAdapter(Gson gson) {
        return new AutoValue_PhotoFavsEntityImpl.GsonTypeAdapter(gson);
    }

    @SerializedName("person")
    public abstract List<PersonEntityImpl> getPeople();

    public abstract int getPage();

    @SerializedName("id")
    public abstract String getPhotoId();

    public abstract int getTotal();
}
