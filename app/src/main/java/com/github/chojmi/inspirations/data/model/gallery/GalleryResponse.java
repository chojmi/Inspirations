package com.github.chojmi.inspirations.data.model.gallery;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class GalleryResponse implements Parcelable {
    public static TypeAdapter<GalleryResponse> typeAdapter(Gson gson) {
        return new AutoValue_GalleryResponse.GsonTypeAdapter(gson);
    }

    public static GalleryResponse createEmpty() {
//        return new AutoValue_GalleryResponse(Gallery.createEmpty(), "empty");
        return new AutoValue_GalleryResponse();
    }

//    public abstract Gallery getPhotos();
//
//    public abstract String getStat();
}
