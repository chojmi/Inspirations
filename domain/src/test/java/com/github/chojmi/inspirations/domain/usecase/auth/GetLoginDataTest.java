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

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

@RunWith(MockitoJUnitRunner.class)
public class GetLoginDataTest {
    @Mock private AuthTestDataSource mockAuthTestDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestObserver<SubmitUiModel<UserEntity>> testObserver;
    private TestScheduler testScheduler;

    private GetLoginData getLoginData;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getLoginData = new GetLoginData(mockAuthTestDataSource, Runnable::run, mockPostExecutionThread);
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldReturnProperValue() {
        Mockito.when(mockAuthTestDataSource.getLoginData()).thenReturn(Observable.just(mockUserEntity));
        testObserver.assertNotSubscribed();

        getLoginData.process().subscribe(testObserver);
        Mockito.verify(mockAuthTestDataSource, Mockito.times(1)).getLoginData();
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(mockUserEntity));
        testObserver.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockAuthTestDataSource.getLoginData()).thenReturn(Observable.error(fakeThrowable));
        testObserver.assertNotSubscribed();

        getLoginData.process().subscribe(testObserver);
        Mockito.verify(mockAuthTestDataSource, Mockito.times(1)).getLoginData();
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testObserver.assertComplete();
    }
}