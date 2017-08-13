package com.github.chojmi.inspirations.presentation.photo;


import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoComments;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDetailsMapper;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

class PhotoViewPresenter implements PhotoViewContract.Presenter {
    private final Photo photo;
    private final GetPhotoFavs getPhotoFavs;
    private final GetPhotoComments getPhotoComments;
    private final PhotoDetailsMapper photoDetailsMapper;
    private PhotoViewContract.View view;
    private CompositeDisposable disposables;

    PhotoViewPresenter(Photo photo, @NonNull GetPhotoFavs getPhotoFavs,
                       @NonNull GetPhotoComments getPhotoComments, @NonNull PhotoDetailsMapper photoDetailsMapper) {
        this.photo = photo;
        this.getPhotoFavs = checkNotNull(getPhotoFavs);
        this.getPhotoComments = checkNotNull(getPhotoComments);
        this.photoDetailsMapper = checkNotNull(photoDetailsMapper);
    }

    @Override
    public void setView(@NonNull PhotoViewContract.View view) {
        this.view = checkNotNull(view);
        this.disposables = new CompositeDisposable();
        view.showPhoto(photo);
        loadFavs(photo);
        loadComments(photo);
    }

    private void loadFavs(Photo photo) {
        disposables.add(getPhotoFavs.process(checkNotNull(photo).getId()).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.showFavs(photoDetailsMapper.transform(submitUiModel.getResult()));
            }
        }, Timber::e));
    }

    private void loadComments(Photo photo) {
        disposables.add(getPhotoComments.process(checkNotNull(photo).getId()).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.showComments(photoDetailsMapper.transform(submitUiModel.getResult()));
            }
        }, Timber::d));
    }

    @Override
    public void destroyView() {
        this.disposables.clear();
        this.view = null;
    }
}
