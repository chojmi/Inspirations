package com.github.chojmi.inspirations.presentation.gallery.ui.comments;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.presentation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsItemView extends ConstraintLayout {
    @BindView(R.id.comment) TextView commentView;

    public CommentsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.gallery_comments_item_view, this);
        ButterKnife.bind(this);
    }

    public void renderView(CommentEntity commentEntity) {
        commentView.setText(commentEntity.getContent());
    }
}
