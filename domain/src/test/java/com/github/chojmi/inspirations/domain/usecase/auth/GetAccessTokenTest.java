package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;

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
public class GetAccessTokenTest {
    private static final String CORRECT_VERIFIER = "correct_verifier";
    private static final OAuth1AccessToken TEST_ACCESS_TOKEN = new OAuth1AccessToken("token", "secret");
    @Mock private AuthDataSource mockAuthDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestObserver<SubmitUiModel<OAuth1AccessToken>> testObserver;
    private TestScheduler testScheduler;

    private GetAccessToken getAccessToken;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getAccessToken = new GetAccessToken(mockAuthDataSource, Runnable::run, mockPostExecutionThread);
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldReturnProperValue() {
        Mockito.when(mockAuthDataSource.getAccessToken(CORRECT_VERIFIER)).thenReturn(Observable.just(TEST_ACCESS_TOKEN));
        testObserver.assertNotSubscribed();

        getAccessToken.process(CORRECT_VERIFIER).subscribe(testObserver);
        Mockito.verify(mockAuthDataSource, Mockito.times(1)).getAccessToken(CORRECT_VERIFIER);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(TEST_ACCESS_TOKEN));
        testObserver.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockAuthDataSource.getAccessToken("")).thenReturn(Observable.error(fakeThrowable));
        testObserver.assertNotSubscribed();

        getAccessToken.process().subscribe(testObserver);
        Mockito.verify(mockAuthDataSource, Mockito.times(1)).getAccessToken("");
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testObserver.assertComplete();
    }
}