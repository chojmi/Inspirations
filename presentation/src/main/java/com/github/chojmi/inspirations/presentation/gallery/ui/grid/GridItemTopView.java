package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeListEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;
import com.github.chojmi.inspirations.presentation.utils.AspectRatioImageView;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.clearImageCache;
import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.loadImage;

public class GridItemTopView extends FrameLayout {
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.photo) AspectRatioImageView photoHolder;
    @BindView(R.id.person_icon) ImageView personIconHolder;
    @BindView(R.id.title) TextView titleTextView;
    @BindView(R.id.owner) TextView ownerTextView;
    @BindView(R.id.user_profile) LinearLayout profileLayout;

    public GridItemTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.gallery_grid_top_item, this);
        ButterKnife.bind(this);
    }

    public void setPhoto(PhotoWithAuthor photo, PhotoSizeListEntity photoSizeListEntity) {
        if (photoSizeListEntity != null) {
            photoHolder.setAspectRatio(photoSizeListEntity.getRatio());
        }
        toggleProgressBar(true);
        loadImage(photoHolder, photo.getPhoto().getUrl(), new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                toggleProgressBar(false);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                toggleProgressBar(false);
                return false;
            }
        });
        loadImage(personIconHolder, photo.getPerson().getIconUrl());
        titleTextView.setText(photo.getPhoto().getTitle());
        ownerTextView.setText(photo.getPerson().getUsername());
    }

    public void onViewRecycled() {
        clearImageCache(photoHolder);
        clearImageCache(personIconHolder);
    }

    public Observable<Object> getProfileClicksObservable() {
        return RxView.clicks(profileLayout);
    }

    public Observable<Object> getPhotoClicksObservable() {
        return RxView.clicks(photoHolder);
    }

    private void toggleProgressBar(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
