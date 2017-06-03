package com.github.chojmi.inspirations.data.entity.auth;

import android.os.Parcelable;

import com.github.chojmi.inspirations.data.entity.helpers.ContentHolder;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class TokenEntityImpl implements Parcelable {
    public static TypeAdapter<TokenEntityImpl> typeAdapter(Gson gson) {
        return new AutoValue_TokenEntityImpl.GsonTypeAdapter(gson);
    }

    public abstract ContentHolder getToken();

    public abstract ContentHolder getPerms();

    public abstract UserEntityImpl getUser();
}
