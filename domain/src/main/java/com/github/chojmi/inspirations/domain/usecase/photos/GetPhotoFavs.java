package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;
import com.github.chojmi.inspirations.domain.utils.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class GetPhotoFavs implements UseCase<GetPhotoFavs.Args, PhotoFavsEntity> {
    private final UseCaseProcessor<GetPhotoFavs.Args, PhotoFavsEntity> processor;

    @Inject
    GetPhotoFavs(PhotosDataSource photosDataSource, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<GetPhotoFavs.Args, PhotoFavsEntity>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<PhotoFavsEntity> getUseCaseActionObservable(GetPhotoFavs.Args args) {
                return photosDataSource.loadPhotoFavs(args.photoId, args.page);
            }
        };
    }

    @Override
    public Observable<SubmitUiModel<PhotoFavsEntity>> process(GetPhotoFavs.Args args) {
        return processor.process(args);
    }

    public static class Args {
        private final String photoId;
        private final int page;

        private Args(@NonNull String photoId, int page) {
            this.photoId = Preconditions.checkNotNull(photoId);
            this.page = page;
        }

        public static Args create(@NonNull String photoId) {
            return new Args(photoId, 0);
        }

        public static Args create(@NonNull String photoId, int page) {
            return new Args(photoId, page);
        }
    }
}
