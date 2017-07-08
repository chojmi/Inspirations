package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.service.GalleriesService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class RemoteGalleriesDataSource implements GalleriesDataSource {

    private GalleriesService galleryService;
    private RemoteQueryProducer remoteQueryProducer;

    @Inject
    public RemoteGalleriesDataSource(@NonNull GalleriesService galleryService, @NonNull RemoteQueryProducer remoteQueryProducer) {
        this.galleryService = checkNotNull(galleryService);
        this.remoteQueryProducer = checkNotNull(remoteQueryProducer);
    }

    @Override
    public Flowable<List<PhotoEntity>> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Flowable<List<PhotoEntity>> loadGallery(String galleryId, int page) {
        return galleryService.loadGallery(remoteQueryProducer.produceLoadGalleryQuery(galleryId, page))
                .subscribeOn(Schedulers.newThread())
                .map((Function<GalleryEntityImpl, List<PhotoEntity>>) galleryEntity -> ((GalleryEntity) galleryEntity).getPhoto());
    }
}
