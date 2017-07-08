package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoComments;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.mapper.GalleryAttrsMapper;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

class GridPhotoAttrsPresenter implements GridPhotoAttrsContract.Presenter {
    private final GetPhotoFavs getPhotoFavs;
    private final GetPhotoComments getPhotoComments;
    private final GalleryAttrsMapper galleryAttrsMapper;
    private GridPhotoAttrsContract.View view;
    private CompositeDisposable disposables;

    GridPhotoAttrsPresenter(@NonNull GetPhotoFavs getPhotoFavs, @NonNull GetPhotoComments getPhotoComments, @NonNull GalleryAttrsMapper galleryAttrsMapper) {
        this.getPhotoFavs = checkNotNull(getPhotoFavs);
        this.getPhotoComments = checkNotNull(getPhotoComments);
        this.galleryAttrsMapper = checkNotNull(galleryAttrsMapper);
    }

    @Override
    public void setView(@NonNull GridPhotoAttrsContract.View view) {
        this.view = checkNotNull(view);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void loadFavs(int position, Photo photo) {
        disposables.add(getPhotoFavs.process(checkNotNull(photo).getId()).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.showFavs(position, galleryAttrsMapper.transform(submitUiModel.getResult()));
            }
        }, Timber::e));
    }

    @Override
    public void loadComments(int position, Photo photo) {
        disposables.add(getPhotoComments.process(checkNotNull(photo).getId()).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.showComments(position, galleryAttrsMapper.transform(submitUiModel.getResult()));
            }
        }, Timber::d));
    }

    @Override
    public void destroyView() {
        this.disposables.clear();
        this.view = null;
    }
}
