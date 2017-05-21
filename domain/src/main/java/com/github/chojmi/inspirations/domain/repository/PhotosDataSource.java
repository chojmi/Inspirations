package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;

import java.util.List;

import io.reactivex.Observable;

public interface PhotosDataSource {
    Observable<PhotoFavsEntity> loadPhotoFavs(String photoId);

    Observable<List<CommentEntity>> loadPhotoComments(String photoId);
}
