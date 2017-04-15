package com.github.chojmi.inspirations.domain.usecase;

import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;
import com.github.chojmi.inspirations.domain.usecase.gallery.GetGallery;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetGalleryTest {

    private static final String GALLERY_ID = "123";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private GetGallery getGallery;
    @Mock
    private GalleryDataSource mockGalleryDataSource;
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {
        getGallery = new GetGallery(mockGalleryDataSource, mockThreadExecutor,
                mockPostExecutionThread);
    }

    @Test
    public void testGetGalleryUseCaseObservableHappyCase() {
        getGallery.buildUseCaseObservable(GetGallery.Params.forGallery(GALLERY_ID));

        verify(mockGalleryDataSource).loadGallery(GALLERY_ID);
        verifyNoMoreInteractions(mockGalleryDataSource);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }

    @Test
    public void testShouldFailWhenNoOrEmptyParameters() {
        expectedException.expect(NullPointerException.class);
        getGallery.buildUseCaseObservable(null);
    }
}
