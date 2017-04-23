package com.github.chojmi.inspirations.data.entity.helpers;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Timezone implements Parcelable {
    public static TypeAdapter<Timezone> typeAdapter(Gson gson) {
        return new AutoValue_Timezone.GsonTypeAdapter(gson);
    }

    public abstract String getLabel();

    public abstract String getOffset();

    @SerializedName("timezone_id")
    public abstract String getTimezoneId();
}
