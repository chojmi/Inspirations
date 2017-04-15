package com.github.chojmi.inspirations.data.source.remote;

import android.content.Context;

import com.github.chojmi.inspirations.domain.entity.gallery.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.gallery.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class RemotePeopleDataSource extends BaseRemoteDataSource implements PeopleDataSource {

    @Inject
    PeopleService peopleService;

    public RemotePeopleDataSource(Context context) {
        DaggerRemoteComponent.builder().restClientModule(new RestClientModule(context)).build().inject(this);
    }

    @Override
    public Observable<PersonEntity> loadPersonInfo(String userId) {
        Map<String, String> args = getBaseArgs("flickr.people.getInfo");
        args.put("user_id", userId);
        return peopleService.loadPersonInfo(args)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).map(personEntity -> personEntity);
    }

    @Override
    public Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId) {
        return loadUserPublicPhotos(userId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId, int page) {
        Map<String, String> args = getBaseArgs("flickr.people.getPublicPhotos");
        args.put("user_id", userId);
        args.put("page", String.valueOf(page));
        return peopleService.loadUserPublicPhotos(args)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).map(GalleryEntity::getPhoto);
    }
}
