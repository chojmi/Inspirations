package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

@RunWith(MockitoJUnitRunner.class)
public class GetPhotoCommentsTest {
    private static final String FAKE_PHOTO_ID = "fake_photo_id";
    @Mock private PhotosDataSource mockPhotosDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    private TestObserver<SubmitUiModel<PhotoCommentsEntity>> testObserver;
    private TestScheduler testScheduler;

    private GetPhotoComments getPhotoComments;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getPhotoComments = new GetPhotoComments(mockPhotosDataSource, Runnable::run, mockPostExecutionThread);
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldReturnProperValue() {
        final PhotoCommentsEntity correctResult = Mockito.mock(PhotoCommentsEntity.class);
        Mockito.when(mockPhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Observable.just(correctResult));
        testObserver.assertNotSubscribed();

        getPhotoComments.process(FAKE_PHOTO_ID).subscribe(testObserver);
        Mockito.verify(mockPhotosDataSource, Mockito.times(1)).loadPhotoComments(FAKE_PHOTO_ID);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(correctResult));
        testObserver.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockPhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Observable.error(fakeThrowable));
        testObserver.assertNotSubscribed();

        getPhotoComments.process(FAKE_PHOTO_ID).subscribe(testObserver);
        Mockito.verify(mockPhotosDataSource, Mockito.times(1)).loadPhotoComments(FAKE_PHOTO_ID);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testObserver.assertComplete();
    }
}