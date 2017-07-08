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

import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

@RunWith(MockitoJUnitRunner.class)
public class GetPhotoFavsTest {
    private static final String FAKE_PHOTO_ID = "fake_photo_id";
    @Mock private PhotosDataSource mockPhotosDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestSubscriber<SubmitUiModel<PhotoFavsEntity>> testSubscriber;
    private TestScheduler testScheduler;

    private GetPhotoFavs getPhotoFavs;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getPhotoFavs = new GetPhotoFavs(mockPhotosDataSource, Runnable::run, mockPostExecutionThread);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldReturnProperValue() {
        final PhotoFavsEntity correctResult = Mockito.mock(PhotoFavsEntity.class);
        Mockito.when(mockPhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).thenReturn(Flowable.just(correctResult));
        testSubscriber.assertNotSubscribed();

        getPhotoFavs.process(FAKE_PHOTO_ID).subscribe(testSubscriber);
        Mockito.verify(mockPhotosDataSource, Mockito.times(1)).loadPhotoFavs(FAKE_PHOTO_ID);
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(correctResult));
        testSubscriber.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockPhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).thenReturn(Flowable.error(fakeThrowable));
        testSubscriber.assertNotSubscribed();

        getPhotoFavs.process(FAKE_PHOTO_ID).subscribe(testSubscriber);
        Mockito.verify(mockPhotosDataSource, Mockito.times(1)).loadPhotoFavs(FAKE_PHOTO_ID);
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testSubscriber.assertComplete();
    }
}