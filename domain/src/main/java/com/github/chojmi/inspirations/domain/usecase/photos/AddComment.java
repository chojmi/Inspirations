package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;
import com.github.chojmi.inspirations.domain.utils.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class AddComment implements UseCase<AddComment.Args, Void> {
    private final UseCaseProcessor<AddComment.Args, Void> processor;

    @Inject
    AddComment(PhotosDataSource photosDataSource, ThreadExecutor threadExecutor,
               PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<AddComment.Args, Void>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<Void> getUseCaseActionObservable(AddComment.Args args) {
                return photosDataSource.addComment(args.photoId, args.commentText);
            }
        };
    }

    @Override
    public Observable<SubmitUiModel<Void>> process(AddComment.Args args) {
        return processor.process(args);
    }

    public static class Args {
        private final String photoId;
        private final String commentText;

        private Args(@NonNull String photoId, @NonNull String commentText) {
            this.photoId = Preconditions.checkNotNull(photoId);
            this.commentText = Preconditions.checkNotNull(commentText);
        }

        public static AddComment.Args create(@NonNull String photoId, @NonNull String commentText) {
            return new AddComment.Args(photoId, commentText);
        }
    }
}
