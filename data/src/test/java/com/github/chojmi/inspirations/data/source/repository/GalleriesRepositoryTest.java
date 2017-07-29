package com.github.chojmi.inspirations.data.source.repository;

import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GalleriesRepositoryTest {
    private static final String FAKE_GALLERY_ID = "123";
    private static final String FAKE_USER_ID = "123";

    @Mock private GalleriesDataSource mockLocalDataSource;
    @Mock private GalleriesDataSource mockRemoteDataSource;
    @Mock private List<PhotoEntity> mockPhotoList;
    @Mock private GalleryEntity mockGalleryEntity;

    private GalleriesDataSource galleriesDataSource;

    @Before
    public void setUp() {
        galleriesDataSource = new GalleriesRepository(mockRemoteDataSource, mockLocalDataSource);
    }

    @Test
    public void testGetGalleryByGalleryIdFromRemoteHappyCase() {
        when(mockLocalDataSource.loadGalleryByGalleryId(FAKE_GALLERY_ID, 1)).thenReturn(Observable.empty());
        when(mockRemoteDataSource.loadGalleryByGalleryId(FAKE_GALLERY_ID, 1)).thenReturn(Observable.just(mockPhotoList));

        galleriesDataSource.loadGalleryByGalleryId(FAKE_GALLERY_ID).test().assertResult(mockPhotoList);

        verify(mockLocalDataSource, times(1)).loadGalleryByGalleryId(FAKE_GALLERY_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadGalleryByGalleryId(FAKE_GALLERY_ID, 1);
    }

    @Test
    public void testGetGalleryByGalleryIdFromLocalHappyCase() {
        when(mockLocalDataSource.loadGalleryByGalleryId(FAKE_GALLERY_ID, 1)).thenReturn(Observable.just(mockPhotoList));
        when(mockRemoteDataSource.loadGalleryByGalleryId(FAKE_GALLERY_ID, 1)).thenReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        galleriesDataSource.loadGalleryByGalleryId(FAKE_GALLERY_ID).test().assertResult(mockPhotoList);

        verify(mockLocalDataSource, times(1)).loadGalleryByGalleryId(FAKE_GALLERY_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadGalleryByGalleryId(FAKE_GALLERY_ID, 1);
    }

    @Test
    public void testGetGalleryByUserIdFromRemoteHappyCase() {
        when(mockLocalDataSource.loadGalleryByUserId(FAKE_USER_ID, 1)).thenReturn(Observable.empty());
        when(mockRemoteDataSource.loadGalleryByUserId(FAKE_USER_ID, 1)).thenReturn(Observable.just(mockGalleryEntity));

        galleriesDataSource.loadGalleryByUserId(FAKE_GALLERY_ID).test().assertResult(mockGalleryEntity);

        verify(mockLocalDataSource, times(1)).loadGalleryByUserId(FAKE_USER_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadGalleryByUserId(FAKE_USER_ID, 1);
    }

    @Test
    public void testGetGalleryByUserIdFromLocalHappyCase() {
        when(mockLocalDataSource.loadGalleryByUserId(FAKE_USER_ID, 1)).thenReturn(Observable.just(mockGalleryEntity));
        when(mockRemoteDataSource.loadGalleryByUserId(FAKE_USER_ID, 1)).thenReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        galleriesDataSource.loadGalleryByUserId(FAKE_GALLERY_ID).test().assertResult(mockGalleryEntity);

        verify(mockLocalDataSource, times(1)).loadGalleryByUserId(FAKE_USER_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadGalleryByUserId(FAKE_USER_ID, 1);
    }
}