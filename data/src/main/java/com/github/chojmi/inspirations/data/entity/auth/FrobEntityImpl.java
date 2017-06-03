package com.github.chojmi.inspirations.data.entity.auth;

import android.os.Parcelable;

import com.github.chojmi.inspirations.domain.entity.auth.FrobEntity;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import io.reactivex.annotations.Nullable;

@AutoValue
public abstract class FrobEntityImpl implements Parcelable, FrobEntity {
    public static TypeAdapter<FrobEntityImpl> typeAdapter(Gson gson) {
        return new AutoValue_FrobEntityImpl.GsonTypeAdapter(gson);
    }

    public static FrobEntity create(String frob, String loginUrl) {
        return new AutoValue_FrobEntityImpl(frob, loginUrl);
    }

    @Override
    @SerializedName("_content")
    public abstract String getFrob();

    @Nullable
    abstract String getNullableLoginUrl();

    @Override
    public String getLoginUrl() {
        return getNullableLoginUrl() != null ? getNullableLoginUrl() : "";
    }
}
