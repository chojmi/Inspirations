package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.clearImageCache;
import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.loadImage;

public class GridItemTopView extends LinearLayoutCompat {
    @BindView(R.id.photo) ImageView photoHolder;
    @BindView(R.id.person_icon) ImageView personIconHolder;
    @BindView(R.id.title) TextView titleTextView;
    @BindView(R.id.owner) TextView ownerTextView;
    @BindView(R.id.user_profile) LinearLayout profileLayout;

    public GridItemTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setPhoto(PhotoWithAuthor photo) {
        loadImage(photoHolder, photo.getPhoto().getUrl());
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
}
