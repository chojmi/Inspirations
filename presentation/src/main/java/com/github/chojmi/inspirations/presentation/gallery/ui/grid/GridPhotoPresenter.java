package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import android.widget.ImageView;

import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity;
import com.github.chojmi.inspirations.presentation.blueprints.exception.ViewNotFoundException;
import com.github.chojmi.inspirations.presentation.common.FavToggler;
import com.github.chojmi.inspirations.presentation.gallery.model.Person;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

class GridPhotoPresenter implements GridPhotoContract.Presenter {
    private final UseCase<String, List<PhotoWithAuthor>> getPhotosCompoundUseCase;
    private final FavToggler favToggler;
    private CompositeDisposable disposables;
    private GridPhotoContract.View view;

    GridPhotoPresenter(@NonNull UseCase<String, List<PhotoWithAuthor>> getPhotosCompoundUseCase,
                       @NonNull FavToggler favToggler) {
        this.getPhotosCompoundUseCase = checkNotNull(getPhotosCompoundUseCase);
        this.favToggler = checkNotNull(favToggler);
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
        disposables.add(getPhotosCompoundUseCase.process(userId)
                .doOnComplete(() -> view.toggleProgressBar(false))
                .subscribe(submitUiModel -> {
            view.toggleProgressBar(submitUiModel.isInProgress());
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.showPhotos(submitUiModel.getResult());
            }
        }, Timber::e));
    }

    @Override
    public void photoSelected(int position, ImageView imageView) {
        view.openPhotoView(position, imageView);
    }

    @Override
    public void profileSelected(Person person) {
        view.openUserProfile(person);
    }

    @Override
    public void favsSelected(PhotoWithAuthor photo) {
        view.goToFavs(photo.getPhoto());
    }

    @Override
    public void commentsSelected(PhotoWithAuthor photo) {
        view.showComments(photo);
    }

    @Override
    public void favIconSelected(int position, PhotoInfoEntity photo) {
        view.refreshFavSelection(position, favToggler.toggleFav(photo.isFav(), photo.getId()));
    }

    @Override
    public void commentIconSelected(PhotoWithAuthor photo) {
        view.addComment(photo);
    }

    @Override
    public void destroyView() {
        this.favToggler.onDestroy();
        this.disposables.clear();
        this.view = null;
    }
}
