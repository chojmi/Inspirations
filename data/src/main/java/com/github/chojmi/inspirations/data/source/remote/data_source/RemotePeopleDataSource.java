package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.service.PeopleService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class RemotePeopleDataSource implements PeopleDataSource {

    private PeopleService peopleService;
    private RemoteQueryProducer remoteQueryProducer;

    @Inject
    public RemotePeopleDataSource(@NonNull PeopleService peopleService, @NonNull RemoteQueryProducer remoteQueryProducer) {
        this.peopleService = checkNotNull(peopleService);
        this.remoteQueryProducer = checkNotNull(remoteQueryProducer);
    }

    @Override
    public Observable<PersonEntity> loadPersonInfo(String userId) {
        return peopleService.loadPersonInfo(remoteQueryProducer.produceLoadPersonInfoQuery(userId))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).map(personEntity -> personEntity);
    }

    @Override
    public Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId) {
        return loadUserPublicPhotos(userId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId, int page) {
        return peopleService.loadUserPublicPhotos(remoteQueryProducer.produceLoadUserPublicPhotosQuery(userId, page))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map((Function<GalleryEntityImpl, GalleryEntity>) galleryEntity -> galleryEntity)
                .map(GalleryEntity::getPhoto);
    }
}
