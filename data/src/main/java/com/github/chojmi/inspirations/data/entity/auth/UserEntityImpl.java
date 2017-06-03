package com.github.chojmi.inspirations.data.entity.auth;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class UserEntityImpl implements Parcelable {
    public static TypeAdapter<UserEntityImpl> typeAdapter(Gson gson) {
        return new AutoValue_UserEntityImpl.GsonTypeAdapter(gson);
    }

    public abstract String getNsid();

    public abstract String getUsername();

    public abstract String getFullname();
}
