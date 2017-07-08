package com.github.chojmi.inspirations.domain.usecase.galleries;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

@RunWith(MockitoJUnitRunner.class)
public class GetGalleryTest {
    private static final String FAKE_GALLERY_ID = "fake_gallery_id";
    @Mock private GalleriesDataSource mockGalleriesDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestSubscriber<SubmitUiModel<List<PhotoEntity>>> testSubscriber;
    private TestScheduler testScheduler;

    private GetGallery getGallery;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getGallery = new GetGallery(mockGalleriesDataSource, Runnable::run, mockPostExecutionThread);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldReturnProperValue() {
        final List<PhotoEntity> correctResult = Arrays.asList(Mockito.mock(PhotoEntity.class));
        Mockito.when(mockGalleriesDataSource.loadGallery(FAKE_GALLERY_ID)).thenReturn(Flowable.just(correctResult));
        testSubscriber.assertNotSubscribed();

        getGallery.process(FAKE_GALLERY_ID).subscribe(testSubscriber);
        Mockito.verify(mockGalleriesDataSource, Mockito.times(1)).loadGallery(FAKE_GALLERY_ID);
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(correctResult));
        testSubscriber.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockGalleriesDataSource.loadGallery(FAKE_GALLERY_ID)).thenReturn(Flowable.error(fakeThrowable));
        testSubscriber.assertNotSubscribed();

        getGallery.process(FAKE_GALLERY_ID).subscribe(testSubscriber);
        Mockito.verify(mockGalleriesDataSource, Mockito.times(1)).loadGallery(FAKE_GALLERY_ID);
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testSubscriber.assertComplete();
    }
}