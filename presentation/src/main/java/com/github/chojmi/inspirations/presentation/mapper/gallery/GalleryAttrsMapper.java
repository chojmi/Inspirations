package com.github.chojmi.inspirations.presentation.mapper.gallery;

import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.presentation.model.gallery.Comment;
import com.github.chojmi.inspirations.presentation.model.gallery.PhotoComments;
import com.github.chojmi.inspirations.presentation.model.gallery.PhotoFavs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

public class GalleryAttrsMapper {
    private final PersonDataMapper personDataMapper;

    @Inject
    public GalleryAttrsMapper(PersonDataMapper personDataMapper) {
        this.personDataMapper = personDataMapper;
    }

    public PhotoFavs transform(PhotoFavsEntity photoFavsEntity) {
        return PhotoFavs.create(personDataMapper.transform(photoFavsEntity.getPeople()), photoFavsEntity.getPage(), photoFavsEntity.getPhotoId(), photoFavsEntity.getTotal());
    }

    public PhotoComments transform(PhotoCommentsEntity photoCommentsEntity) {
        return PhotoComments.create(transform(photoCommentsEntity.getComments()), photoCommentsEntity.getPhotoId());
    }

    private Comment transform(CommentEntity commentEntity) {
        return Comment.create(commentEntity.getAuthorname(), commentEntity.getContent());
    }

    private List<Comment> transform(List<CommentEntity> people) {
        List<Comment> result = new ArrayList<>();
        Observable.fromIterable(people)
                .map(comment -> transform(comment))
                .toList()
                .subscribe(result::addAll, Timber::e);
        return result;
    }
}
