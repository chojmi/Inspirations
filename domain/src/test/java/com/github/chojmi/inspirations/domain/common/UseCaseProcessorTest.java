package com.github.chojmi.inspirations.domain.common;

import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

@RunWith(MockitoJUnitRunner.class)
public class UseCaseProcessorTest {
    private static final String TEST_INPUT = "test_input";
    private static final String CORRECT_RESULT = "correct_result";
    @Mock Function<String, Observable<String>> useCaseAction;
    @Mock private PostExecutionThread mockPostExecutionThread;
    private TestObserver<SubmitUiModel<String>> testObserver;
    private TestScheduler testScheduler;

    private UseCaseProcessor<String, String> useCaseProcessor;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        useCaseProcessor = new UseCaseProcessor<String, String>(Runnable::run, mockPostExecutionThread) {
            @Override
            public Observable<String> getUseCaseActionObservable(String s) {
                try {
                    return useCaseAction.apply(s);
                } catch (Exception e) {
                    return Observable.error(e);
                }
            }
        };
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldReturnProperValue() throws Exception {
        Mockito.when(useCaseAction.apply(TEST_INPUT)).thenReturn(Observable.just(CORRECT_RESULT));

        testObserver.assertNotSubscribed();
        useCaseProcessor.process(TEST_INPUT).subscribe(testObserver);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(CORRECT_RESULT));
        testObserver.assertComplete();
    }

    @Test
    public void shouldReturnError() throws Exception {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(useCaseAction.apply(TEST_INPUT)).thenReturn(Observable.error(fakeThrowable));

        testObserver.assertNotSubscribed();
        useCaseProcessor.process(TEST_INPUT).subscribe(testObserver);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testObserver.assertComplete();
    }
}