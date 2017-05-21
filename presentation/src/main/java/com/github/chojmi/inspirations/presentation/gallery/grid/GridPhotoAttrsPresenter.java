package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoComments;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.presentation.mapper.gallery.GalleryAttrsMapper;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static dagger.internal.Preconditions.checkNotNull;

class GridPhotoAttrsPresenter implements GridPhotoAttrsContract.Presenter {
    private final GetPhotoFavs getPhotoFavs;
    private final GetPhotoComments getPhotoComments;
    private final GalleryAttrsMapper galleryAttrsMapper;
    private GridPhotoAttrsContract.View view;

    GridPhotoAttrsPresenter(@NonNull GetPhotoFavs getPhotoFavs, @NonNull GetPhotoComments getPhotoComments, @NonNull GalleryAttrsMapper galleryAttrsMapper) {
        this.getPhotoFavs = checkNotNull(getPhotoFavs);
        this.getPhotoComments = checkNotNull(getPhotoComments);
        this.galleryAttrsMapper = checkNotNull(galleryAttrsMapper);
    }

    @Override
    public void setView(@NonNull GridPhotoAttrsContract.View view) {
        this.view = checkNotNull(view);
    }

    @Override
    public void loadFavs(int position, Photo photo) {
        getPhotoFavs.buildUseCaseObservable(GetPhotoFavs.SubmitEvent.createObservable(checkNotNull(photo).getId())).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSuccess()) {
                view.showFavs(position, galleryAttrsMapper.transform(submitUiModel.getResult()));
            }
        });
    }

    @Override
    public void loadComments(int position, Photo photo) {
        getPhotoComments.buildUseCaseObservable(GetPhotoComments.SubmitEvent.createObservable(checkNotNull(photo).getId())).subscribe(new Consumer<GetPhotoComments.SubmitUiModel>() {
            @Override
            public void accept(@NonNull GetPhotoComments.SubmitUiModel submitUiModel) throws Exception {
                if (submitUiModel.isInProgress()) {
                    return;
                }
                if (submitUiModel.isSuccess()) {
                    view.showComments(position, galleryAttrsMapper.transform(submitUiModel.getResult()));
                }
            }
        });
    }

    @Override
    public void destroyView() {
        this.getPhotoFavs.dispose();
        this.getPhotoComments.dispose();
        this.view = null;
    }
}
