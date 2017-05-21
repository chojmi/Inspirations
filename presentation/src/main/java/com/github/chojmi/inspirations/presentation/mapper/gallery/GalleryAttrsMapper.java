package com.github.chojmi.inspirations.presentation.mapper.gallery;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.presentation.model.gallery.PhotoFavs;

import javax.inject.Inject;

public class GalleryAttrsMapper {
    private final PersonDataMapper personDataMapper;

    @Inject
    public GalleryAttrsMapper(PersonDataMapper personDataMapper) {
        this.personDataMapper = personDataMapper;
    }

    public PhotoFavs transform(PhotoFavsEntity photoFavsEntity) {
        return PhotoFavs.create(personDataMapper.transform(photoFavsEntity.getPeople()), photoFavsEntity.getPage(), photoFavsEntity.getPhotoId(), photoFavsEntity.getTotal());
    }

}
