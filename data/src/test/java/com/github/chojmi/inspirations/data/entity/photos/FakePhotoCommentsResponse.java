package com.github.chojmi.inspirations.data.entity.photos;

import android.os.Parcel;

import com.github.chojmi.inspirations.data.source.remote.response.PhotoCommentsResponse;

import java.util.ArrayList;
import java.util.List;

public final class FakePhotoCommentsResponse extends PhotoCommentsResponse {
    @Override
    public String getPhotoId() {
        return null;
    }

    @Override
    public List<CommentEntityImpl> getComments() {
        return new ArrayList<>();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
