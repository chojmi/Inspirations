package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

@RunWith(MockitoJUnitRunner.class)
public class GetPhotoCommentsTest {
    private static final String FAKE_PHOTO_ID = "fake_photo_id";
    @Mock private PhotosDataSource mockPhotosDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestSubscriber<SubmitUiModel<PhotoCommentsEntity>> testSubscriber;
    private TestScheduler testScheduler;

    private GetPhotoComments getPhotoComments;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getPhotoComments = new GetPhotoComments(mockPhotosDataSource, Runnable::run, mockPostExecutionThread);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldReturnProperValue() {
        final PhotoCommentsEntity correctResult = Mockito.mock(PhotoCommentsEntity.class);
        Mockito.when(mockPhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Flowable.just(correctResult));
        testSubscriber.assertNotSubscribed();

        getPhotoComments.process(FAKE_PHOTO_ID).subscribe(testSubscriber);
        Mockito.verify(mockPhotosDataSource, Mockito.times(1)).loadPhotoComments(FAKE_PHOTO_ID);
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(correctResult));
        testSubscriber.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockPhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Flowable.error(fakeThrowable));
        testSubscriber.assertNotSubscribed();

        getPhotoComments.process(FAKE_PHOTO_ID).subscribe(testSubscriber);
        Mockito.verify(mockPhotosDataSource, Mockito.times(1)).loadPhotoComments(FAKE_PHOTO_ID);
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testSubscriber.assertComplete();
    }
}