package com.github.chojmi.inspirations.presentation.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;
import com.github.chojmi.inspirations.presentation.common.PhotoDetailsView;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;
import com.github.chojmi.inspirations.presentation.utils.ImageViewUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.clearImageCache;
import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.loadImage;

public class PhotoViewActivity extends BaseActivity implements PhotoViewContract.View {

    private static final String ARG_PHOTO = "ARG_PHOTO";
    @Inject PhotoViewContract.Presenter presenter;
    @BindView(R.id.photo) ImageView photoHolder;
    @BindView(R.id.item_bottom) PhotoDetailsView photoDetailsView;
    @BindView(R.id.person_icon) ImageView personIcon;
    @BindView(R.id.owner) TextView ownerTextView;
    @BindView(R.id.title) TextView photoTitle;

    public static Intent getCallingIntent(Context context, Photo photo) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(ARG_PHOTO, photo);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
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
        clearImageCache(photoHolder);
        clearImageCache(personIcon);
    }

    @Override
    public void showPhoto(Photo photo) {
        supportPostponeEnterTransition();
        ImageViewUtils.loadImage(photoHolder, photo.getUrl());
        photoTitle.setText(photo.getTitle());
    }

    @Override
    public void showFavs(PhotoFavs photoFavs) {
        photoDetailsView.setFavs(photoFavs);
    }

    @Override
    public void showPhotoInfo(PhotoInfoEntity photoInfo) {
        photoDetailsView.setPhotoInfo(photoInfo);
    }

    @Override
    public void showUserData(PhotoWithAuthor photoWithAuthor) {
        loadImage(personIcon, photoWithAuthor.getPerson().getIconUrl());
        ownerTextView.setText(photoWithAuthor.getPhoto().getTitle());
    }

    @OnClick({R.id.owner, R.id.person_icon})
    public void onPhotoOwnerClick(View view) {
        getNavigator().navigateToUserProfile(this, getPhoto().getOwnerId());
    }

    @OnClick(R.id.close)
    public void onCloseClick(View view) {
        onBackPressed();
    }

    public Photo getPhoto() {
        return getIntent().getParcelableExtra(ARG_PHOTO);
    }
}
