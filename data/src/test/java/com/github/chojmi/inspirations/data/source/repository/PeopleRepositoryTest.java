package com.github.chojmi.inspirations.data.source.repository;

import com.github.chojmi.inspirations.data.entity.people.PersonEntityImpl;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PeopleRepositoryTest {
    private static final String FAKE_USER_ID = "123";

    @Mock private PeopleDataSource mockLocalDataSource;
    @Mock private PeopleDataSource mockRemoteDataSource;
    @Mock private List<PhotoEntity> mockPhotoList;

    private PeopleDataSource galleriesDataSource;

    @Before
    public void setUp() {
        galleriesDataSource = new PeopleRepository(mockRemoteDataSource, mockLocalDataSource);
    }

    @Test
    public void testGetPublicPhotosFromRemoteHappyCase() {
        when(mockLocalDataSource.loadUserPublicPhotos(FAKE_USER_ID, 1)).thenReturn(Observable.empty());
        when(mockRemoteDataSource.loadUserPublicPhotos(FAKE_USER_ID, 1)).thenReturn(Observable.just(mockPhotoList));

        galleriesDataSource.loadUserPublicPhotos(FAKE_USER_ID).test().assertResult(mockPhotoList);

        verify(mockLocalDataSource, times(1)).loadUserPublicPhotos(FAKE_USER_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadUserPublicPhotos(FAKE_USER_ID, 1);
    }

    @Test
    public void testGetPublicPhotosFromLocalHappyCase() {
        when(mockLocalDataSource.loadUserPublicPhotos(FAKE_USER_ID, 1)).thenReturn(Observable.just(mockPhotoList));
        when(mockRemoteDataSource.loadUserPublicPhotos(FAKE_USER_ID, 1)).thenReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        galleriesDataSource.loadUserPublicPhotos(FAKE_USER_ID).test().assertResult(mockPhotoList);

        verify(mockLocalDataSource, times(1)).loadUserPublicPhotos(FAKE_USER_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadUserPublicPhotos(FAKE_USER_ID, 1);
    }

    @Test
    public void testGetPersonInfoFromRemoteHappyCase() {
        PersonEntityImpl mockPersonEntity = Mockito.mock(PersonEntityImpl.class);
        when(mockLocalDataSource.loadPersonInfo(FAKE_USER_ID)).thenReturn(Observable.empty());
        when(mockRemoteDataSource.loadPersonInfo(FAKE_USER_ID)).thenReturn(Observable.just(mockPersonEntity));

        galleriesDataSource.loadPersonInfo(FAKE_USER_ID).test().assertResult(mockPersonEntity);

        verify(mockLocalDataSource, times(1)).loadPersonInfo(FAKE_USER_ID);
        verify(mockRemoteDataSource, times(1)).loadPersonInfo(FAKE_USER_ID);
    }

    @Test
    public void testGetPersonInfoFromLocalHappyCase() {
        PersonEntityImpl mockPersonEntity = Mockito.mock(PersonEntityImpl.class);
        when(mockLocalDataSource.loadPersonInfo(FAKE_USER_ID)).thenReturn(Observable.just(mockPersonEntity));
        when(mockRemoteDataSource.loadPersonInfo(FAKE_USER_ID)).thenReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        galleriesDataSource.loadPersonInfo(FAKE_USER_ID).test().assertResult(mockPersonEntity);

        verify(mockLocalDataSource, times(1)).loadPersonInfo(FAKE_USER_ID);
        verify(mockRemoteDataSource, times(1)).loadPersonInfo(FAKE_USER_ID);
    }
}