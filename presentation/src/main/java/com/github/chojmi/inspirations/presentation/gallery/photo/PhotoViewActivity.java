package com.github.chojmi.inspirations.presentation.gallery.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;
import com.github.chojmi.inspirations.presentation.utils.ImageViewUtils;

import javax.inject.Inject;

import butterknife.BindView;

public class PhotoViewActivity extends BaseActivity implements PhotoViewContract.View {

    private static final String ARG_PHOTO = "ARG_PHOTO";
    @Inject PhotoViewContract.Presenter presenter;
    @BindView(R.id.photo) ImageView imageView;

    public static Intent getCallingIntent(Context context, Photo photo) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(ARG_PHOTO, photo);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInspirationsApp().createPhotoViewComponent(getIntent().getParcelableExtra(ARG_PHOTO)).inject(this);
        setContentView(R.layout.activity_gallery_photo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.destroyView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getInspirationsApp().releasePhotoViewComponent();
    }

    @Override
    public void showPhoto(Photo photo) {
        ImageViewUtils.loadImage(imageView, photo.getUrl());
    }
}
