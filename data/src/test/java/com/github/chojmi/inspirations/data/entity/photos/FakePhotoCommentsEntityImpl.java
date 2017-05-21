package com.github.chojmi.inspirations.data.entity.photos;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class FakePhotoCommentsEntityImpl extends PhotoCommentsEntityImpl {
    @Override
    public List<CommentEntityImpl> getComments() {
        return new ArrayList<>();
    }

    @Override
    public String getPhotoId() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
