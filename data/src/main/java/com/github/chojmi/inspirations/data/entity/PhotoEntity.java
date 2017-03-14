package com.github.chojmi.inspirations.data.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

@AutoValue
public abstract class PhotoEntity implements Parcelable {
    public static TypeAdapter<PhotoEntity> typeAdapter(Gson gson) {
        return new AutoValue_PhotoEntity.GsonTypeAdapter(gson);
    }

    @SerializedName("id")
    public abstract String getId();

    @SerializedName("owner")
    public abstract String getOwner();

    @SerializedName("secret")
    public abstract String getSecret();

    @SerializedName("server")
    public abstract String getServer();

    @SerializedName("farm")
    public abstract int getFarm();

    @SerializedName("title")
    public abstract String getTitle();

    @SerializedName("ispublic")
    public abstract int isPublic();

    @SerializedName("isfriend")
    public abstract int isFriend();

    @SerializedName("isfamily")
    public abstract int isFamily();

    @SerializedName("is_primary")
    public abstract int isPrimary();

    @SerializedName("has_comment")
    public abstract int hasComment();

    public String getUrl() {
        return String.format(Locale.ENGLISH, "https://farm%d.staticflickr.com/%s/%s_%s.jpg", getFarm(), getServer(), getId(), getSecret());
    }
}
