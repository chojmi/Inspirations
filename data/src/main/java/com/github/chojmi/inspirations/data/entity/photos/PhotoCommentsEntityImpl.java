package com.github.chojmi.inspirations.data.entity.photos;

import android.os.Parcelable;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class PhotoCommentsEntityImpl implements Parcelable, PhotoCommentsEntity {
    public static TypeAdapter<PhotoCommentsEntityImpl> typeAdapter(Gson gson) {
        return new AutoValue_PhotoCommentsEntityImpl.GsonTypeAdapter(gson);
    }

    @SerializedName("comment")
    public abstract List<CommentEntityImpl> getComments();

    @SerializedName("photo_id")
    public abstract String getPhotoId();
}
