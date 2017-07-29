package com.github.chojmi.inspirations.domain.usecase.galleries;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

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
public class GetGalleryByUserIdTest {
    private static final String FAKE_USER_ID = "fake_user_id";
    @Mock private GalleriesDataSource mockGalleriesDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestObserver<SubmitUiModel<GalleryEntity>> testObserver;
    private TestScheduler testScheduler;

    private GetGalleryByUserId getGalleryByUserId;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getGalleryByUserId = new GetGalleryByUserId(mockGalleriesDataSource, Runnable::run, mockPostExecutionThread);
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldReturnProperValue() {
        final GalleryEntity correctResult = Mockito.mock(GalleryEntity.class);
        Mockito.when(mockGalleriesDataSource.loadGalleryByUserId(FAKE_USER_ID)).thenReturn(Observable.just(correctResult));
        testObserver.assertNotSubscribed();

        getGalleryByUserId.process(FAKE_USER_ID).subscribe(testObserver);
        Mockito.verify(mockGalleriesDataSource, Mockito.times(1)).loadGalleryByUserId(FAKE_USER_ID);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(correctResult));
        testObserver.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockGalleriesDataSource.loadGalleryByUserId(FAKE_USER_ID)).thenReturn(Observable.error(fakeThrowable));
        testObserver.assertNotSubscribed();

        getGalleryByUserId.process(FAKE_USER_ID).subscribe(testObserver);
        Mockito.verify(mockGalleriesDataSource, Mockito.times(1)).loadGalleryByUserId(FAKE_USER_ID);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testObserver.assertComplete();
    }
}