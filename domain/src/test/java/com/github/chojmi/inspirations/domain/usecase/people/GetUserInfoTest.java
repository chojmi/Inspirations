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
    private static final String FAKE_USER_ID = "123";
    private static final String FAKE_USERNAME = "fake_username";
    private static final String FAKE_DESCRIPTION = "fake_description";
    private static final String FAKE_ICON_URL = "fake_icon_url";

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
        Mockito.when(mockPeopleDataSource.loadPersonInfo(FAKE_USER_ID)).thenReturn(Observable.empty());
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
        PersonEntity fakePersonEntity = new FakePersonEntity();
        Mockito.when(mockPeopleDataSource.loadPersonInfo(FAKE_USER_ID)).thenReturn(Observable.fromCallable(() -> fakePersonEntity));
        Observable<GetUserInfo.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserInfo.SubmitEvent.create(FAKE_USER_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserInfo.SubmitUiModel.inProgress(), GetUserInfo.SubmitUiModel.success(fakePersonEntity));
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockPeopleDataSource.loadPersonInfo(FAKE_USER_ID)).thenReturn(Observable.error(fakeThrowable));
        Observable<GetUserInfo.SubmitUiModel> resultObs = getUserPublicPhotos.buildUseCaseObservable(Observable.fromCallable(() -> GetUserInfo.SubmitEvent.create(FAKE_USER_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetUserInfo.SubmitUiModel.inProgress(), GetUserInfo.SubmitUiModel.failure(fakeThrowable));
        resultObs.test().assertComplete();
    }

    private class FakePersonEntity implements PersonEntity {

        @Override
        public String getUsername() {
            return FAKE_USERNAME;
        }

        @Override
        public String getDescription() {
            return FAKE_DESCRIPTION;
        }

        @Override
        public String getIconUrl() {
            return FAKE_ICON_URL;
        }
    }
}