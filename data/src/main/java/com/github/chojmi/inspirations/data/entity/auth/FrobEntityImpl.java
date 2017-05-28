package com.github.chojmi.inspirations.data.entity.auth;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class FrobEntityImpl implements Parcelable {
    public static TypeAdapter<FrobEntityImpl> typeAdapter(Gson gson) {
        return new AutoValue_FrobEntityImpl.GsonTypeAdapter(gson);
    }

    @SerializedName("_content")
    public abstract String getContent();
}
