package com.github.chojmi.inspirations.presentation.gallery.mapper;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.presentation.gallery.model.Person;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import timber.log.Timber;

public class PersonDataMapper {

    @Inject
    public PersonDataMapper() {
    }

    public Person transform(PersonEntity person) {
        return new Person(person.getId(), person.getUsername(), person.getIconUrl());
    }

    public List<Person> transform(List<PersonEntity> people) {
        List<Person> result = new ArrayList<>();
        Flowable.fromIterable(people)
                .map(person -> transform(person))
                .toList()
                .subscribe(result::addAll, Timber::e);
        return result;
    }
}
