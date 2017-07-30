package com.github.chojmi.inspirations.presentation.profile.user_profile.public_photos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.main.Navigator;
import com.github.chojmi.inspirations.presentation.photo.list.PhotoListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPublicPhotosView extends FrameLayout implements UserPublicPhotosContract.View {
    @Inject UserPublicPhotosContract.Presenter presenter;
    @Inject Navigator navigator;
    @BindView(R.id.rv_user_photos) RecyclerView recyclerView;
    private PhotoListAdapter photoListAdapter;

    public UserPublicPhotosView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (presenter != null) {
            presenter.destroyView();
        }
    }

    @Override
    public void renderView(List<Photo> photos) {
        photoListAdapter.setData(photos);
    }

    public void refresh() {
        initRecyclerView();
        presenter.setView(this);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        photoListAdapter = new PhotoListAdapter();
        recyclerView.setAdapter(photoListAdapter);
        photoListAdapter.getPhotoClicksSubject()
                .subscribe(photo -> navigator.navigateToPhoto(getContext(), photo));
    }
}
