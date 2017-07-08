package com.github.chojmi.inspirations.data.source.repository;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GalleriesRepositoryTest {
    private static final String FAKE_GALLERY_ID = "123";

    @Mock private GalleriesDataSource mockLocalDataSource;
    @Mock private GalleriesDataSource mockRemoteDataSource;
    @Mock private List<PhotoEntity> mockPhotoList;

    private GalleriesDataSource galleriesDataSource;

    @Before
    public void setUp() {
        galleriesDataSource = new GalleriesRepository(mockRemoteDataSource, mockLocalDataSource);
    }

    @Test
    public void testGetGalleryFromRemoteHappyCase() {
        when(mockPhotoList.size()).thenReturn(1);
        when(mockLocalDataSource.loadGallery(FAKE_GALLERY_ID, 1)).thenReturn(Flowable.just(Collections.emptyList()));
        when(mockRemoteDataSource.loadGallery(FAKE_GALLERY_ID, 1)).thenReturn(Flowable.just(mockPhotoList));

        galleriesDataSource.loadGallery(FAKE_GALLERY_ID).test().assertResult(mockPhotoList);

        verify(mockLocalDataSource, times(1)).loadGallery(FAKE_GALLERY_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadGallery(FAKE_GALLERY_ID, 1);
    }

    @Test
    public void testGetGalleryFromLocalHappyCase() {
        when(mockPhotoList.size()).thenReturn(1);
        when(mockLocalDataSource.loadGallery(FAKE_GALLERY_ID, 1)).thenReturn(Flowable.just(mockPhotoList));
        when(mockRemoteDataSource.loadGallery(FAKE_GALLERY_ID, 1)).thenReturn(Flowable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }, BackpressureStrategy.BUFFER));

        galleriesDataSource.loadGallery(FAKE_GALLERY_ID).test().assertResult(mockPhotoList);

        verify(mockLocalDataSource, times(1)).loadGallery(FAKE_GALLERY_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadGallery(FAKE_GALLERY_ID, 1);
    }
}