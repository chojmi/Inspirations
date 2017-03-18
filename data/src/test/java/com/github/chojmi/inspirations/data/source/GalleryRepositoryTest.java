package com.github.chojmi.inspirations.data.source;

import com.github.chojmi.inspirations.data.entity.mapper.GalleryEntityDataMapper;
import com.github.chojmi.inspirations.domain.model.Photo;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GalleryRepositoryTest {

    private static final String FAKE_GALLERY_ID = "123";
    private static final String FAKE_URL = "www.url.pl";

    private GalleryDataSource galleryRepository;

    @Mock
    private GalleryEntityDataMapper mockGalleryEntityDataMapper;
    @Mock
    private GalleryDataSource mockLocalDataSource;
    @Mock
    private GalleryDataSource mockRemoteDataSource;

    @Before
    public void setUp() {
        galleryRepository = new GalleryRepository(mockRemoteDataSource, mockLocalDataSource);
    }

    @Test
    public void testGetGalleryFromRemoteHappyCase() {
        List<Photo> photos = new ArrayList<>();
        photos.add(Photo.create(FAKE_URL));
        given(mockLocalDataSource.loadGallery(FAKE_GALLERY_ID, 1)).willReturn(Observable.just(Collections.emptyList()));
        given(mockRemoteDataSource.loadGallery(FAKE_GALLERY_ID, 1)).willReturn(Observable.just(photos));

        galleryRepository.loadGallery(FAKE_GALLERY_ID).test().assertResult(photos);

        verify(mockLocalDataSource, times(1)).loadGallery(FAKE_GALLERY_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadGallery(FAKE_GALLERY_ID, 1);
    }

    @Test
    public void testGetGalleryFromLocalHappyCase() {
        List<Photo> photos = new ArrayList<>();
        photos.add(Photo.create(FAKE_URL));
        given(mockLocalDataSource.loadGallery(FAKE_GALLERY_ID, 1)).willReturn(Observable.just(photos));
        given(mockRemoteDataSource.loadGallery(FAKE_GALLERY_ID, 1)).willReturn(Observable.create(new ObservableOnSubscribe<List<Photo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Photo>> e) throws Exception {
                throw new IllegalStateException("Shouldn't be invoked");
            }
        }));

        galleryRepository.loadGallery(FAKE_GALLERY_ID).test().assertResult(photos);

        verify(mockLocalDataSource, times(1)).loadGallery(FAKE_GALLERY_ID, 1);
        verify(mockRemoteDataSource, times(1)).loadGallery(FAKE_GALLERY_ID, 1);
    }
}