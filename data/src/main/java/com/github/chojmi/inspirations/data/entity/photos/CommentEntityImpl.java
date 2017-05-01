package com.github.chojmi.inspirations.data.entity.photos;

import android.os.Parcelable;

import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class CommentEntityImpl implements Parcelable, CommentEntity {
    public static TypeAdapter<CommentEntityImpl> typeAdapter(Gson gson) {
        return new AutoValue_CommentEntityImpl.GsonTypeAdapter(gson);
    }

    @Override
    public abstract String getAuthorname();

    @SerializedName("_content")
    @Override
    public abstract String getContent();
}
