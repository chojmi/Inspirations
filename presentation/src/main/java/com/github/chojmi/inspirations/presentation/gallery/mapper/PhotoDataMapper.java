package com.github.chojmi.inspirations.presentation.gallery.mapper;

import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.presentation.gallery.model.Person;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

public class PhotoDataMapper {

    @Inject
    public PhotoDataMapper() {
    }

    private Photo transform(PhotoEntity photo, PersonEntity person) {
        return new Photo(photo.getId(), photo.getTitle(), photo.getUrl(), new Person(person.getUsername(), person.getIconUrl()));
    }

    public List<Photo> transform(List<PhotoEntity> photos, PersonEntity person) {
        List<Photo> result = new ArrayList<>();
        Observable.fromIterable(photos)
                .map(photoEntity -> transform(photoEntity, person))
                .toList()
                .subscribe(result::addAll, Timber::e);
        return result;
    }

    public List<Photo> transform(GalleryEntity galleryEntity, PersonEntity person) {
        return transform(galleryEntity.getPhoto(), person);
    }
}