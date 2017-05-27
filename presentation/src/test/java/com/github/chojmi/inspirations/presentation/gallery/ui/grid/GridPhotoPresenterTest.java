package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.presentation.gallery.compound_usecase.GetPhotosCompoundUseCase;
import com.github.chojmi.inspirations.presentation.gallery.mapper.PhotoDataMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GridPhotoPresenterTest {
    private static final String USER_ID = "123";

    @Mock private GridPhotoContract.View mockGalleryView;
    @Mock private GetUserPublicPhotos mockGetUserPublicPhotos;
    @Mock private GetUserInfo mockGetUserInfo;
    @Mock private PhotoDataMapper mockPhotoDataMapper;

    private GridPhotoPresenter galleryPresenter;

    @Before
    public void setUp() {
        galleryPresenter = new GridPhotoPresenter(new GetPhotosCompoundUseCase(mockGetUserPublicPhotos, mockGetUserInfo, mockPhotoDataMapper));
        galleryPresenter.setView(mockGalleryView);
    }

    @Test
    public void testGalleryPresenterRefreshPhotosHappyCase() {
        galleryPresenter.refreshPhotos(USER_ID);

        ArgumentCaptor<Observable> params = ArgumentCaptor.forClass(Observable.class);
        verify(mockGetUserPublicPhotos, times(2)).execute(any(DisposableObserver.class), params.capture());
        params.getAllValues().get(1).test().assertResult(GetUserPublicPhotos.SubmitEvent.create(USER_ID));
    }

    @Test
    public void testGalleryPresenterRefreshPhotosOnResumeHappyCase() {
        verify(mockGetUserPublicPhotos, times(1)).execute(any(DisposableObserver.class), any(Observable.class));
    }
}