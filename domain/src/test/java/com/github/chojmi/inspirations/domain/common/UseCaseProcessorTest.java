package com.github.chojmi.inspirations.domain.common;

import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

@RunWith(MockitoJUnitRunner.class)
public class UseCaseProcessorTest {
    private static final String TEST_INPUT = "test_input";
    private static final String CORRECT_RESULT = "correct_result";
    @Mock Function<String, Flowable<String>> useCaseAction;
    @Mock private PostExecutionThread mockPostExecutionThread;
    private TestSubscriber<SubmitUiModel<String>> testSubscriber;
    private TestScheduler testScheduler;

    private UseCaseProcessor<String, String> useCaseProcessor;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        useCaseProcessor = new UseCaseProcessor<String, String>(Runnable::run, mockPostExecutionThread) {
            @Override
            public Flowable<String> getUseCaseActionFlowable(String s) {
                try {
                    return useCaseAction.apply(s);
                } catch (Exception e) {
                    return Flowable.error(e);
                }
            }
        };
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldReturnProperValue() throws Exception {
        Mockito.when(useCaseAction.apply(TEST_INPUT)).thenReturn(Flowable.just(CORRECT_RESULT));

        testSubscriber.assertNotSubscribed();
        useCaseProcessor.process(TEST_INPUT).subscribe(testSubscriber);
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(CORRECT_RESULT));
        testSubscriber.assertComplete();
    }

    @Test
    public void shouldReturnError() throws Exception {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(useCaseAction.apply(TEST_INPUT)).thenReturn(Flowable.error(fakeThrowable));

        testSubscriber.assertNotSubscribed();
        useCaseProcessor.process(TEST_INPUT).subscribe(testSubscriber);
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testSubscriber.assertComplete();
    }
}