package com.github.chojmi.inspirations.domain.usecase.blueprints;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DefaultObserverTest {
    private TestObservableEmitter testObservableEmitter;

    @Before
    public void setUp() {
        this.testObservableEmitter = new TestObservableEmitter();
    }

    @Test
    public void shouldOnNextBeInvokedOnObservableEmitter() {
        Observable observable = Observable.just(new Object());

        observable.subscribe(DefaultObserver.create(testObservableEmitter));

        assertTrue(testObservableEmitter.onNextCount == 1);
        assertTrue(testObservableEmitter.onCompleteCount == 1);
        assertTrue(testObservableEmitter.onErrorCount == 0);
    }

    @Test
    public void shouldOnErrorBeInvokedOnObservableEmitter() {
        Observable observable = Observable.error(new Throwable("Fake throwable"));

        observable.subscribe(DefaultObserver.create(testObservableEmitter));

        assertTrue(testObservableEmitter.onNextCount == 0);
        assertTrue(testObservableEmitter.onCompleteCount == 0);
        assertTrue(testObservableEmitter.onErrorCount == 1);
    }

    @Test
    public void shouldWorkWithoutEmitterHappyCase() {
        Observable observable = Observable.just(new Object());
        observable.subscribe(DefaultObserver.create());
    }

    private static class TestObservableEmitter implements ObservableEmitter {
        private int onNextCount, onErrorCount, onCompleteCount = 0;

        @Override
        public void setDisposable(@Nullable Disposable d) {

        }

        @Override
        public void setCancellable(@Nullable Cancellable c) {

        }

        @Override
        public boolean isDisposed() {
            return false;
        }

        @Override
        public ObservableEmitter serialize() {
            return null;
        }

        @Override
        public void onNext(@NonNull Object value) {
            onNextCount++;
        }

        @Override
        public void onError(@NonNull Throwable error) {
            onErrorCount++;
        }

        @Override
        public void onComplete() {
            onCompleteCount++;
        }
    }
}