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

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

@RunWith(MockitoJUnitRunner.class)
public class GetGalleryByGalleryIdTest {
    private static final String FAKE_GALLERY_ID = "fake_gallery_id";
    @Mock private GalleriesDataSource mockGalleriesDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestObserver<SubmitUiModel<List<PhotoEntity>>> testObserver;
    private TestScheduler testScheduler;

    private GetGalleryByGalleryId getGalleryByGalleryId;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getGalleryByGalleryId = new GetGalleryByGalleryId(mockGalleriesDataSource, Runnable::run, mockPostExecutionThread);
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldReturnProperValue() {
        final List<PhotoEntity> correctResult = Arrays.asList(Mockito.mock(PhotoEntity.class));
        Mockito.when(mockGalleriesDataSource.loadGalleryByGalleryId(FAKE_GALLERY_ID)).thenReturn(Observable.just(correctResult));
        testObserver.assertNotSubscribed();

        getGalleryByGalleryId.process(FAKE_GALLERY_ID).subscribe(testObserver);
        Mockito.verify(mockGalleriesDataSource, Mockito.times(1)).loadGalleryByGalleryId(FAKE_GALLERY_ID);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(correctResult));
        testObserver.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockGalleriesDataSource.loadGalleryByGalleryId(FAKE_GALLERY_ID)).thenReturn(Observable.error(fakeThrowable));
        testObserver.assertNotSubscribed();

        getGalleryByGalleryId.process(FAKE_GALLERY_ID).subscribe(testObserver);
        Mockito.verify(mockGalleriesDataSource, Mockito.times(1)).loadGalleryByGalleryId(FAKE_GALLERY_ID);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testObserver.assertComplete();
    }
}