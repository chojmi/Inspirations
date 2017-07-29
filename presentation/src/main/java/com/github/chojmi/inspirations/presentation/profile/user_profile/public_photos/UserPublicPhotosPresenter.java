package com.github.chojmi.inspirations.presentation.profile.user_profile.public_photos;

import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.utils.Preconditions;
import com.github.chojmi.inspirations.presentation.gallery.mapper.PhotoDataMapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class UserPublicPhotosPresenter implements UserPublicPhotosContract.Presenter {
    private final UseCase<String, List<PhotoEntity>> getPhotosEntity;
    private final String userId;
    private final PhotoDataMapper photoDataMapper;
    private CompositeDisposable disposables;
    private UserPublicPhotosContract.View view;

    public UserPublicPhotosPresenter(@NonNull UseCase<String, List<PhotoEntity>> getPhotosEntity,
                                     @NonNull String userId, @NonNull PhotoDataMapper photoDataMapper) {
        this.getPhotosEntity = Preconditions.checkNotNull(getPhotosEntity);
        this.userId = Preconditions.checkNotNull(userId);
        this.photoDataMapper = Preconditions.checkNotNull(photoDataMapper);
    }

    @Override
    public void setView(@NonNull UserPublicPhotosContract.View view) {
        this.view = checkNotNull(view);
        this.disposables = new CompositeDisposable();
        fetchUserInfo();
    }

    @Override
    public void destroyView() {
        this.disposables.clear();
        this.view = null;
    }

    private void fetchUserInfo() {
        disposables.add(getPhotosEntity.process(userId).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.renderView(photoDataMapper.transform(submitUiModel.getResult()));
            }
        }, Timber::e));
    }
}
