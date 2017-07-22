package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
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
public class GetPhotoFavsTest {
    private static final String FAKE_PHOTO_ID = "fake_photo_id";
    @Mock private PhotosDataSource mockPhotosDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestObserver<SubmitUiModel<PhotoFavsEntity>> testObserver;
    private TestScheduler testScheduler;

    private GetPhotoFavs getPhotoFavs;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getPhotoFavs = new GetPhotoFavs(mockPhotosDataSource, Runnable::run, mockPostExecutionThread);
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldReturnProperValue() {
        final PhotoFavsEntity correctResult = Mockito.mock(PhotoFavsEntity.class);
        Mockito.when(mockPhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).thenReturn(Observable.just(correctResult));
        testObserver.assertNotSubscribed();

        getPhotoFavs.process(FAKE_PHOTO_ID).subscribe(testObserver);
        Mockito.verify(mockPhotosDataSource, Mockito.times(1)).loadPhotoFavs(FAKE_PHOTO_ID);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(correctResult));
        testObserver.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockPhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).thenReturn(Observable.error(fakeThrowable));
        testObserver.assertNotSubscribed();

        getPhotoFavs.process(FAKE_PHOTO_ID).subscribe(testObserver);
        Mockito.verify(mockPhotosDataSource, Mockito.times(1)).loadPhotoFavs(FAKE_PHOTO_ID);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testObserver.assertComplete();
    }
}