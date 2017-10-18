package com.github.chojmi.inspirations.presentation.gallery.ui.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;

import butterknife.BindView;

public class CommentsAdapter extends BaseRecyclerViewAdapter<CommentsAdapter.ViewHolder, CommentEntity> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_comments_item, parent, false);
        return new ViewHolder(v, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.renderView(getItem(position));
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder<CommentEntity> {
        @BindView(R.id.comments_item) CommentsItemView commentsItemView;

        ViewHolder(View itemView, ViewGroup parent) {
            super(itemView, parent);
        }

        public void renderView(CommentEntity commentEntity) {
            commentsItemView.renderView(commentEntity);
        }
    }
}
