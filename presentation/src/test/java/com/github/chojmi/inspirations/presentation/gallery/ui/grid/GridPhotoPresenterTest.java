package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GridPhotoPresenterTest {
    private static final String USER_ID = "123";

    @Mock private GridPhotoContract.View mockGalleryView;
    @Mock private UseCase<String, List<PhotoWithAuthor>> mockGetPhotosCompoundUseCase;

    private GridPhotoPresenter galleryPresenter;

    @Before
    public void setUp() {
        Mockito.when(mockGetPhotosCompoundUseCase.process(any())).thenReturn(Observable.empty());
        galleryPresenter = new GridPhotoPresenter(mockGetPhotosCompoundUseCase);
        galleryPresenter.setView(mockGalleryView);
    }

    @Test
    public void testGalleryPresenterRefreshPhotosHappyCase() {
        galleryPresenter.refreshPhotos(USER_ID);

        ArgumentCaptor<String> params = ArgumentCaptor.forClass(String.class);
        verify(mockGetPhotosCompoundUseCase, times(2)).process(params.capture());
        Assert.assertEquals(params.getValue(), USER_ID);
    }
}