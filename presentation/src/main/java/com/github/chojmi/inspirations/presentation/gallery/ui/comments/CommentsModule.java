package com.github.chojmi.inspirations.presentation.gallery.ui.comments;

import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.domain.usecase.photos.AddComment;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoComments;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;
import com.github.chojmi.inspirations.presentation.gallery.ui.GalleryScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@GalleryScope
@Module
public class CommentsModule {
    @Provides
    CommentsContract.Presenter provideCommentsPresenter(CommentsActivity commentsActivity, GetPhotoComments getPhotoComments,
                                                        AddComment addComment) {
        return new CommentsPresenter(commentsActivity.getArgPhotoId(), getPhotoComments, addComment);
    }

    @Provides
    @Named("comments_adapter")
    BaseRecyclerViewAdapter<?, CommentEntity> provideCommentsAdapter() {
        return new CommentsAdapter();
    }
}
