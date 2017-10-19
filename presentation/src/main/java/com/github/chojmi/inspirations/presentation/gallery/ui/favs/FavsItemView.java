package com.github.chojmi.inspirations.presentation.gallery.ui.favs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.utils.ImageViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.clearImageCache;

public class FavsItemView extends LinearLayoutCompat {
    @BindView(R.id.user_icon) ImageView userIconView;
    @BindView(R.id.user_name) TextView userNameView;

    public FavsItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.gallery_favs_item_view, this);
        setOrientation(HORIZONTAL);
        ButterKnife.bind(this);
    }

    public void renderView(PersonEntity personEntity) {
        ImageViewUtils.loadImage(userIconView, personEntity.getIconUrl());
        userNameView.setText(personEntity.getUsername());
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearImageCache(userIconView);
    }
}
