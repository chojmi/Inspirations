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

import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

@RunWith(MockitoJUnitRunner.class)
public class GetAccessTokenTest {
    private static final String CORRECT_VERIFIER = "correct_verifier";
    private static final OAuth1AccessToken TEST_ACCESS_TOKEN = new OAuth1AccessToken("token", "secret");
    @Mock private AuthDataSource mockAuthDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestSubscriber<SubmitUiModel<OAuth1AccessToken>> testSubscriber;
    private TestScheduler testScheduler;

    private GetAccessToken getAccessToken;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getAccessToken = new GetAccessToken(mockAuthDataSource, Runnable::run, mockPostExecutionThread);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldReturnProperValue() {
        Mockito.when(mockAuthDataSource.getAccessToken(CORRECT_VERIFIER)).thenReturn(Flowable.just(TEST_ACCESS_TOKEN));
        testSubscriber.assertNotSubscribed();

        getAccessToken.process(CORRECT_VERIFIER).subscribe(testSubscriber);
        Mockito.verify(mockAuthDataSource, Mockito.times(1)).getAccessToken(CORRECT_VERIFIER);
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(TEST_ACCESS_TOKEN));
        testSubscriber.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockAuthDataSource.getAccessToken("")).thenReturn(Flowable.error(fakeThrowable));
        testSubscriber.assertNotSubscribed();

        getAccessToken.process().subscribe(testSubscriber);
        Mockito.verify(mockAuthDataSource, Mockito.times(1)).getAccessToken("");
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testSubscriber.assertComplete();
    }
}