package com.github.chojmi.inspirations.data.source.repository;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PhotosRepositoryTest {
    private static final String FAKE_PHOTO_ID = "123";
    private static final String FAKE_USERNAME = "fake_username";
    private static final String FAKE_ICON_URL = "fake_icon_url";

    private PhotosDataSource photosDataSource;

    @Mock
    private PhotosDataSource mockLocalDataSource;
    @Mock
    private PhotosDataSource mockRemoteDataSource;

    @Before
    public void setUp() {
        photosDataSource = new PhotosRepository(mockRemoteDataSource, mockLocalDataSource);
    }

    @Test
    public void testGetGalleryFromRemoteHappyCase() {
        final List<PersonEntity> favs = getFakeFavs();
        given(mockLocalDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).willReturn(Observable.just(Collections.emptyList()));
        given(mockRemoteDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).willReturn(Observable.just(favs));

        photosDataSource.loadPhotoFavs(FAKE_PHOTO_ID).test().assertResult(favs);

        verify(mockLocalDataSource, times(1)).loadPhotoFavs(FAKE_PHOTO_ID);
        verify(mockRemoteDataSource, times(1)).loadPhotoFavs(FAKE_PHOTO_ID);
    }

    @Test
    public void testGetGalleryFromLocalHappyCase() {
        final List<PersonEntity> favs = getFakeFavs();
        given(mockLocalDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).willReturn(Observable.just(favs));
        given(mockRemoteDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).willReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        photosDataSource.loadPhotoFavs(FAKE_PHOTO_ID).test().assertResult(favs);

        verify(mockLocalDataSource, times(1)).loadPhotoFavs(FAKE_PHOTO_ID);
        verify(mockRemoteDataSource, times(1)).loadPhotoFavs(FAKE_PHOTO_ID);
    }

    @NonNull
    private List<PersonEntity> getFakeFavs() {
        List<PersonEntity> photos = new ArrayList<>();
        photos.add(new PersonEntity() {
            @Override
            public String getUsername() {
                return FAKE_USERNAME;
            }

            @Override
            public String getIconUrl() {
                return FAKE_ICON_URL;
            }
        });
        return photos;
    }
}