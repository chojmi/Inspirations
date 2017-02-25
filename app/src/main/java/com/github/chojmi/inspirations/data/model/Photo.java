package com.github.chojmi.inspirations.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Photo implements Parcelable {
    public static TypeAdapter<Photo> typeAdapter(Gson gson) {
        return new AutoValue_Photo.GsonTypeAdapter(gson);
    }

    public abstract String getId();

    public abstract String getOwner();

    public abstract String getSecret();

    public abstract String getServer();

    public abstract int getFarm();

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

}
