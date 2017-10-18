package com.github.chojmi.inspirations.presentation.gallery.ui.comments;

import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoComments;
import com.github.chojmi.inspirations.domain.utils.Preconditions;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class CommentsPresenter implements CommentsContract.Presenter {
    private final String photoId;
    private final GetPhotoComments getPhotoComments;
    private final CompositeDisposable disposables;
    private CommentsContract.View view;

    public CommentsPresenter(@NonNull String photoId, @NonNull GetPhotoComments getPhotoComments) {
        this.photoId = Preconditions.checkNotNull(photoId);
        this.getPhotoComments = Preconditions.checkNotNull(getPhotoComments);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void setView(CommentsContract.View view) {
        this.view = Preconditions.checkNotNull(view);
        disposables.add(view.getBackBtnClicksObservable().subscribe(v -> view.closeView(), Timber::e));
        fetchComments();
    }

    private void fetchComments() {
        getPhotoComments.process(photoId).subscribe(submitUiModel -> {
            view.toggleProgressBar(submitUiModel.isInProgress());
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.renderView(submitUiModel.getResult());
            }
        });
    }

    @Override
    public void destroyView() {
        this.disposables.clear();
        this.view = null;
    }
}
