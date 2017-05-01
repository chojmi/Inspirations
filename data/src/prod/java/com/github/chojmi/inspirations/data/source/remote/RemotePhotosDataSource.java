package com.github.chojmi.inspirations.data.source.remote;

import android.content.Context;

import com.github.chojmi.inspirations.data.entity.photos.PersonEntityImpl;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public final class RemotePhotosDataSource extends BaseRemoteDataSource implements PhotosDataSource {

    @Inject
    PhotosService photosService;

    public RemotePhotosDataSource(Context context) {
        DaggerRemoteComponent.builder().restClientModule(new RestClientModule(context)).build().inject(this);
    }

    @Override
    public Observable<List<PersonEntity>> loadPhotoFavs(String photoId) {
        Map<String, String> args = getBaseArgs("flickr.photos.getFavorites");
        args.put("photo_id", photoId);
        return photosService.loadPhotoFavs(args)
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
        return null;
    }
}