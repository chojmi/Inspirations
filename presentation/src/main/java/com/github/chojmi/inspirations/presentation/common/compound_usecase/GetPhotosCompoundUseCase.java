package com.github.chojmi.inspirations.presentation.common.compound_usecase;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.domain.utils.Preconditions;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class GetPhotosCompoundUseCase implements UseCase<String, List<PhotoWithAuthor>> {
    private final GetUserPublicPhotos getUserPublicPhotos;
    private final GetUserInfo getUserInfo;
    private final PhotoDataMapper photoDataMapper;

    public GetPhotosCompoundUseCase(@NonNull GetUserPublicPhotos getUserPublicPhotos, @NonNull GetUserInfo getUserInfo,
                                    @NonNull PhotoDataMapper photoDataMapper) {
        this.getUserPublicPhotos = Preconditions.checkNotNull(getUserPublicPhotos);
        this.getUserInfo = Preconditions.checkNotNull(getUserInfo);
        this.photoDataMapper = Preconditions.checkNotNull(photoDataMapper);
    }

    @Override
    public Observable<SubmitUiModel<List<PhotoWithAuthor>>> process(String userId) {
        return Observable.zip(getUserPublicPhotos.process(userId), getUserInfo.process(userId),
                (publicPhotosModel, userInfoModel) -> {
                    if (publicPhotosModel.isInProgress() || userInfoModel.isInProgress()) {
                        return SubmitUiModel.inProgress();
                    }
                    if (publicPhotosModel.getError() != null) {
                        return SubmitUiModel.fail(publicPhotosModel.getError());
                    }
                    if (userInfoModel.getError() != null) {
                        return SubmitUiModel.fail(userInfoModel.getError());
                    }
                    if (publicPhotosModel.isSucceed() && userInfoModel.isSucceed()) {
                        return SubmitUiModel.success(photoDataMapper.transform(publicPhotosModel.getResult(), userInfoModel.getResult()));
                    }
                    return SubmitUiModel.fail(new Throwable());
                });
    }
}