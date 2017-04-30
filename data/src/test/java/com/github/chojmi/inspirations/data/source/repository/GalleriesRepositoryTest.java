package com.github.chojmi.inspirations.data.source.repository;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

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
public class GalleriesRepositoryTest {

    private static final String FAKE_GALLERY_ID = "123";
    private static final String FAKE_URL = "www.url.pl";
    private static final String FAKE_TITLE = "fake_title";

    private GalleriesDataSource galleriesDataSource;

    @Mock
    private GalleriesDataSource mockLocalDataSource;
    @Mock
    private GalleriesDataSource mockRemoteDataSource;

    @Before
    public void setUp() {
        galleriesDataSource = new GalleriesRepository(mockRemoteDataSource, mockLocalDataSource);
    }

    @Test
    public void testGetGalleryFromRemoteHappyCase() {
        List<PhotoEntity> photos = getFakePhotoEntities();
        given(mockLocalDataSource.loadGallery(FAKE_GALLERY_ID, 1)).willReturn(Observable.just(Collections.emptyList()));
        given(mockRemoteDataSource.loadGallery(FAKE_GALLERY_ID, 1)).willReturn(Observable.just(photos));

        galleriesDataSource.loadGallery(FAKE_GALLERY_ID).test().assertResult(photos);

        verify(mockLocalDataSource, times(1)).loadGallery(FAKE_GALLERY_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadGallery(FAKE_GALLERY_ID, 1);
    }

    @Test
    public void testGetGalleryFromLocalHappyCase() {
        List<PhotoEntity> photos = getFakePhotoEntities();
        given(mockLocalDataSource.loadGallery(FAKE_GALLERY_ID, 1)).willReturn(Observable.just(photos));
        given(mockRemoteDataSource.loadGallery(FAKE_GALLERY_ID, 1)).willReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        galleriesDataSource.loadGallery(FAKE_GALLERY_ID).test().assertResult(photos);

        verify(mockLocalDataSource, times(1)).loadGallery(FAKE_GALLERY_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadGallery(FAKE_GALLERY_ID, 1);
    }

    @NonNull
    private List<PhotoEntity> getFakePhotoEntities() {
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
}