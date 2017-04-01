package com.github.chojmi.inspirations.presentation.gallery;

import com.github.chojmi.inspirations.domain.usecase.GetGallery;

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
public class GalleryPresenterTest {
    private static final String GALLERY_ID = "123";

    private GalleryPresenter galleryPresenter;

    @Mock
    private GalleryContract.View mockGalleryView;
    @Mock
    private GetGallery mockGetGallery;

    @Before
    public void setUp() {
        galleryPresenter = new GalleryPresenter(mockGetGallery);
        galleryPresenter.setView(mockGalleryView);
    }

    @Test
    public void testGalleryPresenterRefreshPhotosHappyCase() {
        galleryPresenter.refreshPhotos(GALLERY_ID);
        ArgumentCaptor<GetGallery.Params> forGalleryParams = ArgumentCaptor.forClass(GetGallery.Params.class);
        verify(mockGetGallery, times(2)).execute(any(DisposableObserver.class), forGalleryParams.capture());
        assertTrue(forGalleryParams.getAllValues().get(1).equals(GetGallery.Params.forGallery(GALLERY_ID)));
    }

    @Test
    public void testGalleryPresenterRefreshPhotosOnResumeHappyCase() {
        verify(mockGetGallery, times(1)).execute(any(DisposableObserver.class), any(GetGallery.Params.class));
    }
}