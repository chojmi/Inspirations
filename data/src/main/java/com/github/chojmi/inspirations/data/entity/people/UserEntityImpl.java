package com.github.chojmi.inspirations.data.entity.people;

import android.os.Parcelable;

import com.github.chojmi.inspirations.data.entity.helpers.ContentHolder;
import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class UserEntityImpl implements Parcelable, UserEntity {
    public static TypeAdapter<UserEntityImpl> typeAdapter(Gson gson) {
        return new AutoValue_UserEntityImpl.GsonTypeAdapter(gson);
    }

    public abstract String getId();

    @SerializedName("username")
    abstract ContentHolder getUsernameContentHolder();

    @Override
    public String getUsername() {
        return getUsernameContentHolder().getContent();
    }
}
