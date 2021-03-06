package com.github.chojmi.inspirations.presentation.common;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

public class PhotoDetailsView extends LinearLayoutCompat {
    @BindView(R.id.comments_root) View commentsRoot;
    @BindView(R.id.comments_count) TextView commentsCount;
    @BindView(R.id.favs_root) View favsRoot;
    @BindView(R.id.favs_icon) View favsIcon;
    @BindView(R.id.favs_count) TextView favsCount;

    public PhotoDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.photo_details, this);
        ButterKnife.bind(this);
    }

    public void setPhotoInfo(@Nullable PhotoInfoEntity photoInfo) {
        if (photoInfo == null) {
            commentsCount.setText("");
            favsIcon.setSelected(false);
            return;
        }
        favsIcon.setSelected(photoInfo.isFav());
        commentsCount.setText(String.valueOf(photoInfo.getCommentsCount()));
    }

    public void setFavs(@Nullable PhotoFavs photoFavs) {
        if (photoFavs == null) {
            favsCount.setText("");
            return;
        }
        favsCount.setText(String.valueOf(photoFavs.getTotal()));
    }

    public Observable<View> getFavsRootClicks() {
        return RxView.clicks(favsRoot).map(o -> favsRoot);
    }

    public Observable<View> getCommentsRootClicks() {
        return RxView.clicks(commentsRoot).map(o -> commentsRoot);
    }

    public Observable<View> getFavsIconClicks() {
        return RxView.clicks(favsIcon).map(o -> favsIcon);
    }
}
