package com.github.chojmi.inspirations.presentation.gallery.ui.comments;

import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.usecase.photos.AddComment;
import com.github.chojmi.inspirations.domain.utils.Preconditions;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class CommentsPresenter implements CommentsContract.Presenter {
    private final String photoId;
    private final UseCase<String, PhotoCommentsEntity> getPhotoComments;
    private final UseCase<AddComment.Args, Void> addComment;
    private final CompositeDisposable disposables;
    private CommentsContract.View view;

    public CommentsPresenter(@NonNull String photoId, @NonNull UseCase<String, PhotoCommentsEntity> getPhotoComments,
                             @NonNull UseCase<AddComment.Args, Void> addComment) {
        this.photoId = Preconditions.checkNotNull(photoId);
        this.getPhotoComments = Preconditions.checkNotNull(getPhotoComments);
        this.addComment = Preconditions.checkNotNull(addComment);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void setView(CommentsContract.View view) {
        this.view = Preconditions.checkNotNull(view);
        disposables.add(view.getBackBtnClicksObservable().subscribe(v -> view.closeView(), Timber::e));
        fetchComments();
    }

    private void fetchComments() {
        disposables.add(getPhotoComments.process(photoId).subscribe(submitUiModel -> {
            view.toggleProgressBar(submitUiModel.isInProgress());
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.renderView(submitUiModel.getResult());
            }
        }, Timber::e));
    }

    @Override
    public void addComment(String commentText) {
        if (commentText.isEmpty()) {
            return;
        }
        disposables.add(addComment.process(AddComment.Args.create(photoId, commentText))
                .subscribe(submitUiModel -> {
                    if (submitUiModel.isInProgress()) {
                        return;
                    }
                    if (submitUiModel.isSucceed()) {
                        fetchComments();
                        view.clearComment();
                    }
                }, Timber::e));
    }

    @Override
    public void destroyView() {
        this.disposables.clear();
        this.view = null;
    }
}
