package com.github.chojmi.inspirations.data.entity.photos;

import android.os.Parcelable;

import com.github.chojmi.inspirations.data.entity.helpers.ImageLinkProvider;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class PersonEntityImpl implements Parcelable, PersonEntity {
    public static TypeAdapter<PersonEntityImpl> typeAdapter(Gson gson) {
        return new AutoValue_PersonEntityImpl.GsonTypeAdapter(gson);
    }

    @Override
    public abstract String getUsername();

    abstract String getNsid();

    @SerializedName("iconserver")
    abstract int getServer();

    @SerializedName("iconfarm")
    abstract int getFarm();

    @Override
    public String getIconUrl() {
        return ImageLinkProvider.provideLink(getFarm(), getServer(), getNsid());
    }
}
