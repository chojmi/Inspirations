package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.presentation.blueprints.exception.ViewNotFoundException;
import com.github.chojmi.inspirations.presentation.gallery.model.Person;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

class GridPhotoPresenter implements GridPhotoContract.Presenter {
    private final UseCase<String, List<Photo>> getPhotosCompoundUseCase;
    private CompositeDisposable disposables;
    private GridPhotoContract.View view;

    GridPhotoPresenter(@NonNull UseCase<String, List<Photo>> getPhotosCompoundUseCase) {
        this.getPhotosCompoundUseCase = checkNotNull(getPhotosCompoundUseCase);
    }

    @Override
    public void setView(@NonNull GridPhotoContract.View view) {
        this.view = checkNotNull(view);
        this.disposables = new CompositeDisposable();
        refreshPhotos("66956608@N06");
    }

    @Override
    public void refreshPhotos(@NonNull String userId) {
        if (view == null) {
            throw new ViewNotFoundException();
        }
        disposables.add(getPhotosCompoundUseCase.process(userId).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.showPhotos(submitUiModel.getResult());
            }
        }, Timber::e));
    }

    @Override
    public void photoSelected(Photo photo) {
        view.openPhotoView(photo);
    }

    @Override
    public void profileSelected(Person person) {
        view.openUserProfile(person);
    }

    @Override
    public void destroyView() {
        this.disposables.clear();
        this.view = null;
    }
}
