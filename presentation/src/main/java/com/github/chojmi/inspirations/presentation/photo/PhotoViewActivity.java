package com.github.chojmi.inspirations.presentation.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;
import com.github.chojmi.inspirations.presentation.common.PhotoDetailsView;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoComments;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;
import com.github.chojmi.inspirations.presentation.utils.ImageViewUtils;

import javax.inject.Inject;

import butterknife.BindView;

import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.clearImageCache;
import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.loadImage;

public class PhotoViewActivity extends BaseActivity implements PhotoViewContract.View {

    private static final String ARG_PHOTO = "ARG_PHOTO";
    @Inject PhotoViewContract.Presenter presenter;
    @BindView(R.id.photo) ImageView imageView;
    @BindView(R.id.item_bottom) PhotoDetailsView photoDetailsView;
    @BindView(R.id.person_icon) ImageView personIcon;
    @BindView(R.id.owner) TextView ownerTextView;

    public static Intent getCallingIntent(Context context, Photo photo) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(ARG_PHOTO, photo);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInspirationsApp().createPhotoViewComponent(getIntent().getParcelableExtra(ARG_PHOTO)).inject(this);
        setContentView(R.layout.photo_item_activity);
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
        clearImageCache(personIcon);
        getInspirationsApp().releasePhotoViewComponent();
    }

    @Override
    public void showPhoto(Photo photo) {
        ImageViewUtils.loadImage(imageView, photo.getUrl());
    }

    @Override
    public void showFavs(PhotoFavs photoFavs) {
        photoDetailsView.setFavs(photoFavs);
    }

    @Override
    public void showComments(PhotoComments photoComments) {
        photoDetailsView.setComments(photoComments);
    }

    @Override
    public void showUserData(PhotoWithAuthor photoWithAuthor) {
        loadImage(personIcon, photoWithAuthor.getPerson().getIconUrl());
        ownerTextView.setText(photoWithAuthor.getPhoto().getTitle());
    }
}
