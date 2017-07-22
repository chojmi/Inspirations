package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;

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
public class GetAuthorizationUrlTest {
    private static final String CORRECT_RESULT = "correct_result";
    @Mock private AuthDataSource mockAuthDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestObserver<SubmitUiModel<String>> testObserver;
    private TestScheduler testScheduler;

    private GetAuthorizationUrl getAuthorizationUrl;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getAuthorizationUrl = new GetAuthorizationUrl(mockAuthDataSource, Runnable::run, mockPostExecutionThread);
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldReturnProperValue() {
        Mockito.when(mockAuthDataSource.getAuthorizationUrl()).thenReturn(Observable.just(CORRECT_RESULT));
        testObserver.assertNotSubscribed();

        getAuthorizationUrl.process().subscribe(testObserver);
        Mockito.verify(mockAuthDataSource, Mockito.times(1)).getAuthorizationUrl();
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(CORRECT_RESULT));
        testObserver.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockAuthDataSource.getAuthorizationUrl()).thenReturn(Observable.error(fakeThrowable));
        testObserver.assertNotSubscribed();

        getAuthorizationUrl.process().subscribe(testObserver);
        Mockito.verify(mockAuthDataSource, Mockito.times(1)).getAuthorizationUrl();
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testObserver.assertComplete();
    }
}