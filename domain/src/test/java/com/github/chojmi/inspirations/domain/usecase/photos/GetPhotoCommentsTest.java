package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetPhotoCommentsTest {
    private static final String FAKE_PHOTO_ID = "123";

    @Mock private PhotosDataSource mockPhotosDataSource;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;
    private TestObserver testObserver;
    private GetPhotoComments getPhotoComments;

    @Before
    public void setUp() throws Exception {
        getPhotoComments = new GetPhotoComments(mockPhotosDataSource, mockThreadExecutor, mockPostExecutionThread);
        testObserver = new TestObserver();
    }

    @Test
    public void shouldInvokeInProgressEventAtBeginning() {
        when(mockPhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Observable.empty());
        Observable<GetPhotoComments.SubmitUiModel> resultObs = getPhotoComments.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoComments.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();

        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();

        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoComments.SubmitUiModel.inProgress());
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnProperValue() {
        PhotoCommentsEntity mockPhotoCommentsEntity = Mockito.mock(PhotoCommentsEntity.class);
        when(mockPhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Observable.fromCallable(() -> mockPhotoCommentsEntity));
        Observable<GetPhotoComments.SubmitUiModel> resultObs = getPhotoComments.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoComments.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();

        resultObs.subscribe(testObserver);

        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoComments.SubmitUiModel.inProgress(), GetPhotoComments.SubmitUiModel.success(mockPhotoCommentsEntity));
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        when(mockPhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Observable.error(fakeThrowable));
        Observable<GetPhotoComments.SubmitUiModel> resultObs = getPhotoComments.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoComments.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();

        resultObs.subscribe(testObserver);

        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoComments.SubmitUiModel.inProgress(), GetPhotoComments.SubmitUiModel.failure(fakeThrowable));
        resultObs.test().assertComplete();
    }
}