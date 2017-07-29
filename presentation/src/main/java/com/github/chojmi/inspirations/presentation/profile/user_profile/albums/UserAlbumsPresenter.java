package com.github.chojmi.inspirations.presentation.profile.user_profile.albums;

import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.utils.Preconditions;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class UserAlbumsPresenter implements UserAlbumsContract.Presenter {
    private final UseCase<String, GalleryEntity> getGalleryEntity;
    private final String userId;
    private CompositeDisposable disposables;
    private UserAlbumsContract.View view;

    public UserAlbumsPresenter(@NonNull UseCase<String, GalleryEntity> getGalleryEntity, @NonNull String userId) {
        this.getGalleryEntity = Preconditions.checkNotNull(getGalleryEntity);
        this.userId = Preconditions.checkNotNull(userId);
    }

    @Override
    public void setView(@NonNull UserAlbumsContract.View view) {
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
        disposables.add(getGalleryEntity.process(userId).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.renderView(submitUiModel.getResult());
            }
        }, Timber::e));
    }
}
