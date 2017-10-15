package com.github.chojmi.inspirations.presentation.profile.user_profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.photo.PhotoListAdapter;
import com.github.chojmi.inspirations.presentation.utils.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class UserPublicPhotosView extends FrameLayout implements UserPublicPhotosContract.View {
    @BindView(R.id.rv_user_photos) RecyclerView recyclerView;
    @BindView(R.id.empty_list) ViewGroup emptyList;
    private PhotoListAdapter photoListAdapter;

    public UserPublicPhotosView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.profile_user_public_photos_view, this);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    @Override
    public void renderView(List<Photo> photos) {
        photoListAdapter.setData(photos);
        ViewUtils.setVisibility(recyclerView, !photos.isEmpty());
        ViewUtils.setVisibility(emptyList, photos.isEmpty());
    }

    public Observable<Pair<ImageView, Photo>> getPhotoClicksObservable() {
        return photoListAdapter.getPhotoClicksSubject();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        photoListAdapter = new PhotoListAdapter();
        recyclerView.setAdapter(photoListAdapter);
    }
}
