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
    private static final String USER_ID = "123";
    private List<PhotoEntity> mockPhotoEntities;

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
        Mockito.when(mockPeopleDataSource.loadUserPublicPhotos(USER_ID)).thenReturn(Observable.empty());
        Observable<GetUserPublicPhotos.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserPublicPhotos.SubmitEvent.create(USER_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserPublicPhotos.SubmitUiModel.inProgress());
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnProperValue() {
        mockPhotoEntities = createMockPhotoEntities();
        Mockito.when(mockPeopleDataSource.loadUserPublicPhotos(USER_ID)).thenReturn(Observable.fromCallable(() -> mockPhotoEntities));
        Observable<GetUserPublicPhotos.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserPublicPhotos.SubmitEvent.create(USER_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserPublicPhotos.SubmitUiModel.inProgress(), GetUserPublicPhotos.SubmitUiModel.success(mockPhotoEntities));
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable mockThrowable = new Throwable("Mock throwable");
        Mockito.when(mockPeopleDataSource.loadUserPublicPhotos(USER_ID)).thenReturn(Observable.error(mockThrowable));
        Observable<GetUserPublicPhotos.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserPublicPhotos.SubmitEvent.create(USER_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserPublicPhotos.SubmitUiModel.inProgress(), GetUserPublicPhotos.SubmitUiModel.failure(mockThrowable));
        resultObs.test().assertComplete();
    }

    private List<PhotoEntity> createMockPhotoEntities() {
        List<PhotoEntity> mockPhotoEntities = new ArrayList<>();
        mockPhotoEntities.add(createMockPhotoEntity());
        return mockPhotoEntities;
    }

    private PhotoEntity createMockPhotoEntity() {
        return new PhotoEntity() {
            @Override
            public String getUrl() {
                return "url";
            }

            @Override
            public String getTitle() {
                return "title";
            }
        };
    }
}