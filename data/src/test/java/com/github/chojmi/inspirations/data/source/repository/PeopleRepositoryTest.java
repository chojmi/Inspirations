package com.github.chojmi.inspirations.data.source.repository;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

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
public class PeopleRepositoryTest {
    private static final String FAKE_USER_ID = "123";
    private static final String FAKE_URL = "www.url.pl";
    private static final String FAKE_TITLE = "fake_title";
    private static final String FAKE_USERNAME = "fake_username";
    private static final String FAKE_ICON_URL = "fake_icon_url";

    private PeopleDataSource galleriesDataSource;

    @Mock
    private PeopleDataSource mockLocalDataSource;
    @Mock
    private PeopleDataSource mockRemoteDataSource;

    @Before
    public void setUp() {
        galleriesDataSource = new PeopleRepository(mockRemoteDataSource, mockLocalDataSource);
    }

    @Test
    public void testGetPublicPhotosFromRemoteHappyCase() {
        List<PhotoEntity> photos = createFakePhotoEntities();
        given(mockLocalDataSource.loadUserPublicPhotos(FAKE_USER_ID, 1)).willReturn(Observable.just(Collections.emptyList()));
        given(mockRemoteDataSource.loadUserPublicPhotos(FAKE_USER_ID, 1)).willReturn(Observable.just(photos));

        galleriesDataSource.loadUserPublicPhotos(FAKE_USER_ID).test().assertResult(photos);

        verify(mockLocalDataSource, times(1)).loadUserPublicPhotos(FAKE_USER_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadUserPublicPhotos(FAKE_USER_ID, 1);
    }

    @Test
    public void testGetPublicPhotosFromLocalHappyCase() {
        List<PhotoEntity> photos = createFakePhotoEntities();
        given(mockLocalDataSource.loadUserPublicPhotos(FAKE_USER_ID, 1)).willReturn(Observable.just(photos));
        given(mockRemoteDataSource.loadUserPublicPhotos(FAKE_USER_ID, 1)).willReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        galleriesDataSource.loadUserPublicPhotos(FAKE_USER_ID).test().assertResult(photos);

        verify(mockLocalDataSource, times(1)).loadUserPublicPhotos(FAKE_USER_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadUserPublicPhotos(FAKE_USER_ID, 1);
    }

    @Test
    public void testGetPersonInfoFromRemoteHappyCase() {
        FakePersonEntity fakePersonEntity = new FakePersonEntity();
        given(mockLocalDataSource.loadPersonInfo(FAKE_USER_ID)).willReturn(Observable.empty());
        given(mockRemoteDataSource.loadPersonInfo(FAKE_USER_ID)).willReturn(Observable.just(fakePersonEntity));

        galleriesDataSource.loadPersonInfo(FAKE_USER_ID).test().assertResult(fakePersonEntity);

        verify(mockLocalDataSource, times(1)).loadPersonInfo(FAKE_USER_ID);
        verify(mockRemoteDataSource, times(1)).loadPersonInfo(FAKE_USER_ID);
    }

    @Test
    public void testGetPersonInfoFromLocalHappyCase() {
        FakePersonEntity fakePersonEntity = new FakePersonEntity();
        given(mockLocalDataSource.loadPersonInfo(FAKE_USER_ID)).willReturn(Observable.just(fakePersonEntity));
        given(mockRemoteDataSource.loadPersonInfo(FAKE_USER_ID)).willReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        galleriesDataSource.loadPersonInfo(FAKE_USER_ID).test().assertResult(fakePersonEntity);

        verify(mockLocalDataSource, times(1)).loadPersonInfo(FAKE_USER_ID);
        verify(mockRemoteDataSource, times(1)).loadPersonInfo(FAKE_USER_ID);
    }

    @NonNull
    private List<PhotoEntity> createFakePhotoEntities() {
        List<PhotoEntity> photos = new ArrayList<>();
        photos.add(new PhotoEntity() {
            @Override
            public String getUrl() {
                return FAKE_URL;
            }

            @Override
            public String getTitle() {
                return FAKE_TITLE;
            }
        });
        return photos;
    }

    private class FakePersonEntity implements PersonEntity {

        @Override
        public String getUsername() {
            return FAKE_USERNAME;
        }

        @Override
        public String getIconUrl() {
            return FAKE_ICON_URL;
        }
    }
}