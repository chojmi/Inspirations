package com.github.chojmi.inspirations.data.source.remote.response;

import android.os.Parcelable;

import com.github.chojmi.inspirations.data.entity.photos.CommentEntityImpl;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class PhotoCommentsResponse implements Parcelable {
    public static TypeAdapter<PhotoCommentsResponse> typeAdapter(Gson gson) {
        return new AutoValue_PhotoCommentsResponse.GsonTypeAdapter(gson);
    }

    @SerializedName("photo_id")
    public abstract String getPhotoId();

    @SerializedName("comment")
    public abstract List<CommentEntityImpl> getComments();
}
