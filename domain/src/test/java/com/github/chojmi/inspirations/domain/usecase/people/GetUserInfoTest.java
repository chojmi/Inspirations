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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetUserInfoTest {
    private static final String FAKE_USER_ID = "123";

    @Mock private PeopleDataSource mockPeopleDataSource;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;
    private TestObserver testObserver;

    private GetUserInfo getUserPublicPhotos;

    @Before
    public void setUp() throws Exception {
        getUserPublicPhotos = new GetUserInfo(mockPeopleDataSource, mockThreadExecutor, mockPostExecutionThread);
        testObserver = new TestObserver();
    }

    @Test
    public void shouldInvokeInProgressEventAtBeginning() {
        when(mockPeopleDataSource.loadPersonInfo(FAKE_USER_ID)).thenReturn(Observable.empty());
        Observable<GetUserInfo.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserInfo.SubmitEvent.create(FAKE_USER_ID)));
        testObserver.assertNotSubscribed();

        resultObs.subscribe(testObserver);

        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserInfo.SubmitUiModel.inProgress());
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnProperValue() {
        PersonEntity mockFakePersonEntity = Mockito.mock(PersonEntity.class);
        when(mockPeopleDataSource.loadPersonInfo(FAKE_USER_ID)).thenReturn(Observable.fromCallable(() -> mockFakePersonEntity));
        Observable<GetUserInfo.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserInfo.SubmitEvent.create(FAKE_USER_ID)));
        testObserver.assertNotSubscribed();

        resultObs.subscribe(testObserver);

        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserInfo.SubmitUiModel.inProgress(), GetUserInfo.SubmitUiModel.success(mockFakePersonEntity));
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        when(mockPeopleDataSource.loadPersonInfo(FAKE_USER_ID)).thenReturn(Observable.error(fakeThrowable));
        Observable<GetUserInfo.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserInfo.SubmitEvent.create(FAKE_USER_ID)));
        testObserver.assertNotSubscribed();

        resultObs.subscribe(testObserver);

        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserInfo.SubmitUiModel.inProgress(), GetUserInfo.SubmitUiModel.failure(fakeThrowable));
        resultObs.test().assertComplete();
    }
}