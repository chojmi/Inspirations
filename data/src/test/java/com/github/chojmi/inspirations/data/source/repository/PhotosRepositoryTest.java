package com.github.chojmi.inspirations.data.source.repository;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoCommentsEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoFavsEntityImpl;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhotosRepositoryTest {
    private static final String FAKE_PHOTO_ID = "123";
    private static final int FAKE_PAGE = 0;
    private static final String FAKE_QUERY = "fake_query";

    @Mock private PhotosDataSource mockLocalDataSource;
    @Mock private PhotosDataSource mockRemoteDataSource;

    @Mock private PhotoFavsEntityImpl mockLocalPhotoFavsEntity;
    @Mock private PhotoFavsEntityImpl mockRemotePhotoFavsEntity;
    @Mock private PhotoCommentsEntityImpl mockLocalPhotoCommentsEntity;
    @Mock private PhotoCommentsEntityImpl mockRemotePhotoCommentsEntity;
    @Mock private GalleryEntityImpl mockLocalGalleryEntity;
    @Mock private GalleryEntityImpl mockRemoteGalleryEntityy;

    private PhotosDataSource photosDataSource;

    @Before
    public void setUp() {
        photosDataSource = new PhotosRepository(mockRemoteDataSource, mockLocalDataSource);
    }

    @Test
    public void testLoadPhotoFavsFromRemoteHappyCase() {
        when(mockLocalDataSource.loadPhotoFavs(FAKE_PHOTO_ID, FAKE_PAGE)).thenReturn(Observable.empty());
        when(mockRemoteDataSource.loadPhotoFavs(FAKE_PHOTO_ID, FAKE_PAGE)).thenReturn(Observable.just(mockRemotePhotoFavsEntity));

        photosDataSource.loadPhotoFavs(FAKE_PHOTO_ID, FAKE_PAGE).test().assertResult(mockRemotePhotoFavsEntity);

        verify(mockLocalDataSource, times(1)).loadPhotoFavs(FAKE_PHOTO_ID, FAKE_PAGE);
        verify(mockRemoteDataSource, times(1)).loadPhotoFavs(FAKE_PHOTO_ID, FAKE_PAGE);
    }

    @Test
    public void testLoadPhotoFavsFromLocalHappyCase() {
        when(mockLocalDataSource.loadPhotoFavs(FAKE_PHOTO_ID, FAKE_PAGE)).thenReturn(Observable.just(mockLocalPhotoFavsEntity));
        when(mockRemoteDataSource.loadPhotoFavs(FAKE_PHOTO_ID, FAKE_PAGE)).thenReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        photosDataSource.loadPhotoFavs(FAKE_PHOTO_ID, FAKE_PAGE).test().assertResult(mockLocalPhotoFavsEntity);

        verify(mockLocalDataSource, times(1)).loadPhotoFavs(FAKE_PHOTO_ID, FAKE_PAGE);
        verify(mockRemoteDataSource, times(1)).loadPhotoFavs(FAKE_PHOTO_ID, FAKE_PAGE);
    }

    @Test
    public void testLoadPhotoCommentsFromRemoteHappyCase() {
        when(mockLocalDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Observable.empty());
        when(mockRemoteDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Observable.just(mockRemotePhotoCommentsEntity));

        photosDataSource.loadPhotoComments(FAKE_PHOTO_ID).test().assertResult(mockRemotePhotoCommentsEntity);

        verify(mockLocalDataSource, times(1)).loadPhotoComments(FAKE_PHOTO_ID);
        verify(mockRemoteDataSource, times(1)).loadPhotoComments(FAKE_PHOTO_ID);
    }

    @Test
    public void testLoadPhotoCommentsFromLocalHappyCase() {
        when(mockLocalDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Observable.just(mockLocalPhotoCommentsEntity));
        when(mockRemoteDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        photosDataSource.loadPhotoComments(FAKE_PHOTO_ID).test().assertResult(mockLocalPhotoCommentsEntity);

        verify(mockLocalDataSource, times(1)).loadPhotoComments(FAKE_PHOTO_ID);
        verify(mockRemoteDataSource, times(1)).loadPhotoComments(FAKE_PHOTO_ID);
    }

    @Test
    public void testLoadSearchPhotoFromRemoteHappyCase() {
        when(mockLocalDataSource.loadSearchPhoto(FAKE_QUERY)).thenReturn(Observable.empty());
        when(mockRemoteDataSource.loadSearchPhoto(FAKE_QUERY)).thenReturn(Observable.just(mockRemoteGalleryEntityy));

        photosDataSource.loadSearchPhoto(FAKE_QUERY).test().assertResult(mockRemoteGalleryEntityy);

        verify(mockLocalDataSource, times(1)).loadSearchPhoto(FAKE_QUERY);
        verify(mockRemoteDataSource, times(1)).loadSearchPhoto(FAKE_QUERY);
    }

    @Test
    public void testLoadSearchPhotoFromLocalHappyCase() {
        when(mockLocalDataSource.loadSearchPhoto(FAKE_QUERY)).thenReturn(Observable.just(mockLocalGalleryEntity));
        when(mockRemoteDataSource.loadSearchPhoto(FAKE_QUERY)).thenReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        photosDataSource.loadSearchPhoto(FAKE_QUERY).test().assertResult(mockLocalGalleryEntity);

        verify(mockLocalDataSource, times(1)).loadSearchPhoto(FAKE_QUERY);
        verify(mockRemoteDataSource, times(1)).loadSearchPhoto(FAKE_QUERY);
    }
}