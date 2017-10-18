package com.github.chojmi.inspirations.presentation.gallery.ui.comments;

import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoComments;
import com.github.chojmi.inspirations.presentation.gallery.ui.GalleryScope;

import dagger.Module;
import dagger.Provides;

@GalleryScope
@Module
public class CommentsModule {
    @Provides
    CommentsContract.Presenter provideGridPhotoPresenter(CommentsActivity commentsActivity, GetPhotoComments getPhotoComments) {
        return new CommentsPresenter(commentsActivity.getArgPhotoId(), getPhotoComments);
    }
}
