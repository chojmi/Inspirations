package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoComments;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoSizeList;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDetailsMapper;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

class GridPhotoAttrsPresenter implements GridPhotoAttrsContract.Presenter {
    private final GetPhotoFavs getPhotoFavs;
    private final GetPhotoComments getPhotoComments;
    private final GetPhotoSizeList getPhotoSizeList;
    private final PhotoDetailsMapper photoDetailsMapper;
    private GridPhotoAttrsContract.View view;
    private CompositeDisposable disposables;

    GridPhotoAttrsPresenter(@NonNull GetPhotoFavs getPhotoFavs, @NonNull GetPhotoComments getPhotoComments,
                            @NonNull GetPhotoSizeList getPhotoSizeList, @NonNull PhotoDetailsMapper photoDetailsMapper) {
        this.getPhotoFavs = checkNotNull(getPhotoFavs);
        this.getPhotoComments = checkNotNull(getPhotoComments);
        this.getPhotoSizeList = checkNotNull(getPhotoSizeList);
        this.photoDetailsMapper = checkNotNull(photoDetailsMapper);
    }

    @Override
    public void setView(@NonNull GridPhotoAttrsContract.View view) {
        this.view = checkNotNull(view);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void loadFavs(int position, PhotoWithAuthor photo) {
        disposables.add(getPhotoFavs.process(checkNotNull(photo).getPhoto().getId()).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.showFavs(position, photoDetailsMapper.transform(submitUiModel.getResult()));
            }
        }, Timber::e));
    }

    @Override
    public void loadComments(int position, PhotoWithAuthor photo) {
        disposables.add(getPhotoComments.process(checkNotNull(photo).getPhoto().getId()).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.showComments(position, photoDetailsMapper.transform(submitUiModel.getResult()));
            }
        }, Timber::d));
    }

    @Override
    public void loadPhotoSizes(int position, PhotoWithAuthor photo) {
        disposables.add(getPhotoSizeList.process(checkNotNull(photo).getPhoto().getId()).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.showPhotoSizes(position, submitUiModel.getResult());
            }
        }, Timber::d));
    }

    @Override
    public void destroyView() {
        this.disposables.clear();
        this.view = null;
    }
}
