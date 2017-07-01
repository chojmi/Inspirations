package com.github.chojmi.inspirations.data.entity.auth;

import android.os.Parcelable;

import com.github.chojmi.inspirations.data.entity.people.UserEntityImpl;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class LoginDataEntityImpl implements Parcelable {
    public static TypeAdapter<LoginDataEntityImpl> typeAdapter(Gson gson) {
        return new AutoValue_LoginDataEntityImpl.GsonTypeAdapter(gson);
    }

    public abstract UserEntityImpl getUser();
}
