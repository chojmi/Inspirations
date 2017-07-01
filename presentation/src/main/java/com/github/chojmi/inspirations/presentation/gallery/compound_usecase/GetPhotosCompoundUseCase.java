package com.github.chojmi.inspirations.presentation.gallery.compound_usecase;

import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitEvent;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitUiModel;
import com.github.chojmi.inspirations.domain.usecase.blueprints.DefaultObserver;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.presentation.gallery.mapper.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.google.auto.value.AutoValue;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.BiFunction;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class GetPhotosCompoundUseCase {
    private final GetUserPublicPhotos getUserPublicPhotos;
    private final GetUserInfo getUserInfo;
    private final PhotoDataMapper photoDataMapper;

    public GetPhotosCompoundUseCase(GetUserPublicPhotos getUserPublicPhotos, GetUserInfo getUserInfo, PhotoDataMapper photoDataMapper) {
        this.getUserPublicPhotos = getUserPublicPhotos;
        this.getUserInfo = getUserInfo;
        this.photoDataMapper = photoDataMapper;
    }

    public Observable<SubmitUiModel> build(String userId) {
        return Observable.zip(
                Observable.create(e -> getUserPublicPhotos.execute(DefaultObserver.create(e), GetUserPublicPhotos.SubmitEvent.createObservable(checkNotNull(userId)))),
                Observable.create(e -> getUserInfo.execute(DefaultObserver.create(e), GetUserInfo.SubmitEvent.createObservable(checkNotNull(userId)))),
                (BiFunction<GetUserPublicPhotos.SubmitUiModel, GetUserInfo.SubmitUiModel, SubmitUiModel>) (publicPhotosModel, userInfoModel) -> {
                    if (publicPhotosModel.isInProgress() || userInfoModel.isInProgress()) {
                        return SubmitUiModel.inProgress();
                    }
                    if (publicPhotosModel.getErrorMessage() != null) {
                        return SubmitUiModel.failure(publicPhotosModel.getErrorMessage());
                    }
                    if (userInfoModel.getErrorMessage() != null) {
                        return SubmitUiModel.failure(userInfoModel.getErrorMessage());
                    }
                    if (publicPhotosModel.isSuccess() && userInfoModel.isSuccess()) {
                        return SubmitUiModel.success(photoDataMapper.transform(publicPhotosModel.getResult(), userInfoModel.getResult()));
                    }
                    return SubmitUiModel.failure(new Throwable());
                });
    }

    public void dispose() {
        this.getUserPublicPhotos.dispose();
        this.getUserInfo.dispose();
    }

    @AutoValue
    public static abstract class SubmitEvent extends BaseSubmitEvent {
        public static Observable<SubmitEvent> createObservable(@NonNull String userId) {
            return Observable.fromCallable(() -> create(userId));
        }

        public static SubmitEvent create(@NonNull String userId) {
            return new AutoValue_GetPhotosCompoundUseCase_SubmitEvent(checkNotNull(userId));
        }

        abstract String getUserId();
    }

    @AutoValue
    public static abstract class SubmitUiModel extends BaseSubmitUiModel {
        public static SubmitUiModel inProgress() {
            return new AutoValue_GetPhotosCompoundUseCase_SubmitUiModel(true, false, null, null);
        }

        public static SubmitUiModel failure(Throwable t) {
            return new AutoValue_GetPhotosCompoundUseCase_SubmitUiModel(false, false, t, null);
        }

        public static SubmitUiModel success(List<Photo> photos) {
            return new AutoValue_GetPhotosCompoundUseCase_SubmitUiModel(false, true, null, photos);
        }

        @Nullable
        public abstract List<Photo> getPhotos();
    }
}