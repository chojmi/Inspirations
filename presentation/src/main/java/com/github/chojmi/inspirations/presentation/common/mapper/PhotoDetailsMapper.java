package com.github.chojmi.inspirations.presentation.common.mapper;

import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.presentation.gallery.model.Comment;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoComments;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

public class PhotoDetailsMapper {
    private final PersonDataMapper personDataMapper;

    @Inject
    public PhotoDetailsMapper(PersonDataMapper personDataMapper) {
        this.personDataMapper = personDataMapper;
    }

    public PhotoFavs transform(PhotoFavsEntity photoFavsEntity) {
        return new PhotoFavs(personDataMapper.transform(photoFavsEntity.getPeople()), photoFavsEntity.getPage(), photoFavsEntity.getPhotoId(), photoFavsEntity.getTotal());
    }

    public PhotoComments transform(PhotoCommentsEntity photoCommentsEntity) {
        return new PhotoComments(transform(photoCommentsEntity.getComments()), photoCommentsEntity.getPhotoId());
    }

    private Comment transform(CommentEntity commentEntity) {
        return new Comment(commentEntity.getAuthorname(), commentEntity.getContent());
    }

    private List<Comment> transform(List<CommentEntity> people) {
        List<Comment> result = new ArrayList<>();
        Observable.fromIterable(people)
                .map(this::transform)
                .toList()
                .subscribe(result::addAll, Timber::e);
        return result;
    }
}
