package com.github.chojmi.inspirations.domain.usecase.people;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

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
public class GetUserInfoTest {
    private static final String FAKE_PERSON_ID = "fake_person_id";
    @Mock private PeopleDataSource mockPeopleDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestObserver<SubmitUiModel<PersonEntity>> testObserver;
    private TestScheduler testScheduler;

    private GetUserInfo getUserInfo;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getUserInfo = new GetUserInfo(mockPeopleDataSource, Runnable::run, mockPostExecutionThread);
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldReturnProperValue() {
        final PersonEntity correctResult = Mockito.mock(PersonEntity.class);
        Mockito.when(mockPeopleDataSource.loadPersonInfo(FAKE_PERSON_ID)).thenReturn(Observable.just(correctResult));
        testObserver.assertNotSubscribed();

        getUserInfo.process(FAKE_PERSON_ID).subscribe(testObserver);
        Mockito.verify(mockPeopleDataSource, Mockito.times(1)).loadPersonInfo(FAKE_PERSON_ID);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(correctResult));
        testObserver.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockPeopleDataSource.loadPersonInfo(FAKE_PERSON_ID)).thenReturn(Observable.error(fakeThrowable));
        testObserver.assertNotSubscribed();

        getUserInfo.process(FAKE_PERSON_ID).subscribe(testObserver);
        Mockito.verify(mockPeopleDataSource, Mockito.times(1)).loadPersonInfo(FAKE_PERSON_ID);
        testScheduler.triggerActions();

        testObserver.assertSubscribed();
        testObserver.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testObserver.assertComplete();
    }
}