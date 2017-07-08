package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.entity.auth.LoginDataEntityImpl;
import com.github.chojmi.inspirations.data.entity.people.UserEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.service.AuthTestService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.data.source.utils.TestAndroidScheduler;
import com.github.chojmi.inspirations.domain.entity.people.UserEntity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemoteAuthTestDataSourceTest {
    private final Map<String, String> fakeQueryMap = new HashMap<>();
    @Mock private RemoteQueryProducer mockRemoteQueryProducer;
    @Mock private AuthTestService mockAuthTestService;
    private TestSubscriber<UserEntity> testSubscriber;

    private RemoteAuthTestDataSource remoteAuthTestDataSource;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() {
        this.remoteAuthTestDataSource = new RemoteAuthTestDataSource(mockAuthTestService, mockRemoteQueryProducer);
        this.testSubscriber = new TestSubscriber();
    }

    @Test
    public void loadLoginDataHappyCase() {
        UserEntityImpl result = Mockito.mock(UserEntityImpl.class);
        LoginDataEntityImpl loginDataEntity = Mockito.mock(LoginDataEntityImpl.class);
        when(mockRemoteQueryProducer.produceLoadLoginData()).thenReturn(fakeQueryMap);
        when(mockAuthTestService.loadLoginData(fakeQueryMap)).thenReturn(Flowable.just(loginDataEntity));
        when(loginDataEntity.getUser()).thenReturn(result);

        remoteAuthTestDataSource.getLoginData().subscribe(testSubscriber);

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(result);
        testSubscriber.assertComplete();
    }
}