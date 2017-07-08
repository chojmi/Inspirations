package com.github.chojmi.inspirations.presentation.profile.user_profile;

import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.utils.Preconditions;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class UserProfilePresenter implements UserProfileContract.Presenter {
    private final UseCase<String, PersonEntity> getUserInfo;
    private final String userId;
    private CompositeDisposable disposables;
    private UserProfileContract.View view;

    public UserProfilePresenter(@NonNull UseCase<String, PersonEntity> getUserInfo, @NonNull String userId) {
        this.getUserInfo = Preconditions.checkNotNull(getUserInfo);
        this.userId = Preconditions.checkNotNull(userId);
    }

    @Override
    public void setView(@NonNull UserProfileContract.View view) {
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
        disposables.add(getUserInfo.process(userId).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.renderProfile(submitUiModel.getResult());
            }
        }, Timber::e));
    }
}
