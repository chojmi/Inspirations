package com.github.chojmi.inspirations.data.entity.photos;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class FakePhotoFavsEntityImpl extends PhotoFavsEntityImpl {

    @Override
    public List<PersonEntityImpl> getPeople() {
        return new ArrayList<>();
    }

    @Override
    public int getPage() {
        return 0;
    }

    @Override
    public String getPhotoId() {
        return "";
    }

    @Override
    public int getTotal() {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
