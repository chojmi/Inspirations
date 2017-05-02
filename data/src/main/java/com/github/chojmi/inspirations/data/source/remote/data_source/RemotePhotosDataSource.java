package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.entity.photos.CommentEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PersonEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.response.PhotoCommentsResponse;
import com.github.chojmi.inspirations.data.source.remote.service.PhotosService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static dagger.internal.Preconditions.checkNotNull;

public final class RemotePhotosDataSource implements PhotosDataSource {

    private PhotosService photosService;
    private RemoteQueryProducer remoteQueryProducer;

    @Inject
    RemotePhotosDataSource(@NonNull PhotosService photosService, @NonNull RemoteQueryProducer remoteQueryProducer) {
        this.photosService = checkNotNull(photosService);
        this.remoteQueryProducer = checkNotNull(remoteQueryProducer);
    }

    @Override
    public Observable<List<PersonEntity>> loadPhotoFavs(String photoId) {
        return photosService.loadPhotoFavs(remoteQueryProducer.produceLoadPhotoFavsQuery(photoId))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<PersonEntityImpl>, ObservableSource<List<PersonEntity>>>() {
                    @Override
                    public ObservableSource<List<PersonEntity>> apply(@NonNull List<PersonEntityImpl> personEntities) throws Exception {
                        return Observable.fromIterable(personEntities).map(personEntity -> (PersonEntity) personEntity).toList().toObservable();
                    }
                });
    }

    @Override
    public Observable<List<CommentEntity>> loadPhotoComments(String photoId) {
        return photosService.loadPhotoComments(remoteQueryProducer.produceLoadPhotoComments(photoId))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(PhotoCommentsResponse::getComments)
                .flatMap(new Function<List<CommentEntityImpl>, ObservableSource<List<CommentEntity>>>() {
                    @Override
                    public ObservableSource<List<CommentEntity>> apply(@NonNull List<CommentEntityImpl> commentEntities) throws Exception {
                        return Observable.fromIterable(commentEntities).map(commentEntity -> (CommentEntity) commentEntity).toList().toObservable();
                    }
                });
    }
}