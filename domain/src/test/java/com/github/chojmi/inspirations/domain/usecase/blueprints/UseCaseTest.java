package com.github.chojmi.inspirations.domain.usecase.blueprints;

import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UseCaseTest {

    @Rule public ExpectedException expectedException = ExpectedException.none();
    private UseCaseTestClass useCase;
    private TestDisposableObserver<BaseSubmitUiModel> testObserver;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {
        this.useCase = new UseCaseTestClass(mockThreadExecutor, mockPostExecutionThread);
        this.testObserver = new TestDisposableObserver<>();
        given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
    }

    @Test
    public void shouldBuildUseCaseObservableReturnCorrectResult() {
        useCase.execute(testObserver, Observable.empty());

        assertTrue(testObserver.valuesCount == 0);
    }

    @Test
    public void shouldBeDisposed() {
        useCase.execute(testObserver, Observable.empty());
        useCase.dispose();

        assertTrue(testObserver.isDisposed());
    }

    @Test
    public void shouldFailWhenExecuteWithNullObserver() {
        expectedException.expect(NullPointerException.class);
        useCase.execute(null, Observable.empty());
    }

    private static class UseCaseTestClass extends UseCase<BaseSubmitUiModel, BaseSubmitEvent> {

        UseCaseTestClass(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
            super(threadExecutor, postExecutionThread);
        }

        @Override
        public Observable<BaseSubmitUiModel> buildUseCaseObservable(Observable<BaseSubmitEvent> params) {
            return Observable.empty();
        }

        @Override
        public void execute(DisposableObserver<BaseSubmitUiModel> observer, Observable<BaseSubmitEvent> inputEvents) {
            super.execute(observer, inputEvents);
        }
    }

    private static class TestDisposableObserver<T> extends DisposableObserver<T> {
        private int valuesCount = 0;

        @Override
        public void onNext(T value) {
            valuesCount++;
        }

        @Override
        public void onError(Throwable e) {
            // no-op by default.
        }

        @Override
        public void onComplete() {
            // no-op by default.
        }
    }
}