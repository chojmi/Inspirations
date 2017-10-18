package com.github.chojmi.inspirations.presentation.gallery.ui.comments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.utils.Preconditions;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.annotations.NonNull;

public class CommentsActivity extends BaseActivity implements CommentsContract.View {
    private static final String ARG_PHOTO_ID = "ARG_PHOTO_ID";
    @Inject CommentsContract.Presenter presenter;

    public static Intent getCallingIntent(Context context, @NonNull String photoId) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra(ARG_PHOTO_ID, Preconditions.checkNotNull(photoId));
        return intent;
    }

    public String getArgPhotoId() {
        return getIntent().getStringExtra(ARG_PHOTO_ID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_comments_activity);
        presenter.setView(this);
    }

    @Override
    public void toggleProgressBar(boolean isVisible) {

    }

    @Override
    public void renderView(PhotoCommentsEntity photoComments) {

    }
}
