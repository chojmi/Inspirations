package com.github.chojmi.inspirations.domain.usecase.people;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)
public class GetUserInfoTest {
    private static final String USER_ID = "123";

    private GetUserInfo getUserPublicPhotos;
    private TestObserver testObserver;
    @Mock
    private PeopleDataSource mockPeopleDataSource;
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() throws Exception {
        getUserPublicPhotos = new GetUserInfo(mockPeopleDataSource, mockThreadExecutor, mockPostExecutionThread);
        testObserver = new TestObserver();
    }

    @Test
    public void shouldInvokeInProgressEventAtBeginning() {
        Mockito.when(mockPeopleDataSource.loadPersonInfo(USER_ID)).thenReturn(Observable.empty());
        Observable<GetUserInfo.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserInfo.SubmitEvent.create(USER_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserInfo.SubmitUiModel.inProgress());
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnProperValue() {
        PersonEntity mockPersonEntity = new MockPersonEntity();
        Mockito.when(mockPeopleDataSource.loadPersonInfo(USER_ID)).thenReturn(Observable.fromCallable(() -> mockPersonEntity));
        Observable<GetUserInfo.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserInfo.SubmitEvent.create(USER_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserInfo.SubmitUiModel.inProgress(), GetUserInfo.SubmitUiModel.success(mockPersonEntity));
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable mockThrowable = new Throwable("Mock throwable");
        Mockito.when(mockPeopleDataSource.loadPersonInfo(USER_ID)).thenReturn(Observable.error(mockThrowable));
        Observable<GetUserInfo.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserInfo.SubmitEvent.create(USER_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserInfo.SubmitUiModel.inProgress(), GetUserInfo.SubmitUiModel.failure(mockThrowable));
        resultObs.test().assertComplete();
    }

    private class MockPersonEntity implements PersonEntity {

    }
}