package com.github.chojmi.inspirations.domain.usecase.galleries;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)
public class GetGalleryTest {
    private static final String GALLERY_ID = "123";

    @Mock private GalleriesDataSource mockGalleriesDataSource;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;
    private TestObserver testObserver;

    private GetGallery getGallery;

    @Before
    public void setUp() throws Exception {
        getGallery = new GetGallery(mockGalleriesDataSource, mockThreadExecutor, mockPostExecutionThread);
        testObserver = new TestObserver();
    }

    @Test
    public void shouldInvokeInProgressEventAtBeginning() {
        Mockito.when(mockGalleriesDataSource.loadGallery(GALLERY_ID)).thenReturn(Observable.empty());
        Observable<GetGallery.SubmitUiModel> resultObs = getGallery.buildUseCaseObservable(Observable.fromCallable(() -> GetGallery.SubmitEvent.create(GALLERY_ID)));
        testObserver.assertNotSubscribed();

        resultObs.subscribe(testObserver);

        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetGallery.SubmitUiModel.inProgress());
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnProperValue() {
        List<PhotoEntity> fakePhotoEntities = new ArrayList<>();
        Mockito.when(mockGalleriesDataSource.loadGallery(GALLERY_ID)).thenReturn(Observable.fromCallable(() -> fakePhotoEntities));
        Observable<GetGallery.SubmitUiModel> resultObs = getGallery.buildUseCaseObservable(Observable.fromCallable(() -> GetGallery.SubmitEvent.create(GALLERY_ID)));
        testObserver.assertNotSubscribed();

        resultObs.subscribe(testObserver);

        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetGallery.SubmitUiModel.inProgress(), GetGallery.SubmitUiModel.success(fakePhotoEntities));
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockGalleriesDataSource.loadGallery(GALLERY_ID)).thenReturn(Observable.error(fakeThrowable));
        Observable<GetGallery.SubmitUiModel> resultObs = getGallery.buildUseCaseObservable(Observable.fromCallable(() -> GetGallery.SubmitEvent.create(GALLERY_ID)));
        testObserver.assertNotSubscribed();

        resultObs.subscribe(testObserver);

        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetGallery.SubmitUiModel.inProgress(), GetGallery.SubmitUiModel.failure(fakeThrowable));
        resultObs.test().assertComplete();
    }
}