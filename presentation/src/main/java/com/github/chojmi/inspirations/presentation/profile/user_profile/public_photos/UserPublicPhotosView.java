package com.github.chojmi.inspirations.presentation.profile.user_profile.public_photos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class UserPublicPhotosView extends FrameLayout implements UserPublicPhotosContract.View {
    @Inject UserPublicPhotosContract.Presenter presenter;

    public UserPublicPhotosView(@NonNull Context context, @Nullable AttributeSet attrs) {
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
    public void renderView(List<PhotoEntity> photos) {
    }
}
