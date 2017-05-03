package com.github.chojmi.inspirations.data.entity.people;

import android.os.Parcel;

import com.github.chojmi.inspirations.data.entity.helpers.ContentHolder;

public final class FakePersonEntityImpl extends PersonEntityImpl {

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getNsid() {
        return null;
    }

    @Override
    public int getIsPro() {
        return 0;
    }

    @Override
    public int getServer() {
        return 0;
    }

    @Override
    public int getFarm() {
        return 0;
    }

    @Override
    public ContentHolder getUsernameContentHolder() {
        return null;
    }

    @Override
    public ContentHolder getDescriptionContentHolder() {
        return null;
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public String getIconUrl() {
        return super.getIconUrl();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
