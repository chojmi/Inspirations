package com.github.chojmi.inspirations.data.entity.helpers;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class ContentHolder implements Parcelable {
    public static TypeAdapter<ContentHolder> typeAdapter(Gson gson) {
        return new AutoValue_ContentHolder.GsonTypeAdapter(gson);
    }

    @SerializedName("_content")
    public abstract String getContent();
}
