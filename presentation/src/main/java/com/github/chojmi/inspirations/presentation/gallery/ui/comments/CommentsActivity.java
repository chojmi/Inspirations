package com.github.chojmi.inspirations.presentation.gallery.ui.comments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.utils.Preconditions;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class CommentsActivity extends BaseActivity implements CommentsContract.View {
    private static final String ARG_PHOTO_ID = "ARG_PHOTO_ID";
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.back) ImageButton backBtn;
    @BindView(R.id.title) TextView titleView;
    @BindView(R.id.comment_text) EditText commentView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @Inject CommentsContract.Presenter presenter;
    @Inject @Named("comments_adapter") BaseRecyclerViewAdapter<?, CommentEntity> adapter;

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
        recyclerView.setAdapter(adapter);
        presenter.setView(this);
    }

    @Override
    public void toggleProgressBar(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void renderView(PhotoCommentsEntity photoComments) {
        final int commentCount = photoComments.getComments().size();
        titleView.setText(getString(R.string.gallery_comments_title, commentCount));
        adapter.setData(photoComments.getComments());
        if (commentCount > 0) {
            recyclerView.scrollToPosition(commentCount - 1);
        }
    }

    @Override
    public Observable<View> getBackBtnClicksObservable() {
        return RxView.clicks(backBtn).map(o -> backBtn);
    }

    @Override
    public void clearComment() {
        commentView.setText("");
    }

    public void onPostCommentClick(View view) {
        presenter.addComment(commentView.getText().toString());
    }

    @Override
    public void closeView() {
        finish();
    }
}
