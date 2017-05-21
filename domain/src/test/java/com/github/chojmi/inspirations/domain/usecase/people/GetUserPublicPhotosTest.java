package com.github.chojmi.inspirations.domain.usecase.people;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

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
public class GetUserPublicPhotosTest {
    private static final String FAKE_USER_ID = "123";
    private static final String FAKE_URL = "www.url.pl";
    private static final String FAKE_TITLE = "fake_title";

    private GetUserPublicPhotos getUserPublicPhotos;
    private TestObserver testObserver;
    @Mock
    private PeopleDataSource mockPeopleDataSource;
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() throws Exception {
        getUserPublicPhotos = new GetUserPublicPhotos(mockPeopleDataSource, mockThreadExecutor, mockPostExecutionThread);
        testObserver = new TestObserver();
    }

    @Test
    public void shouldInvokeInProgressEventAtBeginning() {
        Mockito.when(mockPeopleDataSource.loadUserPublicPhotos(FAKE_USER_ID)).thenReturn(Observable.empty());
        Observable<GetUserPublicPhotos.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserPublicPhotos.SubmitEvent.create(FAKE_USER_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserPublicPhotos.SubmitUiModel.inProgress());
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnProperValue() {
        List<PhotoEntity> fakePhotoEntities = createFakePhotoEntities();
        Mockito.when(mockPeopleDataSource.loadUserPublicPhotos(FAKE_USER_ID)).thenReturn(Observable.fromCallable(() -> fakePhotoEntities));
        Observable<GetUserPublicPhotos.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserPublicPhotos.SubmitEvent.create(FAKE_USER_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserPublicPhotos.SubmitUiModel.inProgress(), GetUserPublicPhotos.SubmitUiModel.success(fakePhotoEntities));
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockPeopleDataSource.loadUserPublicPhotos(FAKE_USER_ID)).thenReturn(Observable.error(fakeThrowable));
        Observable<GetUserPublicPhotos.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserPublicPhotos.SubmitEvent.create(FAKE_USER_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserPublicPhotos.SubmitUiModel.inProgress(), GetUserPublicPhotos.SubmitUiModel.failure(fakeThrowable));
        resultObs.test().assertComplete();
    }

    private List<PhotoEntity> createFakePhotoEntities() {
        List<PhotoEntity> fakePhotoEntities = new ArrayList<>();
        fakePhotoEntities.add(createFakePhotoEntity());
        return fakePhotoEntities;
    }

    private PhotoEntity createFakePhotoEntity() {
        return new PhotoEntity() {
            @Override
            public String getId() {
                return FAKE_USER_ID;
            }

            @Override
            public String getUrl() {
                return FAKE_URL;
            }

            @Override
            public String getTitle() {
                return FAKE_TITLE;
            }
        };
    }
}