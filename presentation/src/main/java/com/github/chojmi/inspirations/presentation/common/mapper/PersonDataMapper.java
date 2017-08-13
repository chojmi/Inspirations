package com.github.chojmi.inspirations.presentation.common.mapper;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.presentation.gallery.model.Person;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
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
        Observable.fromIterable(people)
                .map(person -> transform(person))
                .toList()
                .subscribe(result::addAll, Timber::e);
        return result;
    }
}
