package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.repository.AuthTestDataSource;

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
public class GetLoginDataTest {
    @Mock private AuthTestDataSource mockAuthTestDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestSubscriber<SubmitUiModel<UserEntity>> testSubscriber;
    private TestScheduler testScheduler;

    private GetLoginData getLoginDataTest;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getLoginDataTest = new GetLoginData(mockAuthTestDataSource, Runnable::run, mockPostExecutionThread);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldReturnProperValue() {
        Mockito.when(mockAuthTestDataSource.getLoginData()).thenReturn(Flowable.just(mockUserEntity));
        testSubscriber.assertNotSubscribed();

        getLoginDataTest.process().subscribe(testSubscriber);
        Mockito.verify(mockAuthTestDataSource, Mockito.times(1)).getLoginData();
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(mockUserEntity));
        testSubscriber.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockAuthTestDataSource.getLoginData()).thenReturn(Flowable.error(fakeThrowable));
        testSubscriber.assertNotSubscribed();

        getLoginDataTest.process().subscribe(testSubscriber);
        Mockito.verify(mockAuthTestDataSource, Mockito.times(1)).getLoginData();
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testSubscriber.assertComplete();
    }
}