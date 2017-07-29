package com.github.chojmi.inspirations.presentation.search.photos;

import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.utils.Preconditions;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class SearchPhotosPresenter implements SearchPhotosContract.Presenter {
    private final UseCase<String, GalleryEntity> getGalleryEntity;
    private CompositeDisposable disposables;
    private SearchPhotosContract.View view;

    public SearchPhotosPresenter(@NonNull UseCase<String, GalleryEntity> getGalleryEntity) {
        this.getGalleryEntity = Preconditions.checkNotNull(getGalleryEntity);
    }

    @Override
    public void setView(@NonNull SearchPhotosContract.View view) {
        this.view = checkNotNull(view);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void destroyView() {
        this.disposables.clear();
        this.view = null;
    }

    @Override
    public void search(String text) {
        disposables.add(getGalleryEntity.process(text).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.renderView(submitUiModel.getResult());
            }
        }, Timber::e));
    }
}
