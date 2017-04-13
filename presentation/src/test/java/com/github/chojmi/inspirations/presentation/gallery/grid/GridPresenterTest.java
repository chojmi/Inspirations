package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.domain.usecase.GetUserPublicPhotos;
import com.github.chojmi.inspirations.presentation.mapper.gallery.PhotoDataMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GridPresenterTest {
    private static final String USER_ID = "123";

    private GridPresenter galleryPresenter;

    @Mock
    private GridContract.View mockGalleryView;
    @Mock
    private GetUserPublicPhotos mockGetUserPublicPhotos;
    @Mock
    private PhotoDataMapper mockPhotoDataMapper;

    @Before
    public void setUp() {
        galleryPresenter = new GridPresenter(mockGetUserPublicPhotos, mockPhotoDataMapper);
        galleryPresenter.setView(mockGalleryView);
    }

    @Test
    public void testGalleryPresenterRefreshPhotosHappyCase() {
        galleryPresenter.refreshPhotos(USER_ID);
        ArgumentCaptor<GetUserPublicPhotos.Params> params = ArgumentCaptor.forClass(GetUserPublicPhotos.Params.class);
        verify(mockGetUserPublicPhotos, times(2)).execute(any(DisposableObserver.class), params.capture());
        assertTrue(params.getAllValues().get(1).equals(GetUserPublicPhotos.Params.forUserId(USER_ID)));
    }

    @Test
    public void testGalleryPresenterRefreshPhotosOnResumeHappyCase() {
        verify(mockGetUserPublicPhotos, times(1)).execute(any(DisposableObserver.class), any(GetUserPublicPhotos.Params.class));
    }
}