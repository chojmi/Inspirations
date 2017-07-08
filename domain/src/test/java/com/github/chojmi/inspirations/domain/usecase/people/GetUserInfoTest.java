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

import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

@RunWith(MockitoJUnitRunner.class)
public class GetUserInfoTest {
    private static final String FAKE_PERSON_ID = "fake_person_id";
    @Mock private PeopleDataSource mockPeopleDataSource;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private UserEntity mockUserEntity;
    private TestSubscriber<SubmitUiModel<PersonEntity>> testSubscriber;
    private TestScheduler testScheduler;

    private GetUserInfo getUserInfo;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);
        getUserInfo = new GetUserInfo(mockPeopleDataSource, Runnable::run, mockPostExecutionThread);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldReturnProperValue() {
        final PersonEntity correctResult = Mockito.mock(PersonEntity.class);
        Mockito.when(mockPeopleDataSource.loadPersonInfo(FAKE_PERSON_ID)).thenReturn(Flowable.just(correctResult));
        testSubscriber.assertNotSubscribed();

        getUserInfo.process(FAKE_PERSON_ID).subscribe(testSubscriber);
        Mockito.verify(mockPeopleDataSource, Mockito.times(1)).loadPersonInfo(FAKE_PERSON_ID);
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.success(correctResult));
        testSubscriber.assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockPeopleDataSource.loadPersonInfo(FAKE_PERSON_ID)).thenReturn(Flowable.error(fakeThrowable));
        testSubscriber.assertNotSubscribed();

        getUserInfo.process(FAKE_PERSON_ID).subscribe(testSubscriber);
        Mockito.verify(mockPeopleDataSource, Mockito.times(1)).loadPersonInfo(FAKE_PERSON_ID);
        testScheduler.triggerActions();

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(SubmitUiModel.inProgress(), SubmitUiModel.fail(fakeThrowable));
        testSubscriber.assertComplete();
    }
}