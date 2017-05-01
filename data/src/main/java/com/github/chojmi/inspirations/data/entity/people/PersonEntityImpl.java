package com.github.chojmi.inspirations.data.entity.people;

import android.os.Parcelable;

import com.github.chojmi.inspirations.data.entity.helpers.ContentHolder;
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

    abstract String getId();

    abstract String getNsid();

    @SerializedName("ispro")
    abstract int getIsPro();

    @SerializedName("iconserver")
    abstract int getServer();

    @SerializedName("iconfarm")
    abstract int getFarm();

    @SerializedName("username")
    abstract ContentHolder getUsernameContentHolder();

    @SerializedName("description")
    abstract ContentHolder getDescriptionContentHolder();

    @Override
    public String getUsername() {
        return getUsernameContentHolder().getContent();
    }

    public String getDescription() {
        return getDescriptionContentHolder().getContent();
    }

    @Override
    public String getIconUrl() {
        return ImageLinkProvider.provideLink(getFarm(), getServer(), getNsid());
    }
}
