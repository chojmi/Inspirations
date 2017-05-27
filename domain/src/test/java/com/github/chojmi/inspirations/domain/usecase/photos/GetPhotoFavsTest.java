package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
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
public class GetPhotoFavsTest {
    private static final String FAKE_PHOTO_ID = "123";

    @Mock private PhotosDataSource mockPhotosDataSource;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;
    private TestObserver testObserver;

    private GetPhotoFavs getPhotoFavs;

    @Before
    public void setUp() throws Exception {
        getPhotoFavs = new GetPhotoFavs(mockPhotosDataSource, mockThreadExecutor, mockPostExecutionThread);
        testObserver = new TestObserver();
    }

    @Test
    public void shouldInvokeInProgressEventAtBeginning() {
        when(mockPhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).thenReturn(Observable.empty());
        Observable<GetPhotoFavs.SubmitUiModel> resultObs = getPhotoFavs.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoFavs.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();

        resultObs.subscribe(testObserver);

        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoFavs.SubmitUiModel.inProgress());
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnProperValue() {
        PhotoFavsEntity mockPhotoFavsEntity = Mockito.mock(PhotoFavsEntity.class);
        when(mockPhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).thenReturn(Observable.fromCallable(() -> mockPhotoFavsEntity));
        Observable<GetPhotoFavs.SubmitUiModel> resultObs = getPhotoFavs.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoFavs.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();

        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();

        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoFavs.SubmitUiModel.inProgress(), GetPhotoFavs.SubmitUiModel.success(mockPhotoFavsEntity));
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        when(mockPhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).thenReturn(Observable.error(fakeThrowable));
        Observable<GetPhotoFavs.SubmitUiModel> resultObs = getPhotoFavs.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoFavs.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();

        resultObs.subscribe(testObserver);

        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoFavs.SubmitUiModel.inProgress(), GetPhotoFavs.SubmitUiModel.failure(fakeThrowable));
        resultObs.test().assertComplete();
    }
}