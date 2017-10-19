package com.github.chojmi.inspirations.presentation.gallery.ui.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class CommentsAdapter extends BaseRecyclerViewAdapter<CommentsAdapter.ViewHolder, CommentEntity> {
    private final PublishSubject<String> userClicksSubject = PublishSubject.create();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_comments_item, parent, false);
        return new ViewHolder(v, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.renderView(getItem(position));
    }

    public Observable<String> getUserClicksObservable() {
        return userClicksSubject;
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder<CommentEntity> {
        @BindView(R.id.comments_item) CommentsItemView commentsItemView;

        ViewHolder(View itemView, ViewGroup parent) {
            super(itemView, parent);
            commentsItemView.getUserClicksObservable()
                    .takeUntil(RxView.detaches(parent))
                    .map(o -> getItem(getAdapterPosition()).getAuthorId())
                    .subscribe(userClicksSubject);
        }

        public void renderView(CommentEntity commentEntity) {
            commentsItemView.renderView(commentEntity);
        }
    }
}
