package com.github.chojmi.inspirations.presentation.profile.user_profile;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.utils.Preconditions;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDataMapper;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class UserPublicPhotosPresenter implements UserPublicPhotosContract.Presenter {
    private final UseCase<String, List<PhotoEntity>> getPhotosEntity;
    private final PhotoDataMapper photoDataMapper;
    private final String userId;
    private CompositeDisposable disposables;
    private UserPublicPhotosContract.View view;

    public UserPublicPhotosPresenter(@NonNull UseCase<String, List<PhotoEntity>> getPhotosEntity,
                                     @NonNull PhotoDataMapper photoDataMapper, @NonNull String userId) {
        this.getPhotosEntity = Preconditions.checkNotNull(getPhotosEntity);
        this.photoDataMapper = Preconditions.checkNotNull(photoDataMapper);
        this.userId = Preconditions.checkNotNull(userId);
    }

    @Override
    public void setView(@NonNull UserPublicPhotosContract.View view) {
        this.disposables = new CompositeDisposable();
        this.view = checkNotNull(view);
        fetchUserInfo();
    }

    @Override
    public void destroyView() {
        this.disposables.clear();
        this.view = null;
    }

    private void fetchUserInfo() {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User Id cannot be null");
        }
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
