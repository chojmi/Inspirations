package com.github.chojmi.inspirations.presentation.gallery.compound_usecase;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.presentation.gallery.mapper.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;

public class GetPhotosCompoundUseCase implements UseCase<String, List<PhotoWithAuthor>> {
    private final GetUserPublicPhotos getUserPublicPhotos;
    private final GetUserInfo getUserInfo;
    private final PhotoDataMapper photoDataMapper;

    public GetPhotosCompoundUseCase(GetUserPublicPhotos getUserPublicPhotos, GetUserInfo getUserInfo, PhotoDataMapper photoDataMapper) {
        this.getUserPublicPhotos = getUserPublicPhotos;
        this.getUserInfo = getUserInfo;
        this.photoDataMapper = photoDataMapper;
    }

    @Override
    public Observable<SubmitUiModel<List<PhotoWithAuthor>>> process(String userId) {
        return Observable.zip(getUserPublicPhotos.process(userId),
                getUserInfo.process(userId),
                new BiFunction<SubmitUiModel<List<PhotoEntity>>, SubmitUiModel<PersonEntity>, SubmitUiModel<List<PhotoWithAuthor>>>() {
                    @Override
                    public SubmitUiModel<List<PhotoWithAuthor>> apply(@NonNull SubmitUiModel<List<PhotoEntity>> publicPhotosModel,
                                                                      @NonNull SubmitUiModel<PersonEntity> userInfoModel) throws Exception {
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
                    }
                });
    }
}