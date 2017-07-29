package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
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
public class GetSearchPhotoTest {
    private static final String FAKE_QUERY = "fake_quer";
    @Mock private PhotosDataSource mockPhotosDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private GalleryEntity mockGalleryEntity;
    private TestObserver<SubmitUiModel<GalleryEntity>> testObserver;
    private TestScheduler testScheduler;

    private GetSearchPhoto getSearchPhoto;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getSearchPhoto = new GetSearchPhoto(mockPhotosDataSource, Runnable::run, mockPostExecutionThread);
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldReturnProperValue() {
        Mockito.when(mockPhotosDataSource.loadSearchPhoto(FAKE_QUERY)).thenReturn(Observable.just(mockGalleryEntity));
        testObserver.assertNotSubscribed();

        getSearchPhoto.process(FAKE_QUERY).subscribe(testObserver);
        Mockito.verify(mockPhotosDataSource, Mockito.times(1)).loadSearchPhoto(FAKE_QUERY);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(mockGalleryEntity));
        testObserver.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockPhotosDataSource.loadSearchPhoto(FAKE_QUERY)).thenReturn(Observable.error(fakeThrowable));
        testObserver.assertNotSubscribed();

        getSearchPhoto.process(FAKE_QUERY).subscribe(testObserver);
        Mockito.verify(mockPhotosDataSource, Mockito.times(1)).loadSearchPhoto(FAKE_QUERY);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testObserver.assertComplete();
    }
}