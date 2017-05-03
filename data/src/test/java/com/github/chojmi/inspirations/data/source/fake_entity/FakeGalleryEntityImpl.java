package com.github.chojmi.inspirations.data.source.fake_entity;

import android.os.Parcel;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;

import java.util.ArrayList;
import java.util.List;

public final class FakeGalleryEntityImpl extends GalleryEntityImpl {

    @Override
    public int getPage() {
        return 0;
    }

    @Override
    public int getPages() {
        return 0;
    }

    @Override
    public int getPerpage() {
        return 0;
    }

    @Override
    public int getTotal() {
        return 0;
    }

    @Override
    public List getPhoto() {
        return new ArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}