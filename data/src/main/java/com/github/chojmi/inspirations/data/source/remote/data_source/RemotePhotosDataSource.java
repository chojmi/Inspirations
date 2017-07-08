package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.source.remote.service.PhotosService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class RemotePhotosDataSource implements PhotosDataSource {

    private PhotosService photosService;
    private RemoteQueryProducer remoteQueryProducer;

    @Inject
    RemotePhotosDataSource(@NonNull PhotosService photosService, @NonNull RemoteQueryProducer remoteQueryProducer) {
        this.photosService = checkNotNull(photosService);
        this.remoteQueryProducer = checkNotNull(remoteQueryProducer);
    }

    @Override
    public Flowable<PhotoFavsEntity> loadPhotoFavs(String photoId) {
        return photosService.loadPhotoFavs(remoteQueryProducer.produceLoadPhotoFavsQuery(photoId))
                .subscribeOn(Schedulers.newThread())
                .map(photoFavsEntity -> photoFavsEntity);
    }

    @Override
    public Flowable<PhotoCommentsEntity> loadPhotoComments(String photoId) {
        return photosService.loadPhotoComments(remoteQueryProducer.produceLoadPhotoComments(photoId))
                .subscribeOn(Schedulers.newThread())
                .map(photoCommentsEntity -> photoCommentsEntity);
    }
}