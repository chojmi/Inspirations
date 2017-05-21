package com.github.chojmi.inspirations.domain.entity.photos;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;

import java.util.List;

public interface PhotoFavsEntity<V extends PersonEntity> {

    List<V> getPeople();

    int getPage();

    String getPhotoId();

    int getTotal();
}
