package com.github.chojmi.inspirations.presentation.profile.user_profile.albums;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.github.chojmi.inspirations.domain.entity.GalleryEntity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class UserAlbumsView extends FrameLayout implements UserAlbumsContract.View {
    @Inject UserAlbumsContract.Presenter presenter;

    public UserAlbumsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.setView(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (presenter != null) {
            presenter.destroyView();
        }
    }

    @Override
    public void renderView(GalleryEntity gallery) {
    }
}
