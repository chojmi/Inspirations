package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;

import java.util.List;

import io.reactivex.Observable;

public interface PhotosDataSource {
    Observable<List<PersonEntity>> loadPhotoFavs(String photoId);
}
