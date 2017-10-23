package com.github.chojmi.inspirations.presentation.photo;

import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.presentation.common.FavToggler;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDetailsMapper;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

class PhotoViewPresenter implements PhotoViewContract.Presenter {
    private final Photo photo;
    private final UseCase<GetPhotoFavs.Args, PhotoFavsEntity> getPhotoFavs;
    private final UseCase<String, PhotoInfoEntity> getPhotoInfo;
    private final UseCase<String, PersonEntity> getUserInfo;
    private final FavToggler favToggler;
    private final PhotoDataMapper photoDataMapper;
    private final PhotoDetailsMapper photoDetailsMapper;
    private CompositeDisposable disposables;
    private PhotoViewContract.View view;

    PhotoViewPresenter(Photo photo, @NonNull UseCase<GetPhotoFavs.Args, PhotoFavsEntity> getPhotoFavs,
                       @NonNull UseCase<String, PhotoInfoEntity> getPhotoInfo, @NonNull UseCase<String, PersonEntity> getUserInfo,
                       @NonNull FavToggler favToggler, @NonNull PhotoDetailsMapper photoDetailsMapper, @NonNull PhotoDataMapper photoDataMapper) {
        this.photo = photo;
        this.getPhotoFavs = checkNotNull(getPhotoFavs);
        this.getPhotoInfo = checkNotNull(getPhotoInfo);
        this.getUserInfo = checkNotNull(getUserInfo);
        this.favToggler = checkNotNull(favToggler);
        this.photoDataMapper = checkNotNull(photoDataMapper);
        this.photoDetailsMapper = checkNotNull(photoDetailsMapper);
    }

    @Override
    public void setView(@NonNull PhotoViewContract.View view) {
        this.disposables = new CompositeDisposable();
        this.view = checkNotNull(view);
        view.showPhoto(photo);
        loadFavs(photo);
        loadPhotoInfo(photo);
        loadUserData(photo);
    }

    @Override
    public void favIconSelected(boolean isFav) {
        disposables.add(favToggler.toggleFav(isFav, photo.getId()).subscribe(result -> loadPhotoInfo(photo), Timber::e));
    }

    private void loadFavs(Photo photo) {
        disposables.add(getPhotoFavs.process(GetPhotoFavs.Args.create(checkNotNull(photo).getId())).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.showFavs(photoDetailsMapper.transform(submitUiModel.getResult()));
            }
        }, Timber::e));
    }

    private void loadPhotoInfo(Photo photo) {
        disposables.add(getPhotoInfo.process(checkNotNull(photo).getId()).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.showPhotoInfo(submitUiModel.getResult());
            }
        }, Timber::d));
    }

    private void loadUserData(Photo photo) {
        disposables.add(getUserInfo.process(checkNotNull(photo).getOwnerId()).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.showUserData(photoDataMapper.transform(photo, submitUiModel.getResult()));
            }
        }, Timber::d));
    }

    @Override
    public void favsSelected() {
        view.goToFavs(photo);
    }

    @Override
    public void commentsSelected() {
        view.showComments(photo);
    }

    @Override
    public void destroyView() {
        this.favToggler.onDestroy();
        this.disposables.clear();
        this.view = null;
    }
}
