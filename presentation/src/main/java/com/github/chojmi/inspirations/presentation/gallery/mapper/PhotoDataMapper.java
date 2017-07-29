package com.github.chojmi.inspirations.presentation.gallery.mapper;

import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.presentation.gallery.model.Person;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

public class PhotoDataMapper {

    @Inject
    public PhotoDataMapper() {
    }

    private PhotoWithAuthor transform(PhotoEntity photo, PersonEntity person) {
        return new PhotoWithAuthor(transform(photo), new Person(person.getId(), person.getUsername(), person.getIconUrl()));
    }

    private Photo transform(PhotoEntity photoEntity) {
        return new Photo(photoEntity.getId(), photoEntity.getTitle(), photoEntity.getUrl());
    }

    public List<PhotoWithAuthor> transform(List<PhotoEntity> photos, PersonEntity person) {
        List<PhotoWithAuthor> result = new ArrayList<>();
        Observable.fromIterable(photos)
                .map(photoEntity -> transform(photoEntity, person))
                .toList()
                .subscribe(result::addAll, Timber::e);
        return result;
    }

    public List<Photo> transform(List<PhotoEntity> photoEntities) {
        List<Photo> result = new ArrayList<>();
        Observable.fromIterable(photoEntities)
                .map(this::transform)
                .toList()
                .subscribe(result::addAll, Timber::e);
        return result;
    }

    public List<Photo> transform(GalleryEntity<PhotoEntity> galleryEntity) {
        List<Photo> result = new ArrayList<>();
        Observable.fromIterable(galleryEntity.getPhoto())
                .map(this::transform)
                .toList()
                .subscribe(result::addAll, Timber::e);
        return result;
    }
}